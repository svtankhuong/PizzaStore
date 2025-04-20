package BUS;

import DTO.NhanVienDTO;
import DAO.NhanVienDAO;
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

    public boolean Suanhanvien(String maNV, String ho, String ten, String gioiTinh, String ngaySinh) {
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

    public boolean xoaNhanVienMem(String maNV) {
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
}
