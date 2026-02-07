import Controller.ClaimController;
import javax.swing.*;

/**
 * Main - จุดเริ่มต้นของโปรแกรม
 * ระบบคำนวณเงินเยียวยาของรัฐ
 */
public class Main {
    public static void main(String[] args) {
        // Set Look and Feel to system default
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Run application on Event Dispatch Thread
        SwingUtilities.invokeLater(() -> {
            new ClaimController();
        });
    }
}
