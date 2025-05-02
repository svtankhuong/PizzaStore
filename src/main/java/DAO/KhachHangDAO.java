
package DAO;

import DTO.KhachHangDTO;
import config.JDBC;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class KhachHangDAO
{
    public ArrayList<KhachHangDTO> layDSKH(){
        String sql = "SELECT * FROM KhachHang";
        ArrayList<KhachHangDTO> dsKH = new ArrayList<>();
        
        try ( Connection conn = JDBC.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)){
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                int idKH = rs.getInt("MaKH");
                String holot = rs.getString("HoLot");
                String tenKH = rs.getString("Ten");
                String sdt = rs.getString("SDT");
                String diachi = rs.getString("DiaChi");
                Long tct = rs.getLong("TongChiTieu");
                KhachHangDTO kh = new KhachHangDTO(idKH, holot, tenKH, sdt, diachi, tct);
                dsKH.add(kh);
            }
        }catch(SQLException e)
        {
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
}
