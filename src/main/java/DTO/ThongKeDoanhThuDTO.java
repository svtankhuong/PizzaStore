package DTO;

import java.util.Date;

public class ThongKeDoanhThuDTO {
    private Date tuNgay;
    private Date denNgay;
    private double tongThu;
    private String maNV;
    private String tenNV;
    private String maKH;
    private String tenKH;
    private int quy;
    private int nam;

    // Constructor mặc định
    public ThongKeDoanhThuDTO() {}

    // Constructor đầy đủ
    public ThongKeDoanhThuDTO(Date tuNgay, Date denNgay, double tongThu, 
                              String maNV, String tenNV, String maKH, String tenKH, int quy, int nam) {
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

    public double getTongThu() { return tongThu; }
    public void setTongThu(double tongThu) { this.tongThu = tongThu; }

    public String getMaNV() { return maNV; }
    public void setMaNV(String maNV) { this.maNV = maNV; }

    public String getTenNV() { return tenNV; }
    public void setTenNV(String tenNV) { this.tenNV = tenNV; }

    public String getMaKH() { return maKH; }
    public void setMaKH(String maKH) { this.maKH = maKH; }

    public String getTenKH() { return tenKH; }
    public void setTenKH(String tenKH) { this.tenKH = tenKH; }

    public int getQuy() { return quy; }
    public void setQuy(int quy) { this.quy = quy; }

    public int getNam() { return nam; }
    public void setNam(int nam) { this.nam = nam; }
}