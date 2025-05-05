package BUS;

import DAO.PhieuNhapDAO;
import DTO.PhieuNhapDTO;
import config.JDBC;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PhieuNhapBUS {
    private static final Logger LOGGER = Logger.getLogger(PhieuNhapBUS.class.getName());
    private ArrayList<PhieuNhapDTO> phieuNhapList;
    private PhieuNhapDAO phieuNhapDAO;
    private ChiTietPhieuNhapBUS chiTietPhieuNhapBUS;

    public PhieuNhapBUS() {
        phieuNhapDAO = new PhieuNhapDAO();
        chiTietPhieuNhapBUS = new ChiTietPhieuNhapBUS();
        phieuNhapList = new ArrayList<>();
        try {
            phieuNhapList.addAll(phieuNhapDAO.getAllPhieuNhap());
            LOGGER.log(Level.INFO, "Tải danh sách phiếu nhập thành công, số lượng: {0}", phieuNhapList.size());
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Lỗi khi tải danh sách phiếu nhập", e);
        }
    }

    public List<Object[]> getAllPhieuNhap() {
        List<Object[]> result = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        for (PhieuNhapDTO pn : phieuNhapList) {
            result.add(new Object[]{
                pn.getMaPN(),
                pn.getMaNCC(),
                pn.getMaNV(),
                pn.getTongTien(),
                sdf.format(pn.getNgayLap())
            });
        }
        LOGGER.log(Level.INFO, "Lấy danh sách phiếu nhập để hiển thị, số lượng: {0}", result.size());
        return result;
    }

    public String addPhieuNhap(String maPN, String maNCC, String maNV, String ngayLap, List<Object[]> chiTietRows) throws SQLException {
        Connection conn = null;
        try {
            conn = JDBC.getConnection();
            conn.setAutoCommit(false);
            LOGGER.log(Level.INFO, "Bắt đầu thêm phiếu nhập: MaPN={0}", maPN);

            if (maPN.isEmpty() || maNCC.isEmpty() || maNV.isEmpty() || ngayLap.isEmpty()) {
                throw new IllegalArgumentException("Vui lòng nhập đầy đủ thông tin!");
            }
            int maPNInt, maNCCInt, maNVInt;
            try {
                maPNInt = Integer.parseInt(maPN);
                maNCCInt = Integer.parseInt(maNCC);
                maNVInt = Integer.parseInt(maNV);
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Mã PN, Mã NCC, Mã NV phải là số nguyên!");
            }
            if (maPNInt <= 0 || maNCCInt <= 0 || maNVInt <= 0) {
                throw new IllegalArgumentException("Mã PN, Mã NCC, Mã NV phải lớn hơn 0!");
            }
            if (getPhieuNhapById(maPNInt) != null) {
                throw new IllegalArgumentException("Mã phiếu nhập đã tồn tại!");
            }
            Date ngayLapDate;
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                sdf.setLenient(false);
                ngayLapDate = sdf.parse(ngayLap);
            } catch (ParseException e) {
                throw new IllegalArgumentException("Ngày lập không đúng định dạng yyyy-MM-dd!");
            }
            if (chiTietRows.isEmpty()) {
                throw new IllegalArgumentException("Danh sách chi tiết phiếu nhập không được rỗng!");
            }
            for (Object[] row : chiTietRows) {
                try {
                    int maNL = Integer.parseInt(row[0].toString());
                    int soLuong = Integer.parseInt(row[1].toString());
                    double donGia = Double.parseDouble(row[2].toString());
                    if (maNL <= 0) {
                        throw new IllegalArgumentException("Mã nguyên liệu phải lớn hơn 0!");
                    }
                    if (soLuong <= 0) {
                        throw new IllegalArgumentException("Số lượng phải lớn hơn 0!");
                    }
                    if (donGia < 0) {
                        throw new IllegalArgumentException("Đơn giá không được âm!");
                    }
                } catch (NumberFormatException e) {
                    throw new IllegalArgumentException("Dữ liệu chi tiết phiếu nhập không hợp lệ (phải là số)!");
                }
            }

            double tongTien = chiTietPhieuNhapBUS.calculateTongTien(chiTietRows);
            LOGGER.log(Level.INFO, "Tổng tiền tính toán: {0}", tongTien);
            PhieuNhapDTO pn = new PhieuNhapDTO(maPNInt, maNCCInt, maNVInt, tongTien, ngayLapDate);

            boolean phieuNhapSuccess = phieuNhapDAO.addPhieuNhap(pn);
            LOGGER.log(Level.INFO, "Thêm phiếu nhập vào DB: {0}", phieuNhapSuccess);
            if (!phieuNhapSuccess) {
                conn.rollback();
                return "Lỗi: Không thể thêm phiếu nhập vào cơ sở dữ liệu!";
            }

            chiTietPhieuNhapBUS.addChiTietPhieuNhap(chiTietRows, maPNInt, conn);
            LOGGER.log(Level.INFO, "Thêm chi tiết phiếu nhập vào DB thành công");

            conn.commit();
            phieuNhapList.add(pn);
            LOGGER.log(Level.INFO, "Thêm phiếu nhập thành công: MaPN={0}", maPNInt);
            return "Thêm phiếu nhập thành công!";
        } catch (IllegalArgumentException | SQLException e) {
            if (conn != null) conn.rollback();
            LOGGER.log(Level.WARNING, "Lỗi khi thêm phiếu nhập: {0}", e.getMessage());
            return "Lỗi: " + e.getMessage();
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    JDBC.closeConnection(conn);
                } catch (SQLException e) {
                    LOGGER.log(Level.WARNING, "Lỗi đóng kết nối", e);
                }
            }
        }
    }

    public String updatePhieuNhap(String maPN, String maNCC, String maNV, String ngayLap, List<Object[]> chiTietRows) throws SQLException {
        Connection conn = null;
        try {
            conn = JDBC.getConnection();
            conn.setAutoCommit(false);
            LOGGER.log(Level.INFO, "Bắt đầu cập nhật phiếu nhập: MaPN={0}", maPN);

            // Kiểm tra dữ liệu đầu vào
            if (maPN.isEmpty() || maNCC.isEmpty() || maNV.isEmpty() || ngayLap.isEmpty()) {
                throw new IllegalArgumentException("Vui lòng nhập đầy đủ thông tin!");
            }
            int maPNInt, maNCCInt, maNVInt;
            try {
                maPNInt = Integer.parseInt(maPN);
                maNCCInt = Integer.parseInt(maNCC);
                maNVInt = Integer.parseInt(maNV);
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Mã PN, Mã NCC, Mã NV phải là số nguyên!");
            }
            if (maPNInt <= 0 || maNCCInt <= 0 || maNVInt <= 0) {
                throw new IllegalArgumentException("Mã PN, Mã NCC, Mã NV phải lớn hơn 0!");
            }
            PhieuNhapDTO existingPN = getPhieuNhapById(maPNInt);
            if (existingPN == null) {
                throw new IllegalArgumentException("Phiếu nhập không tồn tại!");
            }
            Date ngayLapDate;
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                sdf.setLenient(false);
                ngayLapDate = sdf.parse(ngayLap);
            } catch (ParseException e) {
                throw new IllegalArgumentException("Ngày lập không đúng định dạng yyyy-MM-dd!");
            }
            if (chiTietRows.isEmpty()) {
                throw new IllegalArgumentException("Danh sách chi tiết phiếu nhập không được rỗng!");
            }
            LOGGER.log(Level.INFO, "Danh sách chi tiết phiếu nhập nhận được: {0}", chiTietRows.size());
            // Log chi tiết từng dòng trong chiTietRows
            for (int i = 0; i < chiTietRows.size(); i++) {
                Object[] row = chiTietRows.get(i);
                LOGGER.log(Level.INFO, "Chi tiết phiếu nhập dòng {0}: MaNL={1}, SoLuong={2}, DonGia={3}, ThanhTien={4}",
                    new Object[]{i + 1, row[0], row[1], row[2], Double.parseDouble(row[1].toString()) * Double.parseDouble(row[2].toString())});
            }
            for (Object[] row : chiTietRows) {
                try {
                    int maNL = Integer.parseInt(row[0].toString());
                    int soLuong = Integer.parseInt(row[1].toString());
                    double donGia = Double.parseDouble(row[2].toString());
                    if (maNL <= 0) {
                        throw new IllegalArgumentException("Mã nguyên liệu phải lớn hơn 0!");
                    }
                    if (soLuong <= 0) {
                        throw new IllegalArgumentException("Số lượng phải lớn hơn 0!");
                    }
                    if (donGia < 0) {
                        throw new IllegalArgumentException("Đơn giá không được âm!");
                    }
                } catch (NumberFormatException e) {
                    throw new IllegalArgumentException("Dữ liệu chi tiết phiếu nhập không hợp lệ (phải là số)!");
                }
            }

            // Tính tổng tiền và tạo đối tượng PhieuNhapDTO
            double tongTien = chiTietPhieuNhapBUS.calculateTongTien(chiTietRows);
            LOGGER.log(Level.INFO, "Tổng tiền tính toán: {0}", tongTien);
            PhieuNhapDTO pn = new PhieuNhapDTO(maPNInt, maNCCInt, maNVInt, tongTien, ngayLapDate);

            // Cập nhật thông tin phiếu nhập
            boolean phieuNhapSuccess = phieuNhapDAO.updatePhieuNhap(pn);
            LOGGER.log(Level.INFO, "Cập nhật phiếu nhập vào DB: {0}", phieuNhapSuccess);
            if (!phieuNhapSuccess) {
                conn.rollback();
                LOGGER.log(Level.WARNING, "Không thể cập nhật thông tin phiếu nhập trong DB");
                return "Lỗi: Không thể cập nhật thông tin phiếu nhập trong cơ sở dữ liệu!";
            }

            // Cập nhật chi tiết phiếu nhập
            chiTietPhieuNhapBUS.updateChiTietPhieuNhap(chiTietRows, maPNInt, conn);
            LOGGER.log(Level.INFO, "Cập nhật chi tiết phiếu nhập vào DB thành công");

            // Commit giao dịch
            conn.commit();
            LOGGER.log(Level.INFO, "Commit giao dịch thành công");

            // Cập nhật danh sách trong bộ nhớ
            int index = phieuNhapList.indexOf(existingPN);
            if (index >= 0) {
                phieuNhapList.set(index, pn);
            } else {
                phieuNhapList.add(pn);
            }
            LOGGER.log(Level.INFO, "Cập nhật phiếu nhập thành công: MaPN={0}", maPNInt);
            return "Sửa phiếu nhập thành công!";
        } catch (IllegalArgumentException | SQLException e) {
            if (conn != null) {
                conn.rollback();
                LOGGER.log(Level.WARNING, "Rollback giao dịch: {0}", e.getMessage());
            }
            return "Lỗi: " + e.getMessage();
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    JDBC.closeConnection(conn);
                    LOGGER.log(Level.INFO, "Đóng kết nối thành công");
                } catch (SQLException e) {
                    LOGGER.log(Level.WARNING, "Lỗi đóng kết nối", e);
                }
            }
        }
    }

    public List<Object[]> searchPhieuNhap(String maPN, String maNCC, String maNV) {
        List<Object[]> result = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        for (PhieuNhapDTO pn : phieuNhapList) {
            boolean match = true;
            if (!maPN.isEmpty()) {
                try {
                    if (pn.getMaPN() != Integer.parseInt(maPN)) {
                        match = false;
                    }
                } catch (NumberFormatException e) {
                    match = false;
                }
            }
            if (!maNCC.isEmpty()) {
                try {
                    if (pn.getMaNCC() != Integer.parseInt(maNCC)) {
                        match = false;
                    }
                } catch (NumberFormatException e) {
                    match = false;
                }
            }
            if (!maNV.isEmpty()) {
                try {
                    if (pn.getMaNV() != Integer.parseInt(maNV)) {
                        match = false;
                    }
                } catch (NumberFormatException e) {
                    match = false;
                }
            }
            if (match) {
                result.add(new Object[]{
                    pn.getMaPN(),
                    pn.getMaNCC(),
                    pn.getMaNV(),
                    pn.getTongTien(),
                    sdf.format(pn.getNgayLap())
                });
            }
        }
        LOGGER.log(Level.INFO, "Tìm kiếm phiếu nhập, số kết quả: {0}", result.size());
        return result;
    }

    private PhieuNhapDTO getPhieuNhapById(int maPN) {
        for (PhieuNhapDTO pn : phieuNhapList) {
            if (pn.getMaPN() == maPN) {
                return pn;
            }
        }
        return null;
    }
}