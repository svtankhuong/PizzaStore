package BUS;

import DAO.ThongKeDoanhThuDAO;
import DTO.ThongKeDoanhThuDTO;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ThongKeDoanhThuBUS {
    private ThongKeDoanhThuDAO thongKeDoanhThuDAO;
    private static final Logger LOGGER = Logger.getLogger(ThongKeDoanhThuBUS.class.getName());

    public ThongKeDoanhThuBUS() {
        thongKeDoanhThuDAO = new ThongKeDoanhThuDAO();
    }

    public List<ThongKeDoanhThuDTO> thongKeTongThuTheoKhoangThoiGian(Date tuNgay, Date denNgay) {
        if (tuNgay == null || denNgay == null) {
            LOGGER.log(Level.WARNING, "tuNgay or denNgay is null");
            return new ArrayList<>();
        }
        if (tuNgay.after(denNgay)) {
            LOGGER.log(Level.WARNING, "tuNgay is after denNgay");
            return new ArrayList<>();
        }
        return thongKeDoanhThuDAO.thongKeTongThuTheoKhoangThoiGian(tuNgay, denNgay);
    }

    public List<ThongKeDoanhThuDTO> thongKeTongThuTheoNgay(Date ngay) {
        if (ngay == null) {
            LOGGER.log(Level.WARNING, "ngay is null");
            return new ArrayList<>();
        }
        return thongKeDoanhThuDAO.thongKeTongThuTheoNgay(ngay);
    }

    public List<ThongKeDoanhThuDTO> thongKeTongThuTheoThang(int thang, int nam) {
        if (thang < 1 || thang > 12) {
            LOGGER.log(Level.WARNING, "Invalid thang: {0}", thang);
            return new ArrayList<>();
        }
        if (nam < 1900 || nam > 9999) {
            LOGGER.log(Level.WARNING, "Invalid nam: {0}", nam);
            return new ArrayList<>();
        }
        return thongKeDoanhThuDAO.thongKeTongThuTheoThang(thang, nam);
    }

    public List<ThongKeDoanhThuDTO> thongKeTongThuTheoNam(int nam) {
        if (nam < 1900 || nam > 9999) {
            LOGGER.log(Level.WARNING, "Invalid nam: {0}", nam);
            return new ArrayList<>();
        }
        return thongKeDoanhThuDAO.thongKeTongThuTheoNam(nam);
    }

    public List<ThongKeDoanhThuDTO> thongKeTongThuTheoQuy(int quy, int nam) {
        if (quy < 1 || quy > 4) {
            LOGGER.log(Level.WARNING, "Invalid quy: {0}", quy);
            return new ArrayList<>();
        }
        if (nam < 1900 || nam > 9999) {
            LOGGER.log(Level.WARNING, "Invalid nam: {0}", nam);
            return new ArrayList<>();
        }
        return thongKeDoanhThuDAO.thongKeTongThuTheoQuy(quy, nam);
    }

    public List<ThongKeDoanhThuDTO> thongKeTongThuTheoNhanVienQuy(int quy, int nam) {
        if (quy < 1 || quy > 4) {
            LOGGER.log(Level.WARNING, "Invalid quy: {0}", quy);
            return new ArrayList<>();
        }
        if (nam < 1900 || nam > 9999) {
            LOGGER.log(Level.WARNING, "Invalid nam: {0}", nam);
            return new ArrayList<>();
        }
        return thongKeDoanhThuDAO.thongKeTongThuTheoNhanVienQuy(quy, nam);
    }

    public List<ThongKeDoanhThuDTO> thongKeTongThuTheoKhachHangQuy(int quy, int nam) {
        if (quy < 1 || quy > 4) {
            LOGGER.log(Level.WARNING, "Invalid quy: {0}", quy);
            return new ArrayList<>();
        }
        if (nam < 1900 || nam > 9999) {
            LOGGER.log(Level.WARNING, "Invalid nam: {0}", nam);
            return new ArrayList<>();
        }
        return thongKeDoanhThuDAO.thongKeTongThuTheoKhachHangQuy(quy, nam);
    }
}