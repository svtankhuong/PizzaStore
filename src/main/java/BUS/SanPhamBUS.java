package BUS;

import java.util.List;

import DAO.SanPhamDAO;
import DTO.SanPhamDTO;

public class SanPhamBUS {
    private SanPhamDAO sanPhamDAO;

    public SanPhamBUS() {
        sanPhamDAO = new SanPhamDAO();
    }

    public List<SanPhamDTO> getAllSanPham() {
        return sanPhamDAO.layDSSP();
    }

    public boolean themSanPham(SanPhamDTO sanPham) {
        if (!validateSanPham(sanPham)) {
            return false;
        }
        return sanPhamDAO.themSanPham(sanPham);
    }

    public boolean suaSanPham(SanPhamDTO sanPham) {
        if (!validateSanPham(sanPham)) {
            return false;
        }
        return sanPhamDAO.suaSanPham(sanPham);
    }

    public boolean xoaSanPham(String maSP) {
        return sanPhamDAO.xoaSanPham(maSP);
    }

    public List<SanPhamDTO> timKiemSanPham(String keyword) {
        return sanPhamDAO.timKiemSanPham(keyword);
    }

    public boolean xuatExcel(String filePath) {
        return sanPhamDAO.xuatExcel(filePath);
    }

    public boolean xuatExcelMotSanPham(SanPhamDTO sp, String filePath) {
        return sanPhamDAO.xuatExcelMotSanPham(sp, filePath);
    }

    private boolean validateSanPham(SanPhamDTO sanPham) {
        if (sanPham.getMaSP() < 0 ||
                sanPham.getTenSP().trim().isEmpty() ||
                sanPham.getLoai().trim().isEmpty() ||
                sanPham.getDvTinh().trim().isEmpty() ||
                sanPham.getAnhSP().trim().isEmpty()) {
            return false;
        }

        if (sanPham.getSoLuong() < 0 || sanPham.getDonGia() < 0) {
            return false;
        }

        return true;
    }

    public boolean daTonTaiMaSP(int maSP) {
        List<SanPhamDTO> ds = getAllSanPham();
        for (SanPhamDTO sp : ds) {
            if (sp.getMaSP() == maSP) {
                return true;
            }
        }
        return false;
    }

}