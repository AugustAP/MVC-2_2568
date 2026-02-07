package Model;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Database - จัดการข้อมูลด้วย CSV File
 */
public class Database {
    private static final String DATA_DIR = "data/";
    private static final String CLAIMANTS_FILE = DATA_DIR + "claimants.csv";
    private static final String CLAIMS_FILE = DATA_DIR + "claims.csv";
    private static final String POLICIES_FILE = DATA_DIR + "policies.csv";
    private static final String COMPENSATIONS_FILE = DATA_DIR + "compensations.csv";

    public Database() {
        initializeDataDirectory();
        initializeDefaultData();
    }

    private void initializeDataDirectory() {
        File dir = new File(DATA_DIR);
        if (!dir.exists()) {
            dir.mkdirs();
        }
    }

    private void initializeDefaultData() {
        // สร้างข้อมูลเริ่มต้นถ้ายังไม่มี
        if (!new File(CLAIMANTS_FILE).exists()) {
            saveClaimants(new ArrayList<>());
        }
        if (!new File(CLAIMS_FILE).exists()) {
            saveClaims(new ArrayList<>());
        }
        if (!new File(POLICIES_FILE).exists()) {
            List<Policy> defaultPolicies = new ArrayList<>();
            defaultPolicies.add(new Policy("P001", 20000, "รายได้ทั่วไป"));
            defaultPolicies.add(new Policy("P002", 6500, "รายได้น้อย"));
            defaultPolicies.add(new Policy("P003", 20000, "รายได้สูง"));
            savePolicies(defaultPolicies);
        }
        if (!new File(COMPENSATIONS_FILE).exists()) {
            saveCompensations(new ArrayList<>());
        }
    }

