package View;

import javax.swing.*;
import java.awt.*;

public class LoginView extends JFrame {
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JButton btnLogin;
    private JButton btnRegister;

    public LoginView() {
        initComponents();
    }

    private void initComponents() {
        setTitle("ระบบคำนวณเงินเยียวยาของรัฐ - เข้าสู่ระบบ");
        setSize(450, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(Color.WHITE);

        // Main Panel
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        Font labelFont = new Font("Tahoma", Font.BOLD, 16);
        Font fieldFont = new Font("Tahoma", Font.PLAIN, 14);
        Font btnFont = new Font("Tahoma", Font.BOLD, 16);

        // Title
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        JLabel lblTitle = new JLabel("เข้าสู่ระบบ", JLabel.CENTER);
        lblTitle.setFont(new Font("Tahoma", Font.BOLD, 28));
        lblTitle.setForeground(new Color(25, 118, 210));
        mainPanel.add(lblTitle, gbc);

        // Subtitle
        gbc.gridy = 1;
        JLabel lblSub = new JLabel("กรุณากรอกข้อมูลเพื่อเข้าใช้งาน", JLabel.CENTER);
        lblSub.setFont(new Font("Tahoma", Font.PLAIN, 16));
        lblSub.setForeground(Color.BLACK); // สีดำ
        mainPanel.add(lblSub, gbc);

        // Username
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        JLabel lblUser = new JLabel("ชื่อผู้ใช้ (Username):");
        lblUser.setFont(labelFont);
        lblUser.setForeground(Color.BLACK); // สีดำ
        mainPanel.add(lblUser, gbc);

        gbc.gridx = 1;
        txtUsername = new JTextField(15);
        txtUsername.setFont(fieldFont);
        txtUsername.setForeground(Color.BLACK); // สีดำ
        mainPanel.add(txtUsername, gbc);

        // Password
        gbc.gridy = 3;
        gbc.gridx = 0;
        JLabel lblPass = new JLabel("รหัสผ่าน (Password):");
        lblPass.setFont(labelFont);
        lblPass.setForeground(Color.BLACK); // สีดำ
        mainPanel.add(lblPass, gbc);

        gbc.gridx = 1;
        txtPassword = new JPasswordField(15);
        txtPassword.setFont(fieldFont);
        txtPassword.setForeground(Color.BLACK); // สีดำ
        mainPanel.add(txtPassword, gbc);

        // Login Button
        gbc.gridy = 4;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        btnLogin = new JButton("เข้าสู่ระบบ");
        btnLogin.setFont(btnFont);
        btnLogin.setBackground(new Color(25, 118, 210));
        btnLogin.setForeground(Color.BLACK); // เปลี่ยนเป็นสีดำ
        btnLogin.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnLogin.setPreferredSize(new Dimension(200, 40));
        mainPanel.add(btnLogin, gbc);

        // Separator
        gbc.gridy = 5;
        JLabel lblOr = new JLabel("- หรือ -", JLabel.CENTER);
        lblOr.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblOr.setForeground(Color.GRAY);
        mainPanel.add(lblOr, gbc);

        // Register Button for Claimant
        gbc.gridy = 6;
        btnRegister = new JButton("ลงทะเบียนประชาชนใหม่");
        btnRegister.setFont(btnFont);
        btnRegister.setBackground(new Color(76, 175, 80));
        btnRegister.setForeground(Color.BLACK); // เปลี่ยนเป็นสีดำ
        btnRegister.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnRegister.setPreferredSize(new Dimension(200, 40));
        mainPanel.add(btnRegister, gbc);

        add(mainPanel);
    }

    public String getUsername() {
        return txtUsername.getText().trim();
    }

    public String getPassword() {
        return new String(txtPassword.getPassword());
    }

    public JButton getBtnLogin() {
        return btnLogin;
    }

    public JButton getBtnRegister() {
        return btnRegister;
    }

    public void clearFields() {
        txtUsername.setText("");
        txtPassword.setText("");
    }
}
