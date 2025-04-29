/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTO;

import java.time.LocalDate;

public class ThongTinKMDTO {
    private final int maKM;
    private String tenKM;
    private String tenCTKM;
    private long phanTramGiam;
    private LocalDate ngayKetThuc;

    // Constructor
    public ThongTinKMDTO(int maKM, String tenKM, String tenCTKM, long phanTramGiam, LocalDate ngayKetThuc) {
        this.maKM = maKM;
        this.tenKM = tenKM;
        this.tenCTKM = tenCTKM;
        this.phanTramGiam = phanTramGiam;
        this.ngayKetThuc = ngayKetThuc;
    }

    // Getter
    public int getMaKM() {
        return maKM;
    }

    public String getTenKM() {
        return tenKM;
    }

    public String getTenCTKM() {
        return tenCTKM;
    }

    public long getPhanTramGiam() {
        return phanTramGiam;
    }

    public LocalDate getNgayKetThuc() {
        return ngayKetThuc;
    }

    // Setter
    public void setTenKM(String tenKM) {
        this.tenKM = tenKM;
    }

    public void setTenCTKM(String tenCTKM) {
        this.tenCTKM = tenCTKM;
    }

    public void setPhanTramGiam(int phanTramGiam) {
        this.phanTramGiam = phanTramGiam;
    }

    public void setNgayKetThuc(LocalDate ngayKetThuc) {
        this.ngayKetThuc = ngayKetThuc;
    }
}

