package BUS;

import DAO.TaiKhoanDAO;
import DTO.TaiKhoanDTO;
import MyCustom.MyDialog;
import java.util.ArrayList;

public class TaiKhoanBUS {

    private static ArrayList<TaiKhoanDTO> danhSachTaiKhoan;

    public TaiKhoanBUS() {
        // Đảm bảo danh sách được đọc từ DB ngay khi đối tượng được tạo
        docDanhSachTaiKhoan();
    }

    // Phương thức đọc danh sách tài khoản từ DAO
    public void docDanhSachTaiKhoan() {
        TaiKhoanDAO dao = new TaiKhoanDAO();
        danhSachTaiKhoan = dao.getDanhSachTaiKhoan();
    }

    // Getter danh sách tài khoản
    public static ArrayList<TaiKhoanDTO> getDanhSachTaiKhoan() {
        if (danhSachTaiKhoan == null) {
            danhSachTaiKhoan = new ArrayList<>(); // Khởi tạo danh sách nếu nó là null
        }
        return danhSachTaiKhoan;
    }

    // Lấy tài khoản theo mã nhân viên
    public static TaiKhoanDTO getTaiKhoanTheoMaNV(int maNhanVien) {
        if (danhSachTaiKhoan == null) {
            return null; // Nếu danh sách là null, trả về null
        }

        for (TaiKhoanDTO tk : danhSachTaiKhoan) {
            if (tk.getMaNV() == maNhanVien) {
                return tk;
            }
        }
        return null; // Không tìm thấy tài khoản
    }

    public boolean capTaiKhoan(String tenDangNhap, String matKhau, int maQuyen, int maNV) {
        try {
//            int maQuyen = Integer.parseInt(maQuyenStr);
//            int maNV = Integer.parseInt(maNVStr);

            // Kiểm tra nếu nhân viên đã có tài khoản
            if (getTaiKhoanTheoMaNV(maNV) != null) {
                System.out.println("Nhân viên đã có tài khoản.");
                return false;
            }

            TaiKhoanDTO tkMoi = new TaiKhoanDTO();
            tkMoi.setTenDangNhap(tenDangNhap);
            tkMoi.setMatKhau(matKhau);
            tkMoi.setMaQuyen(maQuyen);
            tkMoi.setMaNV(maNV);

            TaiKhoanDAO dao = new TaiKhoanDAO();
            boolean thanhCong = dao.ThemTaiKhoan(tkMoi);

            if (thanhCong && danhSachTaiKhoan != null) {
                danhSachTaiKhoan.add(tkMoi);
            }

            return thanhCong;
        } catch (NumberFormatException e) {
            System.err.println("Lỗi chuyển kiểu số: " + e.getMessage());
            return false;
        }
    }

    public boolean suaTaiKhoan(String tenDangNhap, String matKhau, int maQuyen, int maNV) {
        TaiKhoanDTO tk = new TaiKhoanDTO();
        tk.setTenDangNhap(tenDangNhap);
        tk.setMatKhau(matKhau);
        tk.setMaQuyen(maQuyen);
        tk.setMaNV(maNV);

        TaiKhoanDAO dao = new TaiKhoanDAO();
        boolean thanhCong = dao.SuaTaiKhoan(tk);

        if (!thanhCong) {
            new MyDialog("Cập nhật tài khoản thất bại!", MyDialog.ERROR_DIALOG);
        } else {
            new MyDialog("Cập nhật tài khoản thành công!", MyDialog.SUCCESS_DIALOG);
        }
        return thanhCong;
    }

    public void XoaTaiKhoan(String tenDangNhap) {
        TaiKhoanDAO dao = new TaiKhoanDAO();
        boolean isDeleted = dao.XoaTaiKhoan(tenDangNhap);  // Gọi phương thức xóa tài khoản từ DAO

        if (isDeleted) {
            // Nếu xóa thành công, hiển thị thông báo thành công
            new MyDialog("Tài khoản đã được xóa thành công!", MyDialog.SUCCESS_DIALOG);
        } else {
            // Nếu xóa không thành công, hiển thị thông báo lỗi
            new MyDialog("Tài khoản không tồn tại hoặc không thể xóa.", MyDialog.ERROR_DIALOG);
        }
    }

}
