package GUI;

//import QuanLyPizza.BUS.PhanQuyenBUS;
//import QuanLyPizza.BUS.TaiKhoanBUS;
//import QuanLyPizza.DTO.PhanQuyen;
import BUS.QuyenBUS;
import BUS.TaiKhoanBUS;
import DAO.TaiKhoanDAO;
import DTO.QuyenDTO;
import DTO.TaiKhoanDTO;
import java.awt.Image;
import java.awt.Toolkit;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;

public final class DlgCapTaiKhoan extends javax.swing.JDialog {
    
    private int maNV;

    public DlgCapTaiKhoan(int maNV) {
        this.maNV = maNV;

        initComponents();
        this.setTitle("Cấp tài khoản");
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setModal(true);
        Image icon = Toolkit.getDefaultToolkit().getImage("image/ManagerUI/icon-app.png");
        this.setIconImage(icon);

        txtMaNV.setText(String.valueOf(maNV)); // Đổi thành chuỗi để hiển thị trong JTextField
        loadDataCmbQuyen();
        loadTaiKhoanChoNhanVien(maNV);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel2 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        pnTitle = new javax.swing.JPanel();
        lblTitle = new javax.swing.JLabel();
        pnInfo = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        txtMaNV = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtTenDangNhap = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        cmbQuyen = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        txtMatKhau = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        btnTaoTaiKhoan = new javax.swing.JButton();
        btnSuaTaiKhoan = new javax.swing.JButton();
        btnXoataikhoan = new javax.swing.JButton();

        jLabel2.setText("jLabel2");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        lblTitle.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        lblTitle.setText("Cấp tài khoản nhân viên");
        pnTitle.add(lblTitle);

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel3.setText("Mã Nhân viên");

        txtMaNV.setEditable(false);
        txtMaNV.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel4.setText("Tên đăng nhập");

        txtTenDangNhap.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txtTenDangNhap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTenDangNhapActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel5.setText("Quyền");

        cmbQuyen.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        cmbQuyen.setToolTipText("");

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel6.setText("Mật khẩu");

        txtMatKhau.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txtMatKhau.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMatKhauActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnInfoLayout = new javax.swing.GroupLayout(pnInfo);
        pnInfo.setLayout(pnInfoLayout);
        pnInfoLayout.setHorizontalGroup(
            pnInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnInfoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(jLabel3)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6))
                .addGap(18, 18, 18)
                .addGroup(pnInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtMatKhau)
                    .addComponent(txtMaNV)
                    .addComponent(txtTenDangNhap)
                    .addComponent(cmbQuyen, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        pnInfoLayout.setVerticalGroup(
            pnInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnInfoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtMaNV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtTenDangNhap, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtMatKhau, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(cmbQuyen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btnTaoTaiKhoan.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        btnTaoTaiKhoan.setText("Cấp tài khoản");
        btnTaoTaiKhoan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTaoTaiKhoanActionPerformed(evt);
            }
        });

        btnSuaTaiKhoan.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        btnSuaTaiKhoan.setText("Đổi tài khoản/mật khẩu");
        btnSuaTaiKhoan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaTaiKhoanActionPerformed(evt);
            }
        });

        btnXoataikhoan.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        btnXoataikhoan.setText("Xóa tài khoản");
        btnXoataikhoan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoataikhoanActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnTitle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(pnInfo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnTaoTaiKhoan)
                .addGap(48, 48, 48)
                .addComponent(btnSuaTaiKhoan)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 17, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGap(111, 111, 111)
                .addComponent(btnXoataikhoan)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(pnTitle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnInfo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 29, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnTaoTaiKhoan)
                        .addComponent(btnSuaTaiKhoan)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnXoataikhoan, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

