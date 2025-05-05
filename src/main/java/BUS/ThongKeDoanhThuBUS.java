package BUS;

import DAO.ThongKeDoanhThuDAO;
import DTO.ThongKeDoanhThuDTO;

import java.util.Date;
import java.util.List;

public class ThongKeDoanhThuBUS {
    private ThongKeDoanhThuDAO thongKeDoanhThuDAO;

    public ThongKeDoanhThuBUS() {
        thongKeDoanhThuDAO = new ThongKeDoanhThuDAO();
    }

    public List<ThongKeDoanhThuDTO> thongKeTongThuTheoKhoangThoiGian(Date tuNgay, Date denNgay) {
        return thongKeDoanhThuDAO.thongKeTongThuTheoKhoangThoiGian(tuNgay, denNgay);
    }

    public List<ThongKeDoanhThuDTO> thongKeTongThuTheoNgay(Date ngay) {
        return thongKeDoanhThuDAO.thongKeTongThuTheoNgay(ngay);
    }

    public List<ThongKeDoanhThuDTO> thongKeTongThuTheoThang(int thang, int nam) {
        return thongKeDoanhThuDAO.thongKeTongThuTheoThang(thang, nam);
    }

    public List<ThongKeDoanhThuDTO> thongKeTongThuTheoNam(int nam) {
        return thongKeDoanhThuDAO.thongKeTongThuTheoNam(nam);
    }

    public List<ThongKeDoanhThuDTO> thongKeTongThuTheoQuy(int quy, int nam) {
        return thongKeDoanhThuDAO.thongKeTongThuTheoQuy(quy, nam);
    }

    public List<ThongKeDoanhThuDTO> thongKeTongThuTheoNhanVienQuy(int quy, int nam) {
        return thongKeDoanhThuDAO.thongKeTongThuTheoNhanVienQuy(quy, nam);
    }

    public List<ThongKeDoanhThuDTO> thongKeTongThuTheoKhachHangQuy(int quy, int nam) {
        return thongKeDoanhThuDAO.thongKeTongThuTheoKhachHangQuy(quy, nam);
    }
}