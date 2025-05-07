package DAO;

import DTO.CTPhieuNhapDTO;
import config.JDBC;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CTPhieuNhapDAO {

    public boolean ThemCTPhieuNhap(CTPhieuNhapDTO ctPhieuNhap) {
        String sql = "INSERT INTO chitietphieunhap(MaPN, MaNL, SoLuong, DonGia, ThanhTien) VALUES(?, ?, ?, ?, ?)";
        try (Connection conn = JDBC.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, ctPhieuNhap.getMaPN());
            stmt.setInt(2, ctPhieuNhap.getMaNL());
            stmt.setInt(3, ctPhieuNhap.getSoLuong());
            stmt.setDouble(4, ctPhieuNhap.getDonGia());
            stmt.setDouble(5, ctPhieuNhap.getThanhTien());
            return stmt.executeUpdate() > 0;
        } catch (SQLException ex) {
            Logger.getLogger(CTPhieuNhapDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean SuaCTPhieuNhap(CTPhieuNhapDTO ctPhieuNhap) {
        String sql = "UPDATE chitietphieunhap SET SoLuong = ?, DonGia = ?, ThanhTien = ? WHERE MaPN = ? AND MaNL = ?";
        try (Connection conn = JDBC.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, ctPhieuNhap.getSoLuong());
            stmt.setDouble(2, ctPhieuNhap.getDonGia());
            stmt.setDouble(3, ctPhieuNhap.getThanhTien());
            stmt.setInt(4, ctPhieuNhap.getMaPN());
            stmt.setInt(5, ctPhieuNhap.getMaNL());
            return stmt.executeUpdate() > 0;
        } catch (SQLException ex) {
            Logger.getLogger(CTPhieuNhapDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean XoaCTPhieuNhap(int maPN, int maNL) {
        String sql = "DELETE FROM chitietphieunhap WHERE MaPN = ? AND MaNL = ?";
        try (Connection conn = JDBC.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, maPN);
            stmt.setInt(2, maNL);
            return stmt.executeUpdate() > 0;
        } catch (SQLException ex) {
            Logger.getLogger(CTPhieuNhapDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public List<CTPhieuNhapDTO> LayDanhSachCTPhieuNhap() {
        List<CTPhieuNhapDTO> danhSach = new ArrayList<>();
        String sql = "SELECT * FROM chitietphieunhap";
        try (Connection conn = JDBC.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                CTPhieuNhapDTO ctPhieuNhap = new CTPhieuNhapDTO();
                ctPhieuNhap.setMaPN(rs.getInt("MaPN"));
                ctPhieuNhap.setMaNL(rs.getInt("MaNL"));
                ctPhieuNhap.setSoLuong(rs.getInt("SoLuong"));
                ctPhieuNhap.setDonGia(rs.getDouble("DonGia"));
                ctPhieuNhap.setThanhTien(rs.getDouble("ThanhTien"));
                danhSach.add(ctPhieuNhap);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CTPhieuNhapDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return danhSach;
    }
    public boolean KiemTraMaPNvaMaNL(int maPN, int maNL) {
        String sql = "SELECT COUNT(*) FROM chitietphieunhap WHERE MaPN = ? AND MaNL = ?";
        try (Connection conn = JDBC.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, maPN);
            stmt.setInt(2, maNL);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(CTPhieuNhapDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
}