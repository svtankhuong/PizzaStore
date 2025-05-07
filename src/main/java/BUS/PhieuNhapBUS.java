package BUS;

import DAO.PhieuNhapDAO;
import DTO.CTPhieuNhapDTO;
import DTO.PhieuNhapDTO;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class PhieuNhapBUS {
    private final PhieuNhapDAO phieuNhapDAO;
    private CTPhieuNhapBUS ctPhieuNhapBUS = new CTPhieuNhapBUS();

    public PhieuNhapBUS() {
        this.phieuNhapDAO = new PhieuNhapDAO();
    }

    public ArrayList<PhieuNhapDTO> LayDanhSachPhieuNhap() {
        return new ArrayList<>(phieuNhapDAO.LayDanhSachPhieuNhap());
    }
    public int ThemPhieuNhap(PhieuNhapDTO phieuNhap) {
        return phieuNhapDAO.ThemPhieuNhap(phieuNhap);
    }

    public boolean SuaPhieuNhap(PhieuNhapDTO phieuNhap) {
        if (!KiemTraMaPhieuNhap(phieuNhap.getMaPN())) {
            return false;
        }
        return phieuNhapDAO.SuaPhieuNhap(phieuNhap);
    }

    public boolean KiemTraMaPhieuNhap(int maPN) {
        List<PhieuNhapDTO> danhSach = LayDanhSachPhieuNhap();
        return danhSach.stream().anyMatch(pn -> pn.getMaPN() == maPN);
    }

    public boolean XoaPhieuNhap(int maPN) {
        return phieuNhapDAO.XoaPhieuNhap(maPN);
    }

    public PhieuNhapDTO TimTheoMaPN(int maPN) {
        return LayDanhSachPhieuNhap().stream()
                .filter(pn -> pn.getMaPN() == maPN)
                .findFirst()
                .orElse(null);
    }

    public ArrayList<PhieuNhapDTO> TimTheoMaNCC(int maNCC) {
        ArrayList<PhieuNhapDTO> ketQua = new ArrayList<>();
        LayDanhSachPhieuNhap().stream()
                .filter(pn -> pn.getMaNCC() == maNCC)
                .forEach(ketQua::add);
        return ketQua;
    }

    public ArrayList<PhieuNhapDTO> TimTheoNgayNhap(Date ngayNhap) {
        ArrayList<PhieuNhapDTO> ketQua = new ArrayList<>();
        LayDanhSachPhieuNhap().stream()
                .filter(pn -> pn.getNgayLap().equals(ngayNhap))
                .forEach(ketQua::add);
        return ketQua;
    }

    public ArrayList<PhieuNhapDTO> TimTheoMaNCCvaMaNV(int maNCC, int maNV) {
        ArrayList<PhieuNhapDTO> ketQua = new ArrayList<>();
        LayDanhSachPhieuNhap().stream()
                .filter(pn -> pn.getMaNCC() == maNCC && pn.getMaNV() == maNV)
                .forEach(ketQua::add);
        return ketQua;
    }

    public ArrayList<PhieuNhapDTO> TimTheoMaNV(int maNV) {
        ArrayList<PhieuNhapDTO> ketQua = new ArrayList<>();
        LayDanhSachPhieuNhap().stream()
                .filter(pn -> pn.getMaNV() == maNV)
                .forEach(ketQua::add);
        return ketQua;
    }
    public ArrayList<PhieuNhapDTO> TimTheoMaNCCvaNgayNhap(int maNCC, Date ngayNhap) {
        ArrayList<PhieuNhapDTO> ketQua = new ArrayList<>();
        LayDanhSachPhieuNhap().stream()
                .filter(pn -> pn.getMaNCC() == maNCC && pn.getNgayLap().equals(ngayNhap))
                .forEach(ketQua::add);
        return ketQua;
    }
    public ArrayList<PhieuNhapDTO> TimTheoMaNCCvaMaNVvaNgayNhap(int maNCC, int maNV, Date ngayNhap) {
        ArrayList<PhieuNhapDTO> ketQua = new ArrayList<>();
        LayDanhSachPhieuNhap().stream()
                .filter(pn -> pn.getMaNCC() == maNCC &&
                        pn.getMaNV() == maNV &&
                        pn.getNgayLap().equals(ngayNhap))
                .forEach(ketQua::add);
        return ketQua;
    }

    public BigDecimal TinhTongTien(int maPN) {
        ArrayList<CTPhieuNhapDTO> dsCTPN = ctPhieuNhapBUS.LayDanhSachCTPhieuNhap();
        double tongTien = 0;
        for (CTPhieuNhapDTO ct : dsCTPN) {
            if (ct.getMaPN() == maPN) {
                tongTien += ct.getThanhTien();
            }
        }
        return new BigDecimal(tongTien);
    }
}