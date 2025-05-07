package DTO;

import java.sql.Date;
import java.math.BigDecimal;

public class ThongKeDoanhChiDTO {
    private Date tuNgay;
    private Date denNgay;
    private BigDecimal tongChi;
    private int quy;
    private int nam;

    // Constructor mặc định
    public ThongKeDoanhChiDTO() {}


    public ThongKeDoanhChiDTO(Date tuNgay, Date denNgay, BigDecimal tongChi, int quy, int nam) {
        this.tuNgay = tuNgay;
        this.denNgay = denNgay;
        this.tongChi = tongChi;
        this.quy = quy;
        this.nam = nam;
    }

    // Getters và Setters
    public Date getTuNgay() { return tuNgay; }
    public void setTuNgay(Date tuNgay) { this.tuNgay = tuNgay; }

    public Date getDenNgay() { return denNgay; }
    public void setDenNgay(Date denNgay) { this.denNgay = denNgay; }

    public BigDecimal getTongChi() { return tongChi; }
    public void setTongChi(BigDecimal tongChi) { this.tongChi = tongChi; }

    public int getQuy() { return quy; }
    public void setQuy(int quy) { this.quy = quy; }

    public int getNam() { return nam; }
    public void setNam(int nam) { this.nam = nam; }
}