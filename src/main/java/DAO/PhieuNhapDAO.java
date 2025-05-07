package DAO;

import DTO.PhieuNhapDTO;
import config.JDBC;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.math.BigDecimal;
import java.sql.Date;

public class PhieuNhapDAO {
    public int ThemPhieuNhap(PhieuNhapDTO phieuNhap) {
        String sql = "INSERT INTO PhieuNhap(MaNCC, MaNV, TongTien, NgayLap) VALUES(?, ?, ?, ?)";
        try (Connection conn = JDBC.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, phieuNhap.getMaNCC());
            stmt.setInt(2, phieuNhap.getMaNV());
            stmt.setBigDecimal(3, phieuNhap.getTongTien());
            stmt.setDate(4, new java.sql.Date(phieuNhap.getNgayLap().getTime()));
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                ResultSet generatedKeys = stmt.getGeneratedKeys();
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1); // Lấy MaPN tự động tạo
                }
            }
            return -1; // Trả về -1 nếu thất bại
        } catch (SQLException ex) {
            Logger.getLogger(PhieuNhapDAO.class.getName()).log(Level.SEVERE, null, ex);
            return -1;
        }
    }
    public boolean SuaPhieuNhap(PhieuNhapDTO phieuNhap) {
        String sql = "UPDATE PhieuNhap SET MaNCC = ?, MaNV = ?, TongTien = ?, NgayLap = ? WHERE MaPN = ?";
        try (Connection conn = JDBC.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, phieuNhap.getMaNCC());
            stmt.setInt(2, phieuNhap.getMaNV());
            stmt.setBigDecimal(3, phieuNhap.getTongTien());
            stmt.setDate(4, new java.sql.Date(phieuNhap.getNgayLap().getTime()));
            stmt.setInt(5, phieuNhap.getMaPN());
            return stmt.executeUpdate() > 0;
        } catch (SQLException ex) {
            Logger.getLogger(PhieuNhapDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean XoaPhieuNhap(int maPN) {
        String sql = "DELETE FROM PhieuNhap WHERE MaPN = ?";
        try (Connection conn = JDBC.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, maPN);
            return stmt.executeUpdate() > 0;
        } catch (SQLException ex) {
            Logger.getLogger(PhieuNhapDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public List<PhieuNhapDTO> LayDanhSachPhieuNhap() {
        List<PhieuNhapDTO> danhSach = new ArrayList<>();
        String sql = "SELECT * FROM PhieuNhap";
        try (Connection conn = JDBC.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                PhieuNhapDTO phieuNhap = new PhieuNhapDTO();
                phieuNhap.setMaPN(rs.getInt("MaPN"));
                phieuNhap.setMaNCC(rs.getInt("MaNCC"));
                phieuNhap.setMaNV(rs.getInt("MaNV"));
                phieuNhap.setTongTien(rs.getBigDecimal("TongTien"));
                phieuNhap.setNgayLap(rs.getDate("NgayLap"));
                danhSach.add(phieuNhap);
            }
        } catch (SQLException ex) {
            Logger.getLogger(PhieuNhapDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return danhSach;
    }
}