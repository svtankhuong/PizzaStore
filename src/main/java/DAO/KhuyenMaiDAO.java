package DAO;

import DTO.KhuyenMaiDTO;
import config.JDBC;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class KhuyenMaiDAO
{
    public ArrayList<KhuyenMaiDTO> layDSKM(){
        String sql = "SELECT * FROM KhuyenMai";
        ArrayList<KhuyenMaiDTO> dsKM = new ArrayList<>();

        try ( Connection conn = JDBC.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)){
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                int idKM = rs.getInt("MaKM");
                String tenKM = rs.getString("TenKM");
                LocalDate startDate = rs.getDate("NgayBatDau").toLocalDate();
                LocalDate endDate = rs.getDate("NgayKetThuc").toLocalDate();
                KhuyenMaiDTO kh = new KhuyenMaiDTO(idKM, tenKM, startDate, endDate);
                dsKM.add(kh);
            }
        }catch(SQLException e)
        {
            System.err.println("Lỗi khi lấy danh sách khuyến mãi: " + e.getMessage());
        }
        return dsKM;
    }

    public boolean themKhuyenMai(KhuyenMaiDTO km) {
        String sql = "INSERT INTO KhuyenMai (MaKM, TenKM, NgayBatDau, NgayKetThuc) VALUES (?, ?, ?, ?)";

        try (Connection conn = JDBC.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, km.getMaKM());
            ps.setString(2, km.getTenKM());
            ps.setDate(3, Date.valueOf(km.getNgayBatDau()));
            ps.setDate(4, Date.valueOf(km.getNgayKetThuc()));

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Lỗi khi thêm khuyến mãi: " + e.getMessage());
            return false;
        }
    }

    public boolean suaKhuyenMai(KhuyenMaiDTO km) {
        String sql = "UPDATE KhuyenMai SET TenKM = ?, NgayBatDau = ?, NgayKetThuc = ? WHERE MaKM = ?";

        try (Connection conn = JDBC.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, km.getTenKM());
            ps.setDate(2, Date.valueOf(km.getNgayBatDau()));
            ps.setDate(3, Date.valueOf(km.getNgayKetThuc()));
            ps.setInt(4, km.getMaKM());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Lỗi khi sửa khuyến mãi: " + e.getMessage());
            return false;
        }
    }

    public boolean xoaKhuyenMai(int maKM) {
        String sql = "DELETE FROM KhuyenMai WHERE MaKM = ?";

        try (Connection conn = JDBC.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, maKM);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Lỗi khi xóa khuyến mãi: " + e.getMessage());
            return false;
        }
    }

}