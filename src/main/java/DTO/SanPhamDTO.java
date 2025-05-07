package DTO;

public class SanPhamDTO {
    final private int MaSP; // bien final khong cho phep thay doi MaSP
    private String Ten;
    private String DvTinh;
    private long DonGia;
    private int SoLuong;
    private String Loai;
    private String Anh;

    public SanPhamDTO() {
        this.MaSP = 0;
    }

    
    public SanPhamDTO(int idSP, String tenSP, String dvtinh, long DonGia, int soluong, String loai, String anh) {
        this.MaSP = idSP;
        this.Ten = tenSP;
        this.DvTinh = dvtinh;
        this.DonGia = DonGia;
        this.SoLuong = soluong;
        this.Loai = loai;
        this.Anh = anh;
    }

    // Getter cho MaSP
    public int getMaSP() {
        return MaSP;
    }

    // Getter và Setter cho TenSP
    public String getTenSP() {
        return Ten;
    }

    public void setTenSP(String tenSP) {
        this.Ten = tenSP;
    }

    // Getter và Setter cho DvTinh
    public String getDvTinh() {
        return DvTinh;
    }

    public void setDvTinh(String dvTinh) {
        this.DvTinh = dvTinh;
    }

    // Getter và Setter cho DonGia
    public long getDonGia() {
        return DonGia;
    }

    public void setDonGia(long donGia) {
        this.DonGia = donGia;
    }

    // Getter và Setter cho SoLuong
    public int getSoLuong() {
        return SoLuong;
    }

    public void setSoLuong(int soLuong) {
        this.SoLuong = soLuong;
    }

    // Getter và Setter cho Loai
    public String getLoai() {
        return Loai;
    }

    public void setLoai(String loai) {
        this.Loai = loai;
    }
    
    public void setAnhSP(String anhsp){
        this.Anh = anhsp;
    }
    
    public String getAnhSP(){
        return Anh;
    }
}

