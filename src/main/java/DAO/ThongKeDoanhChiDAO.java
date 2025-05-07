package DAO;

import DTO.ThongKeDoanhChiDTO;
import config.JDBC;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ThongKeDoanhChiDAO {

    // Tổng chi theo khoảng thời gian
    public List<ThongKeDoanhChiDTO> thongKeTongChiTheoKhoangThoiGian(Date tuNgay, Date denNgay) {
        List<ThongKeDoanhChiDTO> result = new ArrayList<>();
        Connection conn = JDBC.getConnection();
        if (conn == null) {
            Logger.getLogger(ThongKeDoanhChiDAO.class.getName()).log(Level.SEVERE, "Failed to get database connection");
            return result;
        }

        String sql = "SELECT COALESCE(SUM(TongTien), 0) AS tongChi " +
                "FROM PhieuNhap WHERE NgayLap BETWEEN ? AND ?";

        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setDate(1, tuNgay);
            stmt.setDate(2, denNgay);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                ThongKeDoanhChiDTO tk = new ThongKeDoanhChiDTO();
                tk.setTuNgay(tuNgay);
                tk.setDenNgay(denNgay);
                tk.setTongChi(rs.getBigDecimal("tongChi"));
                result.add(tk);
            }
        } catch (SQLException e) {
            Logger.getLogger(ThongKeDoanhChiDAO.class.getName()).log(Level.SEVERE, "Error in thongKeTongChiTheoKhoangThoiGian", e);
        } finally {
            JDBC.closeConnection(conn);
        }
        return result;
    }

    // Tổng chi theo ngày
    public List<ThongKeDoanhChiDTO> thongKeTongChiTheoNgay(Date ngay) {
        return thongKeTongChiTheoKhoangThoiGian(ngay, ngay);
    }

    // Tổng chi theo tháng
    public List<ThongKeDoanhChiDTO> thongKeTongChiTheoThang(int thang, int nam) {
        Calendar cal = Calendar.getInstance();
        cal.set(nam, thang - 1, 1); // Ngày đầu tháng
        Date tuNgay = new Date(cal.getTimeInMillis());
        cal.set(nam, thang - 1, cal.getActualMaximum(Calendar.DAY_OF_MONTH)); // Ngày cuối tháng
        Date denNgay = new Date(cal.getTimeInMillis());
        return thongKeTongChiTheoKhoangThoiGian(tuNgay, denNgay);
    }

    // Tổng chi theo năm
    public List<ThongKeDoanhChiDTO> thongKeTongChiTheoNam(int nam) {
        Calendar cal = Calendar.getInstance();
        cal.set(nam, Calendar.JANUARY, 1); // Ngày đầu năm
        Date tuNgay = new Date(cal.getTimeInMillis());
        cal.set(nam, Calendar.DECEMBER, 31); // Ngày cuối năm
        Date denNgay = new Date(cal.getTimeInMillis());
        return thongKeTongChiTheoKhoangThoiGian(tuNgay, denNgay);
    }

    // Tổng chi theo quý
    public List<ThongKeDoanhChiDTO> thongKeTongChiTheoQuy(int quy, int nam) {
        Calendar cal = Calendar.getInstance();
        int thangBatDau = (quy - 1) * 3; // Tháng bắt đầu của quý (0, 3, 6, 9)
        cal.set(nam, thangBatDau, 1); // Ngày đầu quý
        Date tuNgay = new Date(cal.getTimeInMillis());
        cal.set(nam, thangBatDau + 2, cal.getActualMaximum(Calendar.DAY_OF_MONTH)); // Ngày cuối quý
        Date denNgay = new Date(cal.getTimeInMillis());
        return thongKeTongChiTheoKhoangThoiGian(tuNgay, denNgay);
    }
}