package DAO;

import DTO.KhuyenMaiDTO;
import config.JDBC;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class KhuyenMaiDAO
{
    public ArrayList<KhuyenMaiDTO> layDSKH(){
        String sql = "SELECT * FROM KhuyenMai";
        ArrayList<KhuyenMaiDTO> dsKM = new ArrayList<>();
        
        try ( Connection conn = JDBC.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)){
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                int idKM = rs.getInt("MaKM");
                String tenKM = rs.getString("TenKM");
                LocalDate startDate = rs.getDate("NgayBatDau").toLocalDate();
                LocalDate endDate = rs.getDate("NgayKetThuc").toLocalDate();
                KhuyenMaiDTO kh = new KhuyenMaiDTO(idKM, tenKM, startDate, endDate);
                dsKM.add(kh);
            }
        }catch(SQLException e)
        {
            System.err.println("Lỗi khi lấy danh sách khuyến mãi: " + e.getMessage());
        }
        return dsKM;
    }
}
