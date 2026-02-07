package Controller;

import Model.Claimant;
import Model.Database;
import Model.UserRole;
import View.LoginView;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class AuthenticationController {

    public interface AuthenticationCallback {
        void onLoginSuccess(UserRole role, Claimant claimant);
    }

    private Database database;
    private LoginView loginView;
    private AuthenticationCallback callback;

    public AuthenticationController(Database database, AuthenticationCallback callback) {
        this.database = database;
        this.callback = callback;
        this.loginView = new LoginView();
        setupListeners();
    }

    public void start() {
        loginView.setVisible(true);
    }

    private void setupListeners() {
        // Login Button
        loginView.getBtnLogin().addActionListener(e -> handleLogin());

        // Register Button
        loginView.getBtnRegister().addActionListener(e -> handleRegister());
    }

    private void handleLogin() {
        String user = loginView.getUsername();
        String pass = loginView.getPassword();

        if (user.isEmpty() || pass.isEmpty()) {
            JOptionPane.showMessageDialog(loginView, "กรุณากรอก Username และ Password");
            return;
        }

        // 1. Check Admin/Officer (Hardcoded)
        if (user.equals("admin") && pass.equals("admin")) {
            loginView.dispose();
            callback.onLoginSuccess(UserRole.OFFICER, null);
            return;
        }

        // 2. Check Database for Citizen
        Claimant claimant = database.authenticateClaimant(user, pass);
        if (claimant != null) {
            loginView.dispose();
            callback.onLoginSuccess(UserRole.CITIZEN, claimant);
        } else {
            JOptionPane.showMessageDialog(loginView, "ชื่อผู้ใช้หรือรหัสผ่านไม่ถูกต้อง", "เข้าสู่ระบบผิดพลาด",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handleRegister() {
        // Simple Register Dialog
        JPanel panel = new JPanel(new GridLayout(0, 1));
        JTextField txtUser = new JTextField();
        JTextField txtPass = new JPasswordField();
        JTextField txtFirst = new JTextField();
        JTextField txtLast = new JTextField();

        panel.add(new JLabel("ชื่อผู้ใช้ (Username):"));
        panel.add(txtUser);
        panel.add(new JLabel("รหัสผ่าน (Password):"));
        panel.add(txtPass);
        panel.add(new JLabel("ชื่อ (First Name):"));
        panel.add(txtFirst);
        panel.add(new JLabel("นามสกุล (Last Name):"));
        panel.add(txtLast);

        int result = JOptionPane.showConfirmDialog(loginView, panel, "ลงทะเบียนสมาชิกใหม่",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            String u = txtUser.getText().trim();
            String p = txtPass.getText().trim();
            String f = txtFirst.getText().trim();
            String l = txtLast.getText().trim();

            if (u.isEmpty() || p.isEmpty() || f.isEmpty() || l.isEmpty()) {
                JOptionPane.showMessageDialog(loginView, "กรุณากรอกข้อมูลให้ครบถ้วน");
                return;
            }

            if (database.isUsernameTaken(u)) {
                JOptionPane.showMessageDialog(loginView, "ชื่อผู้ใช้นี้มีอผู้ใช้งานแล้ว");
                return;
            }

            // Create new Claimant
            String id = generateClaimantId();
            Claimant newC = new Claimant(id, f, l, 0.0, "ทั่วไป", u, p);
            database.addClaimant(newC);

            JOptionPane.showMessageDialog(loginView, "ลงทะเบียนสำเร็จ! กรุณาเข้าสู่ระบบ");
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
}
