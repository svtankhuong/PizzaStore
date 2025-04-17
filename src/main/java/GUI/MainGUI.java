package GUI;

import MyCustom.ImagePanel;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 *
 * @author ADMIN
 */
public class MainGUI extends javax.swing.JFrame {
    private CardLayout cardLayout;
    private JPanel contentPanel;
    private ArrayList<JLabel> menuLabels;
    private final Color clMenuItem = new Color(255, 204, 153); // Màu mặc định
    private final Color clMenuItemSelected = new Color(255, 153, 102); // Màu khi chọn
    public MainGUI() {
        
        initComponents();
        initializeCardLayout();
        setupMenuEvents();
        java.awt.EventQueue.invokeLater(() -> {
            setLabelIcon(LblSale, "/ManagerUI/lblBanHang.png");
            setLabelIcon(LblStaffs, "/ManagerUI/lblNhanVien.png");
            setLabelIcon(LblNH, "/ManagerUI/lblNhapHang.png");
            setLabelIcon(LblCustomers, "/ManagerUI/lblKhachHang.png");
            setLabelIcon(LblProducts, "/ManagerUI/lblSanPham.png");
            setLabelIcon(LblTK, "/ManagerUI/lblThongKe.png");
        });
        jPanel4.setLayout(new java.awt.BorderLayout());
        jPanel4.add(contentPanel, java.awt.BorderLayout.CENTER);
    }
    
    private void setLabelIcon(javax.swing.JLabel label, String resourcePath) {
        ImageIcon icon = new ImageIcon(getClass().getResource(resourcePath));
        Image img = icon.getImage();
        int width = label.getWidth();
        int height = label.getHeight();
        Image scaledImg = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        label.setIcon(new ImageIcon(scaledImg));
    }
    private void initializeCardLayout() {
        // Khởi tạo CardLayout và contentPanel thay cho jPanel4
        cardLayout = new CardLayout();
        contentPanel = new JPanel(cardLayout);
        contentPanel.setPreferredSize(new Dimension(1250, 711));

        // Thêm các panel vào contentPanel
        JPanel salePanel = new JPanel(); // Panel Bán hàng (có thể thay bằng PnQuanLyBanHangGUI)
        salePanel.setBackground(Color.WHITE);
        salePanel.add(new JLabel("Panel Bán hàng", SwingConstants.CENTER));

        PnNhanVien3 staffPanel = new PnNhanVien3(); // Panel Nhân viên

        JPanel importPanel = new JPanel(); // Panel Nhập hàng
        importPanel.setBackground(Color.WHITE);
        importPanel.add(new JLabel("Panel Nhập hàng", SwingConstants.CENTER));

        JPanel customerPanel = new JPanel(); // Panel Khách hàng
        customerPanel.setBackground(Color.WHITE);
        customerPanel.add(new JLabel("Panel Khách hàng", SwingConstants.CENTER));

        JPanel productPanel = new JPanel(); // Panel Sản phẩm
        productPanel.setBackground(Color.WHITE);
        productPanel.add(new JLabel("Panel Sản phẩm", SwingConstants.CENTER));

        JPanel statsPanel = new JPanel(); // Panel Thống kê
        statsPanel.setBackground(Color.WHITE);
        statsPanel.add(new JLabel("Panel Thống kê", SwingConstants.CENTER));

        // Thêm các panel vào CardLayout với tên định danh
        contentPanel.add(salePanel, "sale");
        contentPanel.add(staffPanel, "staff");
        contentPanel.add(importPanel, "import");
        contentPanel.add(customerPanel, "customer");
        contentPanel.add(productPanel, "product");
        contentPanel.add(statsPanel, "stats");

        // Hiển thị panel Bán hàng mặc định
        cardLayout.show(contentPanel, "sale");
        LblSale.setBackground(clMenuItemSelected);
    }

