/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUI;

import BUS.HoaDonBUS;
import DTO.HoaDonDTO;
import DTO.SanPhamDTO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.Dimension;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.EventQueue;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.io.File;
import java.util.ArrayList;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;
import org.kordamp.ikonli.swing.FontIcon;
// import java.util.ArrayList; // Uncomment khi sử dụng ArrayList

// Uncomment các import cho lớp BUS và DTO của bạn
// import BUS.HoaDonBUS;
// import BUS.SanPhamBUS; // Ví dụ
// import DTO.HoaDonDTO;
// import DTO.SanPhamDTO;
// import DTO.ChiTietHoaDonDTO;

public class PnQLBH extends JPanel {

    private JPanel panelHoaDon;
    private JPanel panelBanHang;
    
    private JTable tableHoaDon;
    private JTable tableChiTietHoaDon;
    private JTable tableSanPham;
    private JTable tableGioHang;
    private JLabel lblAnhSanPham; // Thêm JLabel để hiển thị ảnh sản phẩm
    // Khai báo các biến cần dùng ở các hàm khác ở đây
    private JTextField tfMaKM;
    private JTextField tfGiamGia;
    private JTextField tfTongCong;
    private JFormattedTextField ftNgayLap;
    private JComponent[] fields;
    private JTextField ttKH;
    private JTextField ttKM;
    // ... các JTextField/JComponent khác từ mảng 'fields' nếu cần
    private JButton btnThemVaoGio;
    private JButton btnXoaKhoiGio;
    private JButton btnTaoHoaDon;
    private JButton btnXuatHoaDon;
    private JButton btnLocHoaDon;
    private JButton btnReset;
    private JButton btnThongTinHoaDon;
    private int slTD;

    // Khai báo các đối tượng BUS ở cấp lớp nếu cần truy cập từ nhiều phương thức
    // private HoaDonBUS hdBUS;
    // private SanPhamBUS spBUS;

    private final String DEFAULT_IMAGE_PATH = "src/main/resources/SanPham/default.png"; // Đường dẫn ảnh mặc định
    private final int IMAGE_WIDTH = 200; // Chiều rộng ảnh mong muốn
    private final int IMAGE_HEIGHT = 200; // Chiều cao ảnh mong muốn


