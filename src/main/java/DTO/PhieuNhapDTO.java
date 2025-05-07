package DTO;

import java.math.BigDecimal;
import java.util.Date;

public class PhieuNhapDTO {
    private int maPN;
    private int maNCC;
    private int maNV;
    private BigDecimal tongTien;
    private Date ngayLap;

    public PhieuNhapDTO() {
    }

    public PhieuNhapDTO(int maPN, int maNCC, int maNV, BigDecimal tongTien, Date ngayLap) {
        this.maPN = maPN;
        this.maNCC = maNCC;
        this.maNV = maNV;
        this.tongTien = tongTien;
        this.ngayLap = ngayLap;
    }

    public int getMaPN() {
        return maPN;
    }

    public void setMaPN(int maPN) {
        this.maPN=maPN;
    }

    public int getMaNCC() {
        return maNCC;
    }

    public void setMaNCC(int maNCC) {
        this.maNCC = maNCC;
    }

    public int getMaNV() {
        return maNV;
    }

    public void setMaNV(int maNV) {
        this.maNV = maNV;
    }

    public BigDecimal getTongTien() {
        return tongTien;
    }

    public void setTongTien(BigDecimal tongTien) {
        this.tongTien = tongTien;
    }

    public Date getNgayLap() {
        return ngayLap;
    }

    public void setNgayLap(Date ngayLap) {
        this.ngayLap = ngayLap;
    }
}