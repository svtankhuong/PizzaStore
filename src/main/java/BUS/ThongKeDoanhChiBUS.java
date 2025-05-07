package BUS;

import DAO.ThongKeDoanhChiDAO;
import DTO.ThongKeDoanhChiDTO;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ThongKeDoanhChiBUS {
    private ThongKeDoanhChiDAO thongKeDoanhChiDAO;
    private static final Logger LOGGER = Logger.getLogger(ThongKeDoanhChiBUS.class.getName());

    public ThongKeDoanhChiBUS() {
        thongKeDoanhChiDAO = new ThongKeDoanhChiDAO();
    }

    public List<ThongKeDoanhChiDTO> thongKeTongChiTheoKhoangThoiGian(Date tuNgay, Date denNgay) {
        if (tuNgay == null || denNgay == null) {
            LOGGER.log(Level.WARNING, "tuNgay or denNgay is null");
            return new ArrayList<>();
        }
        if (tuNgay.after(denNgay)) {
            LOGGER.log(Level.WARNING, "tuNgay is after denNgay");
            return new ArrayList<>();
        }
        return thongKeDoanhChiDAO.thongKeTongChiTheoKhoangThoiGian(tuNgay, denNgay);
    }

    public List<ThongKeDoanhChiDTO> thongKeTongChiTheoNgay(Date ngay) {
        if (ngay == null) {
            LOGGER.log(Level.WARNING, "ngay is null");
            return new ArrayList<>();
        }
        return thongKeDoanhChiDAO.thongKeTongChiTheoNgay(ngay);
    }

    public List<ThongKeDoanhChiDTO> thongKeTongChiTheoThang(int thang, int nam) {
        if (thang < 1 || thang > 12) {
            LOGGER.log(Level.WARNING, "Invalid thang: {0}", thang);
            return new ArrayList<>();
        }
        if (nam < 1900 || nam > 9999) {
            LOGGER.log(Level.WARNING, "Invalid nam: {0}", nam);
            return new ArrayList<>();
        }
        return thongKeDoanhChiDAO.thongKeTongChiTheoThang(thang, nam);
    }

    public List<ThongKeDoanhChiDTO> thongKeTongChiTheoNam(int nam) {
        if (nam < 1900 || nam > 9999) {
            LOGGER.log(Level.WARNING, "Invalid nam: {0}", nam);
            return new ArrayList<>();
        }
        return thongKeDoanhChiDAO.thongKeTongChiTheoNam(nam);
    }

    public List<ThongKeDoanhChiDTO> thongKeTongChiTheoQuy(int quy, int nam) {
        if (quy < 1 || quy > 4) {
            LOGGER.log(Level.WARNING, "Invalid quy: {0}", quy);
            return new ArrayList<>();
        }
        if (nam < 1900 || nam > 9999) {
            LOGGER.log(Level.WARNING, "Invalid nam: {0}", nam);
            return new ArrayList<>();
        }
        return thongKeDoanhChiDAO.thongKeTongChiTheoQuy(quy, nam);
    }
}