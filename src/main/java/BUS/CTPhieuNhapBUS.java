package BUS;

import DAO.CTPhieuNhapDAO;
import DTO.CTPhieuNhapDTO;
import java.util.ArrayList;
import java.util.List;

public class CTPhieuNhapBUS {
    private final CTPhieuNhapDAO ctPhieuNhapDAO;

    public CTPhieuNhapBUS() {
        this.ctPhieuNhapDAO = new CTPhieuNhapDAO();
    }

    public ArrayList<CTPhieuNhapDTO> LayDanhSachCTPhieuNhap() {
        return new ArrayList<>(ctPhieuNhapDAO.LayDanhSachCTPhieuNhap());
    }

    public boolean ThemCTPhieuNhap(CTPhieuNhapDTO ctPhieuNhap) {

        return ctPhieuNhapDAO.ThemCTPhieuNhap(ctPhieuNhap);
    }

    public boolean SuaCTPhieuNhap(CTPhieuNhapDTO ctPhieuNhap) {
        return ctPhieuNhapDAO.SuaCTPhieuNhap(ctPhieuNhap);
    }

    public boolean KiemTraMaNL(int maPN, int maNL) {
        CTPhieuNhapDAO ctPhieuNhapDAO = new CTPhieuNhapDAO();
        return ctPhieuNhapDAO.KiemTraMaPNvaMaNL(maPN, maNL);
    }

    public boolean XoaCTPhieuNhap(int maPN,int maNL) {
        return ctPhieuNhapDAO.XoaCTPhieuNhap(maPN,maNL);
    }
    public double TinhThanhTien(int soLuong, double donGia) {
        return soLuong * donGia;
    }
}