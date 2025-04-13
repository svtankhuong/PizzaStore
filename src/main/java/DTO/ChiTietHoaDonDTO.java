package DTO;

public class ChiTietHoaDonDTO
{
    final private int MaCTHD;
    private int MaHD;
    private int MaSP;
    private int MaNV;
    private Integer MaCTKM;
    private int SoLuong;
    final private long DonGia;
    private long ThanhTien;
    
    public ChiTietHoaDonDTO(int idCTHD, int idHD, int idSP, int idNV , Integer MaCTKM,int sl, long dongia, long thanhtien){
        this.MaCTHD = idCTHD;
        this.MaHD = idHD;
        this.MaSP = idSP;
        this.MaNV = idNV;
        this.MaCTKM = MaCTKM;
        this.SoLuong = sl;
        this.DonGia = dongia;
        this.ThanhTien = thanhtien;
    }

    // Getters
    public int getMaCTHD() {
        return MaCTHD;
    }

    public int getMaHD() {
        return MaHD;
    }

    public int getMaSP() {
        return MaSP;
    }

    public int getMaNV() {
        return MaNV;
    }

    public Integer getMaCTKM() {
        return MaCTKM;
    }

    public int getSoLuong() {
        return SoLuong;
    }

    public long getDonGia() {
        return DonGia;
    }

    public long getThanhTien() {
        return ThanhTien;
    }

    // Setters
    public void setMaHD(int maHD) {
        this.MaHD = maHD;
    }

    public void setMaSP(int maSP) {
        this.MaSP = maSP;
    }

    public void setMaNV(int maNV) {
        this.MaNV = maNV;
    }

    public void setMaCTKM(Integer maCTKM) {
        this.MaCTKM = maCTKM;
    }

    public void setSoLuong(int soLuong) {
        this.SoLuong = soLuong;
    }

    public void setThanhTien(long thanhTien) {
        this.ThanhTien = thanhTien;
    }
}
