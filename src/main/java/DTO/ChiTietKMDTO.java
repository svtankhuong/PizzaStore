package DTO;

public class ChiTietKMDTO {
    private int MaKM;
    private long PhanTramGiam;
    private long Toithieugiam;
    private String TenCTKM;

    public ChiTietKMDTO() {
    }

    public ChiTietKMDTO(int MaKM, long phantramgiam, long toithieugiam, String tenctkm) {
        this.MaKM = MaKM;
        this.PhanTramGiam = phantramgiam;
        this.Toithieugiam = toithieugiam;
        this.TenCTKM = tenctkm;
    }

    public int getMaKM() {
        return MaKM;
    }

    public void setMaKM(int maKM) {
        MaKM = maKM;
    }

    public long getPhanTramGiam() {
        return PhanTramGiam;
    }

    public void setPhanTramGiam(long phanTramGiam) {
        PhanTramGiam = phanTramGiam;
    }

    public long getToithieugiam() {
        return Toithieugiam;
    }

    public void setToithieugiam(long toithieugiam) {
        Toithieugiam = toithieugiam;
    }

    public String getTenCTKM() {
        return TenCTKM;
    }

    public void setTenCTKM(String tenCTKM) {
        TenCTKM = tenCTKM;
    }
}