    // Claimants CRUD
    public List<Claimant> loadClaimants() {
        List<Claimant> claimants = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(CLAIMANTS_FILE))) {
            String line;
            boolean isFirstLine = true;
            while ((line = br.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false; // Skip header
                    continue;
                }
                if (line.trim().isEmpty())
                    continue;

                String[] parts = line.split(",");
                // Expecting 7 parts: ID, First, Last, Income, Type, Username, Password
                if (parts.length >= 7) {
                    Claimant claimant = new Claimant(
                            parts[0].trim(),
                            parts[1].trim(),
                            parts[2].trim(),
                            Double.parseDouble(parts[3].trim()),
                            parts[4].trim(),
                            parts[5].trim(),
                            parts[6].trim());
                    claimants.add(claimant);
                } else if (parts.length >= 5) {
                    // Old format support (auto-migrate with empty user/pass or skip?)
                    // Let's create with "migrated_user" + id
                    Claimant claimant = new Claimant(
                            parts[0].trim(),
                            parts[1].trim(),
                            parts[2].trim(),
                            Double.parseDouble(parts[3].trim()),
                            parts[4].trim(),
                            "" + parts[0].trim(),
                            "1234" // Default pass
                    );
                    claimants.add(claimant);
                }
            }
        } catch (IOException e) {
            // File doesn't exist or empty, return empty list
        }
        return claimants;
    }

    public void saveClaimants(List<Claimant> claimants) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(CLAIMANTS_FILE))) {
            // Write header
            bw.write("claimantId,firstName,lastName,monthlyIncome,claimantType,username,password");
            bw.newLine();

            // Write data
            for (Claimant c : claimants) {
                bw.write(String.format("%s,%s,%s,%.2f,%s,%s,%s",
                        c.getClaimantId(),
                        c.getFirstName(),
                        c.getLastName(),
                        c.getMonthlyIncome(),
                        c.getClaimantType(),
                        c.getUsername() == null ? "" : c.getUsername(),
                        c.getPassword() == null ? "" : c.getPassword()));
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Claimant authenticateClaimant(String username, String password) {
        List<Claimant> claimants = loadClaimants();
        for (Claimant c : claimants) {
            if (c.getUsername() != null && c.getUsername().equals(username) &&
                    c.getPassword() != null && c.getPassword().equals(password)) {
                return c;
            }
        }
        return null; // Not found or wrong password
    }

    public boolean isUsernameTaken(String username) {
        List<Claimant> claimants = loadClaimants();
        for (Claimant c : claimants) {
            if (c.getUsername() != null && c.getUsername().equalsIgnoreCase(username)) {
                return true;
            }
        }
        return false;
    }

    public void addClaimant(Claimant claimant) {
        List<Claimant> claimants = loadClaimants();
        claimants.add(claimant);
        saveClaimants(claimants);
    }

    public Claimant getClaimantById(String id) {
        List<Claimant> claimants = loadClaimants();
        for (Claimant c : claimants) {
            if (c.getClaimantId().equals(id)) {
                return c;
            }
        }
        return null;
    }

    public Claimant getClaimantByName(String firstName, String lastName) {
        List<Claimant> claimants = loadClaimants();
        for (Claimant c : claimants) {
            if (c.getFirstName().trim().equalsIgnoreCase(firstName.trim()) &&
                    c.getLastName().trim().equalsIgnoreCase(lastName.trim())) {
                return c;
            }
        }
        return null;
    }

    public void updateClaimant(Claimant updatedClaimant) {
        List<Claimant> claimants = loadClaimants();
        boolean found = false;
        for (int i = 0; i < claimants.size(); i++) {
            if (claimants.get(i).getClaimantId().equals(updatedClaimant.getClaimantId())) {
                claimants.set(i, updatedClaimant);
                found = true;
                break;
            }
        }
        if (found) {
            saveClaimants(claimants);
        } else {
            addClaimant(updatedClaimant);
        }
    }

    // Claims CRUD
    public List<Claim> loadClaims() {
        List<Claim> claims = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(CLAIMS_FILE))) {
            String line;
            boolean isFirstLine = true;
            while ((line = br.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false; // Skip header
                    continue;
                }
                if (line.trim().isEmpty())
                    continue;

                String[] parts = line.split(",");
                if (parts.length >= 5) {
                    String claimId = parts[0].trim();
                    String claimantId = parts[1].trim();
                    LocalDate submissionDate = LocalDate.parse(parts[2].trim());
                    String status = parts[3].trim();
                    double monthlyIncome = Double.parseDouble(parts[4].trim());
                    String claimType = parts.length > 5 ? parts[5].trim() : "ทั่วไป";

                    Claim claim;
                    if (claimType.equals("รายได้น้อย")) {
                        claim = new LowIncomeClaim(claimId, claimantId, monthlyIncome);
                    } else if (claimType.equals("รายได้สูง")) {
                        claim = new HighIncomeClaim(claimId, claimantId, monthlyIncome);
                    } else {
                        claim = new Claim(claimId, claimantId, monthlyIncome);
                    }
                    claim.setSubmissionDate(submissionDate);
                    claim.setStatus(status);
                    claims.add(claim);
                }
            }
        } catch (IOException e) {
            // File doesn't exist or empty, return empty list
        }
        return claims;
    }

    public void saveClaims(List<Claim> claims) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(CLAIMS_FILE))) {
            // Write header
            bw.write("claimId,claimantId,submissionDate,status,monthlyIncome,claimType");
            bw.newLine();

            // Write data
            for (Claim c : claims) {
                bw.write(String.format("%s,%s,%s,%s,%.2f,%s",
                        c.getClaimId(),
                        c.getClaimantId(),
                        c.getSubmissionDate().toString(),
                        c.getStatus(),
                        c.getMonthlyIncome(),
                        c.getClaimType()));
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addClaim(Claim claim) {
        List<Claim> claims = loadClaims();
        claims.add(claim);
        saveClaims(claims);
    }

    public Claim getClaimById(String id) {
        List<Claim> claims = loadClaims();
        for (Claim c : claims) {
            if (c.getClaimId().equals(id)) {
                return c;
            }
        }
        return null;
    }

    // Policies CRUD
    public List<Policy> loadPolicies() {
        List<Policy> policies = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(POLICIES_FILE))) {
            String line;
            boolean isFirstLine = true;
            while ((line = br.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false; // Skip header
                    continue;
                }
                if (line.trim().isEmpty())
                    continue;

                String[] parts = line.split(",");
                if (parts.length >= 3) {
                    Policy policy = new Policy(
                            parts[0].trim(),
                            Double.parseDouble(parts[1].trim()),
                            parts[2].trim());
                    policies.add(policy);
                }
            }
        } catch (IOException e) {
            // File doesn't exist or empty, return empty list
        }
        return policies;
    }

    public void savePolicies(List<Policy> policies) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(POLICIES_FILE))) {
            // Write header
            bw.write("policyId,maxCompensation,incomeCondition");
            bw.newLine();

            // Write data
            for (Policy p : policies) {
                bw.write(String.format("%s,%.2f,%s",
                        p.getPolicyId(),
                        p.getMaxCompensation(),
                        p.getIncomeCondition()));
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Compensations CRUD
    public List<Compensation> loadCompensations() {
        List<Compensation> compensations = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(COMPENSATIONS_FILE))) {
            String line;
            boolean isFirstLine = true;
            while ((line = br.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false; // Skip header
                    continue;
                }
                if (line.trim().isEmpty())
                    continue;

                String[] parts = line.split(",");
                if (parts.length >= 3) {
                    String claimId = parts[0].trim();
                    double compensationAmount = Double.parseDouble(parts[1].trim());
                    LocalDate calculationDate = LocalDate.parse(parts[2].trim());

                    Compensation compensation = new Compensation(claimId, compensationAmount);
                    compensation.setCalculationDate(calculationDate);
                    compensations.add(compensation);
                }
            }
        } catch (IOException e) {
            // File doesn't exist or empty, return empty list
        }
        return compensations;
    }

    public void saveCompensations(List<Compensation> compensations) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(COMPENSATIONS_FILE))) {
            // Write header
            bw.write("claimId,compensationAmount,calculationDate");
            bw.newLine();

            // Write data
            for (Compensation c : compensations) {
                bw.write(String.format("%s,%.2f,%s",
                        c.getClaimId(),
                        c.getCompensationAmount(),
                        c.getCalculationDate().toString()));
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addCompensation(Compensation compensation) {
        List<Compensation> compensations = loadCompensations();
        compensations.add(compensation);
        saveCompensations(compensations);
    }
}
