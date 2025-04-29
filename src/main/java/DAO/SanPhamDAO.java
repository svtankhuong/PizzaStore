package DAO;

import DTO.SanPhamDTO;
import config.JDBC;

import java.sql.*;
import java.util.ArrayList;

public class SanPhamDAO {

    public ArrayList<SanPhamDTO> layDSSP() {
        String sql = "SELECT * FROM SanPham";
        ArrayList<SanPhamDTO> dsSP = new ArrayList<>();

        try (Connection conn = JDBC.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int idSP = rs.getInt("MaSP");
                String tenSP = rs.getString("Ten");
                String dvTinh = rs.getString("DvTinh");
                Long donGia = rs.getLong("DonGia");
                int soLuong = rs.getInt("SoLuong");
                String loaiSP = rs.getString("Loai");
                String anhSP = rs.getString("Anh");

                SanPhamDTO sp = new SanPhamDTO(idSP, tenSP, dvTinh, donGia, soLuong, loaiSP, anhSP);
                dsSP.add(sp);
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi lấy danh sách sản phẩm: " + e.getMessage());
        }

        return dsSP;
    }

    public boolean themSanPham(SanPhamDTO sanPham) {
        String sql = "INSERT INTO SanPham (MaSP, Ten, DvTinh, DonGia, SoLuong, Loai, Anh) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = JDBC.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, sanPham.getMaSP());
            stmt.setString(2, sanPham.getTenSP());
            stmt.setString(3, sanPham.getDvTinh());
            stmt.setLong(4, sanPham.getDonGia());
            stmt.setInt(5, sanPham.getSoLuong());
            stmt.setString(6, sanPham.getLoai());
            stmt.setString(7, sanPham.getAnhSP());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Lỗi khi thêm sản phẩm: " + e.getMessage());
            return false;
        }
    }

    public boolean suaSanPham(SanPhamDTO sanPham) {
        String sql = "UPDATE SanPham SET Ten = ?, DvTinh = ?, DonGia = ?, SoLuong = ?, Loai = ?, Anh = ? WHERE MaSP = ?";

        try (Connection conn = JDBC.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, sanPham.getTenSP());
            stmt.setString(2, sanPham.getDvTinh());
            stmt.setLong(3, sanPham.getDonGia());
            stmt.setInt(4, sanPham.getSoLuong());
            stmt.setString(5, sanPham.getLoai());
            stmt.setString(6, sanPham.getAnhSP());
            stmt.setInt(7, sanPham.getMaSP());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Lỗi khi cập nhật sản phẩm: " + e.getMessage());
            return false;
        }
    }

    public boolean xoaSanPham(String maSP) {
        String sql = "DELETE FROM SanPham WHERE MaSP = ?";

        try (Connection conn = JDBC.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, maSP);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Lỗi khi xóa sản phẩm: " + e.getMessage());
            return false;
        }
    }

    public ArrayList<SanPhamDTO> timKiemSanPham(String keyword) {
        String sql = "SELECT * FROM SanPham WHERE MaSP LIKE ? OR Ten LIKE ?";
        ArrayList<SanPhamDTO> ketQua = new ArrayList<>();

        try (Connection conn = JDBC.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "%" + keyword + "%");
            stmt.setString(2, "%" + keyword + "%");

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                SanPhamDTO sp = new SanPhamDTO(
                        rs.getInt("MaSP"),
                        rs.getString("Ten"),
                        rs.getString("DvTinh"),
                        rs.getLong("DonGia"),
                        rs.getInt("SoLuong"),
                        rs.getString("Loai"),
                        rs.getString("Anh")
                );
                ketQua.add(sp);
            }
        } catch (SQLException e) {
            System.err.println("Lỗi tìm kiếm sản phẩm: " + e.getMessage());
        }

        return ketQua;
    }

    public boolean xuatExcel() {
        return true;
    }

    public boolean nhapExcel() {
        return true;
    }
}