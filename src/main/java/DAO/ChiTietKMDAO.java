package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import DTO.ChiTietKMDTO;
import config.JDBC;

public class ChiTietKMDAO {

    public ArrayList<ChiTietKMDTO> layDSCTKM() {
        String sql = "SELECT * FROM chitietkhuyenmai";
        ArrayList<ChiTietKMDTO> dsCTKM = new ArrayList<>();

        try (Connection conn = JDBC.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int idKM = rs.getInt("MaKM");
                long phanTramGiam = rs.getLong("PhanTramGiam");
                long toiThieuGiam = rs.getLong("Toithieugiam");
                String tenCTKM = rs.getString("TenCTKM");

                ChiTietKMDTO kh = new ChiTietKMDTO(idKM, phanTramGiam, toiThieuGiam, tenCTKM);
                dsCTKM.add(kh);
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi lấy danh sách chi tiết khuyến mãi: " + e.getMessage());
        }

        return dsCTKM;
    }

    public boolean themChiTietKM(ChiTietKMDTO km) {
        String sql = "INSERT INTO chitietkhuyenmai (MaKM, PhanTramGiam, Toithieugiam, TenCTKM) VALUES (?, ?, ?, ?)";

        try (Connection conn = JDBC.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, km.getMaKM());
            ps.setLong(2, km.getPhanTramGiam());
            ps.setLong(3, km.getToithieugiam());
            ps.setString(4, km.getTenCTKM());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Lỗi khi thêm chi tiết khuyến mãi: " + e.getMessage());
            return false;
        }
    }

    public boolean suaChiTietKM(ChiTietKMDTO km, String oldTenCTKM) {
        String sql = "UPDATE chitietkhuyenmai SET PhanTramGiam = ?, Toithieugiam = ?, TenCTKM = ? WHERE MaKM = ? AND TenCTKM = ?";

        try (Connection conn = JDBC.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, km.getPhanTramGiam());
            ps.setLong(2, km.getToithieugiam());
            ps.setString(3, km.getTenCTKM());
            ps.setInt(4, km.getMaKM());
            ps.setString(5, oldTenCTKM);

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Lỗi khi sửa chi tiết khuyến mãi: " + e.getMessage());
            return false;
        }
    }

    public boolean xoaChiTietKM(int maKM, String tenCTKM) {
        String sql = "DELETE FROM chitietkhuyenmai WHERE MaKM = ? AND TenCTKM = ?";

        try (Connection conn = JDBC.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, maKM);
            ps.setString(2, tenCTKM);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Lỗi khi xóa chi tiết khuyến mãi: " + e.getMessage());
            return false;
        }
    }
}
