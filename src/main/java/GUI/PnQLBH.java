/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUI;

import DTO.GioHangDTO;
import BUS.HoaDonBUS;
import BUS.NhanVienBUS;
import DAO.ChiTietKMDAO;
import DAO.KhachHangDAO;
import DAO.SanPhamDAO;
import DTO.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.Dimension;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
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
    private JButton btnRefreshChiTietSP;
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
        btnThemVaoGio.addActionListener((ActionEvent e) -> {
            ThemVaoGioHang();
        });
        btnTaoHoaDon.addActionListener((ActionEvent e) -> {
            TaoHoaDon(tk);
        });
    }
    
    private void TaoHoaDon(ArrayList<Object> tk){
        int MaNV = Integer.parseInt(tk.get(2).toString());
        String a = ttKH.getText();
        String f = ttKM.getText();
        HoaDonDTO d = null;
        if(a.isBlank() && f.isBlank()){
            JOptionPane.showMessageDialog(this, "Bạn không được để trống khách hàng và khuyến mãi" , "Lỗi", JOptionPane.ERROR_MESSAGE);
        } else if(a.isBlank()){
            JOptionPane.showMessageDialog(this, "Bạn không được để trống khách hàng" , "Lỗi", JOptionPane.ERROR_MESSAGE);
        } else if(f.isBlank()){
            JOptionPane.showMessageDialog(this, "Bạn không được để trống khuyến mãi" , "Lỗi", JOptionPane.ERROR_MESSAGE);            
        } else if(tableGioHang.getRowCount() == 0){
            JOptionPane.showMessageDialog(this, "Giỏ hàng không có sản phẩm" , "Lỗi", JOptionPane.ERROR_MESSAGE);                
        } else{
            HoaDonBUS hd = new HoaDonBUS();
            String[] b = ttKH.getText().split("\\s+");  
            KhachHangDAO c = new KhachHangDAO();
            KhachHangDTO kh = c.TimKhachHangTheoMa(Integer.parseInt(b[0]));
            int n = tableHoaDon.getRowCount();
            int o = tableChiTietHoaDon.getRowCount();
            long sum = 0;
            for(int i=0; i < tableGioHang.getRowCount(); i++){
                sum += Long.parseLong(tableGioHang.getModel().getValueAt(i,4).toString());
            }
            if (ttKM.getText().equals("Không khuyến mãi"))
            {
                System.out.println(String.format("Tong tien cua hoa don co ma %d ( khong khuyen mai)", n));
                System.out.println(sum);
                d = new HoaDonDTO(n, MaNV , kh.getMaKH(), null, 0, sum);
                them_hd_va_cthd_vao_csdl(MaNV, n, o, null , hd, d);
            } else
            {
                String[] e = ttKM.getText().split("\\s+");
                int MaKM = Integer.parseInt(e[0]);
                ChiTietKMDAO ctkmdao = new ChiTietKMDAO();
                ChiTietKMDTO ctkm = ctkmdao.timKiemCTKM(MaKM);
                if (ctkm != null)
                {
                    // Khuyến mãi và tông tiền
                    double discount = (double) ctkm.getPhanTramGiam();
                    long min_decrease = ctkm.getToithieugiam();
                    double money = (discount / 100) *(double) sum;  // Số tiền giảm
                    System.out.println(String.format("Tong tien cua hoa don chua them khuyen mai la %d", sum));
                    System.out.println(String.format("So tien giam cua hoa don co ma %d la %.0f", n, money));
                    long sum_with_discount = 0;
                    if (money <= min_decrease){
                        sum_with_discount = sum - min_decrease;
                    } else{
                        sum_with_discount = sum - (long)money;
                    }
                    System.out.println(String.format("Tong tien cua hoa don co ma %d ( voi ma khuyen mai %d)", n, MaKM));
                    System.out.println(sum_with_discount);
                    d = new HoaDonDTO(n, MaNV, kh.getMaKH(), MaKM, (long) money, sum_with_discount);
                    them_hd_va_cthd_vao_csdl(MaNV, n, o, MaKM, hd, d);
                }
            }
        }
    }
    
    private void them_hd_va_cthd_vao_csdl(int MaNV, int n, int o, Integer maCTKM , HoaDonBUS hd, HoaDonDTO d){
        // Một cái hash map có key là mã sản phẩm và value là số lượng sản phẩm
        HashMap<Integer, Integer> dssp_damua= new HashMap<>();
        Boolean flag = true; // Kiểm tra coi là thêm chi tiết hoá đơn vào csdl có thành công
        int sodong_giohang = tableGioHang.getRowCount();
        for (int i = 0; i < sodong_giohang; i++){
            int MaSP = (Integer) tableGioHang.getModel().getValueAt(i, 0);
            int slSP = (Integer) tableGioHang.getModel().getValueAt(i, 3);
            long dongiaSP = (long) tableGioHang.getModel().getValueAt(i, 2);
            long thanhtien = (long) tableGioHang.getModel().getValueAt(i, 4);
            int l = o + i;
            ChiTietHoaDonDTO cthd = new ChiTietHoaDonDTO(l, n, MaSP , MaNV, maCTKM, slSP, dongiaSP, thanhtien);
            if( hd.addDetailReceipt(cthd) == false){
                System.out.println("Them CTHD Vao CSDL That Bai");
                flag=false;
                break;
            }else{
                flag=true;
                dssp_damua.put(MaSP, slSP);
            }
        }
        Boolean kt = hd.addReceipt(d);
        if(kt == false){
            JOptionPane.showMessageDialog(this, "Thêm Hoá Đơn Thất Bại" , "Lỗi", JOptionPane.ERROR_MESSAGE);       
        }else{
            if(flag){
                boolean suaSP = true;
                JOptionPane.showMessageDialog(this, "Thêm Hoá Đơn Thành Công!");
                for (int MaSP : dssp_damua.keySet()){
                    SanPhamDAO spdao = new SanPhamDAO();
                    ArrayList<SanPhamDTO> dssp = spdao.timKiemSanPham(String.valueOf(MaSP));
                    SanPhamDTO sp = dssp.get(0);
                    int slht = sp.getSoLuong();
                    sp.setSoLuong(slht - dssp_damua.get(MaSP));
                    if (!spdao.suaSanPham(sp))
                    {
                        System.out.println("Sua San Pham Bi Loi");
                        suaSP = false;
                        break;
                    }
                }
                if (suaSP)
                {
                    loadDataFromSource();
                    updateUI();
                }
                
            } else{
                JOptionPane.showMessageDialog(this, "Thêm Toàn Bộ Chi Tiết Hoá Đơn Vào CSDL Thất Bại" , "Lỗi", JOptionPane.ERROR_MESSAGE);    
                JOptionPane.showMessageDialog(this, "Thêm Hoá Đơn Thất Bại" , "Lỗi", JOptionPane.ERROR_MESSAGE);  
            }         
        }
    }
    
    
    private void ThemVaoGioHang(){
        int row = tableSanPham.getSelectedRow();
        if (row == -1)
        {
            JOptionPane.showMessageDialog(this, "Bạn chưa chọn dòng trong bảng sản phẩm" , "Lỗi", JOptionPane.ERROR_MESSAGE);        
        } else
        {
            DefaultTableModel modelBasket = (DefaultTableModel) tableGioHang.getModel();
            GioHangDTO a = lay_du_lieu_CTSP();
            Object[] rowData = {
                a.getMaSP(),
                a.getTenSP(),
                a.getDonGia(),
                a.getSoLuong(),
                a.getThanhTien()
            };
            modelBasket.addRow(rowData);
            tableSanPham.clearSelection();
            JOptionPane.showMessageDialog(this, "Thêm thông tin vao bảng giỏ hàng thành công!");
        }
    }
    
    public GioHangDTO lay_du_lieu_CTSP(){
        //"Mã SP", "Tên SP", "Đơn Giá", "Số Lượng", "Thành Tiền"
        int MaSP = -1; // Giá trị mặc định không hợp lệ để kiểm tra
        String TenSP = null;
        long DonGia = -1L; // Giá trị mặc định không hợp lệ
        int SL = 0;
        for (int i = 0; i < fields.length; i++){
            if (fields[i] instanceof JTextField)
            {
                JTextField a = (JTextField) fields[i];
                switch (i)
                {
                    case 0:
                        MaSP = Integer.parseInt(a.getText());
                        break;
                    case 1:
                        TenSP = a.getText();
                        break;
                    case 2:
                        DonGia = Long.parseLong(a.getText());
                        break;
                }
                a.setForeground(Color.blue);
            } else if (fields[i] instanceof JSpinner) {
                JSpinner b = (JSpinner) fields[i];
                SL = (Integer) b.getValue();
            }   
        }
        GioHangDTO a = new GioHangDTO(MaSP, TenSP, DonGia, SL);
        return a;
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
            public void onCustomerSelected(int maKH,String tenKH) {
                ttKH.setText(String.format("%d  %s", maKH, tenKH));
            }

            @Override
            public void onDiscountSelected(Integer maKM,String tenKM) {
                // Không dùng
            }
        });
        a.setVisible(true);
        a.setLocationRelativeTo(null);// Ép kích thước hiển thị
        a.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    private void showTableDiscount(){
        InforDiscount b = new InforDiscount();
        b.setSelectDiscountListener(new SelectCustomerOrDiscount() {
            @Override
            public void onCustomerSelected(int maKH,String tenKH) {
                // Không dùng
            }

            @Override
            public void onDiscountSelected(Integer maKM,String tenKM) {
                if(tenKM == null && maKM == null){
                    ttKM.setText("Không khuyến mãi");
                }else
                    ttKM.setText(String.format("%d  %s", maKM, tenKM));
            }
        });
        b.setVisible(true);
        b.setLocationRelativeTo(null);
        b.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
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
        int startYTop = 10; // Tọa độ Y ban đầu
        int buttonWidth1 = 32;
        int buttonHeight1 = 32;

        JLabel lblChiTietSP = new JLabel("Chi Tiết Sản Phẩm");
        lblChiTietSP.setFont(new java.awt.Font("Times New Roman", 1, 20));
        // Lấy kích thước ưu tiên của label để tính toán
        int labelWidth = 165;
        int labelHeight = 30; // Chiều cao bạn đã đặt

        // Tạo nút với icon Refresh
        ImageIcon refreshIcon = new ImageIcon("src/main/resources/image/Refresh-icon.png"); // Đảm bảo đường dẫn đúng
        Image scaledRefresh = refreshIcon.getImage().getScaledInstance(buttonWidth1, buttonHeight1, Image.SCALE_SMOOTH);
        btnRefreshChiTietSP = new JButton(new ImageIcon(scaledRefresh));
        btnRefreshChiTietSP.setToolTipText("Làm mới Chi Tiết SP");
        btnRefreshChiTietSP.setPreferredSize(new Dimension(buttonWidth1, buttonHeight1)); // Đặt kích thước ưu tiên cho nút

        // Tính toán tổng chiều rộng cần thiết
        int totalComponentWidth = labelWidth + buttonWidth1;

        // Tính toán vị trí X bắt đầu để căn giữa
        int startX = panelRightStartX + (panelRightWidth - totalComponentWidth) / 2;

        // Đặt vị trí cho label
        lblChiTietSP.setBounds(startX, startYTop, labelWidth, labelHeight);
        panelBanHang.add(lblChiTietSP);

        // Đặt vị trí cho nút, căn giữa theo chiều dọc với label (tính toán Y)
        int buttonY = startYTop + (labelHeight - buttonHeight1) / 2;
        btnRefreshChiTietSP.setBounds(startX + labelWidth, buttonY, buttonWidth1, buttonHeight1);
        panelBanHang.add(btnRefreshChiTietSP);


        // Ví dụ hành động khi bấm nút (giữ nguyên)
        btnRefreshChiTietSP.addActionListener(e -> {
            reset_toan_bo_field();
        });



        String[] labels = {"Mã SP:", "Tên SP:", "Đơn Giá:", "Số Lượng Mua:", "Loại SP:", "Nhân Viên Lập:", "Khách Hàng:", "Khuyến Mãi:"};
        fields = new JComponent[labels.length];
        int currentY = startYTop + labelHeight + 10; ;

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
        tabbedPane.addTab("Bán Hàng", panelBanHang);
        tabbedPane.addTab("Hoá Đơn", panelHoaDon);

        // Add tabbedPane to the main panel
        setLayout(new BorderLayout());
        add(tabbedPane, BorderLayout.CENTER);

        setPreferredSize(new Dimension(1250, 750));
        
        setBackgroundForAllComponents(this);
    }

    private void reset_toan_bo_field(){
        String c = "";
        for (int i = 0; i < fields.length; i++){
            if (fields[i] instanceof JTextField)
            {
                JTextField a = (JTextField) fields[i];
                switch (i)
                {
                    case 0:
                        a.setText(c);
                        break;
                    case 1:
                        a.setText(c);
                        break;
                    case 2:
                        a.setText(c);
                        break;
                    case 4:
                        a.setText(c);
                        break;
                    case 5:
                        a.setText(c);
                        break;
                }
                a.setForeground(Color.blue);
            } else if (fields[i] instanceof JSpinner) {
                JSpinner b = (JSpinner) fields[i];
                b.setModel(new SpinnerNumberModel(0, 0, slTD, 1));
                b.setEnabled(false);
            }   
        }
        ttKH.setText(c);
        ttKM.setText(c);
        loadProductImage(DEFAULT_IMAGE_PATH);
        JOptionPane.showMessageDialog(this, "Đã làm mới chi tiết sản phẩm!");
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
        ){
            @Override
            public boolean isCellEditable(int row, int column)
            {
                return false;
            }
            
        });
        tableSanPham.getColumnModel().getColumn(1).setPreferredWidth(200);
        tableSanPham.getColumnModel().getColumn(4).setPreferredWidth(100);
        tableSanPham.setFont(new java.awt.Font("Times New Roman", 0, 18));
        tableSanPham.setRowHeight(25);
        // Click dòng trong bảng sản phẩm thì bỏ chọn dòng trong bảng giỏ hàng 
        tableSanPham.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e)
            {
                int row = tableSanPham.rowAtPoint(e.getPoint());
                if (row != -1) {
                    xuly_chonhang_bangsanpham(tk, row);
                } 
            }

            @Override
            public void mouseReleased(MouseEvent e)
            {
                int row = tableSanPham.rowAtPoint(e.getPoint());
                if (row != -1) {
                    tableGioHang.clearSelection();
                } 
            }
        });


        // --- Bảng Giỏ Hàng ---
        tableGioHang.setModel(new DefaultTableModel(
                new Object[][]{},
                new String[]{"Mã Sản Phẩm", "Tên Sản Phẩm", "Đơn Giá", "Số Lượng", "Thành Tiền"}
        ) {
             @Override
             public boolean isCellEditable(int row, int column) { return false; } 
        });
        tableGioHang.setFont(new java.awt.Font("Times New Roman", 0, 18));
        tableGioHang.setRowHeight(25);
        // Click dòng trong bảng giỏ hàng thì bỏ chọn dòng trong bảng sản phẩm        
        tableGioHang.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = tableGioHang.rowAtPoint(e.getPoint());
                if (row != -1) {
                    xuly_chonhang_banggiohang(tk, row);
                }
            }
            
            @Override
            public void mouseReleased(MouseEvent e)
            {
                int row = tableGioHang.rowAtPoint(e.getPoint());
                if (row != -1) {
                    tableSanPham.clearSelection();
                }
            }
        });

    }
    // Phương thức nạp dữ liệu động từ BUS/DAO
    private void loadDataFromSource() {
        DefaultTableModel modelBasket = (DefaultTableModel) tableGioHang.getModel();
        modelBasket.setRowCount(0);
        // --- Nạp dữ liệu cho Bảng Hóa Đơn ---
        DefaultTableModel modelHoaDon = (DefaultTableModel) tableHoaDon.getModel();
        modelHoaDon.setRowCount(0);
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
        }

        // --- Nạp Bảng Chi Tiết Hóa Đơn: Sẽ được gọi khi chọn hóa đơn ---
        // Xóa dữ liệu bảng chi tiết khi mới load panel
        DefaultTableModel modelChiTiet = (DefaultTableModel) tableChiTietHoaDon.getModel();
        modelChiTiet.setRowCount(0);
    }
    
    private void xuly_chonhang_bangsanpham(ArrayList<Object> tk, int row){
        int maSP = (Integer) tableSanPham.getModel().getValueAt(row, 0);
        String tenSP = (String) tableSanPham.getModel().getValueAt(row, 1);
        long dongia = (long) tableSanPham.getModel().getValueAt(row, 2);
        int slSP = (Integer) tableSanPham.getModel().getValueAt(row, 3);
        slTD = slSP;
        String loaiSP = (String) tableSanPham.getValueAt(tableSanPham.getSelectedRow(), 4);;
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
                }
                a.setForeground(Color.blue);
            } else if (fields[i] instanceof JSpinner) {
                JSpinner b = (JSpinner) fields[i];
                JComponent editor = b.getEditor();
                JFormattedTextField textField = ((JSpinner.DefaultEditor) editor).getTextField();
                // Xóa border của text box và điều chỉnh padding
                textField.setBorder(new EmptyBorder(0, 2, 0, 0));
                textField.setHorizontalAlignment(JTextField.LEFT); // Căn phải text
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
                chanNhapJSpinner(b);
            } 
        }
        HoaDonBUS hd = new HoaDonBUS();
        loadProductImage(hd.timAnhChoSanPham(String.valueOf(maSP)));
    }
    
    private void chanNhapJSpinner(JSpinner spinner) {
        // Lấy editor của spinner
        JComponent editor = spinner.getEditor();

        if (editor instanceof JSpinner.DefaultEditor defaultEditor) {
            JFormattedTextField textField = defaultEditor.getTextField();

            // Chặn người dùng gõ tay
            textField.setEditable(false);

            // Không cho focus bằng tab
            textField.setFocusable(false);
            textField.setBackground(Color.white);
        }
    }

    private void xuly_chonhang_banggiohang(ArrayList<Object> tk, int row){
        //"Mã Sản Phẩm", "Tên Sản Phẩm", "Đơn Giá", "Số Lượng", "Thành Tiền"
        int maSP = (Integer) tableGioHang.getModel().getValueAt(row, 0);
        String tenSP = (String) tableGioHang.getModel().getValueAt(row, 1);
        String loaiSP = null;
        long dongia = (long) tableGioHang.getModel().getValueAt(row, 2);
        int slSP = (Integer) tableGioHang.getModel().getValueAt(row, 3);
        slTD = slSP;
        SanPhamDAO e = new SanPhamDAO();
        ArrayList<SanPhamDTO> dsSP = e.timKiemSanPham(String.valueOf(maSP));
        for (SanPhamDTO spdto: dsSP){
            loaiSP = spdto.getLoai();
        }
        
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
                chanNhapJSpinner(b);
                b.setValue(slSP);
                b.setEnabled(false);
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
