import BUS.CTPhieuNhapBUS;
import BUS.PhieuNhapBUS;
import DTO.CTPhieuNhapDTO;
import DTO.PhieuNhapDTO;
import com.toedter.calendar.JDateChooser;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.*;
import java.io.File;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class PhieuNhapGUI extends JPanel {
    private PhieuNhapBUS phieuNhapBUS;
    private CTPhieuNhapBUS ctPhieuNhapBUS;
    private JPanel pnContent;
    private JTextField txtSearchMaPN;
    private JTable phieuNhapTable;
    private DefaultTableModel phieuNhapModel;

    public PhieuNhapGUI() {
        phieuNhapBUS = new PhieuNhapBUS();
        ctPhieuNhapBUS = new CTPhieuNhapBUS();
        initComponents();
    }

    private void initComponents() {
        setLayout(new BorderLayout());

        // Panel nút chức năng
        JPanel pnFunctionButtons = new JPanel(new GridLayout(1, 4, 5, 5));
        JButton btnThem = new JButton("Thêm");
        JButton btnSua = new JButton("Sửa");
        JButton btnTimKiem = new JButton("Tìm kiếm");
        JButton btnXuatExcel = new JButton("Xuất Excel");

        // Thêm màu cho các nút
        btnThem.setBackground(new Color(34, 139, 34)); // Xanh lá
        btnThem.setForeground(Color.WHITE);
        btnSua.setBackground(new Color(255, 215, 0)); // Vàng
        btnSua.setForeground(Color.BLACK);
        btnTimKiem.setBackground(new Color(30, 144, 255)); // Xanh dương
        btnTimKiem.setForeground(Color.WHITE);
        btnXuatExcel.setBackground(new Color(0, 100, 0)); // Xanh đậm
        btnXuatExcel.setForeground(Color.WHITE);

        btnThem.addActionListener(e -> showThemPanel());
        btnSua.addActionListener(e -> showSuaPanel());
        btnTimKiem.addActionListener(e -> showTimKiemPanel());
        btnXuatExcel.addActionListener(e -> showXuatExcelPanel());
        pnFunctionButtons.add(btnThem);
        pnFunctionButtons.add(btnSua);
        pnFunctionButtons.add(btnTimKiem);
        pnFunctionButtons.add(btnXuatExcel);

        // Panel nội dung (ban đầu rỗng)
        pnContent = new JPanel(new BorderLayout());
        pnContent.add(new JLabel("Chọn chức năng từ các nút trên", JLabel.CENTER));

        // Panel hình nền
        JPanel pnBackground = new JPanel(new BorderLayout());
        try {
            ImageIcon backgroundImage = new ImageIcon(getClass().getResource("/ManagerUI/bgnhaphang"));
            JLabel lblBackground = new JLabel(backgroundImage);
            lblBackground.setHorizontalAlignment(JLabel.CENTER);
            pnBackground.add(lblBackground, BorderLayout.CENTER);
        } catch (Exception e) {

        }

        // Panel chính
        JPanel pnMain = new JPanel(new BorderLayout());
        pnMain.add(pnFunctionButtons, BorderLayout.NORTH);
        pnMain.add(pnContent, BorderLayout.CENTER);
        pnMain.add(pnBackground, BorderLayout.SOUTH);

        add(pnMain);
    }

    private void showThemPanel() {
        pnContent.removeAll();
        JPanel pnThem = new JPanel(new BorderLayout(10, 10));

        // Panel nhập liệu phiếu nhập
        JPanel pnPhieuNhapInput = new JPanel(new GridLayout(5, 2, 8, 8));
        pnPhieuNhapInput.setBorder(BorderFactory.createTitledBorder("Thông tin phiếu nhập"));
        Font font = new Font("Arial", Font.PLAIN, 11);
        JLabel lblMaPN = new JLabel("Mã PN:");
        JLabel lblMaNCC = new JLabel("Mã NCC:");
        JLabel lblMaNV = new JLabel("Mã NV:");
        JLabel lblTongTien = new JLabel("Tổng tiền:");
        JLabel lblNgayLap = new JLabel("Ngày lập:");
        JTextField txtMaPN = new JTextField();
        JTextField txtMaNCC = new JTextField();
        JTextField txtMaNV = new JTextField();
        JTextField txtTongTien = new JTextField();
        txtTongTien.setEditable(false);
        JDateChooser dateNgayLap = new JDateChooser();
        lblMaPN.setFont(font);
        lblMaNCC.setFont(font);
        lblMaNV.setFont(font);
        lblTongTien.setFont(font);
        lblNgayLap.setFont(font);
        txtMaPN.setFont(font);
        txtMaNCC.setFont(font);
        txtMaNV.setFont(font);
        txtTongTien.setFont(font);
        dateNgayLap.setFont(font);
        txtMaPN.setPreferredSize(new Dimension(80, 20));
        txtMaNCC.setPreferredSize(new Dimension(80, 20));
        txtMaNV.setPreferredSize(new Dimension(80, 20));
        txtTongTien.setPreferredSize(new Dimension(80, 20));
        pnPhieuNhapInput.add(lblMaPN);
        pnPhieuNhapInput.add(txtMaPN);
        pnPhieuNhapInput.add(lblMaNCC);
        pnPhieuNhapInput.add(txtMaNCC);
        pnPhieuNhapInput.add(lblMaNV);
        pnPhieuNhapInput.add(txtMaNV);
        pnPhieuNhapInput.add(lblTongTien);
        pnPhieuNhapInput.add(txtTongTien);
        pnPhieuNhapInput.add(lblNgayLap);
        pnPhieuNhapInput.add(dateNgayLap);

        // Panel nhập liệu chi tiết phiếu nhập
        JPanel pnCTPhieuNhapInput = new JPanel(new GridLayout(4, 2, 8, 8));
        pnCTPhieuNhapInput.setBorder(BorderFactory.createTitledBorder("Chi tiết phiếu nhập"));
        JLabel lblMaNL = new JLabel("Mã NL:");
        JLabel lblSoLuong = new JLabel("Số lượng:");
        JLabel lblDonGia = new JLabel("Đơn giá:");
        JLabel lblThanhTien = new JLabel("Thành tiền:");
        JTextField txtMaNL = new JTextField();
        JTextField txtSoLuong = new JTextField();
        JTextField txtDonGia = new JTextField();
        JTextField txtThanhTien = new JTextField();
        txtThanhTien.setEditable(false);
        lblMaNL.setFont(font);
        lblSoLuong.setFont(font);
        lblDonGia.setFont(font);
        lblThanhTien.setFont(font);
        txtMaNL.setFont(font);
        txtSoLuong.setFont(font);
        txtDonGia.setFont(font);
        txtThanhTien.setFont(font);
        txtMaNL.setPreferredSize(new Dimension(80, 20));
        txtSoLuong.setPreferredSize(new Dimension(80, 20));
        txtDonGia.setPreferredSize(new Dimension(80, 20));
        txtThanhTien.setPreferredSize(new Dimension(80, 20));
        pnCTPhieuNhapInput.add(lblMaNL);
        pnCTPhieuNhapInput.add(txtMaNL);
        pnCTPhieuNhapInput.add(lblSoLuong);
        pnCTPhieuNhapInput.add(txtSoLuong);
        pnCTPhieuNhapInput.add(lblDonGia);
        pnCTPhieuNhapInput.add(txtDonGia);
        pnCTPhieuNhapInput.add(lblThanhTien);
        pnCTPhieuNhapInput.add(txtThanhTien);

        // Tự động tính thành tiền
        txtSoLuong.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                updateThanhTien(txtSoLuong, txtDonGia, txtThanhTien);
            }
        });
        txtDonGia.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                updateThanhTien(txtSoLuong, txtDonGia, txtThanhTien);
            }
        });

        // Bảng danh sách chi tiết phiếu nhập
        DefaultTableModel ctPhieuNhapModel = new DefaultTableModel(new String[]{"Mã PN", "Mã NL", "Số lượng", "Đơn giá", "Thành tiền"}, 0);
        JTable ctPhieuNhapTable = new JTable(ctPhieuNhapModel);
        ctPhieuNhapTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                int row = ctPhieuNhapTable.getSelectedRow();
                if (row >= 0) {
                    txtMaNL.setText(String.valueOf(ctPhieuNhapModel.getValueAt(row, 1)));
                    txtSoLuong.setText(String.valueOf(ctPhieuNhapModel.getValueAt(row, 2)));
                    txtDonGia.setText(String.valueOf(ctPhieuNhapModel.getValueAt(row, 3)));
                    txtThanhTien.setText(String.valueOf(ctPhieuNhapModel.getValueAt(row, 4)));
                }
            }
        });
        JScrollPane scrollPane = new JScrollPane(ctPhieuNhapTable);

        // Nút chức năng chi tiết
        JPanel pnCTButtons = new JPanel(new FlowLayout());
        JButton btnThemCT = new JButton("Thêm CT");
        JButton btnSuaCT = new JButton("Sửa CT");
        JButton btnXoaCT = new JButton("Xóa CT");
        btnThemCT.setBackground(new Color(30, 144, 255));
        btnSuaCT.setBackground(new Color(30, 144, 255));
        btnXoaCT.setBackground(new Color(30, 144, 255));
        btnThemCT.setForeground(Color.WHITE);
        btnSuaCT.setForeground(Color.WHITE);
        btnXoaCT.setForeground(Color.WHITE);
        btnThemCT.addActionListener(e -> themCTPhieuNhap(txtMaPN, txtMaNL, txtSoLuong, txtDonGia, txtThanhTien, ctPhieuNhapModel, txtTongTien));
        btnSuaCT.addActionListener(e -> suaCTPhieuNhap(txtMaPN, txtMaNL, txtSoLuong, txtDonGia, txtThanhTien, ctPhieuNhapModel, txtTongTien));
        btnXoaCT.addActionListener(e -> xoaCTPhieuNhap(txtMaPN, txtMaNL, ctPhieuNhapModel, txtTongTien));
        pnCTButtons.add(btnThemCT);
        pnCTButtons.add(btnSuaCT);
        pnCTButtons.add(btnXoaCT);

        // Nút xác nhận phiếu nhập
        JPanel pnButton = new JPanel(new FlowLayout());
        JButton btnXacNhan = new JButton("Xác nhận");
        btnXacNhan.setBackground(new Color(30, 144, 255));
        btnXacNhan.setForeground(Color.WHITE);
        btnXacNhan.addActionListener(e -> themPhieuNhap(txtMaPN, txtMaNCC, txtMaNV, txtTongTien, dateNgayLap, ctPhieuNhapModel));
        pnButton.add(btnXacNhan);

        // Sắp xếp giao diện
        JPanel pnInputs = new JPanel(new GridLayout(1, 2, 10, 10));
        pnInputs.add(pnPhieuNhapInput);
        pnInputs.add(pnCTPhieuNhapInput);
        JPanel pnCenter = new JPanel(new BorderLayout(10, 10));
        pnCenter.add(pnCTButtons, BorderLayout.NORTH);
        pnCenter.add(scrollPane, BorderLayout.CENTER);
        pnThem.add(pnInputs, BorderLayout.NORTH);
        pnThem.add(pnCenter, BorderLayout.CENTER);
        pnThem.add(pnButton, BorderLayout.SOUTH);

        pnContent.add(pnThem, BorderLayout.CENTER);
        pnContent.revalidate();
        pnContent.repaint();
    }

    private void showSuaPanel() {
        pnContent.removeAll();
        JPanel pnSua = new JPanel(new BorderLayout());

        // Panel tìm kiếm mã PN
        JPanel pnSearch = new JPanel(new FlowLayout());
        JLabel lblSearchMaPN = new JLabel("Tìm Mã PN:");
        txtSearchMaPN = new JTextField(10);
        JButton btnSearch = new JButton("Tìm kiếm");
        lblSearchMaPN.setFont(new Font("Arial", Font.PLAIN, 12));
        txtSearchMaPN.setFont(new Font("Arial", Font.PLAIN, 12));
        btnSearch.setBackground(new Color(30, 144, 255));
        btnSearch.setForeground(Color.WHITE);
        btnSearch.addActionListener(e -> searchPhieuNhapForSua());
        pnSearch.add(lblSearchMaPN);
        pnSearch.add(txtSearchMaPN);
        pnSearch.add(btnSearch);

        // Bảng danh sách phiếu nhập
        phieuNhapModel = new DefaultTableModel(new String[]{"Mã PN", "Mã NCC", "Mã NV", "Tổng tiền", "Ngày lập"}, 0);
        phieuNhapTable = new JTable(phieuNhapModel);
        phieuNhapTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                int row = phieuNhapTable.getSelectedRow();
                if (row >= 0) {
                    int maPN = (int) phieuNhapModel.getValueAt(row, 0);
                    showEditDialog(maPN);
                }
            }
        });
        loadPhieuNhapTable();
        JScrollPane scrollPane = new JScrollPane(phieuNhapTable);

        pnSua.add(pnSearch, BorderLayout.NORTH);
        pnSua.add(scrollPane, BorderLayout.CENTER);

        pnContent.add(pnSua, BorderLayout.CENTER);
        pnContent.revalidate();
        pnContent.repaint();
    }

    private void showEditDialog(int maPN) {
        PhieuNhapDTO pn = phieuNhapBUS.TimTheoMaPN(maPN);
        if (pn == null) {
            JOptionPane.showMessageDialog(this, "Không tìm thấy phiếu nhập!");
            return;
        }

        JDialog dialog = new JDialog(SwingUtilities.getWindowAncestor(this), "Sửa Phiếu Nhập", Dialog.ModalityType.APPLICATION_MODAL);
        dialog.setSize(800, 600);
        dialog.setLocationRelativeTo(this);
        JPanel pnEdit = new JPanel(new BorderLayout());

        // Panel nhập liệu phiếu nhập
        JPanel pnPhieuNhapInput = new JPanel(new GridLayout(5, 2, 5, 5));
        pnPhieuNhapInput.setBorder(BorderFactory.createTitledBorder("Thông tin phiếu nhập"));
        Font font = new Font("Arial", Font.PLAIN, 12);
        JLabel lblMaPN = new JLabel("Mã PN:");
        JLabel lblMaNCC = new JLabel("Mã NCC:");
        JLabel lblMaNV = new JLabel("Mã NV:");
        JLabel lblTongTien = new JLabel("Tổng tiền:");
        JLabel lblNgayLap = new JLabel("Ngày lập:");
        JTextField txtMaPN = new JTextField(String.valueOf(pn.getMaPN()));
        txtMaPN.setEditable(false);
        JTextField txtMaNCC = new JTextField(String.valueOf(pn.getMaNCC()));
        JTextField txtMaNV = new JTextField(String.valueOf(pn.getMaNV()));
        JTextField txtTongTien = new JTextField(pn.getTongTien().toString());
        txtTongTien.setEditable(false);
        JDateChooser dateNgayLap = new JDateChooser(pn.getNgayLap());
        lblMaPN.setFont(font); lblMaNCC.setFont(font); lblMaNV.setFont(font);
        lblTongTien.setFont(font); lblNgayLap.setFont(font);
        txtMaPN.setFont(font); txtMaNCC.setFont(font); txtMaNV.setFont(font);
        txtTongTien.setFont(font); dateNgayLap.setFont(font);
        txtMaNCC.setPreferredSize(new Dimension(100, 20));
        txtMaNV.setPreferredSize(new Dimension(100, 20));
        txtTongTien.setPreferredSize(new Dimension(100, 20));
        pnPhieuNhapInput.add(lblMaPN); pnPhieuNhapInput.add(txtMaPN);
        pnPhieuNhapInput.add(lblMaNCC); pnPhieuNhapInput.add(txtMaNCC);
        pnPhieuNhapInput.add(lblMaNV); pnPhieuNhapInput.add(txtMaNV);
        pnPhieuNhapInput.add(lblTongTien); pnPhieuNhapInput.add(txtTongTien);
        pnPhieuNhapInput.add(lblNgayLap); pnPhieuNhapInput.add(dateNgayLap);

        // Panel chi tiết phiếu nhập
        JPanel pnCTPhieuNhapInput = new JPanel(new GridLayout(4, 2, 5, 5));
        pnCTPhieuNhapInput.setBorder(BorderFactory.createTitledBorder("Chi tiết phiếu nhập"));
        JLabel lblMaNL = new JLabel("Mã NL:");
        JLabel lblSoLuong = new JLabel("Số lượng:");
        JLabel lblDonGia = new JLabel("Đơn giá:");
        JLabel lblThanhTien = new JLabel("Thành tiền:");
        JTextField txtMaNL = new JTextField();
        JTextField txtSoLuong = new JTextField();
        JTextField txtDonGia = new JTextField();
        JTextField txtThanhTien = new JTextField();
        txtThanhTien.setEditable(false);
        lblMaNL.setFont(font); lblSoLuong.setFont(font); lblDonGia.setFont(font); lblThanhTien.setFont(font);
        txtMaNL.setFont(font); txtSoLuong.setFont(font); txtDonGia.setFont(font); txtThanhTien.setFont(font);
        txtMaNL.setPreferredSize(new Dimension(100, 20));
        txtSoLuong.setPreferredSize(new Dimension(100, 20));
        txtDonGia.setPreferredSize(new Dimension(100, 20));
        txtThanhTien.setPreferredSize(new Dimension(100, 20));
        pnCTPhieuNhapInput.add(lblMaNL); pnCTPhieuNhapInput.add(txtMaNL);
        pnCTPhieuNhapInput.add(lblSoLuong); pnCTPhieuNhapInput.add(txtSoLuong);
        pnCTPhieuNhapInput.add(lblDonGia); pnCTPhieuNhapInput.add(txtDonGia);
        pnCTPhieuNhapInput.add(lblThanhTien); pnCTPhieuNhapInput.add(txtThanhTien);

        // Tự động tính thành tiền
        txtSoLuong.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                updateThanhTien(txtSoLuong, txtDonGia, txtThanhTien);
            }
        });
        txtDonGia.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                updateThanhTien(txtSoLuong, txtDonGia, txtThanhTien);
            }
        });

        // Bảng chi tiết phiếu nhập
        DefaultTableModel ctPhieuNhapModel = new DefaultTableModel(new String[]{"Mã PN", "Mã NL", "Số lượng", "Đơn giá", "Thành tiền"}, 0);
        JTable ctPhieuNhapTable = new JTable(ctPhieuNhapModel);
        ctPhieuNhapTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                int row = ctPhieuNhapTable.getSelectedRow();
                if (row >= 0) {
                    txtMaNL.setText(String.valueOf(ctPhieuNhapModel.getValueAt(row, 1)));
                    txtSoLuong.setText(String.valueOf(ctPhieuNhapModel.getValueAt(row, 2)));
                    txtDonGia.setText(String.valueOf(ctPhieuNhapModel.getValueAt(row, 3)));
                    txtThanhTien.setText(String.valueOf(ctPhieuNhapModel.getValueAt(row, 4)));
                }
            }
        });
        loadCTPhieuNhapTable(ctPhieuNhapModel, maPN);
        JScrollPane scrollPane = new JScrollPane(ctPhieuNhapTable);

        // Nút chức năng chi tiết
        JPanel pnCTButtons = new JPanel(new FlowLayout());
        JButton btnThemCT = new JButton("Thêm CT");
        JButton btnSuaCT = new JButton("Sửa CT");
        JButton btnXoaCT = new JButton("Xóa CT");
        btnThemCT.setBackground(new Color(30, 144, 255));
        btnSuaCT.setBackground(new Color(30, 144, 255));
        btnXoaCT.setBackground(new Color(30, 144, 255));
        btnThemCT.setForeground(Color.WHITE);
        btnSuaCT.setForeground(Color.WHITE);
        btnXoaCT.setForeground(Color.WHITE);
        btnThemCT.addActionListener(e -> themCTPhieuNhap(txtMaPN, txtMaNL, txtSoLuong, txtDonGia, txtThanhTien, ctPhieuNhapModel, txtTongTien));
        btnSuaCT.addActionListener(e -> suaCTPhieuNhap(txtMaPN, txtMaNL, txtSoLuong, txtDonGia, txtThanhTien, ctPhieuNhapModel, txtTongTien));
        btnXoaCT.addActionListener(e -> xoaCTPhieuNhap(txtMaPN, txtMaNL, ctPhieuNhapModel, txtTongTien));
        pnCTButtons.add(btnThemCT); pnCTButtons.add(btnSuaCT); pnCTButtons.add(btnXoaCT);

        // Nút xác nhận
        JPanel pnButton = new JPanel(new FlowLayout());
        JButton btnXacNhan = new JButton("Xác nhận");
        btnXacNhan.setBackground(new Color(30, 144, 255));
        btnXacNhan.setForeground(Color.WHITE);
        btnXacNhan.addActionListener(e -> {
            suaPhieuNhap(txtMaPN, txtMaNCC, txtMaNV, txtTongTien, dateNgayLap);
            dialog.dispose();
            loadPhieuNhapTable();
        });
        pnButton.add(btnXacNhan);

        // Gộp các nút vào panel phía dưới
        JPanel pnBottom = new JPanel(new BorderLayout());
        pnBottom.add(pnCTButtons, BorderLayout.NORTH);
        pnBottom.add(pnButton, BorderLayout.SOUTH);

        // Sắp xếp giao diện tổng thể
        JPanel pnInputs = new JPanel(new GridLayout(1, 2));
        pnInputs.add(pnPhieuNhapInput);
        pnInputs.add(pnCTPhieuNhapInput);
        pnEdit.add(pnInputs, BorderLayout.NORTH);
        pnEdit.add(scrollPane, BorderLayout.CENTER);
        pnEdit.add(pnBottom, BorderLayout.SOUTH);

        dialog.add(pnEdit);
        dialog.setVisible(true);
    }


    private void showTimKiemPanel() {
        pnContent.removeAll();
        JPanel pnTimKiem = new JPanel(new BorderLayout());

        // Panel tìm kiếm
        JPanel pnSearch = new JPanel(new GridLayout(5, 2, 5, 5));
        pnSearch.setBorder(BorderFactory.createTitledBorder("Tìm kiếm"));
        Font font = new Font("Arial", Font.PLAIN, 12);
        txtSearchMaPN = new JTextField();
        JTextField txtSearchMaNCC = new JTextField();
        JTextField txtSearchMaNV = new JTextField();
        JDateChooser dateSearchNgayNhap = new JDateChooser();
        JLabel lblMaPN = new JLabel("Mã PN:");
        JLabel lblMaNCC = new JLabel("Mã NCC:");
        JLabel lblMaNV = new JLabel("Mã NV:");
        JLabel lblNgayNhap = new JLabel("Ngày nhập:");
        lblMaPN.setFont(font);
        lblMaNCC.setFont(font);
        lblMaNV.setFont(font);
        lblNgayNhap.setFont(font);
        txtSearchMaPN.setFont(font);
        txtSearchMaNCC.setFont(font);
        txtSearchMaNV.setFont(font);
        dateSearchNgayNhap.setFont(font);
        txtSearchMaPN.setPreferredSize(new Dimension(100, 20));
        txtSearchMaNCC.setPreferredSize(new Dimension(100, 20));
        txtSearchMaNV.setPreferredSize(new Dimension(100, 20));
        JButton btnSearch = new JButton("Tìm kiếm");
        btnSearch.setBackground(new Color(30, 144, 255));
        btnSearch.setForeground(Color.WHITE);
        btnSearch.addActionListener(e -> searchPhieuNhap(txtSearchMaPN, txtSearchMaNCC, txtSearchMaNV, dateSearchNgayNhap));
        pnSearch.add(lblMaPN);
        pnSearch.add(txtSearchMaPN);
        pnSearch.add(lblMaNCC);
        pnSearch.add(txtSearchMaNCC);
        pnSearch.add(lblMaNV);
        pnSearch.add(txtSearchMaNV);
        pnSearch.add(lblNgayNhap);
        pnSearch.add(dateSearchNgayNhap);
        pnSearch.add(new JLabel(""));
        pnSearch.add(btnSearch);

        // Bảng kết quả tìm kiếm
        phieuNhapModel = new DefaultTableModel(new String[]{"Mã PN", "Mã NCC", "Mã NV", "Tổng tiền", "Ngày lập"}, 0);
        phieuNhapTable = new JTable(phieuNhapModel);
        JScrollPane scrollPane = new JScrollPane(phieuNhapTable);

        pnTimKiem.add(pnSearch, BorderLayout.NORTH);
        pnTimKiem.add(scrollPane, BorderLayout.CENTER);

        pnContent.add(pnTimKiem, BorderLayout.CENTER);
        pnContent.revalidate();
        pnContent.repaint();
    }

    private void showXuatExcelPanel() {
        pnContent.removeAll();
        JPanel pnXuatExcel = new JPanel(new BorderLayout(10, 10));
        pnXuatExcel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        pnXuatExcel.setBackground(Color.WHITE);

        // Thông báo
        JLabel lblInstruction = new JLabel("Nhấn nút dưới đây để xuất danh sách phiếu nhập ra file Excel", JLabel.CENTER);
        lblInstruction.setFont(new Font("Arial", Font.PLAIN, 16));
        lblInstruction.setForeground(Color.BLACK);

        // Nút xuất file
        JButton btnXuat = new JButton("Xuất File Excel");
        btnXuat.setBackground(new Color(30, 144, 255));
        btnXuat.setForeground(Color.WHITE);
        btnXuat.setFont(new Font("Arial", Font.BOLD, 16));
        btnXuat.setPreferredSize(new Dimension(200, 50));
        btnXuat.setBorder(BorderFactory.createRaisedBevelBorder());
        btnXuat.addActionListener(e -> xuatExcel());

        // Panel chứa nút
        JPanel pnButton = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        pnButton.setOpaque(false);
        pnButton.add(btnXuat);

        // Sắp xếp giao diện
        pnXuatExcel.add(lblInstruction, BorderLayout.NORTH);
        pnXuatExcel.add(pnButton, BorderLayout.CENTER);

        pnContent.add(pnXuatExcel, BorderLayout.CENTER);
        pnContent.revalidate();
        pnContent.repaint();
    }

    private void loadPhieuNhapTable() {
        phieuNhapModel.setRowCount(0);
        ArrayList<PhieuNhapDTO> dsPhieuNhap = phieuNhapBUS.LayDanhSachPhieuNhap();
        for (PhieuNhapDTO pn : dsPhieuNhap) {
            phieuNhapModel.addRow(new Object[]{pn.getMaPN(), pn.getMaNCC(), pn.getMaNV(), pn.getTongTien(), pn.getNgayLap()});
        }
    }

    private void loadCTPhieuNhapTable(DefaultTableModel model, int maPN) {
        model.setRowCount(0);
        ArrayList<CTPhieuNhapDTO> dsCTPN = ctPhieuNhapBUS.LayDanhSachCTPhieuNhap();
        for (CTPhieuNhapDTO ct : dsCTPN) {
            if (ct.getMaPN() == maPN) {
                model.addRow(new Object[]{ct.getMaPN(), ct.getMaNL(), ct.getSoLuong(), ct.getDonGia(), ct.getThanhTien()});
            }
        }
    }

    private void updateThanhTien(JTextField txtSoLuong, JTextField txtDonGia, JTextField txtThanhTien) {
        try {
            int soLuong = txtSoLuong.getText().isEmpty() ? 0 : Integer.parseInt(txtSoLuong.getText());
            double donGia = txtDonGia.getText().isEmpty() ? 0 : Double.parseDouble(txtDonGia.getText());
            double thanhTien = ctPhieuNhapBUS.TinhThanhTien(soLuong, donGia);
            txtThanhTien.setText(String.valueOf(thanhTien));
        } catch (NumberFormatException e) {
            txtThanhTien.setText("");
        }
    }
    private void updateTongTien(JTextField txtMaPN, JTextField txtTongTien, DefaultTableModel ctPhieuNhapModel) {
        try {
            double tongTien = 0;
            for (int i = 0; i < ctPhieuNhapModel.getRowCount(); i++) {
                tongTien += (Double) ctPhieuNhapModel.getValueAt(i, 4); // Cột Thành tiền
            }
            txtTongTien.setText(String.format("%.2f", tongTien));
        } catch (Exception e) {
            txtTongTien.setText("");
            JOptionPane.showMessageDialog(this, "Lỗi khi tính tổng tiền: " + e.getMessage());
        }
    }
    private void searchPhieuNhapForSua() {
        try {
            phieuNhapModel.setRowCount(0);
            String maPN = txtSearchMaPN.getText().trim();
            if (!maPN.isEmpty()) {
                PhieuNhapDTO pn = phieuNhapBUS.TimTheoMaPN(Integer.parseInt(maPN));
                if (pn != null) {
                    phieuNhapModel.addRow(new Object[]{pn.getMaPN(), pn.getMaNCC(), pn.getMaNV(), pn.getTongTien(), pn.getNgayLap()});
                } else {
                    JOptionPane.showMessageDialog(this, "Không tìm thấy phiếu nhập với mã: " + maPN);
                }
            } else {
                loadPhieuNhapTable();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi tìm kiếm: " + e.getMessage());
        }
    }

    private void themPhieuNhap(JTextField txtMaPN, JTextField txtMaNCC, JTextField txtMaNV, JTextField txtTongTien, JDateChooser dateNgayLap, DefaultTableModel ctPhieuNhapModel) {
        try {
            // Validate required fields for PhieuNhap
            if (txtMaPN.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Mã phiếu nhập không được để trống!");
                return;
            }
            if (!txtMaPN.getText().matches("\\d+")) {
                JOptionPane.showMessageDialog(this, "Mã phiếu nhập phải là số nguyên dương!");
                return;
            }
            int maPN = Integer.parseInt(txtMaPN.getText());
            if (phieuNhapBUS.KiemTraMaPhieuNhap(maPN)) {
                JOptionPane.showMessageDialog(this, "Mã phiếu nhập đã tồn tại!");
                return;
            }
            if (txtMaNCC.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Mã nhà cung cấp không được để trống!");
                return;
            }
            if (!txtMaNCC.getText().matches("\\d+")) {
                JOptionPane.showMessageDialog(this, "Mã nhà cung cấp phải là số nguyên dương!");
                return;
            }
            if (txtMaNV.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Mã nhân viên không được để trống!");
                return;
            }
            if (!txtMaNV.getText().matches("\\d+")) {
                JOptionPane.showMessageDialog(this, "Mã nhân viên phải là số nguyên dương!");
                return;
            }
            if (dateNgayLap.getDate() == null) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn ngày lập phiếu nhập!");
                return;
            }
            if (ctPhieuNhapModel.getRowCount() == 0) {
                JOptionPane.showMessageDialog(this, "Vui lòng thêm ít nhất một chi tiết phiếu nhập!");
                return;
            }

            // Create PhieuNhapDTO
            PhieuNhapDTO pn = new PhieuNhapDTO();
            pn.setMaPN(maPN);
            pn.setMaNCC(Integer.parseInt(txtMaNCC.getText()));
            pn.setMaNV(Integer.parseInt(txtMaNV.getText()));
            pn.setTongTien(new BigDecimal(txtTongTien.getText().isEmpty() ? "0" : txtTongTien.getText()));
            pn.setNgayLap(new Date(dateNgayLap.getDate().getTime()));
            boolean themPNThanhCong = phieuNhapBUS.ThemPhieuNhap(pn);

            // Add all chi tiết phiếu nhập from table
            boolean themCTPNThanhCong = true;
            if (themPNThanhCong) {
                for (int i = 0; i < ctPhieuNhapModel.getRowCount(); i++) {
                    CTPhieuNhapDTO ct = new CTPhieuNhapDTO();
                    ct.setMaPN(maPN);
                    ct.setMaNL((Integer) ctPhieuNhapModel.getValueAt(i, 1));
                    ct.setSoLuong((Integer) ctPhieuNhapModel.getValueAt(i, 2));
                    ct.setDonGia((Double) ctPhieuNhapModel.getValueAt(i, 3));
                    ct.setThanhTien((Double) ctPhieuNhapModel.getValueAt(i, 4));
                    try {
                        if (!ctPhieuNhapBUS.ThemCTPhieuNhap(ct)) {
                            JOptionPane.showMessageDialog(this, "Lỗi khi thêm chi tiết phiếu nhập (Mã NL: " + ct.getMaNL() + "). Kiểm tra mã nguyên liệu hoặc cơ sở dữ liệu!");
                            themCTPNThanhCong = false;
                            break;
                        }
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(this, "Lỗi khi thêm chi tiết phiếu nhập (Mã NL: " + ct.getMaNL() + "): " + ex.getMessage());
                        themCTPNThanhCong = false;
                        break;
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "Lỗi khi thêm phiếu nhập!");
                themCTPNThanhCong = false;
            }

            if (themPNThanhCong && themCTPNThanhCong) {
                JOptionPane.showMessageDialog(this, "Thêm phiếu nhập và chi tiết thành công!");
                // Clear inputs and table
                txtMaPN.setText("");
                txtMaNCC.setText("");
                txtMaNV.setText("");
                txtTongTien.setText("");
                dateNgayLap.setDate(null);
                ctPhieuNhapModel.setRowCount(0);
            } else {
                JOptionPane.showMessageDialog(this, "Thêm thất bại! Vui lòng kiểm tra dữ liệu nhập.");
                if (themPNThanhCong) {
                    phieuNhapBUS.XoaPhieuNhap(maPN); // Rollback
                }
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Dữ liệu nhập không hợp lệ! Vui lòng kiểm tra định dạng số.");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi hệ thống: " + e.getMessage());
        }
    }


    private void suaPhieuNhap(JTextField txtMaPN, JTextField txtMaNCC, JTextField txtMaNV, JTextField txtTongTien, JDateChooser dateNgayLap) {
        try {
            // Sửa phiếu nhập
            PhieuNhapDTO pn = new PhieuNhapDTO();
            pn.setMaPN(Integer.parseInt(txtMaPN.getText()));
            pn.setMaNCC(Integer.parseInt(txtMaNCC.getText()));
            pn.setMaNV(Integer.parseInt(txtMaNV.getText()));
            pn.setTongTien(new BigDecimal(txtTongTien.getText()));
            pn.setNgayLap(new Date(dateNgayLap.getDate().getTime()));
            boolean suaPNThanhCong = phieuNhapBUS.KiemTraMaPhieuNhap(pn.getMaPN()) && phieuNhapBUS.SuaPhieuNhap(pn);

            if (suaPNThanhCong) {
                JOptionPane.showMessageDialog(this, "Sửa phiếu nhập thành công!");
            } else {
                JOptionPane.showMessageDialog(this, "Sửa thất bại! Kiểm tra dữ liệu nhập.");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi: " + e.getMessage());
        }
    }


    private void themCTPhieuNhap(JTextField txtMaPN, JTextField txtMaNL, JTextField txtSoLuong, JTextField txtDonGia, JTextField txtThanhTien, DefaultTableModel ctPhieuNhapModel, JTextField txtTongTien) {
        try {
            // Kiểm tra mã phiếu nhập trước
            if (txtMaPN.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập mã phiếu nhập trước khi thêm chi tiết!");
                return;
            }
            if (!txtMaPN.getText().matches("\\d+")) {
                JOptionPane.showMessageDialog(this, "Mã phiếu nhập phải là số nguyên dương!");
                return;
            }
            int maPN = Integer.parseInt(txtMaPN.getText());

            // Kiểm tra mã nguyên liệu
            if (txtMaNL.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập mã nguyên liệu!");
                return;
            }
            if (!txtMaNL.getText().matches("\\d+")) {
                JOptionPane.showMessageDialog(this, "Mã nguyên liệu phải là số nguyên dương!");
                return;
            }
            int maNL = Integer.parseInt(txtMaNL.getText());

            // Kiểm tra số lượng
            if (txtSoLuong.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập số lượng!");
                return;
            }
            if (!txtSoLuong.getText().matches("\\d+")) {
                JOptionPane.showMessageDialog(this, "Số lượng phải là số nguyên dương!");
                return;
            }

            // Kiểm tra đơn giá
            if (txtDonGia.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập đơn giá!");
                return;
            }
            if (!txtDonGia.getText().matches("\\d+(\\.\\d+)?")) {
                JOptionPane.showMessageDialog(this, "Đơn giá phải là số thực không âm!");
                return;
            }

            // Kiểm tra cặp (MaPN, MaNL) trong cơ sở dữ liệu
            if (ctPhieuNhapBUS.KiemTraMaNL(maPN, maNL)) {
                JOptionPane.showMessageDialog(this, "Cặp (Mã PN: " + maPN + ", Mã NL: " + maNL + ") đã tồn tại trong cơ sở dữ liệu!");
                return;
            }

            // Tạo CTPhieuNhapDTO
            CTPhieuNhapDTO ct = new CTPhieuNhapDTO();
            ct.setMaPN(maPN);
            ct.setMaNL(maNL);
            ct.setSoLuong(Integer.parseInt(txtSoLuong.getText()));
            ct.setDonGia(Double.parseDouble(txtDonGia.getText()));
            ct.setThanhTien(Double.parseDouble(txtThanhTien.getText()));

            // Thêm vào cơ sở dữ liệu
            boolean themThanhCong = ctPhieuNhapBUS.ThemCTPhieuNhap(ct);
            if (themThanhCong) {
                // Thêm vào bảng tạm
                ctPhieuNhapModel.addRow(new Object[]{ct.getMaPN(), ct.getMaNL(), ct.getSoLuong(), ct.getDonGia(), ct.getThanhTien()});
                updateTongTien(txtMaPN, txtTongTien, ctPhieuNhapModel);
                JOptionPane.showMessageDialog(this, "Thêm chi tiết phiếu nhập thành công!");
                // Xóa trắng các ô nhập liệu
                txtMaNL.setText("");
                txtSoLuong.setText("");
                txtDonGia.setText("");
                txtThanhTien.setText("");
            } else {
                JOptionPane.showMessageDialog(this, "Thêm chi tiết phiếu nhập thất bại! Vui lòng kiểm tra dữ liệu.");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Dữ liệu nhập không đúng định dạng số! Vui lòng kiểm tra lại.");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi hệ thống khi thêm chi tiết phiếu nhập: " + e.getMessage());
        }
    }

    private void suaCTPhieuNhap(JTextField txtMaPN, JTextField txtMaNL, JTextField txtSoLuong, JTextField txtDonGia, JTextField txtThanhTien, DefaultTableModel ctPhieuNhapModel, JTextField txtTongTien) {
        try {
            // Validate inputs
            if (!txtMaPN.getText().matches("\\d+")) {
                JOptionPane.showMessageDialog(this, "Mã phiếu nhập phải là số nguyên dương!");
                return;
            }
            int maPN = Integer.parseInt(txtMaPN.getText());
            if (txtMaNL.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Mã nguyên liệu không được để trống!");
                return;
            }
            if (!txtMaNL.getText().matches("\\d+")) {
                JOptionPane.showMessageDialog(this, "Mã nguyên liệu phải là số nguyên dương!");
                return;
            }
            int maNL = Integer.parseInt(txtMaNL.getText());
            if (txtSoLuong.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Số lượng không được để trống!");
                return;
            }
            if (!txtSoLuong.getText().matches("\\d+")) {
                JOptionPane.showMessageDialog(this, "Số lượng phải là số nguyên dương!");
                return;
            }
            if (txtDonGia.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Đơn giá không được để trống!");
                return;
            }
            if (!txtDonGia.getText().matches("\\d+(\\.\\d+)?")) {
                JOptionPane.showMessageDialog(this, "Đơn giá phải là số thực không âm!");
                return;
            }

            // Kiểm tra xem chi tiết có tồn tại trong cơ sở dữ liệu
            if (!ctPhieuNhapBUS.KiemTraMaNL(maPN, maNL)) {
                JOptionPane.showMessageDialog(this, "Chi tiết phiếu nhập (Mã PN: " + maPN + ", Mã NL: " + maNL + ") không tồn tại trong cơ sở dữ liệu!");
                return;
            }

            // Tạo CTPhieuNhapDTO
            CTPhieuNhapDTO ct = new CTPhieuNhapDTO();
            ct.setMaPN(maPN);
            ct.setMaNL(maNL);
            ct.setSoLuong(Integer.parseInt(txtSoLuong.getText()));
            ct.setDonGia(Double.parseDouble(txtDonGia.getText()));
            ct.setThanhTien(Double.parseDouble(txtThanhTien.getText()));

            // Sửa trong cơ sở dữ liệu
            boolean suaThanhCong = ctPhieuNhapBUS.SuaCTPhieuNhap(ct);
            if (suaThanhCong) {
                // Cập nhật bảng tạm
                boolean found = false;
                for (int i = 0; i < ctPhieuNhapModel.getRowCount(); i++) {
                    if ((Integer) ctPhieuNhapModel.getValueAt(i, 1) == maNL) {
                        ctPhieuNhapModel.setValueAt(ct.getSoLuong(), i, 2);
                        ctPhieuNhapModel.setValueAt(ct.getDonGia(), i, 3);
                        ctPhieuNhapModel.setValueAt(ct.getThanhTien(), i, 4);
                        found = true;
                        break;
                    }
                }
                if (found) {
                    updateTongTien(txtMaPN, txtTongTien, ctPhieuNhapModel);
                    JOptionPane.showMessageDialog(this, "Sửa chi tiết phiếu nhập thành công!");
                    // Clear input fields
                    txtMaNL.setText("");
                    txtSoLuong.setText("");
                    txtDonGia.setText("");
                    txtThanhTien.setText("");
                } else {
                    JOptionPane.showMessageDialog(this, "Mã nguyên liệu " + maNL + " không tồn tại trong danh sách tạm!");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Sửa chi tiết phiếu nhập thất bại! Vui lòng kiểm tra dữ liệu.");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Dữ liệu nhập không hợp lệ! Vui lòng kiểm tra định dạng số.");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi hệ thống: " + e.getMessage());
        }
    }

    private void xoaCTPhieuNhap(JTextField txtMaPN, JTextField txtMaNL, DefaultTableModel ctPhieuNhapModel, JTextField txtTongTien) {
        try {
            if (!txtMaPN.getText().matches("\\d+")) {
                JOptionPane.showMessageDialog(this, "Mã phiếu nhập phải là số nguyên dương!");
                return;
            }
            int maPN = Integer.parseInt(txtMaPN.getText());
            if (txtMaNL.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Mã nguyên liệu không được để trống!");
                return;
            }
            if (!txtMaNL.getText().matches("\\d+")) {
                JOptionPane.showMessageDialog(this, "Mã nguyên liệu phải là số nguyên dương!");
                return;
            }
            int maNL = Integer.parseInt(txtMaNL.getText());

            // Kiểm tra xem chi tiết có tồn tại trong cơ sở dữ liệu
            if (!ctPhieuNhapBUS.KiemTraMaNL(maPN, maNL)) {
                JOptionPane.showMessageDialog(this, "Chi tiết phiếu nhập (Mã PN: " + maPN + ", Mã NL: " + maNL + ") không tồn tại trong cơ sở dữ liệu!");
                return;
            }

            // Xóa trong cơ sở dữ liệu
            boolean xoaThanhCong = ctPhieuNhapBUS.XoaCTPhieuNhap(maPN, maNL);
            if (xoaThanhCong) {
                // Xóa khỏi bảng tạm
                boolean found = false;
                for (int i = 0; i < ctPhieuNhapModel.getRowCount(); i++) {
                    if ((Integer) ctPhieuNhapModel.getValueAt(i, 1) == maNL) {
                        ctPhieuNhapModel.removeRow(i);
                        found = true;
                        break;
                    }
                }
                if (found) {
                    updateTongTien(txtMaPN, txtTongTien, ctPhieuNhapModel);
                    JOptionPane.showMessageDialog(this, "Xóa chi tiết phiếu nhập thành công!");
                    // Clear input fields
                    txtMaNL.setText("");
                } else {
                    JOptionPane.showMessageDialog(this, "Mã nguyên liệu " + maNL + " không tồn tại trong danh sách tạm!");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Xóa chi tiết phiếu nhập thất bại! Vui lòng kiểm tra dữ liệu.");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Dữ liệu nhập không hợp lệ! Vui lòng kiểm tra định dạng số.");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi hệ thống: " + e.getMessage());
        }
    }




    private void suaPhieuNhap(JTextField txtMaPN, JTextField txtMaNCC, JTextField txtMaNV, JTextField txtTongTien, JDateChooser dateNgayLap, DefaultTableModel ctPhieuNhapModel) {
        try {
            // Validate inputs
            if (!txtMaPN.getText().matches("\\d+")) {
                JOptionPane.showMessageDialog(this, "Mã phiếu nhập phải là số nguyên dương!");
                return;
            }
            int maPN = Integer.parseInt(txtMaPN.getText());
            if (txtMaNCC.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Mã nhà cung cấp không được để trống!");
                return;
            }
            if (!txtMaNCC.getText().matches("\\d+")) {
                JOptionPane.showMessageDialog(this, "Mã nhà cung cấp phải là số nguyên dương!");
                return;
            }
            if (txtMaNV.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Mã nhân viên không được để trống!");
                return;
            }
            if (!txtMaNV.getText().matches("\\d+")) {
                JOptionPane.showMessageDialog(this, "Mã nhân viên phải là số nguyên dương!");
                return;
            }
            if (dateNgayLap.getDate() == null) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn ngày lập phiếu nhập!");
                return;
            }
            if (ctPhieuNhapModel.getRowCount() == 0) {
                JOptionPane.showMessageDialog(this, "Vui lòng thêm ít nhất một chi tiết phiếu nhập!");
                return;
            }

            // Sửa phiếu nhập
            PhieuNhapDTO pn = new PhieuNhapDTO();
            pn.setMaPN(maPN);
            pn.setMaNCC(Integer.parseInt(txtMaNCC.getText()));
            pn.setMaNV(Integer.parseInt(txtMaNV.getText()));
            pn.setTongTien(new BigDecimal(txtTongTien.getText()));
            pn.setNgayLap(new Date(dateNgayLap.getDate().getTime()));
            boolean suaPNThanhCong = phieuNhapBUS.KiemTraMaPhieuNhap(pn.getMaPN()) && phieuNhapBUS.SuaPhieuNhap(pn);

            // Đồng bộ chi tiết phiếu nhập
            boolean suaCTPNThanhCong = true;
            if (suaPNThanhCong) {
                // Lấy danh sách chi tiết hiện tại từ cơ sở dữ liệu
                ArrayList<CTPhieuNhapDTO> dsCTHienTai = ctPhieuNhapBUS.LayDanhSachCTPhieuNhap().stream()
                        .filter(ct -> ct.getMaPN() == maPN)
                        .collect(Collectors.toCollection(ArrayList::new));

                // Danh sách chi tiết từ bảng tạm
                ArrayList<CTPhieuNhapDTO> dsCTMoi = new ArrayList<>();
                for (int i = 0; i < ctPhieuNhapModel.getRowCount(); i++) {
                    CTPhieuNhapDTO ct = new CTPhieuNhapDTO();
                    ct.setMaPN(maPN);
                    ct.setMaNL((Integer) ctPhieuNhapModel.getValueAt(i, 1));
                    ct.setSoLuong((Integer) ctPhieuNhapModel.getValueAt(i, 2));
                    ct.setDonGia((Double) ctPhieuNhapModel.getValueAt(i, 3));
                    ct.setThanhTien((Double) ctPhieuNhapModel.getValueAt(i, 4));
                    dsCTMoi.add(ct);
                }

                // Xóa các chi tiết không còn trong bảng tạm
                for (CTPhieuNhapDTO ctHienTai : dsCTHienTai) {
                    boolean tonTai = dsCTMoi.stream().anyMatch(ct -> ct.getMaNL() == ctHienTai.getMaNL());
                    if (!tonTai) {
                        if (!ctPhieuNhapBUS.XoaCTPhieuNhap(maPN, ctHienTai.getMaNL())) {
                            suaCTPNThanhCong = false;
                            break;
                        }
                    }
                }

                // Thêm hoặc sửa các chi tiết trong bảng tạm
                for (CTPhieuNhapDTO ctMoi : dsCTMoi) {
                    boolean tonTai = dsCTHienTai.stream().anyMatch(ct -> ct.getMaNL() == ctMoi.getMaNL());
                    if (tonTai) {
                        // Sửa chi tiết
                        if (!ctPhieuNhapBUS.SuaCTPhieuNhap(ctMoi)) {
                            suaCTPNThanhCong = false;
                            break;
                        }
                    } else {
                        // Thêm chi tiết mới
                        if (!ctPhieuNhapBUS.ThemCTPhieuNhap(ctMoi)) {
                            suaCTPNThanhCong = false;
                            break;
                        }
                    }
                }
            } else {
                suaCTPNThanhCong = false;
            }

            if (suaPNThanhCong && suaCTPNThanhCong) {
                JOptionPane.showMessageDialog(this, "Sửa phiếu nhập và chi tiết thành công!");
                // Tải lại bảng phiếu nhập
                loadPhieuNhapTable();
            } else {
                JOptionPane.showMessageDialog(this, "Sửa thất bại! Vui lòng kiểm tra dữ liệu nhập.");
                // Nếu sửa chi tiết thất bại, có thể cần rollback sửa phiếu nhập
                if (suaPNThanhCong) {
                    // Rollback sửa phiếu nhập nếu cần
                    PhieuNhapDTO pnCu = phieuNhapBUS.TimTheoMaPN(maPN);
                    if (pnCu != null) {
                        phieuNhapBUS.SuaPhieuNhap(pnCu);
                    }
                }
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Dữ liệu nhập không hợp lệ! Vui lòng kiểm tra định dạng số.");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi hệ thống: " + e.getMessage());
        }
    }


    private void searchPhieuNhap(JTextField txtSearchMaPN, JTextField txtSearchMaNCC, JTextField txtSearchMaNV, JDateChooser dateSearchNgayNhap) {
        try {
            phieuNhapModel.setRowCount(0);
            ArrayList<PhieuNhapDTO> dsPhieuNhap = new ArrayList<>();
            String maPN = txtSearchMaPN.getText().trim();
            String maNCC = txtSearchMaNCC.getText().trim();
            String maNV = txtSearchMaNV.getText().trim();
            Date ngayNhap = dateSearchNgayNhap.getDate() != null ? new Date(dateSearchNgayNhap.getDate().getTime()) : null;

            if (!maPN.isEmpty()) {
                PhieuNhapDTO pn = phieuNhapBUS.TimTheoMaPN(Integer.parseInt(maPN));
                if (pn != null) dsPhieuNhap.add(pn);
            } else if (!maNCC.isEmpty() && !maNV.isEmpty() && ngayNhap != null) {
                dsPhieuNhap = phieuNhapBUS.TimTheoMaNCCvaMaNVvaNgayNhap(Integer.parseInt(maNCC), Integer.parseInt(maNV), ngayNhap);
            } else if (!maNCC.isEmpty() && ngayNhap != null) {
                dsPhieuNhap = phieuNhapBUS.TimTheoMaNCCvaNgayNhap(Integer.parseInt(maNCC), ngayNhap);
            } else if (!maNCC.isEmpty() && !maNV.isEmpty()) {
                dsPhieuNhap = phieuNhapBUS.TimTheoMaNCCvaMaNV(Integer.parseInt(maNCC), Integer.parseInt(maNV));
            } else if (!maNCC.isEmpty()) {
                dsPhieuNhap = phieuNhapBUS.TimTheoMaNCC(Integer.parseInt(maNCC));
            } else if (!maNV.isEmpty()) {
                dsPhieuNhap = phieuNhapBUS.TimTheoMaNV(Integer.parseInt(maNV));
            } else if (ngayNhap != null) {
                dsPhieuNhap = phieuNhapBUS.TimTheoNgayNhap(ngayNhap);
            } else {
                dsPhieuNhap = phieuNhapBUS.LayDanhSachPhieuNhap();
            }

            for (PhieuNhapDTO pn : dsPhieuNhap) {
                phieuNhapModel.addRow(new Object[]{pn.getMaPN(), pn.getMaNCC(), pn.getMaNV(), pn.getTongTien(), pn.getNgayLap()});
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Lỗi tìm kiếm: " + ex.getMessage());
        }
    }

    private void xuatExcel() {
        try {
            Workbook workbook = new XSSFWorkbook();
            // Sheet phiếu nhập
            Sheet sheetPN = workbook.createSheet("Phiếu Nhập");
            Row headerPN = sheetPN.createRow(0);
            String[] headersPN = {"Mã PN", "Mã NCC", "Mã NV", "Tổng tiền", "Ngày lập"};
            for (int i = 0; i < headersPN.length; i++) {
                headerPN.createCell(i).setCellValue(headersPN[i]);
            }
            ArrayList<PhieuNhapDTO> dsPhieuNhap = phieuNhapBUS.LayDanhSachPhieuNhap();
            for (int i = 0; i < dsPhieuNhap.size(); i++) {
                PhieuNhapDTO pn = dsPhieuNhap.get(i);
                Row row = sheetPN.createRow(i + 1);
                row.createCell(0).setCellValue(pn.getMaPN());
                row.createCell(1).setCellValue(pn.getMaNCC());
                row.createCell(2).setCellValue(pn.getMaNV());
                row.createCell(3).setCellValue(pn.getTongTien().doubleValue());
                row.createCell(4).setCellValue(pn.getNgayLap().toString());
            }

            // Sheet chi tiết phiếu nhập
            Sheet sheetCTPN = workbook.createSheet("Chi Tiết Phiếu Nhập");
            Row headerCTPN = sheetCTPN.createRow(0);
            String[] headersCTPN = {"Mã PN", "Mã NL", "Số lượng", "Đơn giá", "Thành tiền"};
            for (int i = 0; i < headersCTPN.length; i++) {
                headerCTPN.createCell(i).setCellValue(headersCTPN[i]);
            }
            ArrayList<CTPhieuNhapDTO> dsCTPN = ctPhieuNhapBUS.LayDanhSachCTPhieuNhap();
            for (int i = 0; i < dsCTPN.size(); i++) {
                CTPhieuNhapDTO ct = dsCTPN.get(i);
                Row row = sheetCTPN.createRow(i + 1);
                row.createCell(0).setCellValue(ct.getMaPN());
                row.createCell(1).setCellValue(ct.getMaNL());
                row.createCell(2).setCellValue(ct.getSoLuong());
                row.createCell(3).setCellValue(ct.getDonGia());
                row.createCell(4).setCellValue(ct.getThanhTien());
            }

            // Lưu file
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Lưu file Excel");
            fileChooser.setSelectedFile(new File("PhieuNhap.xlsx"));
            if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                try (FileOutputStream fos = new FileOutputStream(file)) {
                    workbook.write(fos);
                    JOptionPane.showMessageDialog(this, "Xuất file Excel thành công tại: " + file.getAbsolutePath());
                }
            }
            workbook.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi xuất Excel: " + e.getMessage());
        }
    }
}