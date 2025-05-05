package BUS;

import DTO.NhanVienDTO;
import DTO.QuyenDTO;
import DTO.TaiKhoanDTO;
import DAO.NhanVienDAO;
import DAO.QuyenDAO;
import DAO.TaiKhoanDAO;
import MyCustom.MyDialog;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class NhanVienBUS {

    private final NhanVienDAO nvDAO;

    public NhanVienBUS() {
        nvDAO = new NhanVienDAO();
    }

    private String validateNhanVien(String ho, String ten, String gioiTinh, String ngaySinh) {
        if (ten.trim().isEmpty()) {
            return "Tên không được để trống!";
        }
        if (ho.trim().isEmpty()) {
            return "Họ lót không được để trống!";
        }
        if (gioiTinh == null || gioiTinh.trim().isEmpty()) {
            return "Giới tính không được để trống!";
        }
        if (ngaySinh == null || ngaySinh.trim().isEmpty()) {
            return "Ngày sinh không được để trống!";
        }
        return null;
    }

    public boolean themNhanVien(String ho, String ten, String gioiTinh, String ngaySinh) {
        String loi = validateNhanVien(ho, ten, gioiTinh, ngaySinh);
        if (loi != null) {
            new MyDialog(loi, MyDialog.ERROR_DIALOG);
            return false;
        }

        NhanVienDTO nv = new NhanVienDTO();
        nv.setHoLot(ho.trim());
        nv.setTen(ten.trim());
        nv.setGioiTinh(gioiTinh);
        nv.setNgaysinh(ngaySinh);

        boolean flag = nvDAO.themNhanVien(nv);
        new MyDialog(flag ? "Thêm thành công!" : "Thêm thất bại!", flag ? MyDialog.SUCCESS_DIALOG : MyDialog.ERROR_DIALOG);
        return flag;
    }

    public boolean Suanhanvien(int maNV, String ho, String ten, String gioiTinh, String ngaySinh) {
        String loi = validateNhanVien(ho, ten, gioiTinh, ngaySinh);
        if (loi != null) {
            new MyDialog(loi, MyDialog.ERROR_DIALOG);
            return false;
        }

        NhanVienDTO nv = new NhanVienDTO();
        nv.setMaNV(maNV);
        nv.setHoLot(ho.trim());
        nv.setTen(ten.trim());
        nv.setGioiTinh(gioiTinh);
        nv.setNgaysinh(ngaySinh);

        boolean flag = nvDAO.capNhatNhanVien(nv);
        new MyDialog(flag ? "Sửa thành công!" : "Sửa thất bại!", flag ? MyDialog.SUCCESS_DIALOG : MyDialog.ERROR_DIALOG);
        return flag;
    }

    public boolean xoaNhanVienMem(int maNV) {
        boolean flag = nvDAO.xoaNhanVienMem(maNV);
        if (!flag) {
            new MyDialog("Xóa nhân viên thất bại!", MyDialog.ERROR_DIALOG);
        } else {
            new MyDialog("Đã xóa nhân viên!", MyDialog.SUCCESS_DIALOG);
        }
        return flag;
    }

    public ArrayList<NhanVienDTO> getDanhSachNhanVien() {
        return nvDAO.getDanhSachNhanVien();
    }

    public ArrayList<NhanVienDTO> timKiemNhanVien(String tuKhoa) {
        return nvDAO.timKiemNhanVien(tuKhoa);
    }

    public int getMAX() {
        return nvDAO.getMax();
    }

    public ArrayList<Object[]> hienThiTaiKhoanNhanVien() {
        ArrayList<Object[]> dsThongTin = new ArrayList<>();

        try {
            NhanVienDAO nvDAO = new NhanVienDAO();
            TaiKhoanDAO tkDAO = new TaiKhoanDAO();
            QuyenDAO qDAO = new QuyenDAO();

            ArrayList<NhanVienDTO> dsNhanVien = nvDAO.getDanhSachNhanVien();
            ArrayList<TaiKhoanDTO> dsTaiKhoan = tkDAO.getDanhSachTaiKhoan();
            ArrayList<QuyenDTO> dsQuyen = qDAO.getDanhSachQuyen();

            for (NhanVienDTO nv : dsNhanVien) {
                // Tìm tài khoản của nhân viên (dùng so sánh số nguyên)
                TaiKhoanDTO tk = dsTaiKhoan.stream()
                        .filter(t -> t.getMaNV() == nv.getMaNV())
                        .findFirst().orElse(null);

                // Tìm quyền tương ứng nếu có
                QuyenDTO q = (tk != null)
                        ? dsQuyen.stream()
                                .filter(qu -> qu.getMaQuyen() == tk.getMaQuyen())
                                .findFirst().orElse(null)
                        : null;

                // Lấy thông tin hiển thị
                String tenDangNhap = (tk != null) ? tk.getTenDangNhap() : "Chưa có tài khoản";
                String matKhau = (tk != null && tk.getMatKhau() != null) ? tk.getMatKhau() : "";
                String tenQuyen = (q != null) ? q.getTenQuyen() : "";

                Object[] rowData = {
                    nv.getMaNV(),
                    nv.getHoLot(),
                    nv.getTen(),
                    nv.getNgaysinh(),
                    nv.getGioiTinh(),
                    tenDangNhap,
                    matKhau,
                    tenQuyen
                };

                dsThongTin.add(rowData);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return dsThongTin;
    }

}
