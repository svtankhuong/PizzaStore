package BUS;

import DAO.ThongKeDoanhChiDAO;
import DTO.ThongKeDoanhChiDTO;

import java.util.Date;
import java.util.List;

public class ThongKeDoanhChiBUS {
    private ThongKeDoanhChiDAO thongKeDoanhChiDAO;

    public ThongKeDoanhChiBUS() {
        thongKeDoanhChiDAO = new ThongKeDoanhChiDAO();
    }

    public List<ThongKeDoanhChiDTO> thongKeTongChiTheoKhoangThoiGian(Date tuNgay, Date denNgay) {
        return thongKeDoanhChiDAO.thongKeTongChiTheoKhoangThoiGian(tuNgay, denNgay);
    }

    public List<ThongKeDoanhChiDTO> thongKeTongChiTheoNgay(Date ngay) {
        return thongKeDoanhChiDAO.thongKeTongChiTheoNgay(ngay);
    }

    public List<ThongKeDoanhChiDTO> thongKeTongChiTheoThang(int thang, int nam) {
        return thongKeDoanhChiDAO.thongKeTongChiTheoThang(thang, nam);
    }

    public List<ThongKeDoanhChiDTO> thongKeTongChiTheoNam(int nam) {
        return thongKeDoanhChiDAO.thongKeTongChiTheoNam(nam);
    }

    public List<ThongKeDoanhChiDTO> thongKeTongChiTheoQuy(int quy, int nam) {
        return thongKeDoanhChiDAO.thongKeTongChiTheoQuy(quy, nam);
    }
}