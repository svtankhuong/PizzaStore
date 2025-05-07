package DTO;

import java.sql.Date;
import java.math.BigDecimal;

public class ThongKeDoanhThuDTO {
    private Date tuNgay;
    private Date denNgay;
    private BigDecimal tongThu;
    private int maNV;
    private String tenNV;
    private int maKH;
    private String tenKH;
    private int quy;
    private int nam;

    // Constructor mặc định
    public ThongKeDoanhThuDTO() {}

    // Constructor đầy đủ
    public ThongKeDoanhThuDTO(Date tuNgay, Date denNgay, BigDecimal tongThu,
                              int maNV, String tenNV, int maKH, String tenKH, int quy, int nam) {
        this.tuNgay = tuNgay;
        this.denNgay = denNgay;
        this.tongThu = tongThu;
        this.maNV = maNV;
        this.tenNV = tenNV;
        this.maKH = maKH;
        this.tenKH = tenKH;
        this.quy = quy;
        this.nam = nam;
    }

    // Getters và Setters
    public Date getTuNgay() { return tuNgay; }
    public void setTuNgay(Date tuNgay) { this.tuNgay = tuNgay; }

    public Date getDenNgay() { return denNgay; }
    public void setDenNgay(Date denNgay) { this.denNgay = denNgay; }

    public BigDecimal getTongThu() { return tongThu; }
    public void setTongThu(BigDecimal tongThu) { this.tongThu = tongThu; }

    public int getMaNV() { return maNV; }
    public void setMaNV(int maNV) { this.maNV = maNV; }

    public String getTenNV() { return tenNV; }
    public void setTenNV(String tenNV) { this.tenNV = tenNV; }

    public int getMaKH() { return maKH; }
    public void setMaKH(int maKH) { this.maKH = maKH; }

    public String getTenKH() { return tenKH; }
    public void setTenKH(String tenKH) { this.tenKH = tenKH; }

    public int getQuy() { return quy; }
    public void setQuy(int quy) { this.quy = quy; }

    public int getNam() { return nam; }
    public void setNam(int nam) { this.nam = nam; }
}