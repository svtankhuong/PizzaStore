package GUI;

import BUS.NhanVienBUS;
import BUS.TaiKhoanBUS;
import DTO.NhanVienDTO;
import java.sql.Date;

import java.util.ArrayList;

public class test {
    public static void main(String[] args) {
        // Khởi tạo đối tượng NhanVienBUS
        TaiKhoanBUS TaiKhoanBUS = new TaiKhoanBUS();

        // 1. Kiểm tra lấy danh sách nhân viên
        System.out.println("Them:");

        boolean ketQua = TaiKhoanBUS.capTaiKhoan("provip", "123456", "1", "3");


        if(ketQua) {
            System.out.println("Thêm nhân viên thành công!");
        } else {
            System.out.println("Thêm nhân viên thất bại!");
        }
    }
}