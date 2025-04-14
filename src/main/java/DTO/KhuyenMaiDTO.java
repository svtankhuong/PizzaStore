package DTO;

import java.time.LocalDate;

public class KhuyenMaiDTO {
    final private int MaKM;
    private String TenKM;
    private LocalDate NgayBatDau;
    private LocalDate NgayKetThuc;

    public KhuyenMaiDTO(int idkm, String tenkm, LocalDate startDate, LocalDate endDate) {
        this.MaKM = idkm;
        this.TenKM = tenkm;
        this.NgayBatDau = startDate;
        this.NgayKetThuc = endDate;
    }

    public int getMaKM() {
        return MaKM;
    }

    public String getTenKM() {
        return TenKM;
    }

    public void setTenKM(String tenKM) {
        TenKM = tenKM;
    }

    public LocalDate getNgayBatDau() {
        return NgayBatDau;
    }

    public void setNgayBatDau(LocalDate ngayBatDau) {
        NgayBatDau = ngayBatDau;
    }

    public LocalDate getNgayKetThuc() {
        return NgayKetThuc;
    }

    public void setNgayKetThuc(LocalDate ngayKetThuc) {
        NgayKetThuc = ngayKetThuc;
    }
}

