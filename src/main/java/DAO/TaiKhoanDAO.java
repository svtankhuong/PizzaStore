package DAO;

import BUS.QuyenBUS;
import DTO.NhanVienDTO;
import DTO.QuyenDTO;
import DTO.SanPhamDTO;
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

            pre.setString(1, tk.getMatKhau());     // MatKhau
            pre.setInt(2, tk.getMaQuyen());        // MaQuyen
            pre.setInt(3, tk.getMaNV());           // MaNV
            pre.setString(4, tk.getTenDangNhap()); // WHERE TenDangNhap = ?

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

    public ArrayList<Object> getInforAccount(String username) {
        String sql = "SELECT MaNV, MatKhau, MaQuyen FROM TaiKhoan WHERE TenDangNhap=?";
        ArrayList<Object> thong_tin_DN = new ArrayList<>();
        try (Connection conn = JDBC.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int manv = rs.getInt("MaNV");
                String matkhau = rs.getString("MatKhau");
                int maquyen = rs.getInt("MaQuyen");
                NhanVienDAO a = new NhanVienDAO();
                NhanVienDTO b = a.getNhanVien(manv);
                String ngaysinh = b.getNgaysinh();
                String gioitinh = b.getGioiTinh();
                thong_tin_DN.add(username);        // 0: username
                thong_tin_DN.add(matkhau);         // 1: password
                thong_tin_DN.add(maquyen);         // 2: idNV (MaQuyen)
                thong_tin_DN.add(manv);            // 3: tenNV (MaNV)
                String ho_ten_nhan_vien = b.getHoLot() + " " + b.getTen();
                thong_tin_DN.add(ngaysinh);        // 4: ngaysinh
                thong_tin_DN.add(gioitinh);        // 5: gioitinh
                thong_tin_DN.add(ho_ten_nhan_vien); // 6: quyen (ho_ten)
                QuyenBUS c = new QuyenBUS();
                String tenQuyen = c.getTenQuyenTheoMa(maquyen);
                thong_tin_DN.add(tenQuyen);        // 7: tenQuyen
            }
        } catch (SQLException e) {
            System.err.println("Lỗi : " + e.getMessage());
        }
        return thong_tin_DN;
    }

}
