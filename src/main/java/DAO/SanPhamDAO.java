package DAO;

import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import DTO.SanPhamDTO;
import config.JDBC;

public class SanPhamDAO {

    public ArrayList<SanPhamDTO> layDSSP() {
        String sql = "SELECT * FROM sanpham";
        ArrayList<SanPhamDTO> dsSP = new ArrayList<>();

        try (Connection conn = JDBC.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int idSP = rs.getInt("MaSP");
                String tenSP = rs.getString("Ten");
                String dvTinh = rs.getString("DvTinh");
                Long donGia = rs.getLong("DonGia");
                int soLuong = rs.getInt("SoLuong");
                String loaiSP = rs.getString("Loai");
                String anhSP = rs.getString("Anh");

                SanPhamDTO sp = new SanPhamDTO(idSP, tenSP, dvTinh, donGia, soLuong, loaiSP, anhSP);
                dsSP.add(sp);
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi lấy danh sách sản phẩm: " + e.getMessage());
        }

        return dsSP;
    }

    public boolean themSanPham(SanPhamDTO sanPham) {
        String sql = "INSERT INTO SanPham (Ten, DvTinh, DonGia, SoLuong, Loai, Anh) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = JDBC.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, sanPham.getTenSP());
            stmt.setString(2, sanPham.getDvTinh());
            stmt.setLong(3, sanPham.getDonGia());
            stmt.setInt(4, sanPham.getSoLuong());
            stmt.setString(5, sanPham.getLoai());
            stmt.setString(6, sanPham.getAnhSP());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Lỗi khi thêm sản phẩm: " + e.getMessage());
            return false;
        }
    }

    public boolean suaSanPham(SanPhamDTO sanPham) {
        String sql = "UPDATE SanPham SET Ten = ?, DvTinh = ?, DonGia = ?, SoLuong = ?, Loai = ?, Anh = ? WHERE MaSP = ?";

        try (Connection conn = JDBC.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, sanPham.getTenSP());
            stmt.setString(2, sanPham.getDvTinh());
            stmt.setLong(3, sanPham.getDonGia());
            stmt.setInt(4, sanPham.getSoLuong());
            stmt.setString(5, sanPham.getLoai());
            stmt.setString(6, sanPham.getAnhSP());
            stmt.setInt(7, sanPham.getMaSP());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Lỗi khi cập nhật sản phẩm: " + e.getMessage());
            return false;
        }
    }

    public boolean xoaSanPham(String maSP) {
        String sql = "DELETE FROM sanpham WHERE MaSP = ?";

        try (Connection conn = JDBC.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, maSP);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Lỗi khi xóa sản phẩm: " + e.getMessage());
            return false;
        }
    }

    public ArrayList<SanPhamDTO> timKiemSanPham(String keyword) {
        ArrayList<SanPhamDTO> ketQua = new ArrayList<>();
        String sql;
        boolean isNumber = keyword.trim().matches("\\d+");
        try (Connection conn = JDBC.getConnection()) {
            if (isNumber) {
                sql = "SELECT * FROM sanpham WHERE MaSP = ?";
                try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                    stmt.setInt(1, Integer.parseInt(keyword.trim()));
                    ResultSet rs = stmt.executeQuery();
                    while (rs.next()) {
                        ketQua.add(new SanPhamDTO(
                                rs.getInt("MaSP"),
                                rs.getString("Ten"),
                                rs.getString("DvTinh"),
                                rs.getLong("DonGia"),
                                rs.getInt("SoLuong"),
                                rs.getString("Loai"),
                                rs.getString("Anh")));
                    }
                }
            } else {
                sql = "SELECT * FROM sanpham WHERE Ten LIKE ? OR Loai LIKE ?";
                try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                    stmt.setString(1, "%" + keyword.trim() + "%");
                    stmt.setString(2, "%" + keyword.trim() + "%");
                    ResultSet rs = stmt.executeQuery();
                    while (rs.next()) {
                        ketQua.add(new SanPhamDTO(
                                rs.getInt("MaSP"),
                                rs.getString("Ten"),
                                rs.getString("DvTinh"),
                                rs.getLong("DonGia"),
                                rs.getInt("SoLuong"),
                                rs.getString("Loai"),
                                rs.getString("Anh")));
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Lỗi tìm kiếm sản phẩm: " + e.getMessage());
        }
        return ketQua;
    }

    public boolean xuatExcelMotSanPham(SanPhamDTO sp, String filePath) {
        String[] columns = { "Mã sản phẩm", "Tên sản phẩm", "Đơn vị tính", "Đơn giá", "Số lượng", "Loại", "Ảnh" };
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("SanPham");
            Row headerRow = sheet.createRow(0);
            for (int i = 0; i < columns.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(columns[i]);
            }
            Row row = sheet.createRow(1);
            row.createCell(0).setCellValue(sp.getMaSP());
            row.createCell(1).setCellValue(sp.getTenSP());
            row.createCell(2).setCellValue(sp.getDvTinh());
            row.createCell(3).setCellValue(sp.getDonGia());
            row.createCell(4).setCellValue(sp.getSoLuong());
            // Chuyển số sang tên loại
            String loaiStr = "";
            switch (sp.getLoai()) {
                case "1":
                    loaiStr = "Món chính";
                    break;
                case "2":
                    loaiStr = "Đồ uống";
                    break;
                case "3":
                    loaiStr = "Khác";
                    break;
                default:
                    loaiStr = sp.getLoai();
            }
            row.createCell(5).setCellValue(loaiStr);
            row.createCell(6).setCellValue(sp.getAnhSP());
            for (int i = 0; i < columns.length; i++) {
                sheet.autoSizeColumn(i);
            }
            try (FileOutputStream fileOut = new FileOutputStream(filePath)) {
                workbook.write(fileOut);
            }
            return true;
        } catch (Exception e) {
            System.err.println("Lỗi khi xuất Excel 1 sản phẩm: " + e.getMessage());
            return false;
        }
    }

    public boolean xuatExcel(String filePath) {
        String[] columns = { "Mã sản phẩm", "Tên sản phẩm", "Đơn vị tính", "Đơn giá", "Số lượng", "Loại", "Ảnh" };
        ArrayList<SanPhamDTO> dsSP = layDSSP();
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("SanPham");
            Row headerRow = sheet.createRow(0);
            for (int i = 0; i < columns.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(columns[i]);
            }
            int rowNum = 1;
            for (SanPhamDTO sp : dsSP) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(sp.getMaSP());
                row.createCell(1).setCellValue(sp.getTenSP());
                row.createCell(2).setCellValue(sp.getDvTinh());
                row.createCell(3).setCellValue(sp.getDonGia());
                row.createCell(4).setCellValue(sp.getSoLuong());
                // Chuyển số sang tên loại
                String loaiStr = "";
                switch (sp.getLoai()) {
                    case "1":
                        loaiStr = "Món chính";
                        break;
                    case "2":
                        loaiStr = "Đồ uống";
                        break;
                    case "3":
                        loaiStr = "Khác";
                        break;
                    default:
                        loaiStr = sp.getLoai();
                }
                row.createCell(5).setCellValue(loaiStr);
                row.createCell(6).setCellValue(sp.getAnhSP());
            }
            for (int i = 0; i < columns.length; i++) {
                sheet.autoSizeColumn(i);
            }
            try (FileOutputStream fileOut = new FileOutputStream(filePath)) {
                workbook.write(fileOut);
            }
            return true;
        } catch (Exception e) {
            System.err.println("Lỗi khi xuất Excel: " + e.getMessage());
            return false;
        }
    }

}
