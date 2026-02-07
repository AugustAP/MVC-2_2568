package View;

import javax.swing.*;
import java.awt.*;

/**
 * NewClaimView - หน้ายื่นคำขอเยียวยา
 * ใช้สำหรับสร้างคำขอใหม่ โดยต้องแสดงเงินเยียวยาที่คำนวณให้เห็น
 */
public class NewClaimView extends JFrame {
    private JTextField txtFirstName;
    private JTextField txtLastName;
    private JTextField txtMonthlyIncome;
    private JLabel lblClaimType;
    private JLabel lblCompensationAmount;
    private JButton btnCalculate;
    private JButton btnSubmit;
    private JButton btnCancel;

    public NewClaimView() {
        initComponents();
    }

    private void initComponents() {
        setTitle("ระบบคำนวณเงินเยียวยาของรัฐ - ยื่นคำขอเยียวยา");
        setSize(700, 650);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Main Panel
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(Color.WHITE);

        // Header Panel
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(Color.WHITE);
        JLabel titleLabel = new JLabel("ยื่นคำขอเยียวยาใหม่");
        titleLabel.setFont(new Font("Tahoma", Font.BOLD, 24));
        titleLabel.setForeground(new Color(25, 118, 210));
        headerPanel.add(titleLabel);

        // Form Panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);

        Font labelFont = new Font("Tahoma", Font.BOLD, 16);
        Font fieldFont = new Font("Tahoma", Font.PLAIN, 14);

        // First Name
        gbc.gridx = 0;
        gbc.gridy = 0;
        JLabel lblFirstName = new JLabel("ชื่อ:");
        lblFirstName.setFont(labelFont);
        lblFirstName.setForeground(Color.BLACK);
        formPanel.add(lblFirstName, gbc);

        gbc.gridx = 1;
        txtFirstName = new JTextField(20);
        txtFirstName.setFont(fieldFont);
        txtFirstName.setForeground(Color.BLACK);
        txtFirstName.setPreferredSize(new Dimension(300, 35));
        formPanel.add(txtFirstName, gbc);

        // Last Name
        gbc.gridx = 0;
        gbc.gridy = 1;
        JLabel lblLastName = new JLabel("นามสกุล:");
        lblLastName.setFont(labelFont);
        lblLastName.setForeground(Color.BLACK);
        formPanel.add(lblLastName, gbc);

        gbc.gridx = 1;
        txtLastName = new JTextField(20);
        txtLastName.setFont(fieldFont);
        txtLastName.setForeground(Color.BLACK);
        txtLastName.setPreferredSize(new Dimension(300, 35));
        formPanel.add(txtLastName, gbc);

        // Monthly Income
        gbc.gridx = 0;
        gbc.gridy = 2;
        JLabel lblMonthlyIncome = new JLabel("รายได้ต่อเดือน (บาท):");
        lblMonthlyIncome.setFont(labelFont);
        lblMonthlyIncome.setForeground(Color.BLACK);
        formPanel.add(lblMonthlyIncome, gbc);

        gbc.gridx = 1;
        txtMonthlyIncome = new JTextField(20);
        txtMonthlyIncome.setFont(fieldFont);
        txtMonthlyIncome.setForeground(Color.BLACK);
        txtMonthlyIncome.setPreferredSize(new Dimension(300, 35));
        formPanel.add(txtMonthlyIncome, gbc);

        // Calculate Button
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        btnCalculate = new JButton("คำนวณเงินเยียวยา");
        btnCalculate.setFont(new Font("Tahoma", Font.BOLD, 16));
        btnCalculate.setBackground(new Color(255, 152, 0));
        btnCalculate.setForeground(Color.BLACK); // เปลี่ยนเป็นสีดำ
        btnCalculate.setFocusPainted(false);
        btnCalculate.setPreferredSize(new Dimension(250, 45));
        btnCalculate.setCursor(new Cursor(Cursor.HAND_CURSOR));
        formPanel.add(btnCalculate, gbc);

        // Result Panel
        JPanel resultPanel = new JPanel(new GridBagLayout());
        resultPanel.setBackground(new Color(232, 245, 233));
        resultPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(76, 175, 80), 2),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)));

        GridBagConstraints resultGbc = new GridBagConstraints();
        resultGbc.insets = new Insets(5, 10, 5, 10);

        // Claim Type
        resultGbc.gridx = 0;
        resultGbc.gridy = 0;
        JLabel lblClaimTypeLabel = new JLabel("ประเภทผู้ขอเยียวยา:");
        lblClaimTypeLabel.setFont(labelFont);
        lblClaimTypeLabel.setForeground(Color.BLACK);
        resultPanel.add(lblClaimTypeLabel, resultGbc);

        resultGbc.gridx = 1;
        lblClaimType = new JLabel("-");
        lblClaimType.setFont(new Font("Tahoma", Font.BOLD, 18));
        lblClaimType.setForeground(new Color(56, 142, 60));
        resultPanel.add(lblClaimType, resultGbc);

        // Compensation Amount
        resultGbc.gridx = 0;
        resultGbc.gridy = 1;
        JLabel lblCompensationLabel = new JLabel("จำนวนเงินเยียวยา:");
        lblCompensationLabel.setFont(labelFont);
        lblCompensationLabel.setForeground(Color.BLACK);
        resultPanel.add(lblCompensationLabel, resultGbc);

        resultGbc.gridx = 1;
        lblCompensationAmount = new JLabel("-");
        lblCompensationAmount.setFont(new Font("Tahoma", Font.BOLD, 20));
        lblCompensationAmount.setForeground(new Color(56, 142, 60));
        resultPanel.add(lblCompensationAmount, resultGbc);

        // Button Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.setBackground(Color.WHITE);

        btnSubmit = new JButton("ยืนยันการยื่นคำขอ");
        btnSubmit.setFont(new Font("Tahoma", Font.BOLD, 16));
        btnSubmit.setBackground(new Color(76, 175, 80));
        btnSubmit.setForeground(Color.BLACK); // เปลี่ยนเป็นสีดำ
        btnSubmit.setFocusPainted(false);
        btnSubmit.setPreferredSize(new Dimension(200, 45));
        btnSubmit.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnSubmit.setEnabled(false);

        btnCancel = new JButton("ยกเลิก");
        btnCancel.setFont(new Font("Tahoma", Font.BOLD, 16));
        btnCancel.setBackground(new Color(244, 67, 54));
        btnCancel.setForeground(Color.BLACK); // เปลี่ยนเป็นสีดำ
        btnCancel.setFocusPainted(false);
        btnCancel.setPreferredSize(new Dimension(150, 45));
        btnCancel.setCursor(new Cursor(Cursor.HAND_CURSOR));

        buttonPanel.add(btnSubmit);
        buttonPanel.add(btnCancel);

        // Add all panels to main panel
        mainPanel.add(headerPanel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new BorderLayout(10, 10));
        centerPanel.setBackground(Color.WHITE);
        centerPanel.add(formPanel, BorderLayout.NORTH);
        centerPanel.add(resultPanel, BorderLayout.CENTER);

        mainPanel.add(centerPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    // Getters for components
    // Getters for components
    public JTextField getTxtFirstName() {
        return txtFirstName;
    }

    public JTextField getTxtLastName() {
        return txtLastName;
    }

    public JTextField getTxtMonthlyIncome() {
        return txtMonthlyIncome;
    }

    public JLabel getLblClaimType() {
        return lblClaimType;
    }

    public JLabel getLblCompensationAmount() {
        return lblCompensationAmount;
    }

    public JButton getBtnCalculate() {
        return btnCalculate;
    }

    public JButton getBtnSubmit() {
        return btnSubmit;
    }

    public JButton getBtnCancel() {
        return btnCancel;
    }

    public void clearForm() {
        // txtFirstName.setText(""); // Don't clear user data
        // txtLastName.setText(""); // Don't clear user data
        txtMonthlyIncome.setText("");
        lblClaimType.setText("-");
        lblCompensationAmount.setText("-");
        btnSubmit.setEnabled(false);
    }

    public void setClaimantData(String firstName, String lastName) {
        txtFirstName.setText(firstName);
        txtLastName.setText(lastName);
        txtFirstName.setEditable(false);
        txtLastName.setEditable(false);
        txtFirstName.setBackground(new Color(245, 245, 245));
        txtLastName.setBackground(new Color(245, 245, 245));
    }
}
