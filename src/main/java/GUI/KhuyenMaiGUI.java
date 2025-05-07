package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import BUS.ChiTietKMBUS;
import BUS.KhuyenMaiBUS;
import DTO.ChiTietKMDTO;
import DTO.KhuyenMaiDTO;

public class KhuyenMaiGUI extends JPanel {

    private JTextField txtMaKM;
    private JTextField txtTenKM;
    private JTextField txtNgayBD;
    private JTextField txtNgayKT;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    private JTable tableKhuyenMai;
    private DefaultTableModel tableModel;
    private int selectedIndex = -1;

    // Form panel
    private JPanel formPanel;
    private boolean formVisible = false;

    private KhuyenMaiBUS khuyenMaiBUS;

    public KhuyenMaiGUI() {
        khuyenMaiBUS = new KhuyenMaiBUS();

        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        // Main panel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);

        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BorderLayout());
        contentPanel.setBackground(Color.WHITE);

        JPanel tabPanel = new JPanel();
        tabPanel.setLayout(new BoxLayout(tabPanel, BoxLayout.X_AXIS));
        tabPanel.setBackground(Color.WHITE);
        tabPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.LIGHT_GRAY));

        JLabel khuyenMaiTab = new JLabel("Khuyến mãi");
        khuyenMaiTab.setFont(new Font("Arial", Font.BOLD, 20));
        khuyenMaiTab.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        khuyenMaiTab.setForeground(new Color(255, 102, 0));

        tabPanel.setLayout(new BoxLayout(tabPanel, BoxLayout.X_AXIS));
        tabPanel.add(Box.createHorizontalGlue());
        tabPanel.add(khuyenMaiTab);
        tabPanel.add(Box.createHorizontalGlue());

        JPanel mainContentPanel = new JPanel();
        mainContentPanel.setLayout(new BorderLayout(0, 20));
        mainContentPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainContentPanel.setBackground(Color.WHITE);

        formPanel = createFormPanel();
        formPanel.setVisible(true);
        formPanel.setPreferredSize(new Dimension(0, 300));

        // Table panel
        JPanel tablePanel = new JPanel();
        tablePanel.setLayout(new BorderLayout());
        tablePanel.setBackground(Color.WHITE);
        tablePanel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));

        // Table title
        JLabel tableTitle = new JLabel("Danh sách khuyến mãi");
        tableTitle.setFont(new Font("Arial", Font.BOLD, 16));
        tableTitle.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        tableModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tableModel.addColumn("Mã khuyến mãi");
        tableModel.addColumn("Tên khuyến mãi");
        tableModel.addColumn("Ngày bắt đầu");
        tableModel.addColumn("Ngày kết thúc");
        tableModel.addColumn("Thao tác");

        // Create table
        tableKhuyenMai = new JTable(tableModel);
        tableKhuyenMai.setRowHeight(40);
        tableKhuyenMai.setShowGrid(false);
        tableKhuyenMai.setIntercellSpacing(new Dimension(0, 0));
        tableKhuyenMai.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        tableKhuyenMai.getTableHeader().setBackground(Color.WHITE);
        tableKhuyenMai.getTableHeader().setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.LIGHT_GRAY));
        tableKhuyenMai.setFont(new Font("Arial", Font.PLAIN, 14));

        tableKhuyenMai.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = tableKhuyenMai.getSelectedRow();
                if (selectedRow != -1) {
                    selectedIndex = selectedRow;
                    hienThiKhuyenMaiDTO(selectedIndex);
                }
            }
        });

        loadData();

        JScrollPane scrollPane = new JScrollPane(tableKhuyenMai);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getViewport().setBackground(Color.WHITE);

        tablePanel.add(tableTitle, BorderLayout.NORTH);
        tablePanel.add(scrollPane, BorderLayout.CENTER);

        mainContentPanel.add(formPanel, BorderLayout.NORTH);
        mainContentPanel.add(tablePanel, BorderLayout.CENTER);

        contentPanel.add(tabPanel, BorderLayout.NORTH);
        contentPanel.add(mainContentPanel, BorderLayout.CENTER);

        add(contentPanel, BorderLayout.CENTER);

        tableKhuyenMai.getColumnModel().getColumn(4).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                                                           boolean isSelected, boolean hasFocus, int row, int column) {
                JLabel label = new JLabel();
                label.setHorizontalAlignment(JLabel.CENTER);
                if (value instanceof Icon) {
                    label.setIcon((Icon) value);
                }
                return label;
            }
        });

        tableKhuyenMai.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = tableKhuyenMai.rowAtPoint(e.getPoint());
                int col = tableKhuyenMai.columnAtPoint(e.getPoint());

                if (col == 4) {
                    ImageIcon iconEye = new ImageIcon("/image/eye.png");
                    iconEye = new ImageIcon(iconEye.getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH));
                    tableModel.setValueAt(iconEye, row, col);

                    // Mở dialog chi tiết
                    int maKM = Integer.parseInt(tableModel.getValueAt(row, 0).toString());
                    ChiTietKMGUI dialog = new ChiTietKMGUI(
                            (java.awt.Frame) SwingUtilities.getWindowAncestor(tableKhuyenMai), maKM);
                    dialog.setVisible(true);

                    // Trả lại icon view.png sau khi đóng dialog
                    ImageIcon iconView = new ImageIcon("/image/view.png");
                    iconView = new ImageIcon(iconView.getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH));
                    tableModel.setValueAt(iconView, row, col);
                }
            }
        });
    }

    private JPanel createFormPanel() {
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BorderLayout(0, 10));
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));

        JLabel formTitle = new JLabel("Thông tin khuyến mãi");
        formTitle.setFont(new Font("Arial", Font.BOLD, 16));
        formTitle.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JPanel fieldsPanel = new JPanel();
        fieldsPanel.setLayout(new GridLayout(2, 2, 20, 20));
        fieldsPanel.setBorder(BorderFactory.createEmptyBorder(0, 15, 0, 15));
        fieldsPanel.setBackground(Color.WHITE);

        Font inputFont = new Font("Arial", Font.PLAIN, 16);

        txtMaKM = new JTextField();
        txtMaKM.setFont(inputFont);
        txtTenKM = new JTextField();
        txtTenKM.setFont(inputFont);
        txtNgayBD = new JTextField("yyyy-MM-dd");
        txtNgayBD.setFont(inputFont);
        txtNgayKT = new JTextField("yyyy-MM-dd");
        txtNgayKT.setFont(inputFont);

        setupEnterKeyNavigation(txtMaKM, txtTenKM);
        setupEnterKeyNavigation(txtTenKM, txtNgayBD);
        setupEnterKeyNavigation(txtNgayBD, txtNgayKT);

        // Mã khuyến mãi
        JPanel maKMPanel = new JPanel(new BorderLayout(0, 5));
        maKMPanel.setBackground(Color.WHITE);
        JLabel lblMaKM = new JLabel("Mã khuyến mãi");
        lblMaKM.setFont(new Font("Arial", Font.BOLD, 14));
        txtMaKM.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY),
                BorderFactory.createEmptyBorder(8, 8, 8, 8)));
        maKMPanel.add(lblMaKM, BorderLayout.NORTH);
        maKMPanel.add(txtMaKM, BorderLayout.CENTER);

        // Tên khuyến mãi
        JPanel tenKMPanel = new JPanel(new BorderLayout(0, 5));
        tenKMPanel.setBackground(Color.WHITE);
        JLabel lblTenKM = new JLabel("Tên khuyến mãi");
        lblTenKM.setFont(new Font("Arial", Font.BOLD, 14));
        txtTenKM.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY),
                BorderFactory.createEmptyBorder(8, 8, 8, 8)));
        tenKMPanel.add(lblTenKM, BorderLayout.NORTH);
        tenKMPanel.add(txtTenKM, BorderLayout.CENTER);

        // Ngày bắt đầu
        JPanel ngayBDPanel = new JPanel(new BorderLayout(0, 5));
        ngayBDPanel.setBackground(Color.WHITE);
        JLabel lblNgayBD = new JLabel("Ngày bắt đầu khuyến mãi");
        lblNgayBD.setFont(new Font("Arial", Font.BOLD, 14));
        txtNgayBD.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY),
                BorderFactory.createEmptyBorder(8, 8, 8, 8)));
        txtNgayBD.setForeground(Color.GRAY);
        setupDateField(txtNgayBD);
        ngayBDPanel.add(lblNgayBD, BorderLayout.NORTH);
        ngayBDPanel.add(txtNgayBD, BorderLayout.CENTER);

        // Ngày kết thúc
        JPanel ngayKTPanel = new JPanel(new BorderLayout(0, 5));
        ngayKTPanel.setBackground(Color.WHITE);
        JLabel lblNgayKT = new JLabel("Ngày");
        lblNgayKT.setFont(new Font("Arial", Font.BOLD, 14));
        txtNgayKT.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY),
                BorderFactory.createEmptyBorder(8, 8, 8, 8)));
        txtNgayKT.setForeground(Color.GRAY);
        setupDateField(txtNgayKT);
        ngayKTPanel.add(lblNgayKT, BorderLayout.NORTH);
        ngayKTPanel.add(txtNgayKT, BorderLayout.CENTER);

        fieldsPanel.add(maKMPanel);
        fieldsPanel.add(tenKMPanel);
        fieldsPanel.add(ngayBDPanel);
        fieldsPanel.add(ngayKTPanel);

        // Button panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 0));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        buttonPanel.setBackground(Color.WHITE);

        JButton btnThem = createActionButton("Thêm", "add-icon.png", e -> them());
        JButton btnSua = createActionButton("Sửa", "Pencil-icon.png", e -> sua());
        JButton btnXoa = createActionButton("Xóa", "delete-icon.png", e -> {
            if (selectedIndex != -1) {
                int confirm = JOptionPane.showConfirmDialog(
                        this,
                        "Bạn có chắc chắn muốn xóa khuyến mãi này?",
                        "Xác nhận xóa",
                        JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    xoa();
                }
            } else {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn khuyến mãi cần xóa!",
                        "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        });

        JButton btnLamMoi = createActionButton("Làm mới", "refresh-icon.png", e -> lamMoi());
        JButton btnXuatPDF = createActionButton("Xuất PDF", "pdf.png", e -> xuatPDF());

        buttonPanel.add(btnThem);
        buttonPanel.add(btnSua);
        buttonPanel.add(btnXoa);
        buttonPanel.add(btnLamMoi);
        buttonPanel.add(btnXuatPDF);

        formPanel.add(formTitle, BorderLayout.NORTH);
        formPanel.add(fieldsPanel, BorderLayout.CENTER);
        formPanel.add(buttonPanel, BorderLayout.SOUTH);

        return formPanel;
    }

    private void setupEnterKeyNavigation(JTextField currentField, JTextField nextField) {
        currentField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    if (!currentField.getText().isEmpty() && !currentField.getText().equals("yyyy-MM-dd")) {
                        nextField.requestFocus();
                        if (nextField.getText().equals("yyyy-MM-dd")) {
                            nextField.setText("");
                            nextField.setForeground(Color.BLACK);
                        }
                    }
                }
            }
        });
    }

    private JButton createActionButton(String text, String iconName, ActionListener listener) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setForeground(Color.BLACK);
        button.setBackground(new Color(240, 240, 240));
        button.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
        button.setFocusPainted(false);

        try {
            java.net.URL imgURL = getClass().getResource("/image/" + iconName);
            if (imgURL != null) {
                ImageIcon icon = new ImageIcon(new ImageIcon(imgURL)
                        .getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH));
                button.setIcon(icon);
                button.setIconTextGap(8);
            } else {
                System.out.println("Không tìm thấy file icon: " + iconName);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        button.addActionListener(listener);
        return button;
    }

    private void setupDateField(JTextField field) {
        field.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                if (field.getText().equals("yyyy-MM-dd")) {
                    field.setText("");
                    field.setForeground(Color.BLACK);
                }
            }

            public void focusLost(java.awt.event.FocusEvent evt) {
                if (field.getText().isEmpty()) {
                    field.setText("yyyy-MM-dd");
                    field.setForeground(Color.GRAY);
                }
            }
        });
    }

    private void loadData() {
        List<KhuyenMaiDTO> danhSachKM = khuyenMaiBUS.getAllKhuyenMai();
        capNhatBang(danhSachKM);
    }

    private boolean validateInput() {
        if (txtTenKM.getText().trim().isEmpty() ||
                txtNgayBD.getText().trim().isEmpty() || txtNgayBD.getText().equals("yyyy-MM-dd") ||
                txtNgayKT.getText().trim().isEmpty() || txtNgayKT.getText().equals("yyyy-MM-dd")) {

            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin!",
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        String ngayBDStr = txtNgayBD.getText().trim();
        String ngayKTStr = txtNgayKT.getText().trim();
        if (ngayBDStr.length() != 10 || ngayKTStr.length() != 10) {
            JOptionPane.showMessageDialog(this, "Định dạng ngày phải là yyyy-MM-dd (đủ 10 ký tự)!", "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        try {
            Date ngayBD = dateFormat.parse(ngayBDStr);
            Date ngayKT = dateFormat.parse(ngayKTStr);

            if (ngayKT.before(ngayBD)) {
                JOptionPane.showMessageDialog(this, "Ngày kết thúc phải sau ngày bắt đầu!",
                        "Lỗi", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        } catch (ParseException e) {
            JOptionPane.showMessageDialog(this, "Định dạng ngày không hợp lệ! Sử dụng định dạng yyyy-MM-dd", "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    private void them() {
        if (validateInput()) {
            KhuyenMaiDTO khuyenMai = new KhuyenMaiDTO(
                    0, // MaKM will be auto-generated
                    txtTenKM.getText().trim(),
                    LocalDate.parse(txtNgayBD.getText().trim()),
                    LocalDate.parse(txtNgayKT.getText().trim()));

            boolean success = khuyenMaiBUS.themKhuyenMai(khuyenMai);
            if (success) {
                loadData();
                lamMoi();
                JOptionPane.showMessageDialog(this, "Thêm khuyến mãi thành công!",
                        "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Thêm khuyến mãi thất bại!",
                        "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void sua() {
        if (selectedIndex == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn khuyến mãi cần sửa!",
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (validateInput()) {
            KhuyenMaiDTO khuyenMai = new KhuyenMaiDTO(
                    Integer.parseInt(txtMaKM.getText().trim()),
                    txtTenKM.getText().trim(),
                    LocalDate.parse(txtNgayBD.getText().trim()),
                    LocalDate.parse(txtNgayKT.getText().trim()));

            boolean success = khuyenMaiBUS.suaKhuyenMai(khuyenMai);
            if (success) {
                loadData();
                lamMoi();
                JOptionPane.showMessageDialog(this, "Cập nhật thông tin khuyến mãi thành công!",
                        "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Cập nhật thông tin khuyến mãi thất bại!",
                        "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void xoa() {
        if (selectedIndex != -1) {
            int maKM = Integer.parseInt(tableModel.getValueAt(selectedIndex, 0).toString());
            boolean success = khuyenMaiBUS.xoaKhuyenMai(maKM);

            if (success) {
                loadData();
                lamMoi();
                JOptionPane.showMessageDialog(this, "Xóa khuyến mãi thành công!",
                        "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Xóa khuyến mãi thất bại!",
                        "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void hienThiKhuyenMaiDTO(int index) {
        if (index >= 0 && index < tableModel.getRowCount()) {
            txtMaKM.setText(tableModel.getValueAt(index, 0).toString());
            txtMaKM.setEditable(false);
            txtTenKM.setText(tableModel.getValueAt(index, 1).toString());
            txtNgayBD.setText(tableModel.getValueAt(index, 2).toString());
            txtNgayBD.setForeground(Color.BLACK);
            txtNgayKT.setText(tableModel.getValueAt(index, 3).toString());
            txtNgayKT.setForeground(Color.BLACK);
        }
    }

    private void capNhatBang(List<KhuyenMaiDTO> danhSachKM) {
        tableModel.setRowCount(0);

        for (KhuyenMaiDTO khuyenMai : danhSachKM) {
            ImageIcon iconView = new ImageIcon("D:\\PizzaStore\\src\\main\\resources\\image\\view.png");
            Image img = iconView.getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH);
            iconView = new ImageIcon(img);

            tableModel.addRow(new Object[] {
                    khuyenMai.getMaKM(),
                    khuyenMai.getTenKM(),
                    khuyenMai.getNgayBatDau(),
                    khuyenMai.getNgayKetThuc(),
                    iconView
            });
        }
    }

    private void lamMoi() {
        txtMaKM.setText("");
        txtMaKM.setEditable(false);
        txtTenKM.setText("");
        txtNgayBD.setText("yyyy-MM-dd");
        txtNgayBD.setForeground(Color.GRAY);
        txtNgayKT.setText("yyyy-MM-dd");
        txtNgayKT.setForeground(Color.GRAY);
        selectedIndex = -1;
        tableKhuyenMai.clearSelection();
    }

    private void xuatPDF() {
        int row = tableKhuyenMai.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn khuyến mãi để xuất PDF!", "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Chọn nơi lưu file PDF");
        int userSelection = fileChooser.showSaveDialog(this);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            String filePath = fileChooser.getSelectedFile().getAbsolutePath();
            if (!filePath.toLowerCase().endsWith(".pdf")) {
                filePath += ".pdf";
            }
            String maKM = tableModel.getValueAt(row, 0).toString();
            String tenKM = tableModel.getValueAt(row, 1).toString();
            String ngayBD = tableModel.getValueAt(row, 2).toString();
            String ngayKT = tableModel.getValueAt(row, 3).toString();
            try {
                Document document = new Document();
                PdfWriter.getInstance(document, new java.io.FileOutputStream(filePath));
                document.open();

                // Load custom font
                String fontPath = "D:/PizzaStore/src/main/resources/font/DejaVuSans.ttf";
                BaseFont baseFont = BaseFont.createFont(fontPath, BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
                com.lowagie.text.Font titleFont = new com.lowagie.text.Font(baseFont, 20, com.lowagie.text.Font.BOLD);
                com.lowagie.text.Font headerFont = new com.lowagie.text.Font(baseFont, 14, com.lowagie.text.Font.BOLD);
                com.lowagie.text.Font normalFont = new com.lowagie.text.Font(baseFont, 12,
                        com.lowagie.text.Font.NORMAL);

                // Tiêu đề lớn
                Paragraph title = new Paragraph("THÔNG TIN KHUYẾN MÃI", titleFont);
                title.setAlignment(Element.ALIGN_CENTER);
                title.setSpacingAfter(20f);
                document.add(title);

                // Thông tin khuyến mãi
                PdfPTable infoTable = new PdfPTable(2);
                infoTable.setWidthPercentage(80);
                infoTable.setSpacingAfter(20f);
                infoTable.setHorizontalAlignment(Element.ALIGN_CENTER);

                infoTable.addCell(new PdfPCell(new Paragraph("Mã khuyến mãi:", headerFont)));
                infoTable.addCell(new PdfPCell(new Paragraph(maKM, normalFont)));
                infoTable.addCell(new PdfPCell(new Paragraph("Tên khuyến mãi:", headerFont)));
                infoTable.addCell(new PdfPCell(new Paragraph(tenKM, normalFont)));
                infoTable.addCell(new PdfPCell(new Paragraph("Ngày bắt đầu:", headerFont)));
                infoTable.addCell(new PdfPCell(new Paragraph(ngayBD, normalFont)));
                infoTable.addCell(new PdfPCell(new Paragraph("Ngày kết thúc:", headerFont)));
                infoTable.addCell(new PdfPCell(new Paragraph(ngayKT, normalFont)));
                document.add(infoTable);

                // Lấy danh sách chi tiết khuyến mãi
                ChiTietKMBUS ctkmBus = new ChiTietKMBUS();
                java.util.List<ChiTietKMDTO> dsCTKM = ctkmBus.getAllChiTietKM();
                int maKMInt = Integer.parseInt(maKM);
                java.util.List<ChiTietKMDTO> dsCTKM_MaKM = new java.util.ArrayList<>();
                for (ChiTietKMDTO ctkm : dsCTKM) {
                    if (ctkm.getMaKM() == maKMInt) {
                        dsCTKM_MaKM.add(ctkm);
                    }
                }

                // Bảng chi tiết khuyến mãi
                Paragraph detailTitle = new Paragraph("Danh sách chi tiết khuyến mãi", headerFont);
                detailTitle.setAlignment(Element.ALIGN_LEFT);
                detailTitle.setSpacingAfter(10f);
                document.add(detailTitle);

                PdfPTable detailTable = new PdfPTable(3);
                detailTable.setWidthPercentage(100);
                detailTable.setWidths(new float[] { 3, 3, 5 });

                PdfPCell cell1 = new PdfPCell(new Paragraph("Tên CTKM", headerFont));
                PdfPCell cell2 = new PdfPCell(new Paragraph("Phần trăm giảm (%)", headerFont));
                PdfPCell cell3 = new PdfPCell(new Paragraph("Số tiền tối thiểu (VNĐ)", headerFont));
                cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
                detailTable.addCell(cell1);
                detailTable.addCell(cell2);
                detailTable.addCell(cell3);

                if (dsCTKM_MaKM.isEmpty()) {
                    PdfPCell emptyCell = new PdfPCell(new Paragraph("Không có chi tiết khuyến mãi nào.", normalFont));
                    emptyCell.setColspan(3);
                    emptyCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    detailTable.addCell(emptyCell);
                } else {
                    for (ChiTietKMDTO ctkm : dsCTKM_MaKM) {
                        PdfPCell tenCell = new PdfPCell(new Paragraph(ctkm.getTenCTKM(), normalFont));
                        PdfPCell phanTramCell = new PdfPCell(
                                new Paragraph(String.valueOf(ctkm.getPhanTramGiam()), normalFont));
                        PdfPCell toiThieuCell = new PdfPCell(
                                new Paragraph(String.valueOf(ctkm.getToithieugiam()), normalFont));
                        tenCell.setHorizontalAlignment(Element.ALIGN_LEFT);
                        phanTramCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                        toiThieuCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                        detailTable.addCell(tenCell);
                        detailTable.addCell(phanTramCell);
                        detailTable.addCell(toiThieuCell);
                    }
                }
                document.add(detailTable);

                document.close();
                JOptionPane.showMessageDialog(this, "Xuất PDF thành công!\n" + filePath, "Thông báo",
                        JOptionPane.INFORMATION_MESSAGE);
                try {
                    if (Desktop.isDesktopSupported()) {
                        Desktop.getDesktop().open(new java.io.File(filePath));
                    }
                } catch (Exception ex) {
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Lỗi khi xuất PDF: " + ex.getMessage(), "Lỗi",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}