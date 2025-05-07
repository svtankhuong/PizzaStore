package BUS;

import java.util.List;

import DAO.ChiTietKMDAO;
import DTO.ChiTietKMDTO;

public class ChiTietKMBUS {
    private ChiTietKMDAO chiTietKMDAO;

    public ChiTietKMBUS() {
        chiTietKMDAO = new ChiTietKMDAO();
    }

    public List<ChiTietKMDTO> getAllChiTietKM() {
        return chiTietKMDAO.layDSCTKM();
    }

    public boolean themChiTietKM(ChiTietKMDTO ctkm) {
        if (!validateChiTietKM(ctkm)) {
            return false;
        }
        return chiTietKMDAO.themChiTietKM(ctkm);
    }

    public boolean suaChiTietKM(ChiTietKMDTO ctkm, String oldTenCTKM) {
        if (!validateChiTietKM(ctkm)) {
            return false;
        }
        return chiTietKMDAO.suaChiTietKM(ctkm, oldTenCTKM);
    }

    public boolean xoaChiTietKM(int maKM, String tenCTKM) {
        return chiTietKMDAO.xoaChiTietKM(maKM, tenCTKM);
    }

    private boolean validateChiTietKM(ChiTietKMDTO ctkm) {
        // Kiểm tra tên CTKM không được để trống
        if (ctkm.getTenCTKM() == null || ctkm.getTenCTKM().trim().isEmpty()) {
            return false;
        }

        // Phần trăm giảm phải từ 0 đến 100
        if (ctkm.getPhanTramGiam() < 0 || ctkm.getPhanTramGiam() > 100) {
            return false;
        }

        // Giá trị tối thiểu phải >= 0
        if (ctkm.getToithieugiam() < 0) {
            return false;
        }

        return true;
    }
}
