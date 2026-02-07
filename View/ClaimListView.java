package View;

import Model.Claim;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/**
 * ClaimListView - หน้ารายการคำขอเยียวยา
 * แสดงคำขอทั้งหมดและสถานะปัจจุบัน
 */
public class ClaimListView extends JFrame {
    private JTable claimTable;
    private DefaultTableModel tableModel;
    private JButton btnNewClaim;
    private JButton btnRefresh;

    public ClaimListView() {
        initComponents();
    }

    private void initComponents() {
        setTitle("ระบบคำนวณเงินเยียวยาของรัฐ - รายการคำขอเยียวยา");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Main Panel
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Header Panel
        JPanel headerPanel = new JPanel(new BorderLayout());
        JLabel titleLabel = new JLabel("รายการคำขอเยียวยาทั้งหมด", JLabel.CENTER);
        titleLabel.setFont(new Font("Tahoma", Font.BOLD, 24));
        titleLabel.setForeground(new Color(25, 118, 210));
        headerPanel.add(titleLabel, BorderLayout.CENTER);

        // Table
        String[] columnNames = { "รหัสคำขอ", "รหัสผู้ขอ", "วันที่ยื่นคำขอ", "ประเภท", "รายได้/เดือน", "เงินเยียวยา",
                "สถานะ" };
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        claimTable = new JTable(tableModel);
        claimTable.setFont(new Font("Tahoma", Font.PLAIN, 14));
        claimTable.setRowHeight(30);
        claimTable.setForeground(Color.BLACK); // ตัวอักษรในตารางเป็นสีดำ
        claimTable.setBackground(Color.WHITE); // พื้นหลังตารางเป็นสีขาว
        claimTable.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 16));
        claimTable.getTableHeader().setBackground(new Color(25, 118, 210));
        claimTable.getTableHeader().setForeground(Color.BLACK); // เปลี่ยนเป็นสีดำ

        JScrollPane scrollPane = new JScrollPane(claimTable);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));

        // Button Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.setBackground(Color.WHITE);

        btnNewClaim = new JButton("ยื่นคำขอเยียวยาใหม่");
        btnNewClaim.setFont(new Font("Tahoma", Font.BOLD, 16));
        btnNewClaim.setBackground(new Color(76, 175, 80));
        btnNewClaim.setForeground(Color.BLACK); // เปลี่ยนเป็นสีดำ
        btnNewClaim.setFocusPainted(false);
        btnNewClaim.setPreferredSize(new Dimension(200, 45));
        btnNewClaim.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btnRefresh = new JButton("รีเฟรช");
        btnRefresh.setFont(new Font("Tahoma", Font.BOLD, 16));
        btnRefresh.setBackground(new Color(33, 150, 243));
        btnRefresh.setForeground(Color.BLACK); // เปลี่ยนเป็นสีดำ
        btnRefresh.setFocusPainted(false);
        btnRefresh.setPreferredSize(new Dimension(150, 45));
        btnRefresh.setCursor(new Cursor(Cursor.HAND_CURSOR));

        buttonPanel.add(btnNewClaim);
        buttonPanel.add(btnRefresh);

        // Add components to main panel
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    public void updateClaimList(List<Claim> claims) {
        tableModel.setRowCount(0);
        for (Claim claim : claims) {
            Object[] row = {
                    claim.getClaimId(),
                    claim.getClaimantId(),
                    claim.getSubmissionDate().toString(),
                    claim.getClaimType(),
                    String.format("%.2f", claim.getMonthlyIncome()),
                    String.format("%.2f", claim.calculateCompensation()),
                    claim.getStatus()
            };
            tableModel.addRow(row);
        }
    }

    public JButton getBtnNewClaim() {
        return btnNewClaim;
    }

    public JButton getBtnRefresh() {
        return btnRefresh;
    }
}
