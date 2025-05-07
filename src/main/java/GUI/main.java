/*
package GUI;

import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.SwingUtilities;

public class main {
    public static void main(String[] args) {
        try {
            // Set Nimbus Look and Feel
            for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("FlatLaf Light".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
            
        } catch (Exception e) {
            // Nếu Nimbus không có, sẽ dùng LookAndFeel mặc định
            System.err.println("Nimbus LookAndFeel không khả dụng, dùng mặc định.");
        }
        
        // Khởi tạo giao diện trên luồng Event Dispatch Thread (EDT)
        SwingUtilities.invokeLater(() -> {
            Login a = new Login();
            a.setVisible(true);
        });
    }
}
*/