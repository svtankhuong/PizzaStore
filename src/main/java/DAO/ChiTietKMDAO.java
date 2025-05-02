package DAO;

import DTO.ChiTietKMDTO;
import config.JDBC;
import java.sql.*;
import java.util.ArrayList;

public class ChiTietKMDAO {

    public ArrayList<ChiTietKMDTO> layDSCTKM() {
        String sql = "SELECT * FROM chitietkhuyenmai";
        ArrayList<ChiTietKMDTO> dsCTKM = new ArrayList<>();

        try (Connection conn = JDBC.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int idCTKM = rs.getInt("MACTKM");
                int idKM = rs.getInt("MaKM");
                long phanTramGiam = rs.getLong("PhanTramGiam");
                long toiThieuGiam = rs.getLong("Toithieugiam");
                String tenCTKM = rs.getString("TenCTKM");

                ChiTietKMDTO kh = new ChiTietKMDTO(idCTKM, idKM, phanTramGiam, toiThieuGiam, tenCTKM);
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

    public boolean suaChiTietKM(ChiTietKMDTO km) {
        String sql = "UPDATE chitietkhuyenmai SET PhanTramGiam = ?, Toithieugiam = ?, TenCTKM = ? WHERE MACTKM = ?";

        try (Connection conn = JDBC.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, km.getPhanTramGiam());
            ps.setLong(2, km.getToithieugiam());
            ps.setString(3, km.getTenCTKM());
            ps.setInt(4, km.getMACTKM());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Lỗi khi sửa chi tiết khuyến mãi: " + e.getMessage());
            return false;
        }
    }

    public boolean xoaChiTietKM(int maCTKM) {
        String sql = "DELETE FROM chitietkhuyenmai WHERE MACTKM = ?";

        try (Connection conn = JDBC.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, maCTKM);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Lỗi khi xóa chi tiết khuyến mãi: " + e.getMessage());
            return false;
        }
    }
    
    public ChiTietKMDTO timKiemCTKM(int MaKM) {
        String sql = "SELECT * FROM chitietkhuyenmai WHERE MaKM=?";
        ChiTietKMDTO ctkm = null;
        try (Connection conn = JDBC.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, MaKM);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                int a = rs.getInt("MACTKM"); 
                int b = rs.getInt("MaKM");
                Long c = rs.getLong("PhanTramGiam");
                Long d = rs.getLong("ToiThieuGiam");
                String e = rs.getString("TenCTKM");
                ctkm = new ChiTietKMDTO(a, b, c, d, e);
            }
       
        } catch (SQLException e) {
            System.err.println("Lỗi tìm kiếm khuyến mãi: " + e.getMessage());
            return null;
        }
        return ctkm;
    }
}