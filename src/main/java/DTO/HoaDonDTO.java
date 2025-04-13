package DTO;

import java.time.LocalDate;

public class HoaDonDTO {
    final private int MaHD;
    private int MaNV;
    private int MaKH;
    private Integer MaCTKM;
    final private LocalDate NgayLapHD;
    final private long SoTienGiam;
    private long TongTienHD;

    // Constructor với NgayLapHD là ngày hôm nay
    public HoaDonDTO(int maHD, int maNV, int maKH, Integer maCTKM, long soTienGiam, long tongTienHD) {
        this.MaHD = maHD;
        this.MaNV = maNV;
        this.MaKH = maKH;
        this.MaCTKM = maCTKM;
        this.NgayLapHD = LocalDate.now(); // Gán ngày hôm nay
        this.SoTienGiam = soTienGiam;
        this.TongTienHD = tongTienHD;
    }
    
    public HoaDonDTO(int maHD, int maNV, int maKH, Integer maCTKM, LocalDate dateCreated, Long sotienGiamHD, Long tongTienHD) {
        this.MaHD = maHD;
        this.MaNV = maNV;
        this.MaKH = maKH;
        this.MaCTKM = maCTKM;
        this.NgayLapHD = dateCreated;
        this.SoTienGiam = sotienGiamHD;
        this.TongTienHD = tongTienHD;
    }
    

    // Getter cho MaHD
    public int getMaHD() {
        return MaHD;
    }

    // Getter cho NgayLapHD
    public LocalDate getNgayLapHD() {
        return NgayLapHD;
    }

    // Getter cho SoTienGiam
    public long getSoTienGiam() {
        return SoTienGiam;
    }

    // Getter và Setter cho MaNV
    public int getMaNV() {
        return MaNV;
    }

    public void setMaNV(int maNV) {
        this.MaNV = maNV;
    }

    // Getter và Setter cho MaKH
    public int getMaKH() {
        return MaKH;
    }

    public void setMaKH(int maKH) {
        this.MaKH = maKH;
    }

    // Getter và Setter cho MaCTKM
    public Integer getMaCTKM() {
        return MaCTKM;
    }

    public void setMaCTKM(Integer maCTKM) {
        this.MaCTKM = maCTKM;
    }

    // Getter và Setter cho TongTienHD
    public long getTongTienHD() {
        return TongTienHD;
    }

    public void setTongTienHD(long tongTienHD) {
        this.TongTienHD = tongTienHD;
    }
}
