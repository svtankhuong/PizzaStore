package DAO;

import DTO.ChiTietPhieuNhapDTO;
import config.JDBC;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ChiTietPhieuNhapDAO {
    private static final Logger LOGGER = Logger.getLogger(ChiTietPhieuNhapDAO.class.getName());

    public List<ChiTietPhieuNhapDTO> getAllChiTietPhieuNhap() throws SQLException {
        List<ChiTietPhieuNhapDTO> list = new ArrayList<>();
        String sql = "SELECT * FROM ChiTietPhieuNhap";
        try (Connection conn = JDBC.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                ChiTietPhieuNhapDTO ctp = new ChiTietPhieuNhapDTO(
                    rs.getInt("MaPN"),
                    rs.getInt("MaNL"),
                    rs.getInt("SoLuong"),
                    rs.getDouble("DonGia"),
                    rs.getDouble("ThanhTien")
                );
                list.add(ctp);
            }
            LOGGER.log(Level.INFO, "Lấy tất cả chi tiết phiếu nhập thành công, số lượng: {0}", list.size());
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Lỗi khi lấy tất cả chi tiết phiếu nhập", e);
            throw e;
        }
        return list;
    }

    public void addChiTietPhieuNhap(List<ChiTietPhieuNhapDTO> chiTietList, int maPN, Connection conn) throws SQLException {
        String sql = "INSERT INTO ChiTietPhieuNhap (MaPN, MaNL, SoLuong, DonGia, ThanhTien) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            for (ChiTietPhieuNhapDTO ctp : chiTietList) {
                stmt.setInt(1, ctp.getMaPN());
                stmt.setInt(2, ctp.getMaNL());
                stmt.setInt(3, ctp.getSoLuong());
                stmt.setDouble(4, ctp.getDonGia());
                stmt.setDouble(5, ctp.getThanhTien());
                stmt.addBatch();
            }
            int[] results = stmt.executeBatch();
            LOGGER.log(Level.INFO, "Thêm chi tiết phiếu nhập thành công, số dòng thêm: {0}", results.length);
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Lỗi khi thêm chi tiết phiếu nhập", e);
            throw e;
        }
    }

    public void updateChiTietPhieuNhap(List<ChiTietPhieuNhapDTO> chiTietList, int maPN, Connection conn) throws SQLException {
        // Xóa chi tiết cũ
        String deleteSql = "DELETE FROM ChiTietPhieuNhap WHERE MaPN = ?";
        try (PreparedStatement deleteStmt = conn.prepareStatement(deleteSql)) {
            deleteStmt.setInt(1, maPN);
            int deletedRows = deleteStmt.executeUpdate();
            LOGGER.log(Level.INFO, "Xóa chi tiết phiếu nhập cũ cho MaPN={0}, số dòng xóa: {1}", new Object[]{maPN, deletedRows});
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Lỗi khi xóa chi tiết phiếu nhập cũ", e);
            throw e;
        }

        // Thêm chi tiết mới
        String insertSql = "INSERT INTO ChiTietPhieuNhap (MaPN, MaNL, SoLuong, DonGia, ThanhTien) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement insertStmt = conn.prepareStatement(insertSql)) {
            for (ChiTietPhieuNhapDTO ctp : chiTietList) {
                insertStmt.setInt(1, ctp.getMaPN());
                insertStmt.setInt(2, ctp.getMaNL());
                insertStmt.setInt(3, ctp.getSoLuong());
                insertStmt.setDouble(4, ctp.getDonGia());
                insertStmt.setDouble(5, ctp.getThanhTien());
                insertStmt.addBatch();
            }
            int[] results = insertStmt.executeBatch();
            LOGGER.log(Level.INFO, "Thêm chi tiết phiếu nhập mới cho MaPN={0}, số dòng thêm: {1}", new Object[]{maPN, results.length});
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Lỗi khi thêm chi tiết phiếu nhập mới", e);
            throw e;
        }
    }
}
