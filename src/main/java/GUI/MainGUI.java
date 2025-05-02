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
import javax.swing.JFrame;

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
        String tenquyen = ttdn.get(6).toString();
        switch (tenquyen)
        {
            case "Admin":
                initializeCardLayout();
                setupMenuEvents();
                break;
            case "Thu ngân":
                initializeCardLayout_Sale();
                setupMenuEvents_Sale();
                break;
            case "Bếp trưởng":
                initializeCardLayout_Chef();
               setupMenuEvents_Chef();
                break;
            case "Nhân viên":
                initializeCardLayout_Employee();
                setupMenuEvents_Employee();
                break;
            default:
                throw new AssertionError();
        }

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
        contentPanel.setPreferredSize(new Dimension(1250, 770));

        // Thêm các panel vào contentPanel
        PnQLBH salePanel = new PnQLBH(ttdn);

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
    private void initializeCardLayout_Chef() {
        // Khởi tạo CardLayout và contentPanel thay cho jPanel4
        cardLayout = new CardLayout();
        contentPanel = new JPanel(cardLayout);
        contentPanel.setPreferredSize(new Dimension(1250, 770));

        JPanel importPanel = new JPanel(); // Panel Nhập hàng
        importPanel.setBackground(Color.WHITE);
        importPanel.add(new JLabel("Panel Nhập hàng", SwingConstants.CENTER));

        JPanel productPanel = new JPanel(); // Panel Sản phẩm
        productPanel.setBackground(Color.WHITE);
        productPanel.add(new JLabel("Panel Sản phẩm", SwingConstants.CENTER));


        // Thêm các panel vào CardLayout với tên định danh
        contentPanel.add(importPanel, "import");
        contentPanel.add(productPanel, "product");

        // Hiển thị panel Bán hàng mặc định
        cardLayout.show(contentPanel, "import");
        LblSale.setBackground(clMenuItemSelected);
    }
    
    private void setupMenuEvents_Chef() {
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
                    if (label == LblNH) {
                        cardLayout.show(contentPanel, "import");
                    } else if (label == LblProducts) {
                        cardLayout.show(contentPanel, "product");
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
    private void setupMenuEvents_Sale() {
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
                    } else if (label == LblCustomers) {
                        cardLayout.show(contentPanel, "customer");
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
    private void initializeCardLayout_Sale() {
        // Khởi tạo CardLayout và contentPanel thay cho jPanel4
        cardLayout = new CardLayout();
        contentPanel = new JPanel(cardLayout);
        contentPanel.setPreferredSize(new Dimension(1250, 770));

        // Thêm các panel vào contentPanel
        PnQLBH salePanel = new PnQLBH(ttdn);

        JPanel customerPanel = new JPanel(); // Panel Khách hàng
        customerPanel.setBackground(Color.WHITE);
        customerPanel.add(new JLabel("Panel Khách hàng", SwingConstants.CENTER));

        JPanel statsPanel = new JPanel(); // Panel Thống kê
        statsPanel.setBackground(Color.WHITE);
        statsPanel.add(new JLabel("Panel Thống kê", SwingConstants.CENTER));

        // Thêm các panel vào CardLayout với tên định danh
        contentPanel.add(salePanel, "sale");
        contentPanel.add(customerPanel, "customer");
        contentPanel.add(statsPanel, "stats");

        // Hiển thị panel Bán hàng mặc định
        cardLayout.show(contentPanel, "sale");
        LblSale.setBackground(clMenuItemSelected);
    }
    private void setupMenuEvents_Employee() {   
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
    private void initializeCardLayout_Employee() {
        // Khởi tạo CardLayout và contentPanel thay cho jPanel4
        cardLayout = new CardLayout();
        contentPanel = new JPanel(cardLayout);
        contentPanel.setPreferredSize(new Dimension(1250, 770));

        // Thêm các panel vào contentPanel
        PnQLBH salePanel = new PnQLBH(ttdn);

        // Thêm các panel vào CardLayout với tên định danh
        contentPanel.add(salePanel, "sale");

        // Hiển thị panel Bán hàng mặc định
        cardLayout.show(contentPanel, "sale");
        LblSale.setBackground(clMenuItemSelected);
    }
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
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
        // TODO add your handling code here:
        String param1 = ttdn.get(0).toString();
        String param2 = ttdn.get(1).toString();
        int param3 = Integer.parseInt(ttdn.get(2).toString());
        String param4 = ttdn.get(3).toString();
        String param5 = ttdn.get(4).toString();
        String param6 = ttdn.get(5).toString();
        String param7 = ttdn.get(6).toString();

        // Print all parameters
        System.out.println("Parameters passed to InforAccount constructor:");
        System.out.println("Param 1: " + param1);
        System.out.println("Param 2: " + param2);
        System.out.println("Param 3: " + param3);
        System.out.println("Param 4: " + param4);
        System.out.println("Param 5: " + param5);
        System.out.println("Param 6: " + param6);
        System.out.println("Param 7: " + param7);

        // Create the InforAccount object
        InforAccount account = new InforAccount(param1, param2, param3, param4, param5, param6, param7);
        account.setLocationRelativeTo(null);
        account.setVisible(true);
        account.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }//GEN-LAST:event_XemTaiKhoan

    // Variables declaration - do not modify//GEN-BEGIN:variables
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

