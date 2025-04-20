package DTO;

public class TaiKhoanDTO {
    private String TenDangNhap;
    private String matKhau;
    private int maQuyen;
    private int maNV;

    // Constructor
    public TaiKhoanDTO(String TenDangNhap, String matKhau, int maQuyen, int maNV) {
        this.TenDangNhap = TenDangNhap;
        this.matKhau = matKhau;
        this.maQuyen = maQuyen;
        this.maNV = maNV;
    }

    // Default Constructor
    public TaiKhoanDTO() {
    }

    // Getters and Setters
    public String getTenDangNhap() {
        return TenDangNhap;
    }

    public void setTenDangNhap(String TenDangNhap) {
        this.TenDangNhap = TenDangNhap;
    }

    public String getMatKhau() {
        return matKhau;
    }

    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
    }

    public int getMaQuyen() {
        return maQuyen;
    }

    public void setMaQuyen(int maQuyen) {
        this.maQuyen = maQuyen;
    }

    public int getMaNV() {
        return maNV;
    }

    public void setMaNV(int maNV) {
        this.maNV = maNV;
    }

    @Override
    public String toString() {
        return "TaiKhoanDTO{" +
                "tenDangNhap='" + TenDangNhap + '\'' +
                ", matKhau='" + matKhau + '\'' +
                ", maQuyen=" + maQuyen +
                ", maNV=" + maNV +
                '}';
    }
}