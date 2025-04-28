package DTO;

import java.util.Date;

public class NhanVienDTO {
    private int MaNV;
    private String HoLot;
    private String Ten;
    private String GioiTinh;
    private String Ngaysinh;

    public NhanVienDTO() {
    }

    public NhanVienDTO(int MaNV, String HoLot, String Ten, String GioiTinh, String Ngaysinh) {
        this.MaNV = MaNV;
        this.HoLot = HoLot;
        this.Ten = Ten;
        this.GioiTinh = GioiTinh;
        this.Ngaysinh = Ngaysinh;
    }

    public int getMaNV() {
        return MaNV;
    }

    public void setMaNV(int MaNV) {
        this.MaNV = MaNV;
    }

    public String getHoLot() {
        return HoLot;
    }

    public void setHoLot(String HoLot) {
        this.HoLot = HoLot;
    }

    public String getTen() {
        return Ten;
    }

    public void setTen(String Ten) {
        this.Ten = Ten;
    }

    public String getGioiTinh() {
        return GioiTinh;
    }

    public void setGioiTinh(String GioiTinh) {
        this.GioiTinh = GioiTinh;
    }

    public String getNgaysinh() {
        return Ngaysinh;
    }

    public void setNgaysinh(String Ngaysinh) {
        this.Ngaysinh = Ngaysinh;
    }

    @Override
    public String toString() {
        return "NhanVienDTO{" + "MaNV=" + MaNV + ", HoLot=" + HoLot + ", Ten=" + Ten + ", GioiTinh=" + GioiTinh + ", Ngaysinh=" + Ngaysinh + '}';
    }
}