package Controller;

import Model.*;
import View.ClaimListView;
import View.NewClaimView;

import javax.swing.*;

import java.util.List;
import java.util.Random;

/**
 * ClaimController - ควบคุมการทำงานระหว่าง Model และ View
 */
public class ClaimController {
    private Database database;
    private ClaimListView claimListView;
    private NewClaimView newClaimView;
    private UserRole currentUserRole;

    public ClaimController() {
        database = new Database();
        AuthenticationController authController = new AuthenticationController(database, this::onLoginSuccess);
        authController.start();
    }

    private Claimant currentClaimant; // For Citizen

    private void onLoginSuccess(UserRole role, Claimant claimant) {
        this.currentUserRole = role;
        if (role == UserRole.OFFICER) {
            openClaimListView();
        } else if (role == UserRole.CITIZEN) {
            this.currentClaimant = claimant;
            openNewClaimView(true);
        }
    }

    private void openClaimListView() {
        claimListView = new ClaimListView();
        setupClaimListViewListeners();
        refreshClaimList();
        claimListView.setVisible(true);
    }

    private void setupClaimListViewListeners() {
        // Only Officer can see "New Claim" button in List View? Or both?
        // Ideally Officer sees all claims and can add manually too.
        claimListView.getBtnNewClaim().addActionListener(e -> openNewClaimView(false));
        claimListView.getBtnRefresh().addActionListener(e -> refreshClaimList());

        // Add Logout button logic if needed (simple dispose)
    }

    private void openNewClaimView(boolean isCitizenMode) {
        newClaimView = new NewClaimView();
        setupNewClaimViewListeners();

        if (isCitizenMode) {
            newClaimView.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            // Pre-fill data
            if (currentClaimant != null) {
                newClaimView.setClaimantData(currentClaimant.getFirstName(), currentClaimant.getLastName());
            }
        }

        newClaimView.setVisible(true);
    }

    private void setupNewClaimViewListeners() {
        newClaimView.getBtnCalculate().addActionListener(e -> calculateCompensation());
        newClaimView.getBtnSubmit().addActionListener(e -> submitClaim());
        newClaimView.getBtnCancel().addActionListener(e -> {
            newClaimView.dispose();
            if (currentUserRole == UserRole.OFFICER) {
                // Return to list if Officer
                // ClaimListView is already open in background or we re-open it?
                // Currently ClaimListView is creating NewClaimView as a child window
                // effectively
            } else if (currentUserRole == UserRole.CITIZEN) {
                // Return to Login or Exit if Citizen
                System.exit(0);
            }
        });
    }

    private void calculateCompensation() {
        try {
            // Get input values
            String firstName = newClaimView.getTxtFirstName().getText().trim();
            String lastName = newClaimView.getTxtLastName().getText().trim();
            String incomeStr = newClaimView.getTxtMonthlyIncome().getText().trim();

            // Validate inputs
            if (firstName.isEmpty() || lastName.isEmpty() || incomeStr.isEmpty()) {
                JOptionPane.showMessageDialog(newClaimView,
                        "กรุณากรอกข้อมูลให้ครบถ้วน",
                        "ข้อมูลไม่ครบ",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            double monthlyIncome = Double.parseDouble(incomeStr);

            if (monthlyIncome < 0) {
                JOptionPane.showMessageDialog(newClaimView,
                        "รายได้ต่อเดือนต้องมากกว่าหรือเท่ากับ 0",
                        "ข้อมูลไม่ถูกต้อง",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Determine claim type and calculate compensation
            Claim claim = createClaimByIncome(monthlyIncome);
            double compensation = claim.calculateCompensation();

            // Display results
            newClaimView.getLblClaimType().setText(claim.getClaimType());
            newClaimView.getLblCompensationAmount().setText(String.format("%.2f บาท", compensation));

            // Enable submit button
            newClaimView.getBtnSubmit().setEnabled(true);

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(newClaimView,
                    "กรุณากรอกรายได้เป็นตัวเลขเท่านั้น",
                    "ข้อมูลไม่ถูกต้อง",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void submitClaim() {
        try {
            // Get inputs (Income only)
            double monthlyIncome = Double.parseDouble(newClaimView.getTxtMonthlyIncome().getText().trim());

            // Check if claimant exists (Should exist for Citizen)
            Claimant claimantToSave;
            if (currentUserRole == UserRole.CITIZEN) {
                // Use current logged in user
                currentClaimant.setMonthlyIncome(monthlyIncome);
                // Update Type logic
                String newItemType = (monthlyIncome < 6500) ? "รายได้น้อย"
                        : (monthlyIncome >= 50000 ? "รายได้สูง" : "ทั่วไป");
                currentClaimant.setClaimantType(newItemType);

                database.updateClaimant(currentClaimant);
                claimantToSave = currentClaimant;
            } else {
                // Officer Mode
                String f = newClaimView.getTxtFirstName().getText().trim();
                String l = newClaimView.getTxtLastName().getText().trim();

                Claimant existing = database.getClaimantByName(f, l);
                if (existing != null) {
                    existing.setMonthlyIncome(monthlyIncome);
                    String newItemType = (monthlyIncome < 6500) ? "รายได้น้อย"
                            : (monthlyIncome >= 50000 ? "รายได้สูง" : "ทั่วไป");
                    existing.setClaimantType(newItemType);
                    database.updateClaimant(existing);
                    claimantToSave = existing;
                } else {
                    String id = generateClaimantId();
                    String newItemType = (monthlyIncome < 6500) ? "รายได้น้อย"
                            : (monthlyIncome >= 50000 ? "รายได้สูง" : "ทั่วไป");
                    claimantToSave = new Claimant(id, f, l, monthlyIncome, newItemType, "" + id, "1234");
                    database.addClaimant(claimantToSave);
                }
            }

            String claimantId = claimantToSave.getClaimantId();
            String claimId = generateClaimId();

            // Create Claim object
            Claim claim;
            if (monthlyIncome < 6500) {
                claim = new LowIncomeClaim(claimId, claimantId, monthlyIncome);
            } else if (monthlyIncome >= 50000) {
                claim = new HighIncomeClaim(claimId, claimantId, monthlyIncome);
            } else {
                claim = new Claim(claimId, claimantId, monthlyIncome);
            }

            // Save claim
            double compensationAmount = claim.calculateCompensation();
            claim.setStatus("อนุมัติ");
            database.addClaim(claim);

            // Save compensation
            Compensation compensation = new Compensation(claimId, compensationAmount);
            database.addCompensation(compensation);

            // Show success message with fixed font
            String message = String.format(
                    "ยื่นคำขอเยียวยาสำเร็จ!\n\nรหัสคำขอ: %s\nรหัสผู้ขอ: %s\nจำนวนเงินเยียวยา: %.2f บาท",
                    claimId, claimantId, compensationAmount);
            showMessage(newClaimView, message, "สำเร็จ", JOptionPane.INFORMATION_MESSAGE);

            // Close new claim view
            newClaimView.dispose();

            // Refresh list or Open List View
            if (currentUserRole == UserRole.CITIZEN) {
                // CHANGE: Open ClaimListView instead of exit
                openClaimListView();
            } else {
                refreshClaimList();
            }

        } catch (Exception ex) {
            showMessage(newClaimView, "Error: " + ex.getMessage(), "ข้อผิดพลาด", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    // Helper method to show JOptionPane with Thai-supporting font
    private void showMessage(java.awt.Component parent, String message, String title, int messageType) {
        JTextArea textArea = new JTextArea(message);
        textArea.setFont(new java.awt.Font("Tahoma", java.awt.Font.PLAIN, 18));
        textArea.setEditable(false);
        textArea.setOpaque(false);
        textArea.setBackground(new java.awt.Color(0, 0, 0, 0));
        JOptionPane.showMessageDialog(parent, textArea, title, messageType);
    }

    private Claim createClaimByIncome(double monthlyIncome) {
        if (monthlyIncome < 6500) {
            return new LowIncomeClaim("", "", monthlyIncome);
        } else if (monthlyIncome >= 50000) {
            return new HighIncomeClaim("", "", monthlyIncome);
        } else {
            return new Claim("", "", monthlyIncome);
        }
    }

    private String generateClaimantId() {
        Random random = new Random();
        // C + 5 digits
        StringBuilder sb = new StringBuilder("C");
        for (int i = 0; i < 5; i++) {
            sb.append(random.nextInt(10));
        }
        return sb.toString();
    }

    private String generateClaimId() {
        Random random = new Random();
        // First digit: 1-9
        int firstDigit = random.nextInt(9) + 1;
        // Remaining 7 digits: 0-9
        StringBuilder claimId = new StringBuilder(String.valueOf(firstDigit));
        for (int i = 0; i < 7; i++) {
            claimId.append(random.nextInt(10));
        }
        return claimId.toString();
    }

    private void refreshClaimList() {
        List<Claim> claims = database.loadClaims();
        claimListView.updateClaimList(claims);
    }
}
