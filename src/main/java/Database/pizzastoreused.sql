-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: May 08, 2025 at 08:41 AM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.2.12

create DATABASE pizzastore;
use pizzastore;
SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `pizzastore`
--

-- --------------------------------------------------------

--
-- Table structure for table `chitiethoadon`
--

CREATE TABLE `chitiethoadon` (
  `MaCTHD` int(11) NOT NULL,
  `MaHD` int(11) DEFAULT NULL,
  `MaSP` int(11) DEFAULT NULL,
  `MaNV` int(11) DEFAULT NULL,
  `MaCTKM` int(11) DEFAULT NULL,
  `SoLuong` int(11) DEFAULT NULL,
  `DonGia` decimal(10,0) DEFAULT NULL,
  `ThanhTien` decimal(10,0) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `chitiethoadon`
--

INSERT INTO `chitiethoadon` (`MaCTHD`, `MaHD`, `MaSP`, `MaNV`, `MaCTKM`, `SoLuong`, `DonGia`, `ThanhTien`) VALUES
(1, 1, 1, 1, 1, 3, 35000, 105000),
(2, 1, 2, 1, 1, 2, 20000, 40000),
(3, 2, 3, 2, 2, 1, 150000, 150000),
(4, 3, 4, 3, NULL, 2, 30000, 60000);

-- --------------------------------------------------------

--
-- Table structure for table `chitietkhuyenmai`
--

CREATE TABLE `chitietkhuyenmai` (
  `MACTKM` int(11) NOT NULL,
  `MaKM` int(11) NOT NULL,
  `PhanTramGiam` decimal(10,0) NOT NULL,
  `Toithieugiam` decimal(10,0) NOT NULL,
  `TenCTKM` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `chitietkhuyenmai`
--

INSERT INTO `chitietkhuyenmai` (`MACTKM`, `MaKM`, `PhanTramGiam`, `Toithieugiam`, `TenCTKM`) VALUES
(1, 1, 10, 100000, 'Tết giảm 10% cho hóa đơn từ 100k'),
(2, 2, 15, 150000, 'Hè giảm 15% cho hóa đơn từ 150k'),
(3, 3, 20, 200000, 'Combo giảm 20% hóa đơn combo'),
(4, 1, 20, 200000, 'Tết giảm 20% cho hóa đơn từ 200k');

-- --------------------------------------------------------

--
-- Table structure for table `chitietphieunhap`
--

CREATE TABLE `chitietphieunhap` (
  `MACTPN` int(11) NOT NULL,
  `MaPN` int(11) DEFAULT NULL,
  `MaNL` int(11) DEFAULT NULL,
  `SoLuong` int(11) DEFAULT NULL,
  `DonGia` decimal(10,0) DEFAULT NULL,
  `ThanhTien` decimal(10,0) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `chitietphieunhap`
--

INSERT INTO `chitietphieunhap` (`MACTPN`, `MaPN`, `MaNL`, `SoLuong`, `DonGia`, `ThanhTien`) VALUES
(1, 1, 1, 20, 50000, 1000000),
(2, 1, 2, 25, 40000, 1000000),
(3, 2, 3, 50, 15000, 750000),
(4, 3, 4, 30, 30000, 900000);

-- --------------------------------------------------------

--
-- Table structure for table `hoadon`
--

CREATE TABLE `hoadon` (
  `MaHD` int(11) NOT NULL,
  `MaNV` int(11) DEFAULT NULL,
  `MaKH` int(11) DEFAULT NULL,
  `MaCTKM` int(11) DEFAULT NULL,
  `NgayLap` date DEFAULT NULL,
  `SoTienGiam` decimal(10,0) DEFAULT NULL,
  `TongTien` decimal(10,0) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `hoadon`
--

INSERT INTO `hoadon` (`MaHD`, `MaNV`, `MaKH`, `MaCTKM`, `NgayLap`, `SoTienGiam`, `TongTien`) VALUES
(1, 1, 1, 1, '2025-01-05', 50000, 450000),
(2, 2, 2, 2, '2025-06-10', 60000, 400000),
(3, 3, 3, NULL, '2025-04-15', 0, 200000);

-- --------------------------------------------------------

--
-- Table structure for table `khachhang`
--

CREATE TABLE `khachhang` (
  `MaKH` int(11) NOT NULL,
  `HoLot` varchar(255) DEFAULT NULL,
  `Ten` varchar(255) DEFAULT NULL,
  `SDT` varchar(10) DEFAULT NULL,
  `DiaChi` varchar(255) NOT NULL,
  `TongChiTieu` decimal(10,0) DEFAULT 0,
  `isDelete` tinyint(1) DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `khachhang`
--

INSERT INTO `khachhang` (`MaKH`, `HoLot`, `Ten`, `SDT`, `DiaChi`, `TongChiTieu`, `isDelete`) VALUES
(1, 'Tran Thi', 'Lan', '0901234567', 'Hà Nội', 500000, 0),
(2, 'Nguyen Van', 'Minh', '0912345678', 'Hồ Chí Minh', 1200000, 0),
(3, 'Pham Thi', 'Thuy', '0934567890', 'Đà Nẵng', 800000, 0),
(4, 'Le Van', 'Tung', '0978123456', 'Huế', 250000, 0),
(5, 'Hoang Thi', 'Dao', '0987654321', 'Cần Thơ', 300000, 0),
(6, 'Hoang', 'Vu', '0964603676', '99 An Dương Vương', 0, 0),
(7, 'Le Hoang', 'Cuong', '0923456789', '789 Tran Hung Dao, Q1, TP.HCM', 0, 0),
(8, 'Hoang Thi', 'Em', '0945678901', '202 Nguyen Thi Minh Khai, Q3, TP.HCM', 0, 0),
(9, 'Hoang', 'Vu', '0964603678', '99 Trần Phú', 0, 0),
(10, 'Nguyen Tran', 'Van', '0981270678', '216 Mai Chí Thọ', 0, 0),
(11, 'Nguyen', 'A', '0981270676', '123 Nguyễn Ái Quốc', 0, 0),
(12, 'Nguyen Van', 'Tai', '0964604676', '98 Nguyễn Văn Cừ', 0, 0);

-- --------------------------------------------------------

--
-- Table structure for table `khuyenmai`
--

CREATE TABLE `khuyenmai` (
  `MaKM` int(11) NOT NULL,
  `TenKM` varchar(255) NOT NULL,
  `NgayBatDau` date NOT NULL,
  `NgayKetThuc` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `khuyenmai`
--

INSERT INTO `khuyenmai` (`MaKM`, `TenKM`, `NgayBatDau`, `NgayKetThuc`) VALUES
(1, 'Giảm giá Tết', '2025-01-01', '2025-01-10'),
(2, 'Ưu đãi hè', '2025-06-01', '2025-06-15'),
(3, 'Combo đặc biệt', '2025-04-01', '2025-04-30');

-- --------------------------------------------------------

--
-- Table structure for table `nguyenlieu`
--

CREATE TABLE `nguyenlieu` (
  `MaNL` int(11) NOT NULL,
  `SoLuong` int(11) DEFAULT NULL,
  `DVTinh` varchar(50) DEFAULT NULL,
  `DonGia` decimal(10,0) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `nguyenlieu`
--

INSERT INTO `nguyenlieu` (`MaNL`, `SoLuong`, `DVTinh`, `DonGia`) VALUES
(1, 100, 'Kg', 50000),
(2, 50, 'Kg', 40000),
(3, 200, 'Lít', 15000),
(4, 80, 'Kg', 30000),
(5, 120, 'Kg', 20000);

-- --------------------------------------------------------

--
-- Table structure for table `nhacungcap`
--

CREATE TABLE `nhacungcap` (
  `MaNCC` int(11) NOT NULL,
  `Ten` varchar(255) DEFAULT NULL,
  `DiaChi` varchar(255) DEFAULT NULL,
  `SDT` varchar(10) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `nhacungcap`
--

INSERT INTO `nhacungcap` (`MaNCC`, `Ten`, `DiaChi`, `SDT`) VALUES
(1, 'CTY Thực phẩm A', 'TP.HCM', '0909999999'),
(2, 'CTY Đồ uống B', 'Hà Nội', '0918888888'),
(3, 'CTY Hải sản C', 'Đà Nẵng', '0927777777'),
(4, 'CTY Rau củ D', 'Đà Lạt', '0936666666');

-- --------------------------------------------------------

--
-- Table structure for table `nhanvien`
--

CREATE TABLE `nhanvien` (
  `MaNV` int(11) NOT NULL,
  `HoLot` varchar(255) DEFAULT NULL,
  `Ten` varchar(255) DEFAULT NULL,
  `GioiTinh` varchar(255) DEFAULT NULL,
  `NgaySinh` date DEFAULT NULL,
  `isDelete` tinyint(1) DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `nhanvien`
--

INSERT INTO `nhanvien` (`MaNV`, `HoLot`, `Ten`, `GioiTinh`, `NgaySinh`, `isDelete`) VALUES
(1, 'Nguyen Van', 'An', 'Nam', '2005-04-13', 0),
(2, 'Le Thi', 'Bich', 'Nữ', '2005-04-01', 0),
(3, 'Tran Van', 'Tuan', 'Nam', '2004-10-13', 0),
(4, 'Pham Thi', 'Dao', 'Nữ', '2007-10-13', 0),
(5, 'Vo Hoang', 'Tuan', 'Nam', '2003-10-13', 0),
(6, 'Nguyen', 'A', 'Nam', '2005-04-05', 0),
(7, 'Tran', 'Van A', 'Nam', '2005-02-01', 0),
(8, 'Hoang Van', 'Vu', 'Nam', '2005-02-01', 0),
(9, 'Hoang', 'Vu', 'Nam', '2005-02-01', 0),
(10, 'hoang van', 'Teo', 'Nam', '2025-04-08', 0),
(11, 'Hoang Van', 'Nguyen', 'Nam', '2002-04-11', 0),
(12, 'Tran Van', 'Nam', 'Nam', '2006-06-06', 0),
(13, 'Tran Van', 'Cuong', 'Nam', '2004-10-13', 1),
(14, 'Tran Van', 'Nam', 'Nam', '2006-06-06', 1),
(15, 'Tran', 'Van A', 'Nam', '2005-02-01', 1),
(16, 'Pham Thi', 'Dao', 'Nữ', '2007-10-13', 1),
(17, 'Nguyễn', 'A', 'Nam', '2003-01-13', 0),
(18, 'Trần', 'B', 'Nữ', '1985-05-15', 0),
(19, 'Lê', 'C', 'Nam', '1988-08-20', 0),
(20, 'Phan', 'D', 'Nữ', '2001-01-04', 0);

-- --------------------------------------------------------

--
-- Table structure for table `phieunhap`
--

CREATE TABLE `phieunhap` (
  `MaPN` int(11) NOT NULL,
  `MaNCC` int(11) NOT NULL,
  `MaNV` int(11) NOT NULL,
  `TongTien` decimal(10,0) NOT NULL,
  `NgayLap` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `phieunhap`
--

INSERT INTO `phieunhap` (`MaPN`, `MaNCC`, `MaNV`, `TongTien`, `NgayLap`) VALUES
(1, 1, 1, 2000000, '2025-03-10'),
(2, 2, 2, 1000000, '2025-03-15'),
(3, 3, 3, 1500000, '2025-03-20');

-- --------------------------------------------------------

--
-- Table structure for table `quyen`
--

CREATE TABLE `quyen` (
  `MaQuyen` int(11) NOT NULL,
  `TenQuyen` varchar(255) NOT NULL,
  `QLbanhang` tinyint(1) NOT NULL,
  `QLkhuyenmai` tinyint(1) NOT NULL,
  `QLsanpham` tinyint(1) NOT NULL,
  `QLNhanVien` tinyint(1) NOT NULL,
  `QLkhachhang` tinyint(1) NOT NULL,
  `QLnhaphang` tinyint(1) NOT NULL,
  `ThongKe` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `quyen`
--

INSERT INTO `quyen` (`MaQuyen`, `TenQuyen`, `QLbanhang`, `QLkhuyenmai`, `QLsanpham`, `QLNhanVien`, `QLkhachhang`, `QLnhaphang`, `ThongKe`) VALUES
(1, 'Admin', 1, 1, 1, 1, 1, 1, 1),
(2, 'Thu ngân', 1, 0, 0, 0, 1, 0, 1),
(3, 'Bếp trưởng', 0, 0, 1, 0, 0, 1, 0),
(4, 'Nhân viên', 1, 0, 0, 0, 0, 0, 0);

-- --------------------------------------------------------

--
-- Table structure for table `sanpham`
--

CREATE TABLE `sanpham` (
  `MaSP` int(11) NOT NULL,
  `Ten` varchar(255) DEFAULT NULL,
  `DvTinh` varchar(255) DEFAULT NULL,
  `DonGia` decimal(10,0) DEFAULT NULL,
  `SoLuong` int(11) DEFAULT NULL,
  `Loai` varchar(255) DEFAULT NULL,
  `Anh` varchar(255) DEFAULT 'D:PizzaStoresrcmain\resourcesSanPhamdefault.png'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `sanpham`
--

INSERT INTO `sanpham` (`MaSP`, `Ten`, `DvTinh`, `DonGia`, `SoLuong`, `Loai`, `Anh`) VALUES
(1, 'Pizza Hải Sản Dứa Kem', 'Cái', 300000, 201, '1', 'src/main/resources/SanPham/pizza0.png'),
(2, 'Pizza Hải Sản Bông Cải Xanh', 'Cái', 800000, 100, 'Món chính', 'D:\\PizzaStore\\src\\main\\resources\\SanPham\\pizza1.png'),
(3, 'Pizza Tôm Dứa Sốt Kem Tropical', 'Cái', 450000, 80, 'Món chính', 'D:\\PizzaStore\\src\\main\\resources\\SanPham\\pizza2.png'),
(4, 'Pepsi', 'Lon', 20000, 40, 'Đồ uống', 'D:\\PizzaStore\\src\\main\\resources\\SanPham\\douong0.jpeg'),
(5, 'Pepsi', 'Chai', 15000, 60, 'Đồ uống', 'D:\\PizzaStore\\src\\main\\resources\\SanPham\\douong1.jpeg'),
(6, 'Pizza Hải Sản Dứa Kem', 'Cái', 300000, 201, '1', 'src/main/resources/SanPham/pizza0.png'),
(7, 'Pizza Hai San Thuong', 'Cai', 100000, 1, '1', 'C:\\Users\\somna\\OneDrive\\Hình ảnh\\DongDieuKhien.png'),
(8, 'Pizza Hải Sản Dứa Kem', 'Cái', 300000, 201, '1', 'src/main/resources/SanPham/pizza0.png');

-- --------------------------------------------------------

--
-- Table structure for table `taikhoan`
--

CREATE TABLE `taikhoan` (
  `TenDangNhap` varchar(255) NOT NULL,
  `MatKhau` varchar(255) NOT NULL,
  `MaQuyen` int(11) DEFAULT NULL,
  `MaNV` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `taikhoan`
--

INSERT INTO `taikhoan` (`TenDangNhap`, `MatKhau`, `MaQuyen`, `MaNV`) VALUES
('admin123', 'admin123', 1, 1),
('hoangvanvu', '1234567', 2, 8),
('provip123', '123456', 1, 2),
('taikhoen', 'matkhau1', 3, 3),
('thungan', 'thungan123', 2, 2);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `chitiethoadon`
--
ALTER TABLE `chitiethoadon`
  ADD PRIMARY KEY (`MaCTHD`),
  ADD KEY `MaHD` (`MaHD`),
  ADD KEY `MaSP` (`MaSP`),
  ADD KEY `MaNV` (`MaNV`),
  ADD KEY `MaCTKM` (`MaCTKM`);

--
-- Indexes for table `chitietkhuyenmai`
--
ALTER TABLE `chitietkhuyenmai`
  ADD PRIMARY KEY (`MACTKM`),
  ADD KEY `MaKM` (`MaKM`);

--
-- Indexes for table `chitietphieunhap`
--
ALTER TABLE `chitietphieunhap`
  ADD PRIMARY KEY (`MACTPN`),
  ADD KEY `MaPN` (`MaPN`),
  ADD KEY `MaNL` (`MaNL`);

--
-- Indexes for table `hoadon`
--
ALTER TABLE `hoadon`
  ADD PRIMARY KEY (`MaHD`),
  ADD KEY `MaNV` (`MaNV`),
  ADD KEY `MaKH` (`MaKH`),
  ADD KEY `MaCTKM` (`MaCTKM`);

--
-- Indexes for table `khachhang`
--
ALTER TABLE `khachhang`
  ADD PRIMARY KEY (`MaKH`);

--
-- Indexes for table `khuyenmai`
--
ALTER TABLE `khuyenmai`
  ADD PRIMARY KEY (`MaKM`);

--
-- Indexes for table `nguyenlieu`
--
ALTER TABLE `nguyenlieu`
  ADD PRIMARY KEY (`MaNL`);

--
-- Indexes for table `nhacungcap`
--
ALTER TABLE `nhacungcap`
  ADD PRIMARY KEY (`MaNCC`);

--
-- Indexes for table `nhanvien`
--
ALTER TABLE `nhanvien`
  ADD PRIMARY KEY (`MaNV`);

--
-- Indexes for table `phieunhap`
--
ALTER TABLE `phieunhap`
  ADD PRIMARY KEY (`MaPN`),
  ADD KEY `MaNCC` (`MaNCC`),
  ADD KEY `MaNV` (`MaNV`);

--
-- Indexes for table `quyen`
--
ALTER TABLE `quyen`
  ADD PRIMARY KEY (`MaQuyen`);

--
-- Indexes for table `sanpham`
--
ALTER TABLE `sanpham`
  ADD PRIMARY KEY (`MaSP`);

--
-- Indexes for table `taikhoan`
--
ALTER TABLE `taikhoan`
  ADD PRIMARY KEY (`TenDangNhap`),
  ADD KEY `MaQuyen` (`MaQuyen`),
  ADD KEY `MaNV` (`MaNV`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `chitiethoadon`
--
ALTER TABLE `chitiethoadon`
  MODIFY `MaCTHD` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `chitietkhuyenmai`
--
ALTER TABLE `chitietkhuyenmai`
  MODIFY `MACTKM` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `chitietphieunhap`
--
ALTER TABLE `chitietphieunhap`
  MODIFY `MACTPN` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `hoadon`
--
ALTER TABLE `hoadon`
  MODIFY `MaHD` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `khachhang`
--
ALTER TABLE `khachhang`
  MODIFY `MaKH` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;

--
-- AUTO_INCREMENT for table `khuyenmai`
--
ALTER TABLE `khuyenmai`
  MODIFY `MaKM` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `nguyenlieu`
--
ALTER TABLE `nguyenlieu`
  MODIFY `MaNL` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `nhacungcap`
--
ALTER TABLE `nhacungcap`
  MODIFY `MaNCC` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `nhanvien`
--
ALTER TABLE `nhanvien`
  MODIFY `MaNV` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=21;

--
-- AUTO_INCREMENT for table `phieunhap`
--
ALTER TABLE `phieunhap`
  MODIFY `MaPN` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `quyen`
--
ALTER TABLE `quyen`
  MODIFY `MaQuyen` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT for table `sanpham`
--
ALTER TABLE `sanpham`
  MODIFY `MaSP` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `chitiethoadon`
--
ALTER TABLE `chitiethoadon`
  ADD CONSTRAINT `chitiethoadon_ibfk_1` FOREIGN KEY (`MaHD`) REFERENCES `hoadon` (`MaHD`),
  ADD CONSTRAINT `chitiethoadon_ibfk_2` FOREIGN KEY (`MaSP`) REFERENCES `sanpham` (`MaSP`),
  ADD CONSTRAINT `chitiethoadon_ibfk_3` FOREIGN KEY (`MaNV`) REFERENCES `nhanvien` (`MaNV`),
  ADD CONSTRAINT `chitiethoadon_ibfk_4` FOREIGN KEY (`MaCTKM`) REFERENCES `chitietkhuyenmai` (`MACTKM`);

--
-- Constraints for table `chitietkhuyenmai`
--
ALTER TABLE `chitietkhuyenmai`
  ADD CONSTRAINT `chitietkhuyenmai_ibfk_1` FOREIGN KEY (`MaKM`) REFERENCES `khuyenmai` (`MaKM`);

--
-- Constraints for table `chitietphieunhap`
--
ALTER TABLE `chitietphieunhap`
  ADD CONSTRAINT `chitietphieunhap_ibfk_1` FOREIGN KEY (`MaPN`) REFERENCES `phieunhap` (`MaPN`),
  ADD CONSTRAINT `chitietphieunhap_ibfk_2` FOREIGN KEY (`MaNL`) REFERENCES `nguyenlieu` (`MaNL`);

--
-- Constraints for table `hoadon`
--
ALTER TABLE `hoadon`
  ADD CONSTRAINT `hoadon_ibfk_1` FOREIGN KEY (`MaNV`) REFERENCES `nhanvien` (`MaNV`),
  ADD CONSTRAINT `hoadon_ibfk_2` FOREIGN KEY (`MaKH`) REFERENCES `khachhang` (`MaKH`),
  ADD CONSTRAINT `hoadon_ibfk_3` FOREIGN KEY (`MaCTKM`) REFERENCES `chitietkhuyenmai` (`MACTKM`);

--
-- Constraints for table `phieunhap`
--
ALTER TABLE `phieunhap`
  ADD CONSTRAINT `phieunhap_ibfk_1` FOREIGN KEY (`MaNCC`) REFERENCES `nhacungcap` (`MaNCC`),
  ADD CONSTRAINT `phieunhap_ibfk_2` FOREIGN KEY (`MaNV`) REFERENCES `nhanvien` (`MaNV`);

--
-- Constraints for table `taikhoan`
--
ALTER TABLE `taikhoan`
  ADD CONSTRAINT `taikhoan_ibfk_1` FOREIGN KEY (`MaQuyen`) REFERENCES `quyen` (`MaQuyen`),
  ADD CONSTRAINT `taikhoan_ibfk_2` FOREIGN KEY (`MaNV`) REFERENCES `nhanvien` (`MaNV`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
