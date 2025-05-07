package DTO;

public class KhachHangDTO {
    private int maKH;
    private String hoDem;
    private String ten;
    private String sdt;
    private String diaChi;
    private Long tongChiTieu;

    public KhachHangDTO() {
    }

    public KhachHangDTO(int maKH, String hoDem, String ten, String sdt, String diaChi, Long tongChiTieu) {
        this.maKH = maKH;
        this.hoDem = hoDem;
        this.ten = ten;
        this.sdt = sdt;
        this.diaChi = diaChi;
        this.tongChiTieu = tongChiTieu;
    }

    public int getMaKH() {
        return maKH;
    }

    public void setMaKH(int maKH) {
        this.maKH = maKH;
    }

    public String getHoDem() {
        return hoDem;
    }

    public void setHoDem(String hoDem) {
        this.hoDem = hoDem;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public Long getTongChiTieu() {
        return tongChiTieu;
    }

    public void setTongChiTieu(Long tongChiTieu) {
        this.tongChiTieu = tongChiTieu;
    }
}
