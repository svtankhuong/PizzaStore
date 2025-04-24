package DAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import config.JDBC;

import DTO.TaiKhoanDTO;
import java.sql.PreparedStatement;

public class TaiKhoanDAO {

    public ArrayList<TaiKhoanDTO> getDanhSachTaiKhoan() {
        ArrayList<TaiKhoanDTO> danhSachTaiKhoan = new ArrayList<>();
        try {
            String sql = "SELECT * FROM taikhoan";
            Connection connection = JDBC.getConnection();
            java.sql.PreparedStatement pre = connection.prepareStatement(sql);
            ResultSet rs = pre.executeQuery();
            while (rs.next()) {
                TaiKhoanDTO tk = new TaiKhoanDTO();
                tk.setTenDangNhap(rs.getString("TenDangNhap"));
                tk.setMatKhau(rs.getString("MatKhau"));
                tk.setMaQuyen(rs.getInt("MaQuyen"));
                tk.setMaNV(rs.getInt("MaNV"));
                danhSachTaiKhoan.add(tk);
            }
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return danhSachTaiKhoan;
    }

    public boolean ThemTaiKhoan(TaiKhoanDTO tk) {
        try {
            Connection conn = JDBC.getConnection();
            String sql = "INSERT INTO TaiKhoan (TenDangNhap, MatKhau, MaQuyen, maNV) VALUES (?, ?, ?, ?)";
            java.sql.PreparedStatement pre = conn.prepareStatement(sql);

            pre.setString(1, tk.getTenDangNhap());
            pre.setString(2, tk.getMatKhau());
            pre.setInt(3, tk.getMaQuyen());
            pre.setInt(4, tk.getMaNV());

            int rowsAffected = pre.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean SuaTaiKhoan(TaiKhoanDTO tk) {
        try {
            Connection conn = JDBC.getConnection();
            String sql = "UPDATE taikhoan SET MatKhau = ?, MaQuyen = ?, MaNV = ? WHERE TenDangNhap = ?";
            java.sql.PreparedStatement pre = conn.prepareStatement(sql);

            pre.setString(1, tk.getTenDangNhap());
            pre.setString(2, tk.getMatKhau());
            pre.setInt(3, tk.getMaQuyen());
            pre.setInt(4, tk.getMaNV());

            int rowsAffected = pre.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean XoaTaiKhoan(String TenDangNhap) {
        boolean result = false;
        try {
            String sql = "DELETE FROM taikhoan WHERE TenDangNhap=?";
            Connection conn = JDBC.getConnection();
            PreparedStatement pre = conn.prepareStatement(sql);
            pre.setString(1, TenDangNhap);
            result = pre.executeUpdate() > 0;
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

}
