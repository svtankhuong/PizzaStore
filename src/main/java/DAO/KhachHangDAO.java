package DAO;

import DTO.KhachHangDTO;
import config.JDBC;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class KhachHangDAO {

    public ArrayList<KhachHangDTO> layDSKH() {
        String sql = "SELECT * FROM khachhang WHERE isDelete = 0";
        ArrayList<KhachHangDTO> dsKH = new ArrayList<>();

        try (Connection conn = JDBC.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int idKH = rs.getInt("MaKH");
                String holot = rs.getString("HoLot");
                String tenKH = rs.getString("Ten");
                String sdt = rs.getString("SDT");
                String diachi = rs.getString("DiaChi");
                Long tct = rs.getLong("TongChiTieu");
                KhachHangDTO kh = new KhachHangDTO(idKH, holot, tenKH, sdt, diachi, tct);
                dsKH.add(kh);
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi lấy danh sách Khách Hàng: " + e.getMessage());
        }
        return dsKH;
    }
    
    public KhachHangDTO TimKhachHangTheoMa(int makh){
        String sql = "SELECT * FROM KhachHang WHERE MaKH = ?";
        KhachHangDTO kh = null;
        try ( Connection conn = JDBC.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setInt(1, makh);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                int idKH = rs.getInt("MaKH");
                String holot = rs.getString("HoLot");
                String tenKH = rs.getString("Ten");
                String sdt = rs.getString("SDT");
                String diachi = rs.getString("DiaChi");
                Long tct = rs.getLong("TongChiTieu");
                kh = new KhachHangDTO(idKH, holot, tenKH, sdt, diachi, tct);
            }
        }catch(SQLException e)
        {
            System.err.println("Lỗi khi lấy danh sách Khách Hàng: " + e.getMessage());
        }
        
        return kh;
    }

    public KhachHangDTO getKhachHangTheoMa(int makh) {
        KhachHangDTO kh = null;
        try {
            String sql = "SELECT * FROM khachhang WHERE MaKH=? and isDelete = 0";
            Connection connection = JDBC.getConnection();
            PreparedStatement pre = connection.prepareStatement(sql);
            pre.setInt(1, makh);
            ResultSet rs = pre.executeQuery();
            if (rs.next()) {
                kh = new KhachHangDTO();
                kh.setMaKH(rs.getInt("MaKH"));
                kh.setHoDem(rs.getString("HoLot"));
                kh.setTen(rs.getString("Ten"));
                kh.setSdt(rs.getString("SDT"));
                kh.setDiaChi(rs.getString("DiaChi"));
                kh.setTongChiTieu(rs.getLong("TongChiTieu"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return kh;
    }

    public ArrayList<KhachHangDTO> timKiemKhachHang(String tuKhoa) {
        ArrayList<KhachHangDTO> danhSachKhachHang = new ArrayList<>();
        String sql = "SELECT * FROM khachhang WHERE isDelete = 0 AND (MaKH LIKE ? OR HoLot LIKE ? OR Ten LIKE ? OR SDT LIKE ?)";

        try {
            Connection connection = JDBC.getConnection();
            PreparedStatement pre = connection.prepareStatement(sql);

            // Tạo từ khóa tìm kiếm có dấu %
            String keyword = "%" + tuKhoa + "%";
            pre.setString(1, keyword);  // MaKH (SQL sẽ tự ép kiểu nếu là INT)
            pre.setString(2, keyword);  // HoDem
            pre.setString(3, keyword);  // Ten
            pre.setString(4, keyword); // SDT

            ResultSet rs = pre.executeQuery();

            while (rs.next()) {
                KhachHangDTO kh = new KhachHangDTO();
                kh.setMaKH(rs.getInt("MaKH"));
                kh.setHoDem(rs.getString("HoLot"));
                kh.setTen(rs.getString("Ten"));
                kh.setSdt(rs.getString("SDT"));
                kh.setDiaChi(rs.getString("DiaChi"));
                kh.setTongChiTieu(rs.getLong("TongChiTieu"));
                danhSachKhachHang.add(kh);
            }

            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return danhSachKhachHang;
    }

    public int getMax() {
        int id = 0;
        try {
            String sql = "SELECT MAX(MaKH) FROM khachhang";
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

    public boolean ThemKhachHang(KhachHangDTO kh) {
        boolean result = false;
        try {
            String sql = "INSERT INTO khachhang (MaKH, HoLot, Ten, SDT, DiaChi) VALUES (?, ?, ?, ?, ?)";
            Connection connection = JDBC.getConnection();
            PreparedStatement pre = connection.prepareStatement(sql);
            pre.setInt(1, kh.getMaKH());
            pre.setString(2, kh.getHoDem());
            pre.setString(3, kh.getTen());
            pre.setString(4, kh.getSdt());
            pre.setString(5, kh.getDiaChi());

            int rowsAffected = pre.executeUpdate();
            if (rowsAffected > 0) {
                result = true;
            }

            connection.close(); // đóng kết nối
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public boolean SuaKhachHang(KhachHangDTO kh) {
        boolean result = false;
        try {
            String sql = "UPDATE khachhang SET HoLot = ?,Ten = ?,SDT = ?,DiaChi = ?, TongChiTieu=? WHERE MaKH = ?";
            Connection connection = JDBC.getConnection();
            PreparedStatement pre = connection.prepareStatement(sql);
            pre.setString(1, kh.getHoDem());
            pre.setString(2, kh.getTen());
            pre.setString(3, kh.getSdt());
            pre.setString(4, kh.getDiaChi());
            pre.setLong(5, kh.getTongChiTieu());
            pre.setInt(6, kh.getMaKH());

            int rowsUpdated = pre.executeUpdate();
            if (rowsUpdated > 0) {
                result = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public boolean xoaNkhachHang(int maKH) {
        boolean result = false;
        try {
            String sql = "DELETE FROM khachhang WHERE MaKH=?";
            Connection connection = JDBC.getConnection();
            PreparedStatement pre = connection.prepareStatement(sql);
            pre.setInt(1, maKH);
            result = pre.executeUpdate() > 0;
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public boolean xoamemkhachhang(int maKH) {
        boolean result = false;
        try {
            String sql = "UPDATE FORM khachhang SET isDelete = 1 WHERE MaKH = ?";
            Connection connection = JDBC.getConnection();
            PreparedStatement pre = connection.prepareStatement(sql);
            pre.setInt(1, maKH);
            result = pre.executeUpdate() > 0;
            pre.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

}
