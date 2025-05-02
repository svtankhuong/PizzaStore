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

    private NhanVienDAO nvDAO;

    public NhanVienBUS() {
        nvDAO = new NhanVienDAO();
    }

    public boolean themNhanVien(String ho, String ten, String gioiTinh, String ngaySinh) {
        ho = ho.trim();
        ten = ten.trim();

        if (ten.equals("")) {
            new MyDialog("Tên không được để trống!", MyDialog.ERROR_DIALOG);
            return false;
        }
        NhanVienDTO nv = new NhanVienDTO();
        nv.setHoLot(ho);
        nv.setTen(ten);
        nv.setGioiTinh(gioiTinh);
        nv.setNgaysinh(ngaySinh);

        boolean flag = nvDAO.themNhanVien(nv);
        if (!flag) {
            new MyDialog("Thêm thất bại!", MyDialog.ERROR_DIALOG);
        } else {
            new MyDialog("Thêm thành công!", MyDialog.SUCCESS_DIALOG);
        }
        return flag;
    }

    public boolean Suanhanvien(int maNV, String ho, String ten, String gioiTinh, String ngaySinh) {
        ho = ho.trim();
        ten = ten.trim();

        if (ten.equals("")) {
            new MyDialog("Tên không được để trống!", MyDialog.ERROR_DIALOG);
            return false;
        }
        NhanVienDTO nv = new NhanVienDTO();
        nv.setMaNV(maNV); // Gán MaNV
        nv.setHoLot(ho);
        nv.setTen(ten);
        nv.setGioiTinh(gioiTinh);
        nv.setNgaysinh(ngaySinh);

        boolean flag = nvDAO.capNhatNhanVien(nv);
        if (!flag) {
            new MyDialog("Sửa thất bại!", MyDialog.ERROR_DIALOG);
        } else {
            new MyDialog("Sửa thành công!", MyDialog.SUCCESS_DIALOG);
        }
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
