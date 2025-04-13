package DAO;

import DTO.SanPhamDTO;
import config.JDBC;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class SanPhamDAO
{
        public ArrayList<SanPhamDTO> layDSSP(){
        String sql = "SELECT * FROM SanPham";
        ArrayList<SanPhamDTO> dsSP = new ArrayList<>();
        
        try ( Connection conn = JDBC.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)){
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                int idSP = rs.getInt("MaSP");
                String tenSP = rs.getString("Ten");
                String dvTinh = rs.getString("DvTinh");
                Long dongia = rs.getLong("DonGia");
                int soluong = rs.getInt("SoLuong");
                String loaiSP = rs.getString("Loai");
                SanPhamDTO cthd = new SanPhamDTO(idSP, tenSP, dvTinh, dongia, soluong, loaiSP);
                dsSP.add(cthd);
            }
        }catch(SQLException e)
        {
            System.err.println("Lỗi khi lấy danh sách hóa đơn: " + e.getMessage());
        }
        return dsSP;
    }
}
