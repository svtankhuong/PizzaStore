package DTO;

public class KhachHangDTO {
    final private int MaKH;
    private String HoLot;
    private String TenKH;
    private String SDT;
    private String DiaChi;
    private Long TongChiTieu;

    public KhachHangDTO(int idKH, String holot, String ten, String sdt, String diachi, Long tct) {
        this.MaKH = idKH;
        this.HoLot = holot;
        this.TenKH = ten;
        this.SDT = sdt;
        this.DiaChi = diachi;
        this.TongChiTieu = tct;
    }

    public int getMaKH() {
        return MaKH;
    }

    public String getHoLot() {
        return HoLot;
    }

    public void setHoLot(String hoLot) {
        this.HoLot = hoLot;
    }

    public String getTenKH() {
        return TenKH;
    }

    public void setTenKH(String tenKH) {
        this.TenKH = tenKH;
    }

    public String getSDT() {
        return SDT;
    }

    public void setSDT(String sdt) {
        this.SDT = sdt;
    }

    public String getDiaChi() {
        return DiaChi;
    }

    public void setDiaChi(String diaChi) {
        this.DiaChi = diaChi;
    }

    public Long getTongChiTieu() {
        return TongChiTieu;
    }

    public void setTongChiTieu(Long tongChiTieu) {
        this.TongChiTieu = tongChiTieu;
    }
}