    public PnQLBH(ArrayList<Object> tk) {
        // Chạy giao diện trên EDT
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    // Thiết lập giao diện hệ thống
                    UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());;
                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
                    
                }
            }
            });
        initComponents();       // Khởi tạo các thành phần giao diện
        setupTableModels(tk);     // Khởi tạo cấu trúc bảng (cột)
        loadDataFromSource();   // Nạp dữ liệu động từ BUS/DAO
        loadProductImage(DEFAULT_IMAGE_PATH); // Tải ảnh mặc định ban đầu
        // Viết sự kiện cho nút ... kế bên label khách hàng        
        JButton a = (JButton) fields[6];
        a.addActionListener((ActionEvent e) ->
        {
            showTableCustomer();
        });
        // Viết sự kiện cho nút ... kế bên label Khuyến mãi
        JButton b = (JButton) fields[7];
        b.addActionListener((ActionEvent e) ->
        {
            showTableDiscount();
        });        
    }
    // Hàm đệ quy set màu trắng cho tất cả component
    private void setBackgroundForAllComponents(java.awt.Component component) {
        component.setBackground(Color.WHITE); // Set màu nền trắng cho component

        if (component instanceof Container container) {
            for (Component child : container.getComponents()) {
                setBackgroundForAllComponents(child);
            }
        }
    }

    private void showTableCustomer(){
        InforCustomer a = new InforCustomer();
        a.setSelectCustomerListener(new SelectCustomerOrDiscount() {
            @Override
            public void onCustomerSelected(String tenKH) {
                ttKH.setText(tenKH);
            }

            @Override
            public void onDiscountSelected(String tenKM) {
                // Không dùng
            }
        });
        a.setVisible(true);
    }

    private void showTableDiscount(){
        InforDiscount b = new InforDiscount();
        b.setSelectDiscountListener(new SelectCustomerOrDiscount() {
            @Override
            public void onCustomerSelected(String tenKH) {
                // Không dùng
            }

            @Override
            public void onDiscountSelected(String tenKM) {
                ttKM.setText(tenKM);
            }
        });
        b.setVisible(true);
    }

    
    private void initComponents() {
        UIManager.put("TabbedPane.selected", Color.black);
        UIManager.put("TabbedPane.selectedForeground", Color.white);
        
        JTabbedPane tabbedPane = new JTabbedPane();
        panelHoaDon = new JPanel();
        panelBanHang = new JPanel();

        // === Tab Hóa Đơn (Giữ nguyên) ===
        panelHoaDon.setLayout(null);
        // ... (Các thành phần của panelHoaDon giữ nguyên như trước) ...
        JLabel lblDanhSachHoaDon = new JLabel("Danh Sách Hoá Đơn");
        lblDanhSachHoaDon.setFont(new java.awt.Font("Times New Roman", 1, 18));
        lblDanhSachHoaDon.setBounds(250, 10, 300, 30);
        panelHoaDon.add(lblDanhSachHoaDon);
        btnLocHoaDon = new JButton("Lọc Hoá Đơn");
        btnLocHoaDon.setFont(new java.awt.Font("Times New Roman", 0, 14));
        btnLocHoaDon.setBounds(260, 50, 120, 30);
        panelHoaDon.add(btnLocHoaDon);
        tableHoaDon = new JTable();
        JScrollPane scrollHoaDon = new JScrollPane(tableHoaDon);
        scrollHoaDon.setBounds(10, 90, 600, 300);
        panelHoaDon.add(scrollHoaDon);
        btnReset = new JButton("Reset tất cả");
        btnReset.setFont(new java.awt.Font("Times New Roman", 0, 14));
        btnReset.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/Refresh-icon.png")));
        btnReset.setBounds(50, 400, 150, 40);
        panelHoaDon.add(btnReset);
        btnThongTinHoaDon = new JButton("Thông Tin Hoá Đơn");
        btnThongTinHoaDon.setFont(new java.awt.Font("Times New Roman", 0, 14));
        btnThongTinHoaDon.setBounds(250, 400, 200, 40);
        panelHoaDon.add(btnThongTinHoaDon);
        JLabel lblChiTietHoaDon = new JLabel("Chi Tiết Hoá Đơn");
        lblChiTietHoaDon.setFont(new java.awt.Font("Times New Roman", 1, 18));
        lblChiTietHoaDon.setBounds(820, 50, 200, 30);
        panelHoaDon.add(lblChiTietHoaDon);
        tableChiTietHoaDon = new JTable();
        JScrollPane scrollChiTietHoaDon = new JScrollPane(tableChiTietHoaDon);
        scrollChiTietHoaDon.setBounds(620, 90, 600, 300);
        panelHoaDon.add(scrollChiTietHoaDon);
        JLabel lblMaKM = new JLabel("Mã Khuyến Mãi:");
        lblMaKM.setFont(new java.awt.Font("Times New Roman", 0, 14));
        lblMaKM.setBounds(650, 400, 120, 30);
        panelHoaDon.add(lblMaKM);
        tfMaKM = new JTextField();
        tfMaKM.setFont(new java.awt.Font("Times New Roman", 0, 14));
        tfMaKM.setBounds(800, 400, 300, 30);
        tfMaKM.setEditable(false);
        panelHoaDon.add(tfMaKM);
        JLabel lblGiamGia = new JLabel("Giảm Giá:");
        lblGiamGia.setFont(new java.awt.Font("Times New Roman", 0, 14));
        lblGiamGia.setBounds(650, 440, 120, 30);
        panelHoaDon.add(lblGiamGia);
        tfGiamGia = new JTextField();
        tfGiamGia.setFont(new java.awt.Font("Times New Roman", 0, 14));
        tfGiamGia.setBounds(800, 440, 300, 30);
        tfGiamGia.setEditable(false);
        panelHoaDon.add(tfGiamGia);
        JLabel lblTongCong = new JLabel("Tổng Cộng:");
        lblTongCong.setFont(new java.awt.Font("Times New Roman", 0, 14));
        lblTongCong.setBounds(650, 480, 120, 30);
        panelHoaDon.add(lblTongCong);
        tfTongCong = new JTextField();
        tfTongCong.setFont(new java.awt.Font("Times New Roman", 0, 14));
        tfTongCong.setBounds(800, 480, 300, 30);
        tfTongCong.setEditable(false);
        panelHoaDon.add(tfTongCong);
        JLabel lblNgayLap = new JLabel("Ngày Lập Hóa Đơn:");
        lblNgayLap.setFont(new java.awt.Font("Times New Roman", 0, 14));
        lblNgayLap.setBounds(650, 520, 140, 30);
        panelHoaDon.add(lblNgayLap);
        ftNgayLap = new JFormattedTextField();
        ftNgayLap.setFont(new java.awt.Font("Times New Roman", 0, 14));
        ftNgayLap.setBounds(800, 520, 300, 30);
        ftNgayLap.setEnabled(false);
        panelHoaDon.add(ftNgayLap);


        // === Tab Bán Hàng (Thêm lblAnhSanPham) ===
        panelBanHang.setLayout(null);

        // Các thành phần khác của panelBanHang
        JLabel lblDanhSachSP = new JLabel("Danh Sách Sản Phẩm");
        lblDanhSachSP.setFont(new java.awt.Font("Times New Roman", 1, 18));
        lblDanhSachSP.setBounds(220, 10, 200, 30);
        panelBanHang.add(lblDanhSachSP);
        tableSanPham = new JTable(); // Model set sau
        JScrollPane scrollSanPham = new JScrollPane(tableSanPham);
        scrollSanPham.setBounds(10, 50, 600, 200);
        panelBanHang.add(scrollSanPham);

        JLabel lblGioHang = new JLabel("Giỏ Hàng");
        lblGioHang.setFont(new java.awt.Font("Times New Roman", 1, 18));
        lblGioHang.setBounds(270, 260, 200, 30);
        panelBanHang.add(lblGioHang);
        tableGioHang = new JTable(); // Model set sau
        JScrollPane scrollGioHang = new JScrollPane(tableGioHang);
        scrollGioHang.setBounds(10, 300, 600, 300);
        panelBanHang.add(scrollGioHang);

        // --- Khu vực chi tiết sản phẩm bên phải ---
        int panelRightStartX = 650;
        int panelRightWidth = 580;

        JLabel lblChiTietSP = new JLabel("Chi Tiết Sản Phẩm", SwingConstants.CENTER);
        lblChiTietSP.setFont(new java.awt.Font("Times New Roman", 1, 20));
        lblChiTietSP.setBounds(panelRightStartX, 10, panelRightWidth, 30);
        panelBanHang.add(lblChiTietSP);

        String[] labels = {"Mã SP:", "Tên SP:", "Đơn Giá:", "Số Lượng Mua:", "Loại SP:", "Nhân Viên Lập:", "Khách Hàng:", "Khuyến Mãi:"};
        fields = new JComponent[labels.length];
        int currentY = 50;

        for (int i = 0; i < labels.length; i++) {
            JLabel label = new JLabel(labels[i]);
            label.setFont(new java.awt.Font("Times New Roman", 0, 14));
            label.setBounds(panelRightStartX, currentY, 140, 30);
            panelBanHang.add(label);

            if(label.getText().equals("Số Lượng Mua:")){
                fields[i] = new JSpinner();
                ((JSpinner) fields[i]).setEnabled(false);
                ((JSpinner) fields[i]).setFont(new java.awt.Font("Times New Roman", 0, 14));
                ((JSpinner) fields[i]).setBounds(panelRightStartX + 150, currentY, panelRightWidth - 170, 30);
            }else if (label.getText().equals("Khách Hàng:")){
                ttKH = new JTextField();
                ttKH.setFont(new java.awt.Font("Times New Roman", 0, 14));
                ttKH.setEditable(false);
                ttKH.setBounds(panelRightStartX + 150, currentY, panelRightWidth - 225, 30); // ngắn lại chút để chừa nút
                panelBanHang.add(ttKH);
                // Sau đó thêm nút
                fields[i] = new JButton();
                FontIcon ellipsis = FontIcon.of(FontAwesomeSolid.ELLIPSIS_H);
                ellipsis.setIconSize(28);
                ellipsis.setIconColor(Color.BLACK);
                ((JButton) fields[i]).setIcon(ellipsis);
                ((JButton) fields[i]).setVerticalAlignment(SwingConstants.CENTER);
                ((JButton) fields[i]).setHorizontalAlignment(SwingConstants.CENTER);
                fields[i].setFont(new java.awt.Font("Times New Roman", 0, 14));
                fields[i].setBounds(panelRightStartX + 150 + (panelRightWidth - 225) + 20, currentY, 35, 30); // đặt nút ngay bên phải
            } else if(label.getText().equals("Khuyến Mãi:")){
                ttKM = new JTextField();
                ttKM.setFont(new java.awt.Font("Times New Roman", 0, 14));
                ttKM.setEditable(false);
                ttKM.setBounds(panelRightStartX + 150, currentY, panelRightWidth - 225, 30); // ngắn lại chút để chừa nút
                panelBanHang.add(ttKM);   
                // Sau đó thêm nút
                fields[i] = new JButton();
                FontIcon ellipsis = FontIcon.of(FontAwesomeSolid.ELLIPSIS_H);
                ellipsis.setIconSize(28);
                ellipsis.setIconColor(Color.BLACK);
                ((JButton) fields[i]).setIcon(ellipsis);
                ((JButton) fields[i]).setVerticalAlignment(SwingConstants.CENTER);
                ((JButton) fields[i]).setHorizontalAlignment(SwingConstants.CENTER);
                fields[i].setFont(new java.awt.Font("Times New Roman", 0, 14));
                fields[i].setBounds(panelRightStartX + 150 + (panelRightWidth - 225) + 20, currentY, 35, 30); // đặt nút ngay bên phải
            } else {
                fields[i] = new JTextField();
                ((JTextField) fields[i]).setEditable(false);
                fields[i].setFont(new java.awt.Font("Times New Roman", 0, 14));
                fields[i].setBounds(panelRightStartX + 150, currentY, panelRightWidth - 170, 30);
            }
            panelBanHang.add(fields[i]);
            currentY += 40;
        }


        // Ảnh Sản Phẩm căn giữa
        lblAnhSanPham = new JLabel();
        lblAnhSanPham.setBounds(panelRightStartX + (panelRightWidth - IMAGE_WIDTH) / 2, currentY, IMAGE_WIDTH, IMAGE_HEIGHT);
        lblAnhSanPham.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        lblAnhSanPham.setHorizontalAlignment(SwingConstants.CENTER);
        panelBanHang.add(lblAnhSanPham);

        // --- Các Nút bấm sau ảnh ---
        int panelRightStartX_button = 650;
        int panelRightEndX = 1230; // = 650 + 580
        int panelRightWidth_button = panelRightEndX - panelRightStartX_button;
        int margin = 20;

        int buttonWidth = 180;
        int buttonHeight = 40;
        int horizontalSpace = 20;
        int verticalSpace = 15;

        int startY = lblAnhSanPham.getY() + lblAnhSanPham.getHeight() + 20;

        // Vị trí X bên trái (cột Label)
        int startX_LeftColumn = 650; // <-- CHỈNH lại cho đúng yêu cầu cột Label

        // Vị trí X bên phải (cột nút Xóa, Xuất PDF)
        int totalButtonRowWidth = buttonWidth * 2 + horizontalSpace;
        int startX_RightColumn = panelRightStartX_button + panelRightWidth_button - totalButtonRowWidth - margin;

        // Button 1: Thêm vào Giỏ (bên trái - cột Label)
        btnThemVaoGio = new JButton("Thêm vào Giỏ");
        FontIcon basket = FontIcon.of(FontAwesomeSolid.SHOPPING_BASKET);
        basket.setIconSize(28);
        basket.setIconColor(Color.GREEN);
        btnThemVaoGio.setIcon(basket);
        btnThemVaoGio.setFont(new java.awt.Font("Times New Roman", 0, 14));
        btnThemVaoGio.setBounds(startX_LeftColumn, startY, buttonWidth, buttonHeight);
        panelBanHang.add(btnThemVaoGio);

        // Button 2: Xóa khỏi Giỏ (bên phải)
        btnXoaKhoiGio = new JButton("Xóa khỏi Giỏ");
        FontIcon x = FontIcon.of(FontAwesomeSolid.TIMES);
        x.setIconSize(28);
        x.setIconColor(Color.RED);
        btnXoaKhoiGio.setIcon(x);
        btnXoaKhoiGio.setFont(new java.awt.Font("Times New Roman", 0, 14));
        btnXoaKhoiGio.setBounds(startX_RightColumn + buttonWidth + horizontalSpace, startY, buttonWidth, buttonHeight);
        panelBanHang.add(btnXoaKhoiGio);

        // Button 3: Tạo Hoá Đơn (bên trái - cột Label)
        btnTaoHoaDon = new JButton("Tạo Hoá Đơn");
        FontIcon receipt = FontIcon.of(FontAwesomeSolid.RECEIPT);
        receipt.setIconSize(28);
        receipt.setIconColor(Color.BLUE);
        btnTaoHoaDon.setIcon(receipt);
        btnTaoHoaDon.setFont(new java.awt.Font("Times New Roman", 0, 14));
        btnTaoHoaDon.setBounds(startX_LeftColumn, startY + buttonHeight + verticalSpace, buttonWidth, buttonHeight);
        panelBanHang.add(btnTaoHoaDon);

        // Button 4: Xuất PDF (bên phải)
        btnXuatHoaDon = new JButton("Xuất PDF");
        FontIcon file_pdf = FontIcon.of(FontAwesomeSolid.FILE_PDF);
        file_pdf.setIconSize(28);
        file_pdf.setIconColor(Color.BLUE);
        btnXuatHoaDon.setIcon(file_pdf);
        btnXuatHoaDon.setFont(new java.awt.Font("Times New Roman", 0, 14));
        btnXuatHoaDon.setBounds(startX_RightColumn + buttonWidth + horizontalSpace, startY + buttonHeight + verticalSpace, buttonWidth, buttonHeight);
        panelBanHang.add(btnXuatHoaDon);

        // Add Tabs
        tabbedPane.addTab("Hoá Đơn", panelHoaDon);
        tabbedPane.addTab("Bán Hàng", panelBanHang);

        // Add tabbedPane to the main panel
        setLayout(new BorderLayout());
        add(tabbedPane, BorderLayout.CENTER);

        setPreferredSize(new Dimension(1250, 750));
        
        setBackgroundForAllComponents(this);
    }

    // Phương thức khởi tạo model và cột cho các bảng
    private void setupTableModels(ArrayList<Object> tk) {
        // --- Bảng Hóa Đơn ---
        tableHoaDon.setModel(new DefaultTableModel(
                new Object[][]{}, // Dữ liệu ban đầu trống
                new String[]{"Mã Hoá Đơn", "Mã Nhân Viên", "Mã Khách Hàng", "Ngày Lập", "Tổng Tiền"} // Tên cột
        ) {
             @Override
             public boolean isCellEditable(int row, int column) { return false; } 
        });
        // Thiết lập độ rộng cột, font, chiều cao dòng... cho tableHoaDon
        tableHoaDon.getColumnModel().getColumn(0).setPreferredWidth(80);
        tableHoaDon.getColumnModel().getColumn(1).setPreferredWidth(80);
        tableHoaDon.getColumnModel().getColumn(2).setPreferredWidth(90);
        tableHoaDon.getColumnModel().getColumn(3).setPreferredWidth(100);
        tableHoaDon.getColumnModel().getColumn(4).setPreferredWidth(100);
        tableHoaDon.setFont(new java.awt.Font("Times New Roman", 0, 18));
        tableHoaDon.setRowHeight(25);
        // Thêm Listener để khi chọn hóa đơn thì load chi tiết (xem ví dụ ở loadDataFromSource)
        tableHoaDon.getSelectionModel().addListSelectionListener(event -> {
            if (!event.getValueIsAdjusting() && tableHoaDon.getSelectedRow() != -1) {
                // Lấy mã hóa đơn từ dòng được chọn
                String maHD = tableHoaDon.getValueAt(tableHoaDon.getSelectedRow(), 0).toString();
                loadChiTietHoaDon(maHD); // Gọi hàm load chi tiết
            }
        });


        // --- Bảng Chi Tiết Hóa Đơn ---
        tableChiTietHoaDon.setModel(new DefaultTableModel(
                new Object[][]{},
                new String[]{"Mã CTHD", "Mã SP", "Tên Sản Phẩm", "Số Lượng", "Đơn Giá", "Thành Tiền"}
        ) {
             @Override
             public boolean isCellEditable(int row, int column) { return false; } 
        });
        tableChiTietHoaDon.getColumnModel().getColumn(2).setPreferredWidth(200);
        tableChiTietHoaDon.setFont(new java.awt.Font("Times New Roman", 0, 18));
        tableChiTietHoaDon.setRowHeight(25);
        // Thiết lập độ rộng cột... cho tableChiTietHoaDon


        // --- Bảng Sản Phẩm ---
        tableSanPham.setModel(new DefaultTableModel(
                new Object[][]{},
                new String[]{"Mã Sản Phẩm", "Tên Sản Phẩm", "Đơn Giá", "Số Lượng Tồn", "Loại SP"}
        ));
        tableSanPham.getColumnModel().getColumn(1).setPreferredWidth(200);
        tableSanPham.getColumnModel().getColumn(4).setPreferredWidth(100);
        tableSanPham.setFont(new java.awt.Font("Times New Roman", 0, 18));
        tableSanPham.setRowHeight(25);
         // Thiết lập độ rộng cột... cho tableSanPham
        tableSanPham.getSelectionModel().addListSelectionListener((var event) -> {
            if (!event.getValueIsAdjusting()) {
                xuly_nhanhang_bangsanpham(tk);
            }
        });


        // --- Bảng Giỏ Hàng ---
        tableGioHang.setModel(new DefaultTableModel(
                new Object[][]{},
                new String[]{"Mã SP", "Tên SP", "Đơn Giá", "Số Lượng", "Thành Tiền"}
        ) {
             @Override
             public boolean isCellEditable(int row, int column) { return false; } 
        });
        tableGioHang.setFont(new java.awt.Font("Times New Roman", 0, 18));
        tableGioHang.setRowHeight(25);
        // Thiết lập độ rộng cột... cho tableGioHang
    }
    // Phương thức nạp dữ liệu động từ BUS/DAO
    private void loadDataFromSource() {
        // --- Nạp dữ liệu cho Bảng Hóa Đơn ---
        DefaultTableModel modelHoaDon = (DefaultTableModel) tableHoaDon.getModel();
        modelHoaDon.setRowCount(0); // Xóa dữ liệu cũ trước khi nạp mới
        try {
             HoaDonBUS localHdBUS = new HoaDonBUS(); // Hoặc sử dụng biến hdBUS đã khai báo ở lớp
             ArrayList<HoaDonDTO> dsHD = localHdBUS.hienDSHD(); // Đảm bảo phương thức này tồn tại và trả về ArrayList<HoaDonDTO>
            if (dsHD != null) {
                for (HoaDonDTO hd : dsHD) {
                    Object[] rowData = {
                        hd.getMaHD(),
                        hd.getMaNV(),
                        hd.getMaKH(),
                        hd.getNgayLapHD(), // Đảm bảo có phương thức getNgayLap()
                        hd.getTongTienHD() // Đảm bảo có phương thức getTongTien()
                    };
                    modelHoaDon.addRow(rowData);
                }
            }
        } catch (Exception e) {
             JOptionPane.showMessageDialog(this, "Lỗi khi tải danh sách hóa đơn: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
             e.printStackTrace(); // In lỗi ra console để debug
        }

        // --- Nạp dữ liệu cho Bảng Sản Phẩm (trong tab Bán Hàng) ---
        DefaultTableModel modelSanPham = (DefaultTableModel) tableSanPham.getModel();
        modelSanPham.setRowCount(0);
        try {
             HoaDonBUS localHdBUS = new HoaDonBUS();
             ArrayList<SanPhamDTO> dsSP = localHdBUS.hienDSSP(); // Đảm bảo phương thức này tồn tại và trả về ArrayList<SanPhamDTO>
            if (dsSP != null) {
                for (SanPhamDTO sp : dsSP) {
                    Object[] rowData = {
                        sp.getMaSP(),
                        sp.getTenSP(),
                        sp.getDonGia(),
                        sp.getSoLuong(), // Đây có thể là số lượng tồn
                        sp.getLoai() // Hoặc tên loại SP nếu có phương thức get
                    };
                    modelSanPham.addRow(rowData);
                }
            }
        } catch (Exception e) {
              JOptionPane.showMessageDialog(this, "Lỗi khi tải danh sách sản phẩm: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
              e.printStackTrace();
        }

        // --- Nạp Bảng Chi Tiết Hóa Đơn: Sẽ được gọi khi chọn hóa đơn ---
        // Xóa dữ liệu bảng chi tiết khi mới load panel
        DefaultTableModel modelChiTiet = (DefaultTableModel) tableChiTietHoaDon.getModel();
        modelChiTiet.setRowCount(0);

        // --- Bảng Giỏ Hàng: Để trống khi khởi tạo ---
        DefaultTableModel modelGioHang = (DefaultTableModel) tableGioHang.getModel();
        modelGioHang.setRowCount(0);
    }
    
    private void xuly_nhanhang_bangsanpham(ArrayList<Object> tk){
        int maSP = (Integer) tableSanPham.getValueAt(tableSanPham.getSelectedRow(), 0);
        String tenSP = (String) tableSanPham.getValueAt(tableSanPham.getSelectedRow(), 1);
        long dongia = (long) tableSanPham.getValueAt(tableSanPham.getSelectedRow(), 2);
        int slSP = (Integer) tableSanPham.getValueAt(tableSanPham.getSelectedRow(), 3);
        slTD = slSP;
        String loaiSP = (String) tableSanPham.getValueAt(tableSanPham.getSelectedRow(), 4);
        for (int i = 0; i < fields.length; i++){
            if (fields[i] instanceof JTextField)
            {
                JTextField a = (JTextField) fields[i];
                switch (i)
                {
                    case 0:
                        a.setText(String.valueOf(maSP));
                        break;
                    case 1:
                        a.setText(tenSP);
                        break;
                    case 2:
                        a.setText(String.valueOf(dongia));
                        break;
                    case 4:
                        a.setText(loaiSP);
                        break;
                    case 5:
                        a.setText(tk.get(3).toString());
                        break;
                    default:
                        System.out.println("Khong phai la JTextField");
                }
                a.setForeground(Color.blue);
            } else if (fields[i] instanceof JSpinner) {
                JSpinner b = (JSpinner) fields[i];
                JComponent editor = b.getEditor();
                JFormattedTextField textField = ((JSpinner.DefaultEditor) editor).getTextField();

                // Xóa border của text box và điều chỉnh padding
                textField.setBorder(new EmptyBorder(0, 2, 0, 0));
                textField.setHorizontalAlignment(JTextField.RIGHT); // Căn phải text

                // Tùy chỉnh các nút tăng giảm
                for (Component component : b.getComponents()) {
                    if (component instanceof JButton) {
                        JButton button = (JButton) component;
                        // Xóa border và margin của nút
                        button.setBorder(new LineBorder(Color.lightGray, 1));
                        button.setMargin(new Insets(0, 0, 0, 0));
                        // Đặt kích thước nút để khớp với chiều cao của text box
                        int textFieldHeight = textField.getPreferredSize().height;
                        button.setPreferredSize(new Dimension(button.getPreferredSize().width, textFieldHeight));
                    }
                }

                // Đặt LineBorder cho toàn bộ JSpinner
                b.setBorder(new LineBorder(Color.BLACK, 1));           
                b.setEnabled(true);
                b.setModel(new SpinnerNumberModel(1, 1, slTD, 1));
            } 
        }
        HoaDonBUS hd = new HoaDonBUS();
        loadProductImage(hd.timAnhChoSanPham(String.valueOf(maSP)));
    }

        // Phương thức tải và hiển thị ảnh sản phẩm trên JLabel
    private void loadProductImage(String imagePath) {
        if (imagePath == null || imagePath.trim().isEmpty()) {
            imagePath = DEFAULT_IMAGE_PATH; // Dùng ảnh mặc định nếu đường dẫn trống
        }

        try {
            File imgFile = new File(imagePath);
            if (!imgFile.exists()) {
                 System.err.println("Ảnh không tồn tại: " + imagePath + ". Sử dụng ảnh mặc định.");
                 imgFile = new File(DEFAULT_IMAGE_PATH); // Thử lại với ảnh mặc định
                 if (!imgFile.exists()) {
                     System.err.println("Ảnh mặc định cũng không tồn tại: " + DEFAULT_IMAGE_PATH);
                     lblAnhSanPham.setIcon(null); // Không có ảnh nào thì xóa icon
                     lblAnhSanPham.setText("Không có ảnh");
                     return;
                 }
            }

            ImageIcon originalIcon = new ImageIcon(imgFile.getAbsolutePath());
            Image originalImage = originalIcon.getImage();

            // Thay đổi kích thước ảnh để vừa với JLabel
            Image scaledImage = originalImage.getScaledInstance(IMAGE_WIDTH, IMAGE_HEIGHT, Image.SCALE_SMOOTH);
            ImageIcon scaledIcon = new ImageIcon(scaledImage);

            lblAnhSanPham.setIcon(scaledIcon);
            lblAnhSanPham.setText(null); // Xóa text nếu có ảnh

        } catch (Exception e) {
            System.err.println("Lỗi khi tải ảnh: " + imagePath);
            e.printStackTrace();
            lblAnhSanPham.setIcon(null); // Xóa icon nếu có lỗi
            lblAnhSanPham.setText("Lỗi tải ảnh");
        }
    }

    
    // Phương thức để nạp chi tiết hóa đơn dựa vào mã hóa đơn được chọn
    private void loadChiTietHoaDon(String maHD) {
        DefaultTableModel modelChiTiet = (DefaultTableModel) tableChiTietHoaDon.getModel();
        modelChiTiet.setRowCount(0); // Xóa chi tiết cũ
        // try {
        //     HoaDonBUS localHdBUS = new HoaDonBUS();
             // Giả sử có phương thức lấy chi tiết theo mã hóa đơn
        //     ArrayList<ChiTietHoaDonDTO> dsCTHD = localHdBUS.hienDS_Chi_Tiet_HD_TheoMa(maHD);
        //      if (dsCTHD != null) {
        //          for (ChiTietHoaDonDTO ct : dsCTHD) {
                       // Thay thế getXXX() bằng các phương thức getter thực tế trong ChiTietHoaDonDTO của bạn
        //              Object[] rowData = {
        //                  ct.getMaCTHD(),
        //                  ct.getMaSP(),
        //                  ct.getTenSanPham(), // Hoặc lấy tên SP từ BUS/DAO dựa trên ct.getMaSP()
        //                  ct.getSoLuong(),
        //                  ct.getDonGia(),   // Hoặc lấy đơn giá từ BUS/DAO
        //                  ct.getThanhTien() // Hoặc tính toán = số lượng * đơn giá
        //              };
        //              modelChiTiet.addRow(rowData);
        //          }
        //      }
        // } catch (Exception e) {
        //      JOptionPane.showMessageDialog(this, "Lỗi khi tải chi tiết hóa đơn " + maHD + ": " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        //      e.printStackTrace();
        // }

        // Sau khi load chi tiết, có thể cập nhật các JTextField tổng tiền, giảm giá... của hóa đơn đó
        // updateThongTinTongKetHoaDon(maHD);
    }

}
