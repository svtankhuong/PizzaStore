package DTO;

public class QuyenDTO {
    private int maQuyen;
    private String tenQuyen;
    private boolean QLBanHang;
    private boolean QLKhuyenMai;
    private boolean QLSanPham;
    private boolean QLNhanVien;
    private boolean QLKhachHang;
    private boolean QLNhapHang;
    private boolean ThongKe;
    
    public QuyenDTO() {
    }

    public QuyenDTO(int maQuyen, String tenQuyen, boolean QLBanHang, boolean QLKhuyenMai, boolean QLSanPham, boolean QLNhanVien, boolean QLKhachHang, boolean QLNhapHang, boolean ThongKe) {
        this.maQuyen = maQuyen;
        this.tenQuyen = tenQuyen;
        this.QLBanHang = QLBanHang;
        this.QLKhuyenMai = QLKhuyenMai;
        this.QLSanPham = QLSanPham;
        this.QLNhanVien = QLNhanVien;
        this.QLKhachHang = QLKhachHang;
        this.QLNhapHang = QLNhapHang;
        this.ThongKe = ThongKe;
    }
    public int getMaQuyen() {
        return maQuyen;
    }
    public void setMaQuyen(int maQuyen) {
        this.maQuyen = maQuyen;
    }
    public String getTenQuyen() {
        return tenQuyen;
    }
    public void setTenQuyen(String tenQuyen) {
        this.tenQuyen = tenQuyen;
    }
    public boolean getQLBanHang() {
        return QLBanHang;
    }
    public void setQLBanHang(boolean QLBanHang) {
        this.QLBanHang = QLBanHang;
    }
    public boolean getQLKhuyenMai() {
        return QLKhuyenMai;
    }
    public void setQLKhuyenMai(boolean QLKhuyenMai) {
        this.QLKhuyenMai = QLKhuyenMai;
    }
    public boolean getQLSanPham() {
        return QLSanPham;
    }
    public void setQLSanPham(boolean QLSanPham) {
        this.QLSanPham = QLSanPham;
    }
    public boolean getQLNhanVien() {
        return QLNhanVien;
    }
    public void setQLNhanVien(boolean QLNhanVien) {
        this.QLNhanVien = QLNhanVien;
    }
    public boolean getQLKhachHang() {
        return QLKhachHang;
    }
    public void setQLKhachHang(boolean QLKhachHang) {
        this.QLKhachHang = QLKhachHang;
    }
    public boolean getQLNhapHang() {
        return QLNhapHang;
    }
    public void setQLNhapHang(boolean QLNhapHang) {
        this.QLNhapHang = QLNhapHang;
    }

    public boolean getThongKe() {
        return ThongKe;
    }
    public void setThongKe(boolean ThongKe) {
        this.ThongKe = ThongKe;
    }
    @Override
    public String toString() {
        return "QuyenDTO{" +
                "maQuyen=" + maQuyen +
                ", tenQuyen='" + tenQuyen + '\'' +
                ", QLBanHang=" + QLBanHang +
                ", QLKhuyenMai=" + QLKhuyenMai +
                ", QLSanPham=" + QLSanPham +
                ", QLNhanVien=" + QLNhanVien +
                ", QLKhachHang=" + QLKhachHang +
                ", QLNhapHang=" + QLNhapHang +
                ", ThongKe=" + ThongKe +
                '}';
    }
}
