package DTO;

public class GioHangDTO {
    // Các trường dữ liệu
    // "Mã SP", "Tên SP", "Đơn Giá", "Số Lượng", "Thành Tiền"
    private final int maSP; // final: chỉ có getter, được gán trong constructor
    private String tenSP;
    private long donGia;
    private int soLuong;
    // private long thanhTien; // Loại bỏ trường này, thay bằng phương thức tính toán

    // Constructor để khởi tạo đối tượng GioHangDTO
    public GioHangDTO(int maSP, String tenSP, long donGia, int soLuong) {
        this.maSP = maSP;
        this.tenSP = tenSP;
        this.donGia = donGia;
        this.soLuong = soLuong;
        // Không cần tính thành tiền ở đây nữa
    }

    public int getMaSP() {
        return maSP;
    }

    public String getTenSP() {
        return tenSP;
    }

    public long getDonGia() {
        return donGia;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public long getThanhTien() {
        return this.donGia * this.soLuong; // Tính toán trực tiếp
    }

    public void setTenSP(String tenSP) {
        this.tenSP = tenSP;
    }

    public void setDonGia(long donGia) {
        this.donGia = donGia;
        // Cập nhật thành tiền nếu cần thiết ở các lớp sử dụng DTO này,
        // hoặc chỉ cần gọi getThanhTien() khi cần giá trị mới nhất.
    }


    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
         // Cập nhật thành tiền nếu cần thiết ở các lớp sử dụng DTO này,
        // hoặc chỉ cần gọi getThanhTien() khi cần giá trị mới nhất.
    }
}