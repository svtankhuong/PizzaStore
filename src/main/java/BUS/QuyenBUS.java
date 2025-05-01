package BUS;

import DAO.QuyenDAO;
import DTO.QuyenDTO;
import MyCustom.MyDialog;
import java.util.ArrayList;

public class QuyenBUS {

    private static ArrayList<QuyenDTO> danhSachQuyen;
    private static QuyenDAO qDAO = new QuyenDAO();

    // Đọc danh sách quyền từ DB và lưu vào danhSachQuyen
    public static void docDanhSachQuyen() {
        danhSachQuyen = qDAO.getDanhSachQuyen();
    }

    public QuyenBUS() {
        docDanhSachQuyen();
    }

    // Trả về danh sách quyền đã đọc
    public static ArrayList<QuyenDTO> getListQuyen() {
        return danhSachQuyen;
    }

    // Optional: Lấy quyền theo mã quyền
    public static QuyenDTO getQuyenTheoMa(String maQuyen) {
        for (QuyenDTO q : danhSachQuyen) {
            if (maQuyen.equals(q.getMaQuyen())) {
                return q;
            }
        }
        return null;
    }

    public String getTenQuyenTheoMa(int maQuyen) {
        for (QuyenDTO q : danhSachQuyen) {
            if (q.getMaQuyen() == maQuyen) {
                return q.getTenQuyen();
            }
        }
        return null;
    }

    public boolean themQuyen(QuyenDTO q) {
        return qDAO.themQuyen(q);
    }

    // Cập nhật quyền
    public boolean capnhatquyen(QuyenDTO quyen) {
        return qDAO.capNhatQuyen(quyen);
    }

    // Xóa quyền
    public boolean xoaQuyen(int maQuyen) {
        boolean flag = qDAO.xoaQuyen(maQuyen);
        if (!flag) {
            new MyDialog("Xóa thật bại do đang có nhân viên mang quyền này", MyDialog.ERROR_DIALOG);
        } else {
            new MyDialog("Xóa thành công", MyDialog.ERROR_DIALOG);
        }
        return flag;
    }

}
