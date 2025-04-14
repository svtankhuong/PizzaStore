package DAO;

import DTO.ChiTietHoaDonDTO;
import config.JDBC;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class CTHDDAO
{
        public boolean themCTHD(ChiTietHoaDonDTO cthd){
        String sql = "INSERT INTO ChiTietHoaDon (MaHD, MaSP, MaNV, MaCTKM, SoLuong, DonGia, ThanhTien) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = JDBC.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, cthd.getMaHD());
            ps.setInt(2, cthd.getMaSP());
            ps.setInt(3, cthd.getMaNV());
            ps.setInt(4, cthd.getMaCTKM());
            ps.setLong(5, cthd.getSoLuong());
            ps.setLong(6, cthd.getDonGia());
            ps.setLong(7, cthd.getThanhTien());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Lỗi khi thêm hóa đơn: " + e.getMessage());
        }
        
        return false;
    }
    
    public ArrayList<ChiTietHoaDonDTO> layDSCTHD(){
        String sql = "SELECT * FROM ChiTietHoaDon";
        ArrayList<ChiTietHoaDonDTO> dsCTHD = new ArrayList<>();
        
        try ( Connection conn = JDBC.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)){
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                int id = rs.getInt("MaCTHD");
                int idHD = rs.getInt("MaHD");
                int idSP = rs.getInt("MaSP");
                int idNV = rs.getInt("MaNV");
                Integer idCTKM = rs.getInt("MaCTKM");
                int soluong = rs.getInt("SoLuong");
                Long dongia = rs.getLong("DonGia");
                Long thanhtien = rs.getLong("ThanhTien");
                ChiTietHoaDonDTO cthd = new ChiTietHoaDonDTO(id, idHD, idSP, idNV, idCTKM, soluong, dongia, thanhtien);
                dsCTHD.add(cthd);
            }
        }catch(SQLException e)
        {
            System.err.println("Lỗi khi lấy danh sách hóa đơn: " + e.getMessage());
        }
        return dsCTHD;
    }
    
    public ChiTietHoaDonDTO layDSCTHDTheoMaHD(int MaHD){
        String sql = "SELECT * FROM ChiTietHoaDon WHERE MaHD = ?";
        ChiTietHoaDonDTO cthd = null;
        Boolean isSuccess = false;
        try ( Connection conn = JDBC.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setInt(1, MaHD);
            ResultSet rs = ps.executeQuery();
            if (rs.next()){
                cthd = new ChiTietHoaDonDTO(
                rs.getInt("MaCTHD"),
                rs.getInt("MaHD"),
                rs.getInt("MaSP"),
                rs.getInt("MaNV"),
                rs.getInt("MaCTKM"),
                rs.getInt("SoLuong"),
                rs.getLong("DonGia"),
                rs.getLong("ThanhTien")
                );
                isSuccess = true;
            }
        }catch(SQLException e)
        {
            System.err.println("Lỗi khi lấy danh sách hóa đơn: " + e.getMessage());
        }  
        
        if (isSuccess)
            return cthd;
        else
            return null;
    }
}
