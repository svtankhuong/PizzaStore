package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import BUS.SanPhamBUS;
import DTO.SanPhamDTO;

public class SanPhamGUI extends JPanel {
    private JTextField txtMaSP;
    private JTextField txtTen;
    private JComboBox<String> cboLoai;
    private JTextField txtSoLuong;
    private JTextField txtDonViTinh;
    private JTextField txtDonGia;
    private JTextField txtTimKiem;
    private JTable tableSanPham;
    private DefaultTableModel tableModel;
    private JLabel lblHinhAnh;
    private String currentImagePath = "";

    private SanPhamBUS sanPhamBUS;

    public SanPhamGUI() {
        sanPhamBUS = new SanPhamBUS();
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BorderLayout());
        contentPanel.setBackground(Color.WHITE);

        JPanel tabPanel = new JPanel();
        tabPanel.setLayout(new BoxLayout(tabPanel, BoxLayout.X_AXIS));
        tabPanel.setBackground(Color.WHITE);
        tabPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.LIGHT_GRAY));

        JLabel sanPhamTab = new JLabel("Sản phẩm");
        sanPhamTab.setFont(new Font("Arial", Font.BOLD, 20));
        sanPhamTab.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        sanPhamTab.setForeground(new Color(255, 102, 0));

        tabPanel.add(Box.createHorizontalGlue());
        tabPanel.add(sanPhamTab);
        tabPanel.add(Box.createHorizontalGlue());

        JPanel mainContentPanel = new JPanel();
        mainContentPanel.setLayout(new BorderLayout(0, 20));
        mainContentPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainContentPanel.setBackground(Color.WHITE);

        JPanel formPanel = createFormPanel();
        formPanel.setVisible(true);
        formPanel.setPreferredSize(new Dimension(0, 300));

        JPanel tablePanel = createTablePanel();
        tablePanel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));

        mainContentPanel.add(formPanel, BorderLayout.NORTH);
        mainContentPanel.add(tablePanel, BorderLayout.CENTER);

        contentPanel.add(tabPanel, BorderLayout.NORTH);
        contentPanel.add(mainContentPanel, BorderLayout.CENTER);

        add(contentPanel, BorderLayout.CENTER);

        loadData();
    }

    private JPanel createFormPanel() {
        JPanel formPanel = new JPanel(new BorderLayout());
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));

        JLabel formTitle = new JLabel("Thông tin sản phẩm");
        formTitle.setFont(new Font("Arial", Font.BOLD, 16));
        formTitle.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JPanel fieldsPanel = new JPanel(new GridBagLayout());
        fieldsPanel.setBackground(Color.WHITE);
        fieldsPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        JPanel leftPanel = new JPanel(new GridBagLayout());
        leftPanel.setBackground(Color.WHITE);

        // Mã SP
        addFormField(leftPanel, "Mã sản phẩm", txtMaSP = new JTextField(), 0);

        // Tên SP
        addFormField(leftPanel, "Tên sản phẩm", txtTen = new JTextField(), 1);

        // Loại
        String[] loaiOptions = { "1 - Món chính", "2 - Đồ uống", "3 - Khác" };
        cboLoai = new JComboBox<>(loaiOptions);
        addFormField(leftPanel, "Loại", cboLoai, 2);

        // Số lượng
        addFormField(leftPanel, "Số lượng", txtSoLuong = new JTextField(), 3);

        // Đơn vị tính
        addFormField(leftPanel, "Đơn vị tính", txtDonViTinh = new JTextField(), 4);

        // Đơn giá
        addFormField(leftPanel, "Đơn giá", txtDonGia = new JTextField(), 5);

        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.setBackground(Color.WHITE);
        rightPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 10));

        lblHinhAnh = new JLabel();
        lblHinhAnh.setPreferredSize(new Dimension(200, 200));
        lblHinhAnh.setHorizontalAlignment(JLabel.CENTER);
        lblHinhAnh.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));

        try {
            ImageIcon placeholderIcon = new ImageIcon(getClass().getResource("/resources/placeholder.png"));
            if (placeholderIcon.getIconWidth() == -1) {
                lblHinhAnh.setText("No Image");
            } else {
                Image img = placeholderIcon.getImage();
                Image resizedImg = img.getScaledInstance(180, 180, Image.SCALE_SMOOTH);
                lblHinhAnh.setIcon(new ImageIcon(resizedImg));
            }
        } catch (Exception e) {
            lblHinhAnh.setText("No Image");
        }

        JButton btnChonAnh = new JButton("Chọn ảnh");
        btnChonAnh.setFont(new Font("Arial", Font.PLAIN, 14));
        btnChonAnh.addActionListener(e -> chonAnh());

        JPanel imageButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        imageButtonPanel.setBackground(Color.WHITE);
        imageButtonPanel.add(btnChonAnh);

        rightPanel.add(lblHinhAnh, BorderLayout.CENTER);
        rightPanel.add(imageButtonPanel, BorderLayout.SOUTH);

        JPanel searchPanel = new JPanel(new BorderLayout(10, 0));
        searchPanel.setBackground(Color.WHITE);
        searchPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        JLabel lblTimKiem = new JLabel("Từ khoá tìm:");
        lblTimKiem.setFont(new Font("Arial", Font.BOLD, 14));

        txtTimKiem = new JTextField();
        txtTimKiem.setFont(new Font("Arial", Font.PLAIN, 14));

        searchPanel.add(lblTimKiem, BorderLayout.WEST);
        searchPanel.add(txtTimKiem, BorderLayout.CENTER);

        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        JButton btnThem = createButtonWithIcon("Thêm",
                "D:\\Java\\PizzaStore\\src\\main\\resources\\image\\add-icon.png", e -> them());
        JButton btnSua = createButtonWithIcon("Sửa", "D:\\PizzaStore\\src\\main\\resources\\image\\fix.png",
                e -> sua());
        JButton btnXoa = createButtonWithIcon("Xoá",
                "D:\\Java\\PizzaStore\\src\\main\\resources\\image\\delete-icon.png", e -> xoa());
        JButton btnTimKiem = createButtonWithIcon("Tìm kiếm",
                "D:\\Java\\PizzaStore\\src\\main\\resources\\image\\Search-icon.png", e -> timKiem());
        JButton btnXuat = createButtonWithIcon("Xuất Excel",
                "D:\\Java\\PizzaStore\\src\\main\\resources\\image\\excel-icon.png", e -> xuatExcelOption());
        JButton btnLamMoi = createButtonWithIcon("Làm mới",
                "D:\\Java\\PizzaStore\\src\\main\\resources\\image\\Refresh-icon.png", e -> lamMoi());

        buttonPanel.add(btnThem);
        buttonPanel.add(btnSua);
        buttonPanel.add(btnXoa);
        buttonPanel.add(btnTimKiem);
        buttonPanel.add(btnXuat);
        buttonPanel.add(btnLamMoi);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.7;
        fieldsPanel.add(leftPanel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 0.3;
        fieldsPanel.add(rightPanel, gbc);

        formPanel.add(formTitle, BorderLayout.NORTH);
        formPanel.add(fieldsPanel, BorderLayout.CENTER);
        formPanel.add(searchPanel, BorderLayout.NORTH);
        formPanel.add(buttonPanel, BorderLayout.SOUTH);

        return formPanel;
    }

    private void addFormField(JPanel panel, String labelText, JComponent field, int row) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Arial", Font.BOLD, 14));

        if (field instanceof JTextField) {
            ((JTextField) field).setFont(new Font("Arial", Font.PLAIN, 14));
            // Add Enter key listener
            ((JTextField) field).addActionListener(e -> {
                Component nextComponent = field.getNextFocusableComponent();
                if (nextComponent != null) {
                    nextComponent.requestFocus();
                }
            });
        } else if (field instanceof JComboBox) {
            ((JComboBox<?>) field).setFont(new Font("Arial", Font.PLAIN, 14));
        }

        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.weightx = 0.3;
        panel.add(label, gbc);

        gbc.gridx = 1;
        gbc.gridy = row;
        gbc.weightx = 0.7;
        panel.add(field, gbc);
    }

    private JButton createButtonWithIcon(String text, String iconPath, ActionListener listener) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setBackground(new Color(240, 240, 240));
        button.setFocusPainted(false);
        button.setBorderPainted(true);
        button.setOpaque(true);
        button.addActionListener(listener);

        // Adjust button size based on text length
        int width = text.equals("Làm mới") ? 140 : 120;
        button.setPreferredSize(new Dimension(width, 35));

        try {
            ImageIcon icon = new ImageIcon(iconPath);
            if (icon.getIconWidth() != -1) {
                Image img = icon.getImage();
                Image resizedImg = img.getScaledInstance(20, 20, Image.SCALE_SMOOTH);
                button.setIcon(new ImageIcon(resizedImg));
                button.setIconTextGap(8);
            }
        } catch (Exception e) {
        }

        return button;
    }

    private JPanel createTablePanel() {
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBackground(Color.WHITE);

        JLabel tableTitle = new JLabel("Danh sách sản phẩm");
        tableTitle.setFont(new Font("Arial", Font.BOLD, 16));
        tableTitle.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        tableModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tableModel.addColumn("Mã sản phẩm");
        tableModel.addColumn("Tên sản phẩm");
        tableModel.addColumn("Loại");
        tableModel.addColumn("Đơn giá");
        tableModel.addColumn("Số lượng");
        tableModel.addColumn("Đơn vị tính");
        tableModel.addColumn("Ảnh");

        tableSanPham = new JTable(tableModel);
        tableSanPham.setRowHeight(40);
        tableSanPham.setFont(new Font("Arial", Font.PLAIN, 14));
        tableSanPham.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tableSanPham.setGridColor(new Color(240, 240, 240));

        JTableHeader header = tableSanPham.getTableHeader();
        header.setFont(new Font("Arial", Font.BOLD, 14));
        header.setBackground(new Color(240, 240, 240));
        header.setForeground(Color.BLACK);
        header.setPreferredSize(new Dimension(0, 40));

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);

        for (int i = 0; i < tableSanPham.getColumnCount(); i++) {
            tableSanPham.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        tableSanPham.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = tableSanPham.getSelectedRow();
                if (selectedRow != -1) {
                    hienThiSanPham(selectedRow);
                }
            }
        });

        JScrollPane scrollPane = new JScrollPane(tableSanPham);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getViewport().setBackground(Color.WHITE);

        tablePanel.add(tableTitle, BorderLayout.NORTH);
        tablePanel.add(scrollPane, BorderLayout.CENTER);

        return tablePanel;
    }

    private void hienThiAnhMacDinh() {
        try {
            ImageIcon placeholderIcon = new ImageIcon(getClass().getResource("/resources/placeholder.png"));
            if (placeholderIcon.getIconWidth() == -1) {
                lblHinhAnh.setText("No Image");
            } else {
                Image img = placeholderIcon.getImage();
                Image resizedImg = img.getScaledInstance(180, 180, Image.SCALE_SMOOTH);
                lblHinhAnh.setIcon(new ImageIcon(resizedImg));
                lblHinhAnh.setText("");
            }
        } catch (Exception e) {
            lblHinhAnh.setText("No Image");
        }
    }

    private void chonAnh() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.setDialogTitle("Chọn hình ảnh sản phẩm");

        javax.swing.filechooser.FileFilter imageFilter = new javax.swing.filechooser.FileNameExtensionFilter(
                "Image files", "jpg", "jpeg", "png", "gif");
        fileChooser.setFileFilter(imageFilter);

        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            currentImagePath = selectedFile.getAbsolutePath();

            try {
                ImageIcon imageIcon = new ImageIcon(currentImagePath);
                if (imageIcon.getIconWidth() == -1) {
                    JOptionPane.showMessageDialog(this, "Không thể tải hình ảnh!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    hienThiAnhMacDinh();
                } else {
                    Image img = imageIcon.getImage();
                    Image resizedImg = img.getScaledInstance(180, 180, Image.SCALE_SMOOTH);
                    lblHinhAnh.setIcon(new ImageIcon(resizedImg));
                    lblHinhAnh.setText("");
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Không thể tải hình ảnh!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                hienThiAnhMacDinh();
            }
        }
    }

    private void loadData() {
        tableModel.setRowCount(0);

        List<SanPhamDTO> danhSachSP = sanPhamBUS.getAllSanPham();

        for (SanPhamDTO sp : danhSachSP) {
            String loaiStr = "";
            switch (sp.getLoai()) {
                case "1":
                    loaiStr = "Món chính";
                    break;
                case "2":
                    loaiStr = "Đồ uống";
                    break;
                case "3":
                    loaiStr = "Khác";
                    break;
                default:
                    loaiStr = sp.getLoai();
            }
            tableModel.addRow(new Object[] {
                    sp.getMaSP(),
                    sp.getTenSP(),
                    loaiStr,
                    sp.getDonGia(),
                    sp.getSoLuong(),
                    sp.getDvTinh(),
                    sp.getAnhSP()
            });
        }
    }

    private void hienThiSanPham(int row) {
        txtMaSP.setText(tableModel.getValueAt(row, 0).toString());
        txtTen.setText(tableModel.getValueAt(row, 1).toString());

        String loai = tableModel.getValueAt(row, 2).toString().trim();
        for (int i = 0; i < cboLoai.getItemCount(); i++) {
            String item = cboLoai.getItemAt(i);
            String tenLoaiCombo = item.substring(item.indexOf('-') + 1).trim();
            if (tenLoaiCombo.equalsIgnoreCase(loai)) {
                cboLoai.setSelectedIndex(i);
                break;
            }
        }

        txtDonGia.setText(tableModel.getValueAt(row, 3).toString());
        txtSoLuong.setText(tableModel.getValueAt(row, 4).toString());
        txtDonViTinh.setText(tableModel.getValueAt(row, 5).toString());

        String imagePath = tableModel.getValueAt(row, 6).toString();
        currentImagePath = imagePath;

        try {
            ImageIcon imageIcon = new ImageIcon(imagePath);
            if (imageIcon.getIconWidth() == -1) {
                lblHinhAnh.setIcon(null);
                lblHinhAnh.setText("No Image");
            } else {
                Image img = imageIcon.getImage();
                Image resizedImg = img.getScaledInstance(180, 180, Image.SCALE_SMOOTH);
                lblHinhAnh.setIcon(new ImageIcon(resizedImg));
                lblHinhAnh.setText("");
            }
        } catch (Exception e) {
            lblHinhAnh.setIcon(null);
            lblHinhAnh.setText("No Image");
        }
    }

    private boolean validateInput() {
        if (txtMaSP.getText().trim().isEmpty() ||
                txtTen.getText().trim().isEmpty() ||
                txtSoLuong.getText().trim().isEmpty() ||
                txtDonViTinh.getText().trim().isEmpty() ||
                txtDonGia.getText().trim().isEmpty() ||
                currentImagePath.isEmpty()) {

            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        try {
            int soLuong = Integer.parseInt(txtSoLuong.getText().trim());
            if (soLuong < 0) {
                JOptionPane.showMessageDialog(this, "Số lượng phải là số nguyên dương!", "Lỗi",
                        JOptionPane.ERROR_MESSAGE);
                return false;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Số lượng phải là số nguyên!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        try {
            float donGia = Float.parseFloat(txtDonGia.getText().trim());
            if (donGia < 0) {
                JOptionPane.showMessageDialog(this, "Đơn giá phải là số dương!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Đơn giá phải là số!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        return true;
    }

    private void them() {
        if (!validateInput())
            return;

        int maSP = Integer.parseInt(txtMaSP.getText().trim());
        if (sanPhamBUS.daTonTaiMaSP(maSP)) {
            JOptionPane.showMessageDialog(this, "Mã sản phẩm đã tồn tại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String selectedType = cboLoai.getSelectedItem().toString();
        SanPhamDTO sp = new SanPhamDTO(
                maSP,
                txtTen.getText().trim(),
                txtDonViTinh.getText().trim(),
                (long) Float.parseFloat(txtDonGia.getText().trim()),
                Integer.parseInt(txtSoLuong.getText().trim()),
                selectedType.substring(0, 1),
                currentImagePath);

        boolean success = sanPhamBUS.themSanPham(sp);
        showResult(success, "Thêm");

        if (success) {
            loadData();
            lamMoi(); // reset toàn bộ form và ảnh
        }
    }

    private void showResult(boolean success, String action) {
        if (success) {
            JOptionPane.showMessageDialog(this, action + " sản phẩm thành công!", "Thông báo",
                    JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, action + " sản phẩm thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void xoa() {
        int selectedRow = tableSanPham.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn sản phẩm cần xóa!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this,
                "Bạn có chắc chắn muốn xóa sản phẩm này?",
                "Xác nhận xóa",
                JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            String maSP = tableModel.getValueAt(selectedRow, 0).toString();
            boolean success = sanPhamBUS.xoaSanPham(maSP);

            if (success) {
                JOptionPane.showMessageDialog(this, "Xóa sản phẩm thành công!", "Thông báo",
                        JOptionPane.INFORMATION_MESSAGE);
                loadData();
                lamMoi(); // reset toàn bộ form và ảnh
            } else {
                JOptionPane.showMessageDialog(this, "Xóa sản phẩm thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void timKiem() {
        String keyword = txtTimKiem.getText().trim();
        if (keyword.isEmpty()) {
            loadData();
            return;
        }

        List<SanPhamDTO> ketQua = sanPhamBUS.timKiemSanPham(keyword);

        tableModel.setRowCount(0);
        for (SanPhamDTO sp : ketQua) {
            String loaiStr = "";
            switch (sp.getLoai()) {
                case "1":
                    loaiStr = "Món chính";
                    break;
                case "2":
                    loaiStr = "Đồ uống";
                    break;
                case "3":
                    loaiStr = "Khác";
                    break;
                default:
                    loaiStr = sp.getLoai();
            }
            tableModel.addRow(new Object[] {
                    sp.getMaSP(),
                    sp.getTenSP(),
                    loaiStr,
                    sp.getDonGia(),
                    sp.getSoLuong(),
                    sp.getDvTinh(),
                    sp.getAnhSP()
            });
        }
    }

    private void xuatSanPham() {
        int selectedRow = tableSanPham.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn sản phẩm để xuất!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Chọn nơi lưu file Excel cho sản phẩm");
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Excel Files", "xlsx"));
        int userSelection = fileChooser.showSaveDialog(this);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            String filePath = fileChooser.getSelectedFile().getAbsolutePath();
            if (!filePath.toLowerCase().endsWith(".xlsx")) {
                filePath += ".xlsx";
            }
            // Lấy dữ liệu sản phẩm đang chọn
            SanPhamDTO sp = new SanPhamDTO(
                    Integer.parseInt(tableModel.getValueAt(selectedRow, 0).toString()),
                    tableModel.getValueAt(selectedRow, 1).toString(),
                    tableModel.getValueAt(selectedRow, 5).toString(),
                    Long.parseLong(tableModel.getValueAt(selectedRow, 3).toString()),
                    Integer.parseInt(tableModel.getValueAt(selectedRow, 4).toString()),
                    tableModel.getValueAt(selectedRow, 2).toString(),
                    tableModel.getValueAt(selectedRow, 6).toString());
            boolean success = sanPhamBUS.xuatExcelMotSanPham(sp, filePath);
            if (success) {
                JOptionPane.showMessageDialog(this, "Xuất sản phẩm thành công!\n" + filePath, "Thông báo",
                        JOptionPane.INFORMATION_MESSAGE);
                lamMoi();
            } else {
                JOptionPane.showMessageDialog(this, "Xuất sản phẩm thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void xuatDanhSach() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Chọn nơi lưu file Excel cho danh sách");
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Excel Files", "xlsx"));
        int userSelection = fileChooser.showSaveDialog(this);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            String filePath = fileChooser.getSelectedFile().getAbsolutePath();
            if (!filePath.toLowerCase().endsWith(".xlsx")) {
                filePath += ".xlsx";
            }
            boolean success = sanPhamBUS.xuatExcel(filePath);
            if (success) {
                JOptionPane.showMessageDialog(this, "Xuất danh sách thành công!\n" + filePath, "Thông báo",
                        JOptionPane.INFORMATION_MESSAGE);
                lamMoi();
            } else {
                JOptionPane.showMessageDialog(this, "Xuất danh sách thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void xuatExcelOption() {
        String[] options = { "Xuất sản phẩm", "Xuất danh sách" };
        int choice = JOptionPane.showOptionDialog(this, "Bạn muốn xuất gì?", "Xuất Excel",
                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
        if (choice == 0) {
            xuatSanPham();
        } else if (choice == 1) {
            xuatDanhSach();
        }
    }

    private void lamMoi() {
        txtMaSP.setText("");
        txtTen.setText("");
        cboLoai.setSelectedIndex(0);
        txtSoLuong.setText("");
        txtDonViTinh.setText("");
        txtDonGia.setText("");
        txtTimKiem.setText("");
        currentImagePath = "";

        // Clear image completely
        lblHinhAnh.setIcon(null);
        lblHinhAnh.setText("No Image");

        // Focus on first field
        txtMaSP.requestFocus();

        // Reload data
        loadData();
    }

    private void sua() {
        int selectedRow = tableSanPham.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn sản phẩm cần sửa!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (!validateInput())
            return;

        // Lấy dữ liệu gốc từ bảng
        int maSP = Integer.parseInt(txtMaSP.getText().trim());
        String tenSPGoc = tableModel.getValueAt(selectedRow, 1).toString();
        String loaiGoc = tableModel.getValueAt(selectedRow, 2).toString();
        String donGiaGoc = tableModel.getValueAt(selectedRow, 3).toString();
        String soLuongGoc = tableModel.getValueAt(selectedRow, 4).toString();
        String dvTinhGoc = tableModel.getValueAt(selectedRow, 5).toString();
        String anhGoc = tableModel.getValueAt(selectedRow, 6).toString();

        String selectedType = cboLoai.getSelectedItem().toString();
        String loaiForm = selectedType.substring(selectedType.indexOf('-') + 1).trim();

        boolean isChanged = !txtTen.getText().trim().equals(tenSPGoc) ||
                !loaiForm.equalsIgnoreCase(loaiGoc) ||
                !txtDonGia.getText().trim().equals(donGiaGoc) ||
                !txtSoLuong.getText().trim().equals(soLuongGoc) ||
                !txtDonViTinh.getText().trim().equals(dvTinhGoc) ||
                !currentImagePath.equals(anhGoc);

        if (!isChanged) {
            JOptionPane.showMessageDialog(this, "Bạn chưa thay đổi thông tin nào!", "Thông báo",
                    JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        SanPhamDTO sp = new SanPhamDTO(
                maSP,
                txtTen.getText().trim(),
                txtDonViTinh.getText().trim(),
                (long) Float.parseFloat(txtDonGia.getText().trim()),
                Integer.parseInt(txtSoLuong.getText().trim()),
                selectedType.substring(0, 1),
                currentImagePath);

        boolean success = sanPhamBUS.suaSanPham(sp);
        showResult(success, "Cập nhật");
        if (success) {
            loadData();
            lamMoi(); // reset toàn bộ form và ảnh
        }
    }
}