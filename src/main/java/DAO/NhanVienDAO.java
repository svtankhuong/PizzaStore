package DAO;

import DTO.NhanVienDTO;
import config.JDBC;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class NhanVienDAO {

    // Lấy danh sách tất cả nhân viên
    public ArrayList<NhanVienDTO> getDanhSachNhanVien() {
        ArrayList<NhanVienDTO> danhSachNhanVien = new ArrayList<>();
        try {
            String sql = "SELECT * FROM nhanvien WHERE isDelete = 0";
            Connection connection = JDBC.getConnection();
            PreparedStatement pre = connection.prepareStatement(sql);
            ResultSet rs = pre.executeQuery();
            while (rs.next()) {
                NhanVienDTO nv = new NhanVienDTO();
                nv.setMaNV(rs.getInt("MaNV"));
                nv.setHoLot(rs.getString("HoLot"));
                nv.setTen(rs.getString("Ten"));
                nv.setGioiTinh(rs.getString("GioiTinh"));
                nv.setNgaysinh(rs.getString("Ngaysinh"));
                danhSachNhanVien.add(nv);
            }
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return danhSachNhanVien;
    }

    // Lấy thông tin nhân viên theo mã
    public NhanVienDTO getNhanVien(int maNV) {
        NhanVienDTO nv = null;
        try {
            String sql = "SELECT * FROM nhanvien WHERE MaNV=?";
            Connection connection = JDBC.getConnection();
            PreparedStatement pre = connection.prepareStatement(sql);
            pre.setInt(1, maNV);
            ResultSet rs = pre.executeQuery();
            if (rs.next()) {
                nv = new NhanVienDTO();
                nv.setMaNV(rs.getInt("MaNV"));
                nv.setHoLot(rs.getString("HoLot"));
                nv.setTen(rs.getString("Ten"));
                nv.setGioiTinh(rs.getString("GioiTinh"));
                nv.setNgaysinh(rs.getString("Ngaysinh"));
            }
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return nv;
    }

    // Thêm một nhân viên mới
    public boolean themNhanVien(NhanVienDTO nv) {
        boolean result = false;
        try {
            String sql = "INSERT INTO nhanvien (MaNV, HoLot, Ten, GioiTinh, Ngaysinh) VALUES (?, ?, ?, ?, ?)";
            Connection connection = JDBC.getConnection();
            PreparedStatement pre = connection.prepareStatement(sql);
            pre.setInt(1, nv.getMaNV());
            pre.setString(2, nv.getHoLot());
            pre.setString(3, nv.getTen());
            pre.setString(4, nv.getGioiTinh());
            pre.setString(5, nv.getNgaysinh());
            result = pre.executeUpdate() > 0;
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    // Cập nhật thông tin nhân viên
    public boolean capNhatNhanVien(NhanVienDTO nv) {
        boolean result = false;
        try {
            String sql = "UPDATE nhanvien SET HoLot=?, Ten=?, GioiTinh=?, Ngaysinh=? WHERE MaNV=?";
            Connection connection = JDBC.getConnection();
            PreparedStatement pre = connection.prepareStatement(sql);
            pre.setString(1, nv.getHoLot());
            pre.setString(2, nv.getTen());
            pre.setString(3, nv.getGioiTinh());
            pre.setString(4, nv.getNgaysinh());
            pre.setInt(5, nv.getMaNV());
            result = pre.executeUpdate() > 0;
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    // Xóa một nhân viên
    public boolean xoaNhanVien(int maNV) {
        boolean result = false;
        try {
            String sql = "DELETE FROM nhanvien WHERE MaNV=?";
            Connection connection = JDBC.getConnection();
            PreparedStatement pre = connection.prepareStatement(sql);
            pre.setInt(1, maNV);
            result = pre.executeUpdate() > 0;
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    // Xóa mềm (cập nhật isDelete = 1)
    public boolean xoaNhanVienMem(int maNV) {
        boolean result = false;
        try {
            String sql = "UPDATE nhanvien SET isDelete = 1 WHERE MaNV = ?";
            Connection connection = JDBC.getConnection();
            PreparedStatement pre = connection.prepareStatement(sql);
            pre.setInt(1, maNV);
            result = pre.executeUpdate() > 0;
            pre.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public int getMax() {
        int id = 0;
        try {
            String sql = "SELECT MAX(MaNV) FROM nhanvien";
            Connection connection = JDBC.getConnection();
            PreparedStatement pre = connection.prepareStatement(sql);
            ResultSet rs = pre.executeQuery();

            if (rs.next()) {
                id = rs.getInt(1);
            }

            rs.close();
            pre.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id + 1; // Trả về ID mới (lớn nhất + 1)
    }
    // Tìm kiếm nhân viên theo Họ lót, Tên hoặc Mã NV

    public ArrayList<NhanVienDTO> timKiemNhanVien(String tuKhoa) {
        ArrayList<NhanVienDTO> danhSachNhanVien = new ArrayList<>();
        String sql = "SELECT * FROM nhanvien WHERE isDelete = 0 AND (MaNV LIKE ? OR HoLot LIKE ? OR Ten LIKE ?)";

        try {
            Connection connection = JDBC.getConnection();
            PreparedStatement pre = connection.prepareStatement(sql);

            // Tạo từ khóa tìm kiếm với dấu % ở cả hai đầu
            String keyword = "%" + tuKhoa + "%";
            pre.setString(1, keyword);  // Tìm kiếm theo MaNV
            pre.setString(2, keyword);  // Tìm kiếm theo HoLot
            pre.setString(3, keyword);  // Tìm kiếm theo Ten

            ResultSet rs = pre.executeQuery();

            while (rs.next()) {
                NhanVienDTO nv = new NhanVienDTO();
                nv.setMaNV(rs.getInt("MaNV"));
                nv.setHoLot(rs.getString("HoLot"));
                nv.setTen(rs.getString("Ten"));
                nv.setGioiTinh(rs.getString("GioiTinh"));
                nv.setNgaysinh(rs.getString("Ngaysinh"));
                danhSachNhanVien.add(nv);
            }

            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return danhSachNhanVien;
    }

}