//    private TaiKhoanBUS taiKhoanBUS = new TaiKhoanBUS();
//    private PhanQuyenBUS phanQuyenBUS = new PhanQuyenBUS();

    private void btnTaoTaiKhoanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTaoTaiKhoanActionPerformed
        // Lấy dữ liệu từ các ô nhập liệu
        String tenDangNhap = txtTenDangNhap.getText().trim();
        String matKhau = txtMatKhau.getText().trim();

        int maNV = this.maNV;

        // Mặc định mã quyền là 4 (Nhân viên)
        int maQuyen = 4;
        String tenQuyen = (String) cmbQuyen.getSelectedItem();

        // Tìm mã quyền tương ứng với tên quyền được chọn
        ArrayList<QuyenDTO> dsQuyen = QuyenBUS.getListQuyen();
        for (QuyenDTO q : dsQuyen) {
            if (q.getTenQuyen().equalsIgnoreCase(tenQuyen)) {
                maQuyen = q.getMaQuyen();
                break;
            }
        }

        // Kiểm tra dữ liệu đầu vào
        if (tenDangNhap.isEmpty() || matKhau.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ tên đăng nhập và mật khẩu!", "Lỗi", JOptionPane.WARNING_MESSAGE);
            return;
        }
        // Kiểm tra xem nhân viên đã có tài khoản chưa
        TaiKhoanBUS taiKhoanBUS = new TaiKhoanBUS();
        boolean success = taiKhoanBUS.capTaiKhoan(tenDangNhap, matKhau, maQuyen, maNV);

        // Thông báo kết quả
        if (success) {
            JOptionPane.showMessageDialog(this, "Cấp tài khoản thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            this.dispose(); // Đóng dialog
        } else {
            JOptionPane.showMessageDialog(this, "Cấp tài khoản thất bại! Tên đăng nhập có thể đã tồn tại hoặc nhân viên đã có tài khoản.", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnTaoTaiKhoanActionPerformed

    private void txtTenDangNhapActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTenDangNhapActionPerformed

    }//GEN-LAST:event_txtTenDangNhapActionPerformed

    private void txtMatKhauActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMatKhauActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtMatKhauActionPerformed

    private void btnSuaTaiKhoanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaTaiKhoanActionPerformed
        // Lấy thông tin từ các trường nhập liệu
        String tenDangNhap = txtTenDangNhap.getText();
        String matKhau = txtMatKhau.getText();
        String tenQuyen = (String) cmbQuyen.getSelectedItem(); // Lấy quyền người dùng chọn

        // Kiểm tra nếu các trường khác không được để trống
        if (tenDangNhap.isEmpty() || matKhau.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin tài khoản!");
            return;
        }

        // Lấy mã quyền tương ứng với tên quyền đã chọn
        int maQuyen = -1;
        ArrayList<QuyenDTO> dsQuyen = QuyenBUS.getListQuyen();
        for (QuyenDTO q : dsQuyen) {
            if (q.getTenQuyen().equalsIgnoreCase(tenQuyen)) {
                maQuyen = q.getMaQuyen();
                break;
            }
        }
        TaiKhoanBUS taiKhoanBUS = new TaiKhoanBUS();
        boolean success = taiKhoanBUS.suaTaiKhoan(tenDangNhap, matKhau, maQuyen, maNV);

        if (success) {
            JOptionPane.showMessageDialog(this, "Cập nhật tài khoản thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            this.dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Cập nhật tài khoản thất bại! Có thể nhân viên chưa có tài khoản hoặc xảy ra lỗi.", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnSuaTaiKhoanActionPerformed

    private void btnXoataikhoanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoataikhoanActionPerformed
        String tenDangNhap = txtTenDangNhap.getText(); // Lấy tên đăng nhập từ ô nhập

        // Gọi phương thức XoaTaiKhoan từ TaiKhoanBUS để xóa tài khoản
        TaiKhoanBUS bus = new TaiKhoanBUS();
        bus.XoaTaiKhoan(tenDangNhap); // Thực hiện xóa tài khoản

        // Cập nhật lại giao diện (nếu cần), ví dụ: làm trống ô nhập hoặc làm mới bảng
        txtTenDangNhap.setText(""); // Làm trống ô nhập tên đăng nhập
        txtMatKhau.setText(""); // Làm trống ô nhập tên đăng nhập
    }//GEN-LAST:event_btnXoataikhoanActionPerformed

    private void loadDataCmbQuyen() {
        cmbQuyen.removeAllItems();
        QuyenBUS.docDanhSachQuyen();
        ArrayList<QuyenDTO> dsq = QuyenBUS.getListQuyen();
        for (QuyenDTO q : dsq) {
            cmbQuyen.addItem(q.getTenQuyen()); // Giả sử tên quyền là getTenQuyen()
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnSuaTaiKhoan;
    private javax.swing.JButton btnTaoTaiKhoan;
    private javax.swing.JButton btnXoataikhoan;
    private javax.swing.JComboBox<String> cmbQuyen;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JPanel pnInfo;
    private javax.swing.JPanel pnTitle;
    private javax.swing.JTextField txtMaNV;
    private javax.swing.JTextField txtMatKhau;
    private javax.swing.JTextField txtTenDangNhap;
    // End of variables declaration//GEN-END:variables
    public void loadTaiKhoanChoNhanVien(int maNV) {
        TaiKhoanBUS taiKhoanBUS = new TaiKhoanBUS();
        TaiKhoanDTO taiKhoan = taiKhoanBUS.getTaiKhoanTheoMaNV(maNV);

        if (taiKhoan != null) {
            txtTenDangNhap.setText(taiKhoan.getTenDangNhap());
            txtMatKhau.setText(taiKhoan.getMatKhau());

            int maQuyen = taiKhoan.getMaQuyen();

            // Lấy tên quyền từ maQuyen
            QuyenBUS quyenBUS = new QuyenBUS();
            String tenQuyen = quyenBUS.getTenQuyenTheoMa(maQuyen);

            if (tenQuyen != null) {
                cmbQuyen.setSelectedItem(tenQuyen);
            } else {
                cmbQuyen.setSelectedItem("Nhân viên"); // Không tìm thấy quyền
            }
        } else {
            txtTenDangNhap.setText("");
            txtMatKhau.setText("");
            cmbQuyen.setSelectedItem("Nhân viên");
        }
    }

}
