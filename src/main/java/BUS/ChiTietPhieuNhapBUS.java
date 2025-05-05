package BUS;

import DAO.ChiTietPhieuNhapDAO;
import DTO.ChiTietPhieuNhapDTO;
import config.JDBC;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ChiTietPhieuNhapBUS {
    private static final Logger LOGGER = Logger.getLogger(ChiTietPhieuNhapBUS.class.getName());
    private ArrayList<ChiTietPhieuNhapDTO> chiTietPhieuNhapList;
    private ChiTietPhieuNhapDAO chiTietPhieuNhapDAO;

    public ChiTietPhieuNhapBUS() {
        chiTietPhieuNhapDAO = new ChiTietPhieuNhapDAO();
        chiTietPhieuNhapList = new ArrayList<>();
        try {
            chiTietPhieuNhapList.addAll(chiTietPhieuNhapDAO.getAllChiTietPhieuNhap());
            LOGGER.log(Level.INFO, "Tải danh sách chi tiết phiếu nhập thành công, số lượng: {0}", chiTietPhieuNhapList.size());
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Lỗi khi tải danh sách chi tiết phiếu nhập", e);
        }
    }

    public List<Object[]> getChiTietPhieuNhap(String maPN) {
        List<Object[]> result = new ArrayList<>();
        for (ChiTietPhieuNhapDTO ctp : chiTietPhieuNhapList) {
            if (ctp.getMaPN() == Integer.parseInt(maPN)) {
                result.add(new Object[]{
                    ctp.getMaNL(),
                    ctp.getSoLuong(),
                    ctp.getDonGia(),
                    ctp.getThanhTien()
                });
            }
        }
        LOGGER.log(Level.INFO, "Lấy chi tiết phiếu nhập cho MaPN={0}, số dòng: {1}", new Object[]{maPN, result.size()});
        return result;
    }

    public double calculateTongTien(List<Object[]> chiTietRows) {
        double tongTien = 0;
        for (Object[] row : chiTietRows) {
            int soLuong = Integer.parseInt(row[1].toString());
            double donGia = Double.parseDouble(row[2].toString());
            double thanhTien = soLuong * donGia;
            tongTien += thanhTien;
        }
        LOGGER.log(Level.INFO, "Tính tổng tiền từ chi tiết phiếu nhập: {0}", tongTien);
        return tongTien;
    }

    public void addChiTietPhieuNhap(List<Object[]> chiTietRows, int maPN, Connection conn) throws SQLException {
        LOGGER.log(Level.INFO, "Bắt đầu thêm chi tiết phiếu nhập cho MaPN={0}", maPN);
        List<ChiTietPhieuNhapDTO> chiTietList = new ArrayList<>();
        for (Object[] row : chiTietRows) {
            int maNL = Integer.parseInt(row[0].toString());
            int soLuong = Integer.parseInt(row[1].toString());
            double donGia = Double.parseDouble(row[2].toString());
            double thanhTien = soLuong * donGia;

            ChiTietPhieuNhapDTO ctp = new ChiTietPhieuNhapDTO(maPN, maNL, soLuong, donGia, thanhTien);
            chiTietList.add(ctp);
        }

        chiTietPhieuNhapDAO.addChiTietPhieuNhap(chiTietList, maPN, conn);
        chiTietPhieuNhapList.addAll(chiTietList);
        LOGGER.log(Level.INFO, "Thêm chi tiết phiếu nhập thành công cho MaPN={0}, số dòng: {1}", new Object[]{maPN, chiTietList.size()});
    }

    public void updateChiTietPhieuNhap(List<Object[]> chiTietRows, int maPN, Connection conn) throws SQLException {
        LOGGER.log(Level.INFO, "Bắt đầu cập nhật chi tiết phiếu nhập cho MaPN={0}", maPN);

        // Tạo danh sách chi tiết mới từ chiTietRows
        List<ChiTietPhieuNhapDTO> chiTietList = new ArrayList<>();
        for (Object[] row : chiTietRows) {
            int maNL = Integer.parseInt(row[0].toString());
            int soLuong = Integer.parseInt(row[1].toString());
            double donGia = Double.parseDouble(row[2].toString());
            double thanhTien = soLuong * donGia;

            ChiTietPhieuNhapDTO ctp = new ChiTietPhieuNhapDTO(maPN, maNL, soLuong, donGia, thanhTien);
            chiTietList.add(ctp);
            LOGGER.log(Level.INFO, "Chi tiết mới: MaNL={0}, SoLuong={1}, DonGia={2}, ThanhTien={3}",
                    new Object[]{maNL, soLuong, donGia, thanhTien});
        }

        // Gọi DAO để cập nhật
        chiTietPhieuNhapDAO.updateChiTietPhieuNhap(chiTietList, maPN, conn);

        // Làm mới danh sách trong bộ nhớ từ cơ sở dữ liệu
        chiTietPhieuNhapList.clear();
        chiTietPhieuNhapList.addAll(chiTietPhieuNhapDAO.getAllChiTietPhieuNhap());
        LOGGER.log(Level.INFO, "Làm mới chiTietPhieuNhapList từ cơ sở dữ liệu, số lượng: {0}", chiTietPhieuNhapList.size());

        LOGGER.log(Level.INFO, "Cập nhật chi tiết phiếu nhập thành công cho MaPN={0}", maPN);
    }
}