package DAO;

import DTO.NhanVienDTO;
import DTO.QuyenDTO;

import config.JDBC;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class QuyenDAO {

    public ArrayList<QuyenDTO> getDanhSachQuyen() {
        ArrayList<QuyenDTO> danhSachQuyen = new ArrayList<>();
        try {
            String sql = "SELECT * FROM quyen";
            java.sql.Connection connection = JDBC.getConnection();
            java.sql.PreparedStatement pre = connection.prepareStatement(sql);
            java.sql.ResultSet rs = pre.executeQuery();
            while (rs.next()) {
                QuyenDTO q = new QuyenDTO();
                q.setMaQuyen(rs.getInt("MaQuyen"));
                q.setTenQuyen(rs.getString("TenQuyen"));
                q.setQLBanHang(rs.getBoolean("QLBanHang"));
                q.setQLKhuyenMai(rs.getBoolean("QLKhuyenMai"));
                q.setQLSanPham(rs.getBoolean("QLSanPham"));
                q.setQLNhanVien(rs.getBoolean("QLNhanVien"));
                q.setQLKhachHang(rs.getBoolean("QLKhachHang"));
                q.setQLNhapHang(rs.getBoolean("QLNhapHang"));
                q.setThongKe(rs.getBoolean("ThongKe"));
                danhSachQuyen.add(q);
            }
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return danhSachQuyen;
    }
}