    private void setupMenuEvents() {
        // Lưu các label menu vào danh sách để quản lý
        menuLabels = new ArrayList<>();
        menuLabels.add(LblSale);
        menuLabels.add(LblStaffs);
        menuLabels.add(LblNH);
        menuLabels.add(LblCustomers);
        menuLabels.add(LblProducts);
        menuLabels.add(LblTK);

        // Thêm sự kiện cho các label
        for (JLabel label : menuLabels) {
            label.setOpaque(true); // Cho phép hiển thị màu nền
            label.setBackground(clMenuItem);
            label.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            label.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    // Đặt lại màu nền của tất cả các label
                    for (JLabel lbl : menuLabels) {
                        lbl.setBackground(clMenuItem);
                    }
                    // Đặt màu nền cho label được chọn
                    label.setBackground(clMenuItemSelected);

                    // Hiển thị panel tương ứng
                    if (label == LblSale) {
                        cardLayout.show(contentPanel, "sale");
                    } else if (label == LblStaffs) {
                        cardLayout.show(contentPanel, "staff");
                    } else if (label == LblNH) {
                        cardLayout.show(contentPanel, "import");
                    } else if (label == LblCustomers) {
                        cardLayout.show(contentPanel, "customer");
                    } else if (label == LblProducts) {
                        cardLayout.show(contentPanel, "product");
                    } else if (label == LblTK) {
                        cardLayout.show(contentPanel, "stats");
                    }
                }

                @Override
                public void mouseEntered(MouseEvent e) {
                    if (!label.getBackground().equals(clMenuItemSelected)) {
                        label.setBackground(new Color(255, 180, 130)); // Màu hover
                    }
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    if (!label.getBackground().equals(clMenuItemSelected)) {
                        label.setBackground(clMenuItem);
                    }
                }
            });
        }
    }
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        PanelMain = new javax.swing.JPanel();
        PanelBrand = new ImagePanel("src/main/resources/ManagerUI/pizza-brand.png", 200, 170);
        LblSale = new javax.swing.JLabel();
        LblStaffs = new javax.swing.JLabel();
        LblNH = new javax.swing.JLabel();
        LblCustomers = new javax.swing.JLabel();
        LblProducts = new javax.swing.JLabel();
        LblTK = new javax.swing.JLabel();
        PanelName = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        PanelMain.setBackground(new java.awt.Color(255, 204, 153));
        PanelMain.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        PanelMain.setPreferredSize(new java.awt.Dimension(250, 769));

        PanelBrand.setBackground(new java.awt.Color(255, 204, 153));
        PanelBrand.setPreferredSize(new java.awt.Dimension(200, 200));

        javax.swing.GroupLayout PanelBrandLayout = new javax.swing.GroupLayout(PanelBrand);
        PanelBrand.setLayout(PanelBrandLayout);
        PanelBrandLayout.setHorizontalGroup(
            PanelBrandLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 200, Short.MAX_VALUE)
        );
        PanelBrandLayout.setVerticalGroup(
            PanelBrandLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 200, Short.MAX_VALUE)
        );

        LblSale.setPreferredSize(new java.awt.Dimension(250, 65));

        LblStaffs.setPreferredSize(new java.awt.Dimension(250, 65));
        LblStaffs.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                LblStaffsMouseClicked(evt);
            }
        });

        LblNH.setPreferredSize(new java.awt.Dimension(250, 65));

        LblCustomers.setPreferredSize(new java.awt.Dimension(250, 65));

        LblProducts.setPreferredSize(new java.awt.Dimension(250, 65));

        LblTK.setPreferredSize(new java.awt.Dimension(250, 65));

        javax.swing.GroupLayout PanelMainLayout = new javax.swing.GroupLayout(PanelMain);
        PanelMain.setLayout(PanelMainLayout);
        PanelMainLayout.setHorizontalGroup(
            PanelMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelMainLayout.createSequentialGroup()
                .addGroup(PanelMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(LblStaffs, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(LblNH, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(LblProducts, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, PanelMainLayout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addComponent(PanelBrand, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(LblTK, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(LblCustomers, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(LblSale, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        PanelMainLayout.setVerticalGroup(
            PanelMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelMainLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(PanelBrand, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addComponent(LblSale, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(LblCustomers, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addComponent(LblStaffs, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addComponent(LblNH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addComponent(LblProducts, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addComponent(LblTK, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20))
        );

        PanelName.setBackground(new java.awt.Color(204, 0, 0));
        PanelName.setPreferredSize(new java.awt.Dimension(473, 40));
        PanelName.setLayout(new java.awt.BorderLayout());

        jLabel1.setBackground(new java.awt.Color(102, 255, 0));
        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Phần Mềm Quản Lý Cửa Hàng Bán Bánh Pizza");
        PanelName.add(jLabel1, java.awt.BorderLayout.CENTER);
        jLabel1.getAccessibleContext().setAccessibleName("");

        jPanel4.setPreferredSize(new java.awt.Dimension(1250, 711));

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1250, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(PanelMain, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(PanelName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(PanelName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, 769, Short.MAX_VALUE)
                    .addComponent(PanelMain, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 0, 0))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void LblStaffsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_LblStaffsMouseClicked
        // TODO add your handling code here:z
    }//GEN-LAST:event_LblStaffsMouseClicked

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel LblCustomers;
    private javax.swing.JLabel LblNH;
    private javax.swing.JLabel LblProducts;
    private javax.swing.JLabel LblSale;
    private javax.swing.JLabel LblStaffs;
    private javax.swing.JLabel LblTK;
    private javax.swing.JPanel PanelBrand;
    private javax.swing.JPanel PanelMain;
    private javax.swing.JPanel PanelName;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel4;
    // End of variables declaration//GEN-END:variables

}

