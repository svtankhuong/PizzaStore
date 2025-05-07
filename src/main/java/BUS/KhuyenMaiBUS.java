package BUS;

import java.util.List;

import DAO.KhuyenMaiDAO;
import DTO.KhuyenMaiDTO;

public class KhuyenMaiBUS {
    private KhuyenMaiDAO khuyenMaiDAO;

    public KhuyenMaiBUS() {
        khuyenMaiDAO = new KhuyenMaiDAO();
    }

    public List<KhuyenMaiDTO> getAllKhuyenMai() {
        return khuyenMaiDAO.layDSKM();
    }

    public boolean themKhuyenMai(KhuyenMaiDTO khuyenMai) {

        if (!validateKhuyenMai(khuyenMai)) {
            return false;
        }
        return khuyenMaiDAO.themKhuyenMai(khuyenMai);
    }

    public boolean suaKhuyenMai(KhuyenMaiDTO khuyenMai) {
        if (!validateKhuyenMai(khuyenMai)) {
            return false;
        }
        return khuyenMaiDAO.suaKhuyenMai(khuyenMai);
    }

    public boolean xoaKhuyenMai(int MaKM) {
        return khuyenMaiDAO.xoaKhuyenMai(MaKM);
    }

    public boolean isMaKMExists(String MaKM, int excludeIndex) {
        List<KhuyenMaiDTO> danhSachKM = getAllKhuyenMai();

        for (int i = 0; i < danhSachKM.size(); i++) {
            if (i != excludeIndex && String.valueOf(danhSachKM.get(i).getMaKM()).equalsIgnoreCase(MaKM)) {
                return true;
            }
        }
        return false;
    }

    private boolean validateKhuyenMai(KhuyenMaiDTO khuyenMai) {
        if (khuyenMai.getTenKM().trim().isEmpty() ||
                khuyenMai.getNgayBatDau() == null ||
                khuyenMai.getNgayKetThuc() == null) {
            return false;
        }

        if (khuyenMai.getNgayKetThuc().isBefore(khuyenMai.getNgayBatDau())) {
            return false;
        }

        return true;
    }
}