package DAO;

import DTO.ChiTietKMDTO;
import config.JDBC;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class ChiTietKMDAO
{
    public ArrayList<ChiTietKMDTO> layDSKH(){
        String sql = "SELECT * FROM ChiTietKhuyenMai";
        ArrayList<ChiTietKMDTO> dsCTKM = new ArrayList<>();
        
        try ( Connection conn = JDBC.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)){
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                int idCTKM = rs.getInt("MACTKM");
                int idKM = rs.getInt("MaKM");
                Long phantramgiam = rs.getLong("PhanTramGiam");
                Long Toithieugiam = rs.getLong("Toithieugiam");
                String tenCTKM = rs.getString("TenCTKM");
                ChiTietKMDTO kh = new ChiTietKMDTO(idCTKM, idKM, phantramgiam, Toithieugiam, tenCTKM);
                dsCTKM.add(kh);
            }
        }catch(SQLException e)
        {
            System.err.println("Lỗi khi lấy danh sách chi tiết khuyến mãi: " + e.getMessage());
        }
        return dsCTKM;
    }
}
