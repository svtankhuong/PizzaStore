/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BUS;

import DAO.KhachHangDAO;

import DTO.KhachHangDTO;
import MyCustom.MyDialog;

import java.util.ArrayList;

/**
 *
 * @author somna
 */
public class KhachHangBUS {

    private KhachHangDAO khDAO;
    private ArrayList<KhachHangDTO> danhSachKhachHang;

    public KhachHangBUS() {
        khDAO = new KhachHangDAO();
        danhSachKhachHang = khDAO.layDSKH(); // load sẵn danh sách
    }

    public ArrayList<KhachHangDTO> getDanhSachKhachHang() {
        return danhSachKhachHang;
    }

    public ArrayList<KhachHangDTO> layDSKH() {
        return khDAO.layDSKH();
    }

    public ArrayList<KhachHangDTO> timKiemKhachHang(String tuKhoa) {
        return khDAO.timKiemKhachHang(tuKhoa);
    }

    public int getMAX() {
        return khDAO.getMax();
    }

    public String validateKhachHang(KhachHangDTO kh) {
        if (kh.getHoDem() == null || kh.getHoDem().trim().isEmpty()) {
            return "Họ đệm không được để trống!";
        }
        if (kh.getTen() == null || kh.getTen().trim().isEmpty()) {
            return "Tên không được để trống!";
        }
        if (kh.getSdt() == null || kh.getSdt().trim().isEmpty()) {
            return "Số điện thoại không được để trống!";
        }
        // Kiểm tra độ dài số điện thoại (phải là 10 số)
        if (kh.getSdt().length() != 10) {
            return "Số điện thoại phải có 10 chữ số!";
        }
        // Kiểm tra số điện thoại có đúng định dạng không
        if (!kh.getSdt().matches("^0\\d{9}$")) { // Ví dụ SĐT Việt Nam 10 số bắt đầu bằng 0
            return "Số điện thoại không đúng định dạng!";
        }
        if (kh.getDiaChi() == null || kh.getDiaChi().trim().isEmpty()) {
            return "Địa chỉ không được để trống!";
        }

        return null; // Trả về null nếu không có lỗi
    }

    public boolean themKhachHang(String hoLot, String tenKH, String sdt, String diaChi) {
        // Loại bỏ khoảng trắng thừa ở đầu và cuối chuỗi
        hoLot = hoLot.trim();
        tenKH = tenKH.trim();
        sdt = sdt.trim();
        diaChi = diaChi.trim();

        // Tạo đối tượng KhachHangDTO và gán giá trị cho các trường
        KhachHangDTO kh = new KhachHangDTO();
        kh.setHoDem(hoLot);
        kh.setTen(tenKH);
        kh.setSdt(sdt);
        kh.setDiaChi(diaChi);

        // Kiểm tra tính hợp lệ của khách hàng trước khi thêm
        String error = validateKhachHang(kh);
        if (error != null) {
            new MyDialog(error, MyDialog.ERROR_DIALOG);
            return false;  // Nếu có lỗi thì không thêm khách hàng
        }

        // Kiểm tra nếu số điện thoại đã tồn tại trong danh sách khách hàng
        for (KhachHangDTO existingKhachHang : danhSachKhachHang) {
            if (existingKhachHang.getSdt().equalsIgnoreCase(sdt)) {
                new MyDialog("Số điện thoại đã tồn tại!", MyDialog.ERROR_DIALOG);
                return false;
            }
        }

        // Tiến hành thêm khách hàng vào cơ sở dữ liệu
        boolean flag = khDAO.ThemKhachHang(kh);
        if (!flag) {
            new MyDialog("Thêm khách hàng thất bại!", MyDialog.ERROR_DIALOG);
        } else {
            new MyDialog("Thêm khách hàng thành công!", MyDialog.SUCCESS_DIALOG);
        }
        return flag;
    }

    public boolean SuaKhachHang(String hoLot, String tenKH, String sdt, String diaChi) {
        hoLot = hoLot.trim();
        tenKH = tenKH.trim();
        sdt = sdt.trim();
        diaChi = diaChi.trim();

        KhachHangDTO kh = new KhachHangDTO();
        kh.setHoDem(hoLot);
        kh.setTen(tenKH);
        kh.setSdt(sdt);
        kh.setDiaChi(diaChi);

        String error = validateKhachHang(kh);
        if (error != null) {
            new MyDialog(error, MyDialog.ERROR_DIALOG);
            return false;
        }

        for (KhachHangDTO existingKhachHang : danhSachKhachHang) {
            if (existingKhachHang.getSdt().equalsIgnoreCase(sdt)) {
                new MyDialog("Số điện thoại đã tồn tại!", MyDialog.ERROR_DIALOG);
                return false;
            }
        }
        boolean flag = khDAO.ThemKhachHang(kh);
        if (!flag) {
            new MyDialog("Sửa khách hàng thất bại!", MyDialog.ERROR_DIALOG);
        } else {
            new MyDialog("Sửa khách hàng thành công!", MyDialog.SUCCESS_DIALOG);
        }
        return flag;
    }

    public boolean XoaKhachHang(int maKH) {
        boolean flag = khDAO.xoamemkhachhang(maKH);
        if (!flag) {
            new MyDialog("Xóa khách hàng thất bại!", MyDialog.ERROR_DIALOG);
        } else {
            new MyDialog("Xóa khách hàng thành công!", MyDialog.SUCCESS_DIALOG);
        }
        return flag;
    }

    public ArrayList<KhachHangDTO> locKhachHangTheoChiTieu(long min, long max) {
        ArrayList<KhachHangDTO> ketQua = new ArrayList<>();
        for (KhachHangDTO kh : danhSachKhachHang) {
            long tongChiTieu = kh.getTongChiTieu();
            if (tongChiTieu >= min && tongChiTieu <= max) {
                ketQua.add(kh);
            }
        }
        return ketQua;
    }
    
}
