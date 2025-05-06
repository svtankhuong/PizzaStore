import javax.swing.*;
import java.awt.*;

public class PhieuNhapMain {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Quản Lý Phiếu Nhập");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(1200, 750);
            frame.setLocationRelativeTo(null);

            PhieuNhapGUI phieuNhapGUI = new PhieuNhapGUI();
            frame.add(phieuNhapGUI, BorderLayout.CENTER);

            frame.setVisible(true);
        });
    }
}