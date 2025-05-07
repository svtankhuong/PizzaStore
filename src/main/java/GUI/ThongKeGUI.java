package GUI;

import BUS.ThongKeDoanhThuBUS;
import BUS.ThongKeDoanhChiBUS;
import DTO.ThongKeDoanhThuDTO;
import DTO.ThongKeDoanhChiDTO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Date;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

public class ThongKeGUI extends JPanel {
    private final ThongKeDoanhThuBUS thongKeDoanhThuBUS;
    private final ThongKeDoanhChiBUS thongKeDoanhChiBUS;
    private JTabbedPane tabbedPane;
    private JTable doanhThuTable;
    private JTable chiPhiTable;
    private JTable nhanVienTable;
    private JTable khachHangTable;
    private JTextField tuNgayField;
    private JTextField denNgayField;
    private JTextField ngayField;
    private JTextField thangField;
    private JTextField namField;
    private JTextField quyField;
    private JComboBox<String> loaiThongKeComboBox;
    private JButton btnThongKe;
    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    private final DecimalFormat df = new DecimalFormat("#,###");

    public ThongKeGUI() {
        thongKeDoanhThuBUS = new ThongKeDoanhThuBUS();
        thongKeDoanhChiBUS = new ThongKeDoanhChiBUS();
        initComponents();
    }

    private void initComponents() {
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(1200, 750));
        // Font chung
        Font labelFont = new Font("Segoe UI", Font.PLAIN, 16);
        Font fieldFont = new Font("Segoe UI", Font.PLAIN, 16);
        Font tableFont = new Font("Segoe UI", Font.PLAIN, 13);

        // Panel điều khiển
        JPanel controlPanel = new JPanel();
        controlPanel.setBackground(new Color(173, 216, 230)); // Xanh lam nhạt
        controlPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
        controlPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 15, 10, 15);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Hàng 1: Từ ngày, Đến ngày
        gbc.gridx = 0;
        gbc.gridy = 0;
        JLabel tuNgayLabel = new JLabel("Từ ngày (yyyy-MM-dd):");
        tuNgayLabel.setFont(labelFont);
        controlPanel.add(tuNgayLabel, gbc);

        gbc.gridx = 1;
        tuNgayField = new JTextField(15);
        tuNgayField.setFont(fieldFont);
        tuNgayField.setPreferredSize(new Dimension(150, 35));
        controlPanel.add(tuNgayField, gbc);

        gbc.gridx = 2;
        JLabel denNgayLabel = new JLabel("Đến ngày (yyyy-MM-dd):");
        denNgayLabel.setFont(labelFont);
        controlPanel.add(denNgayLabel, gbc);

        gbc.gridx = 3;
        denNgayField = new JTextField(15);
        denNgayField.setFont(fieldFont);
        denNgayField.setPreferredSize(new Dimension(150, 35));
        controlPanel.add(denNgayField, gbc);

        // Hàng 2: Ngày, Tháng
        gbc.gridx = 0;
        gbc.gridy = 1;
        JLabel ngayLabel = new JLabel("Ngày (yyyy-MM-dd):");
        ngayLabel.setFont(labelFont);
        controlPanel.add(ngayLabel, gbc);

        gbc.gridx = 1;
        ngayField = new JTextField(15);
        ngayField.setFont(fieldFont);
        ngayField.setPreferredSize(new Dimension(150, 35));
        controlPanel.add(ngayField, gbc);

        gbc.gridx = 2;
        JLabel thangLabel = new JLabel("Tháng:");
        thangLabel.setFont(labelFont);
        controlPanel.add(thangLabel, gbc);

        gbc.gridx = 3;
        thangField = new JTextField(15);
        thangField.setFont(fieldFont);
        thangField.setPreferredSize(new Dimension(150, 35));
        controlPanel.add(thangField, gbc);

        // Hàng 3: Năm, Quý
        gbc.gridx = 0;
        gbc.gridy = 2;
        JLabel namLabel = new JLabel("Năm:");
        namLabel.setFont(labelFont);
        controlPanel.add(namLabel, gbc);

        gbc.gridx = 1;
        namField = new JTextField(15);
        namField.setFont(fieldFont);
        namField.setPreferredSize(new Dimension(150, 35));
        controlPanel.add(namField, gbc);

        gbc.gridx = 2;
        JLabel quyLabel = new JLabel("Quý:");
        quyLabel.setFont(labelFont);
        controlPanel.add(quyLabel, gbc);

        gbc.gridx = 3;
        quyField = new JTextField(15);
        quyField.setFont(fieldFont);
        quyField.setPreferredSize(new Dimension(150, 35));
        controlPanel.add(quyField, gbc);

        // Hàng 4: Loại thống kê, Nút Thống kê
        gbc.gridx = 0;
        gbc.gridy = 3;
        JLabel loaiThongKeLabel = new JLabel("Loại thống kê:");
        loaiThongKeLabel.setFont(labelFont);
        controlPanel.add(loaiThongKeLabel, gbc);

        gbc.gridx = 1;
        loaiThongKeComboBox = new JComboBox<>(new String[]{"Tổng thu", "Tổng chi", "Theo nhân viên", "Theo khách hàng"});
        loaiThongKeComboBox.setFont(fieldFont);
        loaiThongKeComboBox.setPreferredSize(new Dimension(150, 35));
        controlPanel.add(loaiThongKeComboBox, gbc);

        gbc.gridx = 2;
        btnThongKe = new JButton("Thống kê");
        btnThongKe.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btnThongKe.setBackground(new Color(30, 144, 255));
        btnThongKe.setForeground(Color.WHITE);
        btnThongKe.setFocusPainted(false);
        btnThongKe.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        btnThongKe.setPreferredSize(new Dimension(150, 35));
        btnThongKe.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btnThongKe.setBackground(new Color(65, 105, 225));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btnThongKe.setBackground(new Color(30, 144, 255));
            }
        });
        btnThongKe.addActionListener(e -> thongKe());
        controlPanel.add(btnThongKe, gbc);

        // TabbedPane
        tabbedPane = new JTabbedPane();
        tabbedPane.setBackground(new Color(220, 220, 220));
        tabbedPane.setForeground(Color.BLACK);
        tabbedPane.setFont(new Font("Segoe UI", Font.BOLD, 14));

        // Tab Thống kê Doanh thu
        JPanel doanhThuPanel = new JPanel(new BorderLayout());
        doanhThuPanel.setBackground(new Color(255, 255, 255));
        doanhThuTable = new JTable();
        doanhThuTable.setFont(tableFont);
        doanhThuTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        customizeTable(doanhThuTable);
        JScrollPane doanhThuScrollPane = new JScrollPane(doanhThuTable,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        customizeScrollPane(doanhThuScrollPane);
        doanhThuPanel.add(doanhThuScrollPane, BorderLayout.CENTER);
        tabbedPane.addTab("Thống Kê Doanh Thu", doanhThuPanel);

        // Tab Thống kê Doanh chi
        JPanel chiPhiPanel = new JPanel(new BorderLayout());
        chiPhiPanel.setBackground(new Color(255, 255, 255));
        chiPhiTable = new JTable();
        chiPhiTable.setFont(tableFont);
        chiPhiTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        customizeTable(chiPhiTable);
        JScrollPane chiPhiScrollPane = new JScrollPane(chiPhiTable,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        customizeScrollPane(chiPhiScrollPane);
        chiPhiPanel.add(chiPhiScrollPane, BorderLayout.CENTER);
        tabbedPane.addTab("Thống Kê Doanh Chi", chiPhiPanel);

        // Tab Thống kê Theo Nhân viên
        JPanel nhanVienPanel = new JPanel(new BorderLayout());
        nhanVienPanel.setBackground(new Color(255, 255, 255));
        nhanVienTable = new JTable();
        nhanVienTable.setFont(tableFont);
        nhanVienTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        customizeTable(nhanVienTable);
        JScrollPane nhanVienScrollPane = new JScrollPane(nhanVienTable,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        customizeScrollPane(nhanVienScrollPane);
        nhanVienPanel.add(nhanVienScrollPane, BorderLayout.CENTER);
        tabbedPane.addTab("Thống Kê Theo Nhân Viên", nhanVienPanel);

        // Tab Thống kê Theo Khách hàng
        JPanel khachHangPanel = new JPanel(new BorderLayout());
        khachHangPanel.setBackground(new Color(255, 255, 255));
        khachHangTable = new JTable();
        khachHangTable.setFont(tableFont);
        khachHangTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        customizeTable(khachHangTable);
        JScrollPane khachHangScrollPane = new JScrollPane(khachHangTable,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        customizeScrollPane(khachHangScrollPane);
        khachHangPanel.add(khachHangScrollPane, BorderLayout.CENTER);
        tabbedPane.addTab("Thống Kê Theo Khách Hàng", khachHangPanel);

        // Thêm vào panel
        add(controlPanel, BorderLayout.NORTH);
        add(tabbedPane, BorderLayout.CENTER);
    }

    private void customizeTable(JTable table) {
        table.setRowHeight(30);
        table.getTableHeader().setBackground(new Color(30, 144, 255));
        table.getTableHeader().setForeground(Color.WHITE);
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));

        // Renderer tùy chỉnh cho các ô
        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                           boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (!isSelected) {
                    c.setBackground(row % 2 == 0 ? new Color(240, 248, 255) : new Color(255, 255, 255));
                }
                return c;
            }
        });

        // Renderer tùy chỉnh cho các cột số (TongThu, TongChi)
        table.setDefaultRenderer(Number.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                           boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (value instanceof Number) {
                    setText(df.format(value));
                }
                if (!isSelected) {
                    c.setBackground(row % 2 == 0 ? new Color(240, 248, 255) : new Color(255, 255, 255));
                }
                return c;
            }
        });

        table.setGridColor(new Color(173, 216, 230));
    }

    private void customizeScrollPane(JScrollPane scrollPane) {
        scrollPane.getVerticalScrollBar().setUI(new javax.swing.plaf.basic.BasicScrollBarUI() {
            @Override
            protected void configureScrollBarColors() {
                this.thumbColor = new Color(30, 144, 255);
                this.trackColor = new Color(220, 220, 220);
            }
        });
        scrollPane.getHorizontalScrollBar().setUI(new javax.swing.plaf.basic.BasicScrollBarUI() {
            @Override
            protected void configureScrollBarColors() {
                this.thumbColor = new Color(30, 144, 255);
                this.trackColor = new Color(220, 220, 220);
            }
        });
    }

    private void thongKe() {
        try {
            String loaiThongKe = (String) loaiThongKeComboBox.getSelectedItem();

            // Thống kê tổng thu
            if ("Tổng thu".equals(loaiThongKe)) {
                // Theo khoảng thời gian
                if (!tuNgayField.getText().isEmpty() && !denNgayField.getText().isEmpty()) {
                    Date tuNgay = validateAndParseDate(tuNgayField.getText(), "Từ ngày không hợp lệ");
                    Date denNgay = validateAndParseDate(denNgayField.getText(), "Đến ngày không hợp lệ");
                    List<ThongKeDoanhThuDTO> thuResult = thongKeDoanhThuBUS.thongKeTongThuTheoKhoangThoiGian(tuNgay, denNgay);
                    updateDoanhThuTable(thuResult);
                    if (thuResult.isEmpty()) {
                        JOptionPane.showMessageDialog(this, "Không có dữ liệu doanh thu cho khoảng thời gian này",
                                "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                    }
                }
                // Theo ngày
                else if (!ngayField.getText().isEmpty()) {
                    Date ngay = validateAndParseDate(ngayField.getText(), "Ngày không hợp lệ");
                    List<ThongKeDoanhThuDTO> thuResult = thongKeDoanhThuBUS.thongKeTongThuTheoNgay(ngay);
                    updateDoanhThuTable(thuResult);
                    if (thuResult.isEmpty()) {
                        JOptionPane.showMessageDialog(this, "Không có dữ liệu doanh thu cho ngày này",
                                "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                    }
                }
                // Theo tháng
                else if (!thangField.getText().isEmpty() && !namField.getText().isEmpty()) {
                    int thang = validateAndParseInt(thangField.getText(), "Tháng không hợp lệ", 1, 12);
                    int nam = validateAndParseInt(namField.getText(), "Năm không hợp lệ", 1900, 9999);
                    List<ThongKeDoanhThuDTO> thuResult = thongKeDoanhThuBUS.thongKeTongThuTheoThang(thang, nam);
                    updateDoanhThuTableThangNam(thuResult, thang, nam);
                    if (thuResult.isEmpty()) {
                        JOptionPane.showMessageDialog(this, "Không có dữ liệu doanh thu cho tháng này",
                                "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                    }
                }
                // Theo năm
                else if (!namField.getText().isEmpty() && thangField.getText().isEmpty() && quyField.getText().isEmpty()) {
                    int nam = validateAndParseInt(namField.getText(), "Năm không hợp lệ", 1900, 9999);
                    List<ThongKeDoanhThuDTO> thuResult = thongKeDoanhThuBUS.thongKeTongThuTheoNam(nam);
                    updateDoanhThuTableNam(thuResult, nam);
                    if (thuResult.isEmpty()) {
                        JOptionPane.showMessageDialog(this, "Không có dữ liệu doanh thu cho năm này",
                                "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                    }
                }
                // Theo quý
                else if (!quyField.getText().isEmpty() && !namField.getText().isEmpty()) {
                    int quy = validateAndParseInt(quyField.getText(), "Quý không hợp lệ", 1, 4);
                    int nam = validateAndParseInt(namField.getText(), "Năm không hợp lệ", 1900, 9999);
                    List<ThongKeDoanhThuDTO> thuResult = thongKeDoanhThuBUS.thongKeTongThuTheoQuy(quy, nam);
                    updateDoanhThuTableQuy(thuResult, quy, nam);
                    if (thuResult.isEmpty()) {
                        JOptionPane.showMessageDialog(this, "Không có dữ liệu doanh thu cho quý này",
                                "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin để thống kê tổng thu",
                            "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            }

            // Thống kê tổng chi
            else if ("Tổng chi".equals(loaiThongKe)) {
                // Theo khoảng thời gian
                if (!tuNgayField.getText().isEmpty() && !denNgayField.getText().isEmpty()) {
                    Date tuNgay = validateAndParseDate(tuNgayField.getText(), "Từ ngày không hợp lệ");
                    Date denNgay = validateAndParseDate(denNgayField.getText(), "Đến ngày không hợp lệ");
                    List<ThongKeDoanhChiDTO> chiResult = thongKeDoanhChiBUS.thongKeTongChiTheoKhoangThoiGian(tuNgay, denNgay);
                    updateChiPhiTable(chiResult);
                    if (chiResult.isEmpty()) {
                        JOptionPane.showMessageDialog(this, "Không có dữ liệu chi phí cho khoảng thời gian này",
                                "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                    }
                }
                // Theo ngày
                else if (!ngayField.getText().isEmpty()) {
                    Date ngay = validateAndParseDate(ngayField.getText(), "Ngày không hợp lệ");
                    List<ThongKeDoanhChiDTO> chiResult = thongKeDoanhChiBUS.thongKeTongChiTheoNgay(ngay);
                    updateChiPhiTable(chiResult);
                    if (chiResult.isEmpty()) {
                        JOptionPane.showMessageDialog(this, "Không có dữ liệu chi phí cho ngày này",
                                "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                    }
                }
                // Theo tháng
                else if (!thangField.getText().isEmpty() && !namField.getText().isEmpty()) {
                    int thang = validateAndParseInt(thangField.getText(), "Tháng không hợp lệ", 1, 12);
                    int nam = validateAndParseInt(namField.getText(), "Năm không hợp lệ", 1900, 9999);
                    List<ThongKeDoanhChiDTO> chiResult = thongKeDoanhChiBUS.thongKeTongChiTheoThang(thang, nam);
                    updateChiPhiTableThangNam(chiResult, thang, nam);
                    if (chiResult.isEmpty()) {
                        JOptionPane.showMessageDialog(this, "Không có dữ liệu chi phí cho tháng này",
                                "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                    }
                }
                // Theo năm
                else if (!namField.getText().isEmpty() && thangField.getText().isEmpty() && quyField.getText().isEmpty()) {
                    int nam = validateAndParseInt(namField.getText(), "Năm không hợp lệ", 1900, 9999);
                    List<ThongKeDoanhChiDTO> chiResult = thongKeDoanhChiBUS.thongKeTongChiTheoNam(nam);
                    updateChiPhiTableNam(chiResult, nam);
                    if (chiResult.isEmpty()) {
                        JOptionPane.showMessageDialog(this, "Không có dữ liệu chi phí cho năm này",
                                "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                    }
                }
                // Theo quý
                else if (!quyField.getText().isEmpty() && !namField.getText().isEmpty()) {
                    int quy = validateAndParseInt(quyField.getText(), "Quý không hợp lệ", 1, 4);
                    int nam = validateAndParseInt(namField.getText(), "Năm không hợp lệ", 1900, 9999);
                    List<ThongKeDoanhChiDTO> chiResult = thongKeDoanhChiBUS.thongKeTongChiTheoQuy(quy, nam);
                    updateChiPhiTableQuy(chiResult, quy, nam);
                    if (chiResult.isEmpty()) {
                        JOptionPane.showMessageDialog(this, "Không có dữ liệu chi phí cho quý này",
                                "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin để thống kê tổng chi",
                            "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            }

            // Thống kê theo nhân viên
            else if ("Theo nhân viên".equals(loaiThongKe)) {
                if (!quyField.getText().isEmpty() && !namField.getText().isEmpty()) {
                    int quy = validateAndParseInt(quyField.getText(), "Quý không hợp lệ", 1, 4);
                    int nam = validateAndParseInt(namField.getText(), "Năm không hợp lệ", 1900, 9999);
                    List<ThongKeDoanhThuDTO> nhanVienResult = thongKeDoanhThuBUS.thongKeTongThuTheoNhanVienQuy(quy, nam);
                    updateNhanVienTable(nhanVienResult);
                    if (nhanVienResult.isEmpty()) {
                        JOptionPane.showMessageDialog(this, "Không có dữ liệu doanh thu theo nhân viên cho quý này",
                                "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Vui lòng nhập quý và năm để thống kê theo nhân viên",
                            "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            }

            // Thống kê theo khách hàng
            else if ("Theo khách hàng".equals(loaiThongKe)) {
                if (!quyField.getText().isEmpty() && !namField.getText().isEmpty()) {
                    int quy = validateAndParseInt(quyField.getText(), "Quý không hợp lệ", 1, 4);
                    int nam = validateAndParseInt(namField.getText(), "Năm không hợp lệ", 1900, 9999);
                    List<ThongKeDoanhThuDTO> khachHangResult = thongKeDoanhThuBUS.thongKeTongThuTheoKhachHangQuy(quy, nam);
                    updateKhachHangTable(khachHangResult);
                    if (khachHangResult.isEmpty()) {
                        JOptionPane.showMessageDialog(this, "Không có dữ liệu doanh thu theo khách hàng cho quý này",
                                "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Vui lòng nhập quý và năm để thống kê theo khách hàng",
                            "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            }

            // Chuyển đến tab tương ứng
            switch (loaiThongKe) {
                case "Tổng thu":
                    tabbedPane.setSelectedIndex(0);
                    break;
                case "Tổng chi":
                    tabbedPane.setSelectedIndex(1);
                    break;
                case "Theo nhân viên":
                    tabbedPane.setSelectedIndex(2);
                    break;
                case "Theo khách hàng":
                    tabbedPane.setSelectedIndex(3);
                    break;
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private Date validateAndParseDate(String input, String errorMessage) throws ParseException {
        if (input == null || input.trim().isEmpty()) {
            throw new ParseException(errorMessage, 0);
        }
        try {
            java.util.Date utilDate = sdf.parse(input);
            return new Date(utilDate.getTime());
        } catch (ParseException e) {
            throw new ParseException(errorMessage, 0);
        }
    }

    private int validateAndParseInt(String input, String errorMessage, int min, int max) throws NumberFormatException {
        if (input == null || input.trim().isEmpty()) {
            throw new NumberFormatException(errorMessage);
        }
        try {
            int value = Integer.parseInt(input);
            if (value < min || value > max) {
                throw new NumberFormatException(errorMessage);
            }
            return value;
        } catch (NumberFormatException e) {
            throw new NumberFormatException(errorMessage);
        }
    }

    private void updateDoanhThuTable(List<ThongKeDoanhThuDTO> data) {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Từ ngày");
        model.addColumn("Đến ngày");
        model.addColumn("Tổng thu");

        for (ThongKeDoanhThuDTO tk : data) {
            model.addRow(new Object[]{
                    tk.getTuNgay() != null ? sdf.format(tk.getTuNgay()) : "",
                    tk.getDenNgay() != null ? sdf.format(tk.getDenNgay()) : "",
                    tk.getTongThu() != null ? df.format(tk.getTongThu()) : "0"
            });
        }
        doanhThuTable.setModel(model);
        if (doanhThuTable.getColumnCount() >= 3) {
            doanhThuTable.getColumnModel().getColumn(0).setPreferredWidth(150);
            doanhThuTable.getColumnModel().getColumn(1).setPreferredWidth(150);
            doanhThuTable.getColumnModel().getColumn(2).setPreferredWidth(200);
        }
    }

    private void updateChiPhiTable(List<ThongKeDoanhChiDTO> data) {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Từ ngày");
        model.addColumn("Đến ngày");
        model.addColumn("Tổng chi");

        for (ThongKeDoanhChiDTO tk : data) {
            model.addRow(new Object[]{
                    tk.getTuNgay() != null ? sdf.format(tk.getTuNgay()) : "",
                    tk.getDenNgay() != null ? sdf.format(tk.getDenNgay()) : "",
                    tk.getTongChi() != null ? df.format(tk.getTongChi()) : "0"
            });
        }
        chiPhiTable.setModel(model);
        if (chiPhiTable.getColumnCount() >= 3) {
            chiPhiTable.getColumnModel().getColumn(0).setPreferredWidth(150);
            chiPhiTable.getColumnModel().getColumn(1).setPreferredWidth(150);
            chiPhiTable.getColumnModel().getColumn(2).setPreferredWidth(200);
        }
    }

    private void updateDoanhThuTableThangNam(List<ThongKeDoanhThuDTO> data, int thang, int nam) {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Tháng");
        model.addColumn("Năm");
        model.addColumn("Tổng thu");

        for (ThongKeDoanhThuDTO tk : data) {
            model.addRow(new Object[]{
                    thang,
                    nam,
                    tk.getTongThu() != null ? df.format(tk.getTongThu()) : "0"
            });
        }
        doanhThuTable.setModel(model);
        if (doanhThuTable.getColumnCount() >= 3) {
            doanhThuTable.getColumnModel().getColumn(0).setPreferredWidth(100);
            doanhThuTable.getColumnModel().getColumn(1).setPreferredWidth(100);
            doanhThuTable.getColumnModel().getColumn(2).setPreferredWidth(200);
        }
    }

    private void updateChiPhiTableThangNam(List<ThongKeDoanhChiDTO> data, int thang, int nam) {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Tháng");
        model.addColumn("Năm");
        model.addColumn("Tổng chi");

        for (ThongKeDoanhChiDTO tk : data) {
            model.addRow(new Object[]{
                    thang,
                    nam,
                    tk.getTongChi() != null ? df.format(tk.getTongChi()) : "0"
            });
        }
        chiPhiTable.setModel(model);
        if (chiPhiTable.getColumnCount() >= 3) {
            chiPhiTable.getColumnModel().getColumn(0).setPreferredWidth(100);
            chiPhiTable.getColumnModel().getColumn(1).setPreferredWidth(100);
            chiPhiTable.getColumnModel().getColumn(2).setPreferredWidth(200);
        }
    }

    private void updateDoanhThuTableNam(List<ThongKeDoanhThuDTO> data, int nam) {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Năm");
        model.addColumn("Tổng thu");

        for (ThongKeDoanhThuDTO tk : data) {
            model.addRow(new Object[]{
                    nam,
                    tk.getTongThu() != null ? df.format(tk.getTongThu()) : "0"
            });
        }
        doanhThuTable.setModel(model);
        if (doanhThuTable.getColumnCount() >= 2) {
            doanhThuTable.getColumnModel().getColumn(0).setPreferredWidth(100);
            doanhThuTable.getColumnModel().getColumn(1).setPreferredWidth(200);
        }
    }

    private void updateChiPhiTableNam(List<ThongKeDoanhChiDTO> data, int nam) {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Năm");
        model.addColumn("Tổng chi");

        for (ThongKeDoanhChiDTO tk : data) {
            model.addRow(new Object[]{
                    nam,
                    tk.getTongChi() != null ? df.format(tk.getTongChi()) : "0"
            });
        }
        chiPhiTable.setModel(model);
        if (chiPhiTable.getColumnCount() >= 2) {
            chiPhiTable.getColumnModel().getColumn(0).setPreferredWidth(100);
            chiPhiTable.getColumnModel().getColumn(1).setPreferredWidth(200);
        }
    }

    private void updateDoanhThuTableQuy(List<ThongKeDoanhThuDTO> data, int quy, int nam) {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Quý");
        model.addColumn("Năm");
        model.addColumn("Tổng thu");

        for (ThongKeDoanhThuDTO tk : data) {
            model.addRow(new Object[]{
                    quy,
                    nam,
                    tk.getTongThu() != null ? df.format(tk.getTongThu()) : "0"
            });
        }
        doanhThuTable.setModel(model);
        if (doanhThuTable.getColumnCount() >= 3) {
            doanhThuTable.getColumnModel().getColumn(0).setPreferredWidth(100);
            doanhThuTable.getColumnModel().getColumn(1).setPreferredWidth(100);
            doanhThuTable.getColumnModel().getColumn(2).setPreferredWidth(200);
        }
    }

    private void updateChiPhiTableQuy(List<ThongKeDoanhChiDTO> data, int quy, int nam) {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Quý");
        model.addColumn("Năm");
        model.addColumn("Tổng chi");

        for (ThongKeDoanhChiDTO tk : data) {
            model.addRow(new Object[]{
                    quy,
                    nam,
                    tk.getTongChi() != null ? df.format(tk.getTongChi()) : "0"
            });
        }
        chiPhiTable.setModel(model);
        if (chiPhiTable.getColumnCount() >= 3) {
            chiPhiTable.getColumnModel().getColumn(0).setPreferredWidth(100);
            chiPhiTable.getColumnModel().getColumn(1).setPreferredWidth(100);
            chiPhiTable.getColumnModel().getColumn(2).setPreferredWidth(200);
        }
    }

    private void updateNhanVienTable(List<ThongKeDoanhThuDTO> data) {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Mã NV");
        model.addColumn("Tên NV");
        model.addColumn("Tổng thu");
        model.addColumn("Quý");
        model.addColumn("Năm");

        for (ThongKeDoanhThuDTO tk : data) {
            model.addRow(new Object[]{
                    tk.getMaNV(),
                    tk.getTenNV() != null ? tk.getTenNV() : "",
                    tk.getTongThu() != null ? df.format(tk.getTongThu()) : "0",
                    tk.getQuy(),
                    tk.getNam()
            });
        }
        nhanVienTable.setModel(model);
        if (nhanVienTable.getColumnCount() >= 5) {
            nhanVienTable.getColumnModel().getColumn(0).setPreferredWidth(100);
            nhanVienTable.getColumnModel().getColumn(1).setPreferredWidth(200);
            nhanVienTable.getColumnModel().getColumn(2).setPreferredWidth(200);
            nhanVienTable.getColumnModel().getColumn(3).setPreferredWidth(100);
            nhanVienTable.getColumnModel().getColumn(4).setPreferredWidth(100);
        }
    }

    private void updateKhachHangTable(List<ThongKeDoanhThuDTO> data) {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Mã KH");
        model.addColumn("Tên KH");
        model.addColumn("Tổng thu");
        model.addColumn("Quý");
        model.addColumn("Năm");

        for (ThongKeDoanhThuDTO tk : data) {
            model.addRow(new Object[]{
                    tk.getMaKH(),
                    tk.getTenKH() != null ? tk.getTenKH() : "",
                    tk.getTongThu() != null ? df.format(tk.getTongThu()) : "0",
                    tk.getQuy(),
                    tk.getNam()
            });
        }
        khachHangTable.setModel(model);
        if (khachHangTable.getColumnCount() >= 5) {
            khachHangTable.getColumnModel().getColumn(0).setPreferredWidth(100);
            khachHangTable.getColumnModel().getColumn(1).setPreferredWidth(200);
            khachHangTable.getColumnModel().getColumn(2).setPreferredWidth(200);
            khachHangTable.getColumnModel().getColumn(3).setPreferredWidth(100);
            khachHangTable.getColumnModel().getColumn(4).setPreferredWidth(100);
        }
    }
}