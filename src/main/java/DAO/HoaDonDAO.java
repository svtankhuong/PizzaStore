package DAO;

import DTO.HoaDonDTO;
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
            ps.setInt(3, hd.getMaCTKM());
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
    
    public HoaDonDTO layHDTheoMa(int idHD){
        String sql = "SELECT * FROM HoaDon WHERE MaHD = ?";
        HoaDonDTO hd = null;
        Boolean isSuccess = false;
        try ( Connection conn = JDBC.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setInt(1, idHD);
            ResultSet rs = ps.executeQuery();
            if (rs.next()){
                hd = new HoaDonDTO(
                rs.getInt("MaHD"),
                rs.getInt("MaNV"),
                rs.getInt("MaKH"),
                rs.getInt("MaCTKM"),
                rs.getDate("NgayLap").toLocalDate(),
                rs.getLong("SoTienGiam"),
                rs.getLong("TongTien")
                );
                isSuccess = true;
            }
        }catch(SQLException e)
        {
            System.err.println("Lỗi khi lấy danh sách hóa đơn: " + e.getMessage());
        }  
        
        if (isSuccess)
            return hd;
        else
            return null;
    }
    
    public ArrayList<HoaDonDTO> layHDTheoMaNV(int maNV) {
        String sql = "SELECT * FROM HoaDon WHERE MaNV = ?";
        ArrayList<HoaDonDTO> danhSachHD = new ArrayList<>();
        try (Connection conn = JDBC.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, maNV);
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
            System.err.println("Lỗi khi lọc hóa đơn theo MaNV: " + e.getMessage());
        }
        return danhSachHD;
    }

    public ArrayList<HoaDonDTO> layHDTheoMaKH(int maKH) {
        String sql = "SELECT * FROM HoaDon WHERE MaKH = ?";
        ArrayList<HoaDonDTO> danhSachHD = new ArrayList<>();
        try (Connection conn = JDBC.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, maKH);
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
            System.err.println("Lỗi khi lọc hóa đơn theo MaKH: " + e.getMessage());
        }
        return danhSachHD;
    }

    public ArrayList<HoaDonDTO> layHDTheoMaCTKM(Integer maCTKM) {
        String sql = "SELECT * FROM HoaDon WHERE MaCTKM = ?";
        ArrayList<HoaDonDTO> danhSachHD = new ArrayList<>();
        try (Connection conn = JDBC.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, maCTKM);
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
            System.err.println("Lỗi khi lọc hóa đơn theo MaCTKM: " + e.getMessage());
        }
        return danhSachHD;
    }

    public ArrayList<HoaDonDTO> layHDTheoNgayLap(LocalDate ngayLap) {
        String sql = "SELECT * FROM HoaDon WHERE NgayLap = ?";
        ArrayList<HoaDonDTO> danhSachHD = new ArrayList<>();
        try (Connection conn = JDBC.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDate(1, java.sql.Date.valueOf(ngayLap));
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
            System.err.println("Lỗi khi lọc hóa đơn theo NgayLap: " + e.getMessage());
        }
        return danhSachHD;
    }

    public ArrayList<HoaDonDTO> layHDTheoTongTien(long tongTien) {
        String sql = "SELECT * FROM HoaDon WHERE TongTien = ?";
        ArrayList<HoaDonDTO> danhSachHD = new ArrayList<>();
        try (Connection conn = JDBC.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, tongTien);
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
            System.err.println("Lỗi khi lọc hóa đơn theo TongTien: " + e.getMessage());
        }
        return danhSachHD;
    }

}
