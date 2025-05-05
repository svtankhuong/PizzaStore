package DTO;

public class ChiTietPhieuNhapDTO {
    private int maPN;
    private int maNL;
    private int soLuong;
    private double donGia;
    private double thanhTien;

    public ChiTietPhieuNhapDTO() {
    }

    public ChiTietPhieuNhapDTO(int maPN, int maNL, int soLuong, double donGia, double thanhTien) {
        this.maPN = maPN;
        this.maNL = maNL;
        this.soLuong = soLuong;
        this.donGia = donGia;
        this.thanhTien = thanhTien;
    }

    public int getMaPN() {
        return maPN;
    }

    public void setMaPN(int maPN) {
        this.maPN = maPN;
    }

    public int getMaNL() {
        return maNL;
    }

    public void setMaNL(int maNL) {
        this.maNL = maNL;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public double getDonGia() {
        return donGia;
    }

    public void setDonGia(double donGia) {
        this.donGia = donGia;
    }

    public double getThanhTien() {
        return thanhTien;
    }

    public void setThanhTien(double thanhTien) {
        this.thanhTien = thanhTien;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof ChiTietPhieuNhapDTO)) return false;
        ChiTietPhieuNhapDTO other = (ChiTietPhieuNhapDTO) obj;
        return maPN == other.maPN && maNL == other.maNL;
    }

    @Override
    public int hashCode() {
        return 31 * maPN + maNL;
    }
}