package DAO;

import DTO.NhanVienDTO;
import DTO.QuyenDTO;

import config.JDBC;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class QuyenDAO {

    public ArrayList<QuyenDTO> getDanhSachQuyen() {
        ArrayList<QuyenDTO> danhSachQuyen = new ArrayList<>();
        try {
            String sql = "SELECT * FROM quyen";
            Connection connection = JDBC.getConnection();
            PreparedStatement pre = connection.prepareStatement(sql);
            ResultSet rs = pre.executeQuery();
            while (rs.next()) {
                QuyenDTO q = new QuyenDTO();
                q.setMaQuyen(rs.getInt("MaQuyen"));
                q.setTenQuyen(rs.getString("TenQuyen"));
                q.setQLBanHang(rs.getBoolean("QLBanHang"));
                q.setQLKhuyenMai(rs.getBoolean("QLKhuyenMai"));
                q.setQLSanPham(rs.getBoolean("QLSanPham"));
                q.setQLNhanVien(rs.getBoolean("QLNhanVien"));
                q.setQLKhachHang(rs.getBoolean("QLKhachHang"));
                q.setQLNhapHang(rs.getBoolean("QLNhapHang"));
                q.setThongKe(rs.getBoolean("ThongKe"));
                danhSachQuyen.add(q);
            }
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return danhSachQuyen;
    }

    public boolean themQuyen(QuyenDTO q) {
        String sql = "INSERT INTO quyen (TenQuyen, QLBanHang, QLKhuyenMai, QLSanPham, QLNhanVien, QLKhachHang, QLNhapHang, ThongKe) "
                + "VALUES (?, 0,0,0,0,0,0,0)";
        try (Connection connection = JDBC.getConnection(); PreparedStatement pre = connection.prepareStatement(sql)) {
            pre.setString(1, q.getTenQuyen());
            return pre.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean capNhatQuyen(QuyenDTO quyen) {
        String sql = "UPDATE quyen SET QLBanHang=?, QLKhuyenMai=?, QLSanPham=?, QLNhanVien=?, QLKhachHang=?, QLNhapHang=?, ThongKe=? WHERE MaQuyen=?";
        try (Connection connection = JDBC.getConnection(); PreparedStatement pre = connection.prepareStatement(sql)) {
            pre.setBoolean(1, quyen.getQLBanHang());
            pre.setBoolean(2, quyen.getQLKhuyenMai());
            pre.setBoolean(3, quyen.getQLSanPham());
            pre.setBoolean(4, quyen.getQLNhanVien());
            pre.setBoolean(5, quyen.getQLKhachHang());
            pre.setBoolean(6, quyen.getQLNhapHang());
            pre.setBoolean(7, quyen.getThongKe());
            pre.setInt(8, quyen.getMaQuyen());

            return pre.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean xoaQuyen(int maQuyen) {
        String sql = "DELETE FROM quyen WHERE MaQuyen=?";
        try (Connection connection = JDBC.getConnection(); PreparedStatement pre = connection.prepareStatement(sql)) {
            pre.setInt(1, maQuyen);
            return pre.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public QuyenDTO getQuyenByMaQuyen(int maQuyen) throws SQLException {
        QuyenDTO quyen = null;
        String query = "SELECT * FROM Quyen WHERE MaQuyen = ?";

        try (Connection conn = JDBC.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, maQuyen);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    quyen = new QuyenDTO();
                    quyen.setMaQuyen(rs.getInt("MaQuyen"));
                    quyen.setTenQuyen(rs.getString("TenQuyen"));
                    quyen.setQLBanHang(rs.getBoolean("QLbanhang"));
                    quyen.setQLKhuyenMai(rs.getBoolean("QLkhuyenmai"));
                    quyen.setQLSanPham(rs.getBoolean("QLsanpham"));
                    quyen.setQLNhanVien(rs.getBoolean("QLNhanVien"));
                    quyen.setQLKhachHang(rs.getBoolean("QLkhachhang"));
                    quyen.setQLNhapHang(rs.getBoolean("QLnhaphang"));
                    quyen.setThongKe(rs.getBoolean("ThongKe"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }

        return quyen;
    }
}
