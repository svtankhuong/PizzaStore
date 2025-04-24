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

        boolean ketQua = TaiKhoanBUS.suaTaiKhoan("provip123", "123456", 1 , 2);


        if(ketQua) {
            System.out.println("Thêm nhân viên thành công!");
        } else {
            System.out.println("Thêm nhân viên thất bại!");
        }
    }
    
            // Lấy tên quyền từ JComboBox
//        String tenQuyen = (String) cmbQuyen.getSelectedItem();
//        String maQuyenStr = "3"; // Giá trị mặc định
////        QuyenBUS quyenBUS = new QuyenBUS();
//        ArrayList<QuyenDTO> dsQuyen = QuyenBUS.getListQuyen();
//        for (QuyenDTO q : dsQuyen) {
//            if (q.getTenQuyen().equals(tenQuyen)) {
//                maQuyenStr = String.valueOf(q.getMaQuyen()); // Chuyển maQuyen thành String
//                break;
//            }
//        }
}