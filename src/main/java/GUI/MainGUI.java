package GUI;

import DAO.QuyenDAO;
import DTO.QuyenDTO;
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
import javax.swing.JFrame;

import GUI.SanPhamGUI;
import java.awt.BorderLayout;
import javax.swing.JOptionPane;

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
    private ArrayList<Object> ttdn;

    public MainGUI(ArrayList<Object> ttdn) {
        this.ttdn = ttdn;
        initComponents();
        int maQuyen = Integer.parseInt(ttdn.get(2).toString());
        QuyenDAO quyenDAO = new QuyenDAO();
        QuyenDTO quyen;
        System.out.println("Nội dung ttdn: " + ttdn);
        try {
            quyen = quyenDAO.getQuyenByMaQuyen(maQuyen);
            if (quyen == null) {
                throw new Exception("Không tìm thấy quyền với MaQuyen: " + maQuyen);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi lấy thông tin quyền!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
            return;
        }

        initializeCardLayout(quyen);
        setupMenuEvents();
        java.awt.EventQueue.invokeLater(() -> {
            if (LblSale.isVisible()) {
                setLabelIcon(LblSale, "/ManagerUI/lblBanHang.png");
            }
            if (LblStaffs.isVisible()) {
                setLabelIcon(LblStaffs, "/ManagerUI/lblNhanVien.png");
            }
            if (LblNH.isVisible()) {
                setLabelIcon(LblNH, "/ManagerUI/lblNhapHang.png");
            }
            if (LblCustomers.isVisible()) {
                setLabelIcon(LblCustomers, "/ManagerUI/lblKhachHang.png");
            }
            if (LblProducts.isVisible()) {
                setLabelIcon(LblProducts, "/ManagerUI/lblSanPham.png");
            }
            if (LblPromotions.isVisible()) {
                setLabelIcon(LblPromotions, "/ManagerUI/lblKhuyenMai.png");
            }
            if (LblTK.isVisible()) {
                setLabelIcon(LblTK, "/ManagerUI/lblThongKe.png");
            }
        });

    }

    private void setLabelIcon(JLabel label, String resourcePath) {
        ImageIcon icon = new ImageIcon(getClass().getResource(resourcePath));
        Image img = icon.getImage();
        int width = label.getWidth();
        int height = label.getHeight();
        Image scaledImg = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        label.setIcon(new ImageIcon(scaledImg));
    }

    private void initializeCardLayout(QuyenDTO quyen) {
        // Khởi tạo CardLayout và contentPanel
        cardLayout = new CardLayout();
        contentPanel = new JPanel(cardLayout);
        contentPanel.setPreferredSize(new Dimension(1250, 770));
        jPanel4.setLayout(new BorderLayout());
        jPanel4.add(contentPanel, BorderLayout.CENTER);

        // Khởi tạo danh sách menu
        menuLabels = new ArrayList<>();

        // Ẩn tất cả label trước
        LblSale.setVisible(false);
        LblStaffs.setVisible(false);
        LblNH.setVisible(false);
        LblCustomers.setVisible(false);
        LblProducts.setVisible(false);
        LblPromotions.setVisible(false);
        LblTK.setVisible(false);

        // Thêm panel và hiển thị label dựa trên quyền
        JLabel defaultLabel = null;
        if (quyen.getQLBanHang()) {
            LblSale.setVisible(true);
            menuLabels.add(LblSale);
            PnQLBH salePanel = new PnQLBH(ttdn);
            contentPanel.add(salePanel, "sale");
            defaultLabel = LblSale;
        }

        if (quyen.getQLNhanVien()) {
            LblStaffs.setVisible(true);
            menuLabels.add(LblStaffs);
            PnNhanVien3 staffPanel = new PnNhanVien3();
            contentPanel.add(staffPanel, "staff");
            if (defaultLabel == null) {
                defaultLabel = LblStaffs;
            }
        }

        if (quyen.getQLNhapHang()) {
            LblNH.setVisible(true);
            menuLabels.add(LblNH);
            JPanel importPanel = new JPanel();
            importPanel.setBackground(Color.WHITE);
            importPanel.add(new JLabel("Panel Nhập hàng", SwingConstants.CENTER));
            contentPanel.add(importPanel, "import");
            if (defaultLabel == null) {
                defaultLabel = LblNH;
            }
        }

        if (quyen.getQLKhachHang()) {
            LblCustomers.setVisible(true);
            menuLabels.add(LblCustomers);
            PnKhachHang customerPanel = new PnKhachHang();
            contentPanel.add(customerPanel, "customer");
            if (defaultLabel == null) {
                defaultLabel = LblCustomers;
            }
        }

        if (quyen.getQLSanPham()) {
            LblProducts.setVisible(true);
            menuLabels.add(LblProducts);
            SanPhamGUI productPanel = new SanPhamGUI();
            contentPanel.add(productPanel, "product");
            if (defaultLabel == null) {
                defaultLabel = LblProducts;
            }
        }

        if (quyen.getQLKhuyenMai()) {
            LblPromotions.setVisible(true);
            menuLabels.add(LblPromotions);
            KhuyenMaiGUI promotionPanel = new KhuyenMaiGUI();
            contentPanel.add(promotionPanel, "promotion");
            if (defaultLabel == null) {
                defaultLabel = LblPromotions;
            }
        }

        if (quyen.getThongKe()) {
            LblTK.setVisible(true);
            menuLabels.add(LblTK);
            JPanel statsPanel = new JPanel();
            statsPanel.setBackground(Color.WHITE);
            statsPanel.add(new JLabel("Panel Thống kê", SwingConstants.CENTER));
            contentPanel.add(statsPanel, "stats");
            if (defaultLabel == null) {
                defaultLabel = LblTK;
            }
        }

        // Hiển thị panel mặc định (nếu có)
        if (defaultLabel != null) {
            cardLayout.show(contentPanel, defaultLabel == LblSale ? "sale"
                    : defaultLabel == LblStaffs ? "staff"
                            : defaultLabel == LblNH ? "import"
                                    : defaultLabel == LblCustomers ? "customer"
                                            : defaultLabel == LblProducts ? "product"
                                                    : defaultLabel == LblPromotions ? "promotion" : "stats");
            defaultLabel.setBackground(clMenuItemSelected);
        } else {
            JOptionPane.showMessageDialog(this, "Tài khoản không có quyền truy cập!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }

        // Cập nhật giao diện
        PanelMain.revalidate();
        PanelMain.repaint();
    }

    private void setupMenuEvents() {
        for (JLabel label : menuLabels) {
            label.setOpaque(true);
            label.setBackground(clMenuItem);
            label.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            label.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    for (JLabel lbl : menuLabels) {
                        lbl.setBackground(clMenuItem);
                    }
                    label.setBackground(clMenuItemSelected);

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
                    } else if (label == LblPromotions) {
                        cardLayout.show(contentPanel, "promotion");
                    } else if (label == LblTK) {
                        cardLayout.show(contentPanel, "stats");
                    }
                }

                @Override
                public void mouseEntered(MouseEvent e) {
                    if (!label.getBackground().equals(clMenuItemSelected)) {
                        label.setBackground(new Color(255, 180, 130));
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

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents()
    {

        PanelMain = new javax.swing.JPanel();
        PanelBrand = new ImagePanel("src/main/resources/ManagerUI/pizza-brand.png", 140, 110);
        LblSale = new javax.swing.JLabel();
        LblStaffs = new javax.swing.JLabel();
        LblNH = new javax.swing.JLabel();
        LblCustomers = new javax.swing.JLabel();
        LblProducts = new javax.swing.JLabel();
        LblPromotions = new javax.swing.JLabel();
        LblTK = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        lbXemTK = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));
        setPreferredSize(new java.awt.Dimension(1500, 790));
        setResizable(false);

        PanelMain.setBackground(new java.awt.Color(255, 204, 153));
        PanelMain.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        PanelMain.setPreferredSize(new java.awt.Dimension(250, 750));

        PanelBrand.setBackground(new java.awt.Color(255, 204, 153));
        PanelBrand.setPreferredSize(new java.awt.Dimension(140, 110));

        javax.swing.GroupLayout PanelBrandLayout = new javax.swing.GroupLayout(PanelBrand);
        PanelBrand.setLayout(PanelBrandLayout);
        PanelBrandLayout.setHorizontalGroup(
            PanelBrandLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 140, Short.MAX_VALUE)
        );
        PanelBrandLayout.setVerticalGroup(
            PanelBrandLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 110, Short.MAX_VALUE)
        );

        LblSale.setPreferredSize(new java.awt.Dimension(250, 65));

        LblStaffs.setPreferredSize(new java.awt.Dimension(250, 65));

        LblNH.setPreferredSize(new java.awt.Dimension(250, 65));

        LblCustomers.setPreferredSize(new java.awt.Dimension(250, 65));

        LblProducts.setPreferredSize(new java.awt.Dimension(250, 65));

        LblPromotions.setPreferredSize(new java.awt.Dimension(250, 65));

        LblTK.setPreferredSize(new java.awt.Dimension(250, 65));

        javax.swing.GroupLayout PanelMainLayout = new javax.swing.GroupLayout(PanelMain);
        PanelMain.setLayout(PanelMainLayout);
        PanelMainLayout.setHorizontalGroup(
            PanelMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(LblSale, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(PanelMainLayout.createSequentialGroup()
                .addGroup(PanelMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(LblStaffs, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(LblNH, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(LblProducts, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(LblPromotions, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(LblTK, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(LblCustomers, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(PanelMainLayout.createSequentialGroup()
                .addGap(55, 55, 55)
                .addComponent(PanelBrand, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        PanelMainLayout.setVerticalGroup(
            PanelMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelMainLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(PanelBrand, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(LblSale, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addComponent(LblCustomers, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addComponent(LblStaffs, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addComponent(LblNH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addComponent(LblProducts, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addComponent(LblPromotions, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addComponent(LblTK, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(112, Short.MAX_VALUE))
        );

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setPreferredSize(new java.awt.Dimension(1250, 750));

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1250, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 750, Short.MAX_VALUE)
        );

        jPanel1.setPreferredSize(new java.awt.Dimension(1500, 40));

        jPanel2.setBackground(new java.awt.Color(255, 255, 51));
        jPanel2.setPreferredSize(new java.awt.Dimension(250, 40));

        lbXemTK.setFont(new java.awt.Font("Times New Roman", 0, 24)); // NOI18N
        lbXemTK.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbXemTK.setText("Xem tài khoản");
        lbXemTK.setPreferredSize(new java.awt.Dimension(250, 40));
        lbXemTK.addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mouseClicked(java.awt.event.MouseEvent evt)
            {
                XemTaiKhoan(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(lbXemTK, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(lbXemTK, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jPanel3.setBackground(new java.awt.Color(255, 153, 255));
        jPanel3.setPreferredSize(new java.awt.Dimension(1250, 40));

        jLabel2.setFont(new java.awt.Font("Times New Roman", 0, 24)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Phần Mềm Quản Lý Cửa Hàng Bán Bánh Pizza");
        jLabel2.setPreferredSize(new java.awt.Dimension(1250, 40));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(PanelMain, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(PanelMain, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, 0))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void XemTaiKhoan(java.awt.event.MouseEvent evt)//GEN-FIRST:event_XemTaiKhoan
    {//GEN-HEADEREND:event_XemTaiKhoan
        String param1 = ttdn.get(0).toString(); // username (Tên đăng nhập: admin123)
        String param2 = ttdn.get(1).toString(); // password (Mật khẩu: admin123)
        int param3 = Integer.parseInt(ttdn.get(3).toString()); // idNV (Mã nhân viên: 1)
        String param4 = ttdn.get(6).toString(); // tenNV (Tên nhân viên: Nguyen Van An)
        String param5 = ttdn.get(4).toString(); // ngaysinh (Ngày sinh: 2005-04-13)
        String param6 = ttdn.get(5).toString(); // gioitinh (Giới tính: Nam)
        String param7 = ttdn.get(7).toString(); // quyen (Quyền: Admin)

        System.out.println("Parameters passed to InforAccount constructor:");
        System.out.println("Param 1: " + param1);
        System.out.println("Param 2: " + param2);
        System.out.println("Param 3: " + param3);
        System.out.println("Param 4: " + param4);
        System.out.println("Param 5: " + param5);
        System.out.println("Param 6: " + param6);
        System.out.println("Param 7: " + param7);

        InforAccount account = new InforAccount(param1, param2, param3, param4, param5, param6, param7);

        account.setLocationRelativeTo(null);
        account.setVisible(true);
        account.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }//GEN-LAST:event_XemTaiKhoan

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel LblPromotions;
    private javax.swing.JLabel LblCustomers;
    private javax.swing.JLabel LblNH;
    private javax.swing.JLabel LblProducts;
    private javax.swing.JLabel LblSale;
    private javax.swing.JLabel LblStaffs;
    private javax.swing.JLabel LblTK;
    private javax.swing.JPanel PanelBrand;
    private javax.swing.JPanel PanelMain;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JLabel lbXemTK;
    // End of variables declaration//GEN-END:variables

}
