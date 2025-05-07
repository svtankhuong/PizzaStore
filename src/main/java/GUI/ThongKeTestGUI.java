package GUI;

import javax.swing.*;
import java.awt.*;

public class ThongKeTestGUI extends JFrame {

    public ThongKeTestGUI() {
        initComponents();
    }

    private void initComponents() {
        setTitle("Thống Kê Doanh Thu và Chi Phí");
        setSize(1200, 750);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Thêm ThongKeGUI (là JPanel) vào JFrame
        ThongKeGUI thongKePanel = new ThongKeGUI();
        add(thongKePanel, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ThongKeTestGUI testFrame = new ThongKeTestGUI();
            testFrame.setVisible(true);
        });
    }
}