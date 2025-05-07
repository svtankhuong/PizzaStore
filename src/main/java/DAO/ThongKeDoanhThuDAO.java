package DAO;

import DTO.ThongKeDoanhThuDTO;
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

public class ThongKeDoanhThuDAO {

    // Tổng thu theo khoảng thời gian
    public List<ThongKeDoanhThuDTO> thongKeTongThuTheoKhoangThoiGian(Date tuNgay, Date denNgay) {
        List<ThongKeDoanhThuDTO> result = new ArrayList<>();
        Connection conn = JDBC.getConnection();
        if (conn == null) {
            Logger.getLogger(ThongKeDoanhThuDAO.class.getName()).log(Level.SEVERE, "Failed to get database connection");
            return result;
        }

        String sql = "SELECT COALESCE(SUM(TongTien), 0) AS tongThu " +
                "FROM HoaDon WHERE NgayLap BETWEEN ? AND ?";

        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setDate(1, tuNgay);
            stmt.setDate(2, denNgay);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                ThongKeDoanhThuDTO tk = new ThongKeDoanhThuDTO();
                tk.setTuNgay(tuNgay);
                tk.setDenNgay(denNgay);
                tk.setTongThu(rs.getBigDecimal("tongThu"));
                result.add(tk);
            }
        } catch (SQLException e) {
            Logger.getLogger(ThongKeDoanhThuDAO.class.getName()).log(Level.SEVERE, "Error in thongKeTongThuTheoKhoangThoiGian", e);
        } finally {
            JDBC.closeConnection(conn);
        }
        return result;
    }

    // Tổng thu theo ngày
    public List<ThongKeDoanhThuDTO> thongKeTongThuTheoNgay(Date ngay) {
        return thongKeTongThuTheoKhoangThoiGian(ngay, ngay);
    }

    // Tổng thu theo tháng
    public List<ThongKeDoanhThuDTO> thongKeTongThuTheoThang(int thang, int nam) {
        Calendar cal = Calendar.getInstance();
        cal.set(nam, thang - 1, 1); // Ngày đầu tháng
        Date tuNgay = new Date(cal.getTimeInMillis());
        cal.set(nam, thang - 1, cal.getActualMaximum(Calendar.DAY_OF_MONTH)); // Ngày cuối tháng
        Date denNgay = new Date(cal.getTimeInMillis());
        return thongKeTongThuTheoKhoangThoiGian(tuNgay, denNgay);
    }

    // Tổng thu theo năm
    public List<ThongKeDoanhThuDTO> thongKeTongThuTheoNam(int nam) {
        Calendar cal = Calendar.getInstance();
        cal.set(nam, Calendar.JANUARY, 1); // Ngày đầu năm
        Date tuNgay = new Date(cal.getTimeInMillis());
        cal.set(nam, Calendar.DECEMBER, 31); // Ngày cuối năm
        Date denNgay = new Date(cal.getTimeInMillis());
        return thongKeTongThuTheoKhoangThoiGian(tuNgay, denNgay);
    }

    // Tổng thu theo quý
    public List<ThongKeDoanhThuDTO> thongKeTongThuTheoQuy(int quy, int nam) {
        Calendar cal = Calendar.getInstance();
        int thangBatDau = (quy - 1) * 3; // Tháng bắt đầu của quý (0, 3, 6, 9)
        cal.set(nam, thangBatDau, 1); // Ngày đầu quý
        Date tuNgay = new Date(cal.getTimeInMillis());
        cal.set(nam, thangBatDau + 2, cal.getActualMaximum(Calendar.DAY_OF_MONTH)); // Ngày cuối quý
        Date denNgay = new Date(cal.getTimeInMillis());
        return thongKeTongThuTheoKhoangThoiGian(tuNgay, denNgay);
    }

    // Tổng thu theo từng nhân viên từng quý
    public List<ThongKeDoanhThuDTO> thongKeTongThuTheoNhanVienQuy(int quy, int nam) {
        List<ThongKeDoanhThuDTO> result = new ArrayList<>();
        Connection conn = JDBC.getConnection();
        if (conn == null) {
            Logger.getLogger(ThongKeDoanhThuDAO.class.getName()).log(Level.SEVERE, "Failed to get database connection");
            return result;
        }

        String sql = "SELECT hd.MaNV, nv.HoLot, nv.Ten, COALESCE(SUM(hd.TongTien), 0) AS tongThu " +
                "FROM HoaDon hd JOIN NhanVien nv ON hd.MaNV = nv.MaNV " +
                "WHERE QUARTER(hd.NgayLap) = ? AND YEAR(hd.NgayLap) = ? " +
                "GROUP BY hd.MaNV, nv.HoLot, nv.Ten";

        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, quy);
            stmt.setInt(2, nam);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                ThongKeDoanhThuDTO tk = new ThongKeDoanhThuDTO();
                tk.setMaNV(rs.getInt("MaNV")); // INT -> int
                tk.setTenNV((rs.getString("HoLot") != null ? rs.getString("HoLot") : "") + " " +
                        (rs.getString("Ten") != null ? rs.getString("Ten") : ""));
                tk.setTongThu(rs.getBigDecimal("tongThu")); // DECIMAL -> BigDecimal
                tk.setQuy(quy);
                tk.setNam(nam);
                result.add(tk);
            }
        } catch (SQLException e) {
            Logger.getLogger(ThongKeDoanhThuDAO.class.getName()).log(Level.SEVERE, "Error in thongKeTongThuTheoNhanVienQuy", e);
        } finally {
            JDBC.closeConnection(conn);
        }
        return result;
    }

    // Tổng thu theo từng khách hàng từng quý
    public List<ThongKeDoanhThuDTO> thongKeTongThuTheoKhachHangQuy(int quy, int nam) {
        List<ThongKeDoanhThuDTO> result = new ArrayList<>();
        Connection conn = JDBC.getConnection();
        if (conn == null) {
            Logger.getLogger(ThongKeDoanhThuDAO.class.getName()).log(Level.SEVERE, "Failed to get database connection");
            return result;
        }

        String sql = "SELECT hd.MaKH, kh.HoLot, kh.Ten, COALESCE(SUM(hd.TongTien), 0) AS tongThu " +
                "FROM HoaDon hd JOIN KhachHang kh ON hd.MaKH = kh.MaKH " +
                "WHERE QUARTER(hd.NgayLap) = ? AND YEAR(hd.NgayLap) = ? " +
                "GROUP BY hd.MaKH, kh.HoLot, kh.Ten";

        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, quy);
            stmt.setInt(2, nam);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                ThongKeDoanhThuDTO tk = new ThongKeDoanhThuDTO();
                tk.setMaKH(rs.getInt("MaKH")); // INT -> int
                tk.setTenKH((rs.getString("HoLot") != null ? rs.getString("HoLot") : "") + " " +
                        (rs.getString("Ten") != null ? rs.getString("Ten") : ""));
                tk.setTongThu(rs.getBigDecimal("tongThu")); // DECIMAL -> BigDecimal
                tk.setQuy(quy);
                tk.setNam(nam);
                result.add(tk);
            }
        } catch (SQLException e) {
            Logger.getLogger(ThongKeDoanhThuDAO.class.getName()).log(Level.SEVERE, "Error in thongKeTongThuTheoKhachHangQuy", e);
        } finally {
            JDBC.closeConnection(conn);
        }
        return result;
    }
}