
-- ============================
-- TẠO BẢNG
-- ============================


use pizzastore;

CREATE TABLE NhanVien (
    MaNV INT AUTO_INCREMENT PRIMARY KEY,
    HoLot varchar(255) DEFAULT NULL,
    Ten varchar(255) DEFAULT NULL,
    GioiTinh varchar(255) DEFAULT NULL,
    NgaySinh date DEFAULT NULL,
    isDelete tinyint(1) DEFAULT 0
);

CREATE TABLE Quyen (
    MaQuyen INT AUTO_INCREMENT PRIMARY KEY,
    TenQuyen VARCHAR(255) NOT NULL,
    QLbanhang BOOLEAN NOT NULL,
    QLkhuyenmai BOOLEAN NOT NULL,
    QLsanpham BOOLEAN NOT NULL,
    QLNhanVien BOOLEAN NOT NULL,
    QLkhachhang BOOLEAN NOT NULL,
    QLnhaphang BOOLEAN NOT NULL,
    ThongKe BOOLEAN NOT NULL
);

CREATE TABLE TaiKhoan (
    TenDangNhap VARCHAR(255) PRIMARY KEY NOT NULL,
    MatKhau VARCHAR(255) NOT NULL,
    MaQuyen INT,
    MaNV INT NOT NULL,
    FOREIGN KEY (MaQuyen) REFERENCES Quyen(MaQuyen),
    FOREIGN KEY (MaNV) REFERENCES NhanVien(MaNV)
);

CREATE TABLE KhachHang (
    MaKH INT AUTO_INCREMENT PRIMARY KEY,
    HoLot VARCHAR(255),
    Ten VARCHAR(255),
    SDT VARCHAR(10),
    DiaChi VARCHAR(255) NOT NULL,
    TongChiTieu DECIMAL
);

CREATE TABLE SanPham (
    MaSP INT AUTO_INCREMENT PRIMARY KEY,
    Ten VARCHAR(255),
    DvTinh VARCHAR(255),
    DonGia DECIMAL,
    SoLuong INT,
    Loai VARCHAR(255),
    Anh VARCHAR(255) DEFAULT 'D:\PizzaStore\src\main\resources\SanPham\default.png'
);

CREATE TABLE KhuyenMai (
    MaKM INT AUTO_INCREMENT PRIMARY KEY,
    TenKM VARCHAR(255) NOT NULL,
    NgayBatDau DATE NOT NULL,
    NgayKetThuc DATE NOT NULL
);

CREATE TABLE chitietkhuyenmai (
    MACTKM INT AUTO_INCREMENT PRIMARY KEY,
    MaKM INT NOT NULL,
    PhanTramGiam DECIMAL NOT NULL,
    Toithieugiam DECIMAL NOT NULL,
    TenCTKM VARCHAR(255) NOT NULL,
    FOREIGN KEY (MaKM) REFERENCES KhuyenMai(MaKM)
);

CREATE TABLE HoaDon (
    MaHD INT AUTO_INCREMENT PRIMARY KEY,
    MaNV INT,
    MaKH INT,
    MaCTKM INT,
    NgayLap DATE,
    SoTienGiam DECIMAL,
    TongTien DECIMAL,
    FOREIGN KEY (MaNV) REFERENCES NhanVien(MaNV),
    FOREIGN KEY (MaKH) REFERENCES KhachHang(MaKH),
    FOREIGN KEY (MaCTKM) REFERENCES chitietkhuyenmai(MACTKM)
);

CREATE TABLE chitiethoadon (
    MaCTHD INT AUTO_INCREMENT PRIMARY KEY,
    MaHD INT,
    MaSP INT,
    MaNV INT,
    MaCTKM INT,
    SoLuong INT,
    DonGia DECIMAL,
    ThanhTien DECIMAL,
    FOREIGN KEY (MaHD) REFERENCES HoaDon(MaHD),
    FOREIGN KEY (MaSP) REFERENCES SanPham(MaSP),
    FOREIGN KEY (MaNV) REFERENCES NhanVien(MaNV),
    FOREIGN KEY (MaCTKM) REFERENCES chitietkhuyenmai(MACTKM)
);

CREATE TABLE NguyenLieu (
    MaNL INT AUTO_INCREMENT PRIMARY KEY,
    SoLuong INT,
    DVTinh VARCHAR(50),
    DonGia DECIMAL
);

CREATE TABLE NhaCungCap (
    MaNCC INT AUTO_INCREMENT PRIMARY KEY,
    Ten VARCHAR(255),
    DiaChi VARCHAR(255),
    SDT VARCHAR(10)
);

CREATE TABLE PhieuNhap (
    MaPN INT AUTO_INCREMENT PRIMARY KEY,
    MaNCC INT NOT NULL,
    MaNV INT NOT NULL,
    TongTien DECIMAL NOT NULL,
    NgayLap DATE NOT NULL,
    FOREIGN KEY (MaNCC) REFERENCES NhaCungCap(MaNCC),
    FOREIGN KEY (MaNV) REFERENCES NhanVien(MaNV)
);

CREATE TABLE chitietphieunhap (
    MACTPN INT AUTO_INCREMENT PRIMARY KEY,
    MaPN INT,
    MaNL INT,
    SoLuong INT,
    DonGia DECIMAL,
    ThanhTien DECIMAL,
    FOREIGN KEY (MaPN) REFERENCES PhieuNhap(MaPN),
    FOREIGN KEY (MaNL) REFERENCES NguyenLieu(MaNL)
);

-- ============================
-- DỮ LIỆU MẪU
-- ============================

INSERT INTO NhanVien (HoLot, Ten, GioiTinh, NgaySinh, isDelete) VALUES
('Nguyen Van', 'An', 'Nam', '2005-04-13', 0),
('Le Thi', 'Bich', 'Nữ', '2005-04-01', 0),
('Tran Van', 'Cuong', 'Nam', '2004-10-13', 0),
('Pham Thi', 'Dao', 'Nữ', '2007-10-13', 0),
('Vo Hoang', 'Tuan', 'Nam', '2003-10-13', 0),
('Nguyen', 'A', 'Nam', '2005-04-05', 0),
('Tran', 'Van A', 'Nam', '2005-02-01', 0),
('Hoang Van', 'Vu', 'Nam', '2005-02-01', 1),
('Hoang', 'Vu', 'Nam', '2005-02-01', 0),
('hoang van', 'Teo', 'Nam', '2025-04-08', 0),
('Hoang Van', 'Nguyen', 'Nam', '2002-04-11', 0),
('Tran Van', 'Nam', 'Nam', '2006-06-06', 0),
('Tran Van', 'Cuong', 'Nam', '2004-10-13', 1),
('Tran Van', 'Nam', 'Nam', '2006-06-06', 1),
('Tran', 'Van A', 'Nam', '2005-02-01', 1),
('Pham Thi', 'Dao', 'Nữ', '2007-10-13', 1);

INSERT INTO Quyen (TenQuyen, QLbanhang, QLkhuyenmai, QLsanpham, QLNhanVien, QLkhachhang, QLnhaphang, ThongKe) VALUES
('Admin', 1,1,1,1,1,1,1),
('Thu ngân', 1,0,0,0,1,0,1),
('Bếp trưởng', 0,0,1,0,0,1,0),
('Nhân viên', 1,0,0,0,0,0,0);

INSERT INTO TaiKhoan (TenDangNhap ,MatKhau, MaQuyen, MaNV) VALUES
('admin123', 'admin123', 1, 1),
('hoangvanvu', '1234567', 2, 8),
('provip123', '123456', 1, 2),
('thungan', 'thungan123', 2, 2);

INSERT INTO KhachHang (HoLot, Ten, SDT, DiaChi, TongChiTieu) VALUES
('Tran Thi', 'Lan', '0901234567', 'Hà Nội', 500000),
('Nguyen Van', 'Minh', '0912345678', 'Hồ Chí Minh', 1200000),
('Pham Thi', 'Thuy', '0934567890', 'Đà Nẵng', 800000),
('Le Van', 'Tung', '0978123456', 'Huế', 250000),
('Hoang Thi', 'Dao', '0987654321', 'Cần Thơ', 300000);

INSERT INTO SanPham (Ten, DvTinh, DonGia, SoLuong, Loai, Anh) VALUES
('Pizza Hải Sản Dứa Kem', 'Cái', 300000, 200, 'Món chính', 'D:\\PizzaStore\\src\\main\\resources\\SanPham\\pizza0.png'),
('Pizza Hải Sản Bông Cải Xanh', 'Cái', 800000, 100, 'Món chính', 'D:\\PizzaStore\\src\\main\\resources\\SanPham\\pizza1.png'),
('Pizza Tôm Dứa Sốt Kem Tropical', 'Cái', 450000, 80, 'Món chính', 'D:\\PizzaStore\\src\\main\\resources\\SanPham\\pizza2.png'),
('Pepsi', 'Lon', 20000, 40, 'Đồ uống', 'D:\\PizzaStore\\src\\main\\resources\\SanPham\\douong0.jpeg'),
('Pepsi', 'Chai', 15000, 60, 'Đồ uống', 'D:\\PizzaStore\\src\\main\\resources\\SanPham\\douong1.jpeg');

INSERT INTO KhuyenMai (TenKM, NgayBatDau, NgayKetThuc) VALUES
('Giảm giá Tết', '2025-01-01', '2025-01-10'),
('Ưu đãi hè', '2025-06-01', '2025-06-15'),
('Combo đặc biệt', '2025-04-01', '2025-04-30');

INSERT INTO chitietkhuyenmai (MaKM, PhanTramGiam, Toithieugiam, TenCTKM) VALUES
(1, 10, 100000, 'Tết giảm 10% cho hóa đơn từ 100k'),
(2, 15, 150000, 'Hè giảm 15% cho hóa đơn từ 150k'),
(3, 20, 200000, 'Combo giảm 20% hóa đơn combo');

INSERT INTO HoaDon (MaNV, MaKH, MaCTKM, NgayLap, SoTienGiam, TongTien) VALUES
(1, 1, 1, '2025-01-05', 50000, 450000),
(2, 2, 2, '2025-06-10', 60000, 400000),
(3, 3, NULL, '2025-04-15', 0, 200000);

INSERT INTO chitiethoadon (MaHD, MaSP, MaNV, MaCTKM, SoLuong, DonGia, ThanhTien) VALUES
(1, 1, 1, 1, 3, 35000, 105000),
(1, 2, 1, 1, 2, 20000, 40000),
(2, 3, 2, 2, 1, 150000, 150000),
(3, 4, 3, NULL, 2, 30000, 60000);

INSERT INTO NguyenLieu (SoLuong, DVTinh, DonGia) VALUES
(100, 'Kg', 50000),
(50, 'Kg', 40000),
(200, 'Lít', 15000),
(80, 'Kg', 30000),
(120, 'Kg', 20000);

INSERT INTO NhaCungCap (Ten, DiaChi, SDT) VALUES
('CTY Thực phẩm A', 'TP.HCM', '0909999999'),
('CTY Đồ uống B', 'Hà Nội', '0918888888'),
('CTY Hải sản C', 'Đà Nẵng', '0927777777'),
('CTY Rau củ D', 'Đà Lạt', '0936666666');

INSERT INTO PhieuNhap (MaNCC, MaNV, TongTien, NgayLap) VALUES
(1, 1, 2000000, '2025-03-10'),
(2, 2, 1000000, '2025-03-15'),
(3, 3, 1500000, '2025-03-20');

INSERT INTO chitietphieunhap (MaPN, MaNL, SoLuong, DonGia, ThanhTien) VALUES
(1, 1, 20, 50000, 1000000),
(1, 2, 25, 40000, 1000000),
(2, 3, 50, 15000, 750000),
(3, 4, 30, 30000, 900000);
