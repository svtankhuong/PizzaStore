package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import BUS.ChiTietKMBUS;
import DTO.ChiTietKMDTO;

public class ChiTietKMGUI extends JDialog {
    private int currentMaKM;
    private JTable table;
    private DefaultTableModel tableModel;
    private ChiTietKMBUS bus = new ChiTietKMBUS();

    public ChiTietKMGUI(Frame parent, int maKM) {
        super(parent, "Chi tiết khuyến mãi", true);
        this.currentMaKM = maKM;
        setSize(900, 550);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout(10, 10));

        JPanel titlePanel = new JPanel();
        JLabel lblTitle = new JLabel("Chi tiết khuyến mãi");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 22));
        titlePanel.add(lblTitle);

        tableModel = new DefaultTableModel();
        tableModel.setColumnIdentifiers(new String[] {
                "Phần trăm giảm", "Tối thiểu giảm", "Tên CTKM"
        });

        table = new JTable(tableModel);
        table.setRowHeight(30);
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        table.setFont(new Font("Arial", Font.PLAIN, 13));

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        table.getColumnModel().getColumn(0).setPreferredWidth(120);
        table.getColumnModel().getColumn(1).setPreferredWidth(120);
        table.getColumnModel().getColumn(2).setPreferredWidth(300);

        JScrollPane scroll = new JScrollPane(table);
        scroll.setPreferredSize(new Dimension(850, 350));
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 0));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        buttonPanel.setBackground(Color.WHITE);

        JButton btnThem = createActionButton("Thêm", "add-icon.png", e -> them());
        JButton btnSua = createActionButton("Sửa", "Pencil-icon.png", e -> sua());
        JButton btnXoa = createActionButton("Xóa", "delete-icon.png", e -> xoa());

        buttonPanel.add(btnThem);
        buttonPanel.add(btnSua);
        buttonPanel.add(btnXoa);

        add(titlePanel, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(buttonPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
        loadData(maKM);
    }

    private JButton createActionButton(String text, String iconName, ActionListener listener) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setForeground(Color.BLACK);
        button.setBackground(new Color(240, 240, 240));
        button.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
        button.setFocusPainted(false);

        try {
            ImageIcon icon = new ImageIcon(
                    new ImageIcon("D:\\Java\\PizzaStore\\src\\main\\resources\\image\\" + iconName)
                            .getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH));
            button.setIcon(icon);
            button.setIconTextGap(8);
        } catch (Exception e) {
            e.printStackTrace();
        }

        button.addActionListener(listener);
        return button;
    }

    private void them() {
        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
        JTextField txtPhanTramGiam = new JTextField();
        JTextField txtToiThieuGiam = new JTextField();
        JTextField txtTenCTKM = new JTextField();

        // Thêm KeyListener để xử lý phím Enter
        KeyAdapter enterKeyAdapter = new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    e.consume(); // Ngăn không cho form đóng
                }
            }
        };

        txtPhanTramGiam.addKeyListener(enterKeyAdapter);
        txtToiThieuGiam.addKeyListener(enterKeyAdapter);
        txtTenCTKM.addKeyListener(enterKeyAdapter);

        panel.add(new JLabel("Phần trăm giảm (%):"));
        panel.add(txtPhanTramGiam);
        panel.add(new JLabel("Số tiền tối thiểu để được giảm (VNĐ):"));
        panel.add(txtToiThieuGiam);
        panel.add(new JLabel("Tên chương trình khuyến mãi:"));
        panel.add(txtTenCTKM);

        // Tạo JOptionPane tùy chỉnh
        JOptionPane optionPane = new JOptionPane(panel, JOptionPane.PLAIN_MESSAGE, JOptionPane.OK_CANCEL_OPTION);
        JDialog dialog = optionPane.createDialog(this, "Thêm chi tiết khuyến mãi");

        // Thêm KeyListener cho dialog
        dialog.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    e.consume(); // Ngăn không cho dialog đóng
                }
            }
        });

        dialog.setVisible(true);
        Object result = optionPane.getValue();

        if (result != null && (Integer) result == JOptionPane.OK_OPTION) {
            try {
                String strPhanTram = txtPhanTramGiam.getText().trim();
                String strToiThieu = txtToiThieuGiam.getText().trim();
                String tenCTKM = txtTenCTKM.getText().trim();

                if (strPhanTram.isEmpty() || strToiThieu.isEmpty() || tenCTKM.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin cho tất cả các trường!",
                            "Thiếu thông tin", JOptionPane.WARNING_MESSAGE);
                    // Hiển thị lại form với dữ liệu đã nhập
                    showAddFormWithData(strPhanTram, strToiThieu, tenCTKM);
                    return;
                }
                long phanTram = Long.parseLong(strPhanTram);
                long toiThieu = Long.parseLong(strToiThieu);
                if (phanTram < 0 || phanTram > 100) {
                    JOptionPane.showMessageDialog(this, "Phần trăm giảm phải nằm trong khoảng 0 đến 100!",
                            "Giá trị không hợp lệ", JOptionPane.WARNING_MESSAGE);
                    // Hiển thị lại form với dữ liệu đã nhập
                    showAddFormWithData(strPhanTram, strToiThieu, tenCTKM);
                    return;
                }
                if (toiThieu < 0) {
                    JOptionPane.showMessageDialog(this, "Số tiền tối thiểu phải lớn hơn hoặc bằng 0!",
                            "Giá trị không hợp lệ", JOptionPane.WARNING_MESSAGE);
                    // Hiển thị lại form với dữ liệu đã nhập
                    showAddFormWithData(strPhanTram, strToiThieu, tenCTKM);
                    return;
                }
                ChiTietKMDTO ctkm = new ChiTietKMDTO(currentMaKM, phanTram, toiThieu, tenCTKM);
                if (bus.themChiTietKM(ctkm)) {
                    JOptionPane.showMessageDialog(this, "Thêm chi tiết khuyến mãi thành công!", "Thành công",
                            JOptionPane.INFORMATION_MESSAGE);
                    loadData(currentMaKM);
                } else {
                    JOptionPane.showMessageDialog(this,
                            "Không thể thêm chi tiết khuyến mãi. Vui lòng kiểm tra lại dữ liệu hoặc liên hệ quản trị viên!",
                            "Lỗi", JOptionPane.ERROR_MESSAGE);
                    // Hiển thị lại form với dữ liệu đã nhập
                    showAddFormWithData(strPhanTram, strToiThieu, tenCTKM);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập số hợp lệ cho phần trăm giảm và số tiền tối thiểu!",
                        "Lỗi định dạng số", JOptionPane.ERROR_MESSAGE);
                // Hiển thị lại form với dữ liệu đã nhập
                showAddFormWithData(txtPhanTramGiam.getText().trim(),
                        txtToiThieuGiam.getText().trim(),
                        txtTenCTKM.getText().trim());
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this,
                        "Đã xảy ra lỗi: " + ex.getMessage() + "\nVui lòng thử lại hoặc liên hệ quản trị viên.",
                        "Lỗi hệ thống", JOptionPane.ERROR_MESSAGE);
                // Hiển thị lại form với dữ liệu đã nhập
                showAddFormWithData(txtPhanTramGiam.getText().trim(),
                        txtToiThieuGiam.getText().trim(),
                        txtTenCTKM.getText().trim());
            }
        }
    }

    private void showAddFormWithData(String phanTram, String toiThieu, String tenCTKM) {
        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
        JTextField txtPhanTramGiam = new JTextField(phanTram);
        JTextField txtToiThieuGiam = new JTextField(toiThieu);
        JTextField txtTenCTKM = new JTextField(tenCTKM);

        // Thêm KeyListener để xử lý phím Enter
        KeyAdapter enterKeyAdapter = new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    e.consume(); // Ngăn không cho form đóng
                }
            }
        };

        txtPhanTramGiam.addKeyListener(enterKeyAdapter);
        txtToiThieuGiam.addKeyListener(enterKeyAdapter);
        txtTenCTKM.addKeyListener(enterKeyAdapter);

        panel.add(new JLabel("Phần trăm giảm (%):"));
        panel.add(txtPhanTramGiam);
        panel.add(new JLabel("Số tiền tối thiểu để được giảm (VNĐ):"));
        panel.add(txtToiThieuGiam);
        panel.add(new JLabel("Tên chương trình khuyến mãi:"));
        panel.add(txtTenCTKM);

        // Tạo JOptionPane tùy chỉnh
        JOptionPane optionPane = new JOptionPane(panel, JOptionPane.PLAIN_MESSAGE, JOptionPane.OK_CANCEL_OPTION);
        JDialog dialog = optionPane.createDialog(this, "Thêm chi tiết khuyến mãi");

        // Thêm KeyListener cho dialog
        dialog.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    e.consume(); // Ngăn không cho dialog đóng
                }
            }
        });

        dialog.setVisible(true);
        Object result = optionPane.getValue();

        if (result != null && (Integer) result == JOptionPane.OK_OPTION) {
            try {
                String strPhanTram = txtPhanTramGiam.getText().trim();
                String strToiThieu = txtToiThieuGiam.getText().trim();
                String strTenCTKM = txtTenCTKM.getText().trim();

                if (strPhanTram.isEmpty() || strToiThieu.isEmpty() || strTenCTKM.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin cho tất cả các trường!",
                            "Thiếu thông tin", JOptionPane.WARNING_MESSAGE);
                    showAddFormWithData(strPhanTram, strToiThieu, strTenCTKM);
                    return;
                }
                long phanTramGiam = Long.parseLong(strPhanTram);
                long toiThieuGiam = Long.parseLong(strToiThieu);
                if (phanTramGiam < 0 || phanTramGiam > 100) {
                    JOptionPane.showMessageDialog(this, "Phần trăm giảm phải nằm trong khoảng 0 đến 100!",
                            "Giá trị không hợp lệ", JOptionPane.WARNING_MESSAGE);
                    showAddFormWithData(strPhanTram, strToiThieu, strTenCTKM);
                    return;
                }
                if (toiThieuGiam < 0) {
                    JOptionPane.showMessageDialog(this, "Số tiền tối thiểu phải lớn hơn hoặc bằng 0!",
                            "Giá trị không hợp lệ", JOptionPane.WARNING_MESSAGE);
                    showAddFormWithData(strPhanTram, strToiThieu, strTenCTKM);
                    return;
                }
                ChiTietKMDTO ctkm = new ChiTietKMDTO(currentMaKM, phanTramGiam, toiThieuGiam, strTenCTKM);
                if (bus.themChiTietKM(ctkm)) {
                    JOptionPane.showMessageDialog(this, "Thêm chi tiết khuyến mãi thành công!", "Thành công",
                            JOptionPane.INFORMATION_MESSAGE);
                    loadData(currentMaKM);
                } else {
                    JOptionPane.showMessageDialog(this,
                            "Không thể thêm chi tiết khuyến mãi. Vui lòng kiểm tra lại dữ liệu hoặc liên hệ quản trị viên!",
                            "Lỗi", JOptionPane.ERROR_MESSAGE);
                    showAddFormWithData(strPhanTram, strToiThieu, strTenCTKM);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập số hợp lệ cho phần trăm giảm và số tiền tối thiểu!",
                        "Lỗi định dạng số", JOptionPane.ERROR_MESSAGE);
                showAddFormWithData(txtPhanTramGiam.getText().trim(),
                        txtToiThieuGiam.getText().trim(),
                        txtTenCTKM.getText().trim());
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this,
                        "Đã xảy ra lỗi: " + ex.getMessage() + "\nVui lòng thử lại hoặc liên hệ quản trị viên.",
                        "Lỗi hệ thống", JOptionPane.ERROR_MESSAGE);
                showAddFormWithData(txtPhanTramGiam.getText().trim(),
                        txtToiThieuGiam.getText().trim(),
                        txtTenCTKM.getText().trim());
            }
        }
    }

    private void sua() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn dòng cần sửa!");
            return;
        }
        // Lấy dữ liệu cũ
        String oldPhanTram = table.getValueAt(row, 0).toString();
        String oldToiThieu = table.getValueAt(row, 1).toString();
        String oldTenCTKM = table.getValueAt(row, 2).toString();

        showEditFormWithData(oldPhanTram, oldToiThieu, oldTenCTKM);
    }

    private void showEditFormWithData(String phanTram, String toiThieu, String tenCTKM) {
        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
        JTextField txtPhanTramGiam = new JTextField(phanTram);
        JTextField txtToiThieuGiam = new JTextField(toiThieu);
        JTextField txtTenCTKM = new JTextField(tenCTKM);

        // Thêm KeyListener để xử lý phím Enter
        KeyAdapter enterKeyAdapter = new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    e.consume(); // Ngăn không cho form đóng
                }
            }
        };

        txtPhanTramGiam.addKeyListener(enterKeyAdapter);
        txtToiThieuGiam.addKeyListener(enterKeyAdapter);
        txtTenCTKM.addKeyListener(enterKeyAdapter);

        panel.add(new JLabel("Phần trăm giảm (%):"));
        panel.add(txtPhanTramGiam);
        panel.add(new JLabel("Số tiền tối thiểu để được giảm (VNĐ):"));
        panel.add(txtToiThieuGiam);
        panel.add(new JLabel("Tên chương trình khuyến mãi:"));
        panel.add(txtTenCTKM);

        // Tạo JOptionPane tùy chỉnh
        JOptionPane optionPane = new JOptionPane(panel, JOptionPane.PLAIN_MESSAGE, JOptionPane.OK_CANCEL_OPTION);
        JDialog dialog = optionPane.createDialog(this, "Sửa chi tiết khuyến mãi");

        // Thêm KeyListener cho dialog
        dialog.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    e.consume(); // Ngăn không cho dialog đóng
                }
            }
        });

        dialog.setVisible(true);
        Object result = optionPane.getValue();

        if (result != null && (Integer) result == JOptionPane.OK_OPTION) {
            try {
                String strPhanTram = txtPhanTramGiam.getText().trim();
                String strToiThieu = txtToiThieuGiam.getText().trim();
                String strTenCTKM = txtTenCTKM.getText().trim();

                // Kiểm tra xem có thay đổi gì không
                if (strPhanTram.equals(phanTram) &&
                        strToiThieu.equals(toiThieu) &&
                        strTenCTKM.equals(tenCTKM)) {
                    JOptionPane.showMessageDialog(this, "Không có thay đổi nào được thực hiện!",
                            "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }

                if (strPhanTram.isEmpty() || strToiThieu.isEmpty() || strTenCTKM.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin cho tất cả các trường!",
                            "Thiếu thông tin", JOptionPane.WARNING_MESSAGE);
                    showEditFormWithData(strPhanTram, strToiThieu, strTenCTKM);
                    return;
                }
                long phanTramGiam = Long.parseLong(strPhanTram);
                long toiThieuGiam = Long.parseLong(strToiThieu);
                if (phanTramGiam < 0 || phanTramGiam > 100) {
                    JOptionPane.showMessageDialog(this, "Phần trăm giảm phải nằm trong khoảng 0 đến 100!",
                            "Giá trị không hợp lệ", JOptionPane.WARNING_MESSAGE);
                    showEditFormWithData(strPhanTram, strToiThieu, strTenCTKM);
                    return;
                }
                if (toiThieuGiam < 0) {
                    JOptionPane.showMessageDialog(this, "Số tiền tối thiểu phải lớn hơn hoặc bằng 0!",
                            "Giá trị không hợp lệ", JOptionPane.WARNING_MESSAGE);
                    showEditFormWithData(strPhanTram, strToiThieu, strTenCTKM);
                    return;
                }
                ChiTietKMDTO ctkm = new ChiTietKMDTO(currentMaKM, phanTramGiam, toiThieuGiam, strTenCTKM);
                if (bus.suaChiTietKM(ctkm, tenCTKM)) {
                    JOptionPane.showMessageDialog(this, "Cập nhật chi tiết khuyến mãi thành công!", "Thành công",
                            JOptionPane.INFORMATION_MESSAGE);
                    loadData(currentMaKM);
                } else {
                    JOptionPane.showMessageDialog(this,
                            "Không thể cập nhật chi tiết khuyến mãi.",
                            "Lỗi", JOptionPane.ERROR_MESSAGE);
                    showEditFormWithData(strPhanTram, strToiThieu, strTenCTKM);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập số hợp lệ cho phần trăm giảm và số tiền tối thiểu!",
                        "Lỗi định dạng số", JOptionPane.ERROR_MESSAGE);
                showEditFormWithData(txtPhanTramGiam.getText().trim(),
                        txtToiThieuGiam.getText().trim(),
                        txtTenCTKM.getText().trim());
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this,
                        "Đã xảy ra lỗi: " + ex.getMessage() + "\nVui lòng thử lại.",
                        "Lỗi hệ thống", JOptionPane.ERROR_MESSAGE);
                showEditFormWithData(txtPhanTramGiam.getText().trim(),
                        txtToiThieuGiam.getText().trim(),
                        txtTenCTKM.getText().trim());
            }
        }
    }

    private void xoa() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn dòng cần xóa!");
            return;
        }
        String oldPhanTram = table.getValueAt(row, 0).toString();
        String oldToiThieu = table.getValueAt(row, 1).toString();
        String oldTenCTKM = table.getValueAt(row, 2).toString();

        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
        panel.add(new JLabel("Phần trăm giảm (%):"));
        panel.add(new JLabel(oldPhanTram));
        panel.add(new JLabel("Số tiền tối thiểu để được giảm (VNĐ):"));
        panel.add(new JLabel(oldToiThieu));
        panel.add(new JLabel("Tên chương trình khuyến mãi:"));
        panel.add(new JLabel(oldTenCTKM));

        int result = JOptionPane.showConfirmDialog(this, panel, "Xác nhận xóa chi tiết khuyến mãi",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            String tenCTKM = oldTenCTKM;
            if (bus.xoaChiTietKM(currentMaKM, tenCTKM)) {
                JOptionPane.showMessageDialog(this, "Xóa chi tiết khuyến mãi thành công!", "Thành công",
                        JOptionPane.INFORMATION_MESSAGE);
                loadData(currentMaKM);
            } else {
                JOptionPane.showMessageDialog(this,
                        "Không thể xóa chi tiết khuyến mãi.",
                        "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void loadData(int maKM) {
        List<ChiTietKMDTO> ds = bus.getAllChiTietKM();
        tableModel.setRowCount(0);
        for (ChiTietKMDTO ctkm : ds) {
            if (ctkm.getMaKM() == maKM) {
                tableModel.addRow(new Object[] {
                        ctkm.getPhanTramGiam(),
                        ctkm.getToithieugiam(),
                        ctkm.getTenCTKM()
                });
            }
        }
    }
}
