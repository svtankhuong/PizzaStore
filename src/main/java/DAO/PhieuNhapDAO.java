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

public class PhieuNhapDAO {
    private static final Logger LOGGER = Logger.getLogger(PhieuNhapDAO.class.getName());

    public List<PhieuNhapDTO> getAllPhieuNhap() throws SQLException {
        List<PhieuNhapDTO> list = new ArrayList<>();
        try (Connection conn = JDBC.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT MaPN, MaNCC, MaNV, TongTien, NgayLap FROM PhieuNhap");
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                PhieuNhapDTO pn = new PhieuNhapDTO(
                    rs.getInt("MaPN"),
                    rs.getInt("MaNCC"),
                    rs.getInt("MaNV"),
                    rs.getDouble("TongTien"),
                    rs.getDate("NgayLap")
                );
                list.add(pn);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Lỗi khi lấy danh sách phiếu nhập", e);
            throw e;
        }
        return list;
    }

    public boolean addPhieuNhap(PhieuNhapDTO pn) throws SQLException {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = JDBC.getConnection();
            String query = "INSERT INTO PhieuNhap (MaPN, MaNCC, MaNV, TongTien, NgayLap) VALUES (?, ?, ?, ?, ?)";
            ps = conn.prepareStatement(query);
            ps.setInt(1, pn.getMaPN());
            ps.setInt(2, pn.getMaNCC());
            ps.setInt(3, pn.getMaNV());
            ps.setDouble(4, pn.getTongTien());
            ps.setDate(5, new java.sql.Date(pn.getNgayLap().getTime()));
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Lỗi khi thêm phiếu nhập", e);
            throw e;
        } finally {
            if (ps != null) try { ps.close(); } catch (SQLException e) { LOGGER.log(Level.WARNING, "Lỗi đóng PreparedStatement", e); }
            if (conn != null) JDBC.closeConnection(conn);
        }
    }

    public boolean updatePhieuNhap(PhieuNhapDTO pn) throws SQLException {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = JDBC.getConnection();
            String query = "UPDATE PhieuNhap SET MaNCC = ?, MaNV = ?, TongTien = ?, NgayLap = ? WHERE MaPN = ?";
            ps = conn.prepareStatement(query);
            ps.setInt(1, pn.getMaNCC());
            ps.setInt(2, pn.getMaNV());
            ps.setDouble(3, pn.getTongTien());
            ps.setDate(4, new java.sql.Date(pn.getNgayLap().getTime()));
            ps.setInt(5, pn.getMaPN());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Lỗi khi cập nhật phiếu nhập", e);
            throw e;
        } finally {
            if (ps != null) try { ps.close(); } catch (SQLException e) { LOGGER.log(Level.WARNING, "Lỗi đóng PreparedStatement", e); }
            if (conn != null) JDBC.closeConnection(conn);
        }
    }
}