package DAO;

import DTO.HoaDonDTO;
import MyCustom.QueryCondition;
import config.JDBC;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class HoaDonDAO
{
    public boolean themHD(HoaDonDTO hd){
        String sql = "INSERT INTO HoaDon (MaNV, MaKH, MaCTKM, NgayLap, SoTienGiam, TongTien) VALUES (?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = JDBC.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, hd.getMaNV());
            ps.setInt(2, hd.getMaKH());
            
            // Xử lý null cho MaCTKM
            if (hd.getMaCTKM() != null) {
                ps.setInt(3, hd.getMaCTKM());
            } else {
                ps.setNull(3, java.sql.Types.INTEGER);
            }
            ps.setDate(4, Date.valueOf(hd.getNgayLapHD()));
            ps.setLong(5, hd.getSoTienGiam());
            ps.setLong(6, hd.getTongTienHD());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Lỗi khi thêm hóa đơn: " + e.getMessage());
        }
        
        return false;
    }
    
    public ArrayList<HoaDonDTO> layDSHD(){
        String sql = "SELECT * FROM HoaDon";
        ArrayList<HoaDonDTO> dsHD = new ArrayList<>();
        
        try ( Connection conn = JDBC.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)){
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                int id = rs.getInt("MaHD");
                int idNV = rs.getInt("MaNV");
                int idKH = rs.getInt("MaKH");
                Integer idCTKM = rs.getInt("MaCTKM");
                Date date = rs.getDate("NgayLap");
                Long sotienGiamHD = rs.getLong("SoTienGiam");
                Long tongtienHD = rs.getLong("TongTien");
                LocalDate dateCreated = date.toLocalDate();
                HoaDonDTO hd = new HoaDonDTO(id, idNV, idKH, idCTKM,dateCreated ,sotienGiamHD, tongtienHD);
                dsHD.add(hd);
            }
        }catch(SQLException e)
        {
            System.err.println("Lỗi khi lấy danh sách hóa đơn: " + e.getMessage());
        }
        return dsHD;
    }
    
    public ArrayList<HoaDonDTO> layHDTheoNFieldTheoDau(ArrayList<QueryCondition> conditions) {
        StringBuilder sql = new StringBuilder("SELECT * FROM HoaDon WHERE ");
        ArrayList<Object> values = new ArrayList<>();

        // Xử lý các điều kiện
        for (int i = 0; i < conditions.size(); i++) {
            QueryCondition qc = conditions.get(i);
            if (i > 0) {
                sql.append(" AND ");
            }
            if (qc.getValue() == null) {
                sql.append(qc.getColumn()).append(" IS NULL");
            } else {
                sql.append(qc.getColumn()).append(" ").append(qc.getOperator()).append(" ?");
                values.add(qc.getValue());
            }
        }

        ArrayList<HoaDonDTO> danhSachHD = new ArrayList<>();

        try (Connection conn = JDBC.getConnection(); PreparedStatement ps = conn.prepareStatement(sql.toString())) {
            // Gán giá trị cho các dấu ?
            for (int i = 0; i < values.size(); i++) {
                setPreparedStatementValue(ps, i + 1, values.get(i));
            }

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                HoaDonDTO hd = new HoaDonDTO(
                    rs.getInt("MaHD"),
                    rs.getInt("MaNV"),
                    rs.getInt("MaKH"),
                    rs.getInt("MaCTKM"),
                    rs.getDate("NgayLap").toLocalDate(),
                    rs.getLong("SoTienGiam"),
                    rs.getLong("TongTien")
                );
                danhSachHD.add(hd);
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi lọc hóa đơn theo N field: " + e.getMessage());
        }

        return danhSachHD;
    }
 
    private void setPreparedStatementValue(PreparedStatement ps, int index, Object value) throws SQLException {
        if (value instanceof Integer) {
             ps.setInt(index, (Integer) value);
        } else if (value instanceof Long) {
             ps.setLong(index, (Long) value);
        } else if (value instanceof String) {
             ps.setString(index, (String) value);
        } else if (value instanceof LocalDate) {
             ps.setDate(index, java.sql.Date.valueOf((LocalDate) value));
        }
    }
}
