USE [master]
GO
/****** Object:  Database [QLThuVien]    Script Date: 04/06/2018 15:57:13 ******/
CREATE DATABASE [QLThuVien] ON  PRIMARY 
( NAME = N'QLThuVien', FILENAME = N'c:\Program Files\Microsoft SQL Server\MSSQL10_50.SQLEXPRESS\MSSQL\DATA\QLThuVien.mdf' , SIZE = 3072KB , MAXSIZE = UNLIMITED, FILEGROWTH = 1024KB )
 LOG ON 
( NAME = N'QLThuVien_log', FILENAME = N'c:\Program Files\Microsoft SQL Server\MSSQL10_50.SQLEXPRESS\MSSQL\DATA\QLThuVien_log.ldf' , SIZE = 1024KB , MAXSIZE = 2048GB , FILEGROWTH = 10%)
GO
ALTER DATABASE [QLThuVien] SET COMPATIBILITY_LEVEL = 100
GO
IF (1 = FULLTEXTSERVICEPROPERTY('IsFullTextInstalled'))
begin
EXEC [QLThuVien].[dbo].[sp_fulltext_database] @action = 'enable'
end
GO
ALTER DATABASE [QLThuVien] SET ANSI_NULL_DEFAULT OFF
GO
ALTER DATABASE [QLThuVien] SET ANSI_NULLS OFF
GO
ALTER DATABASE [QLThuVien] SET ANSI_PADDING OFF
GO
ALTER DATABASE [QLThuVien] SET ANSI_WARNINGS OFF
GO
ALTER DATABASE [QLThuVien] SET ARITHABORT OFF
GO
ALTER DATABASE [QLThuVien] SET AUTO_CLOSE OFF
GO
ALTER DATABASE [QLThuVien] SET AUTO_CREATE_STATISTICS ON
GO
ALTER DATABASE [QLThuVien] SET AUTO_SHRINK OFF
GO
ALTER DATABASE [QLThuVien] SET AUTO_UPDATE_STATISTICS ON
GO
ALTER DATABASE [QLThuVien] SET CURSOR_CLOSE_ON_COMMIT OFF
GO
ALTER DATABASE [QLThuVien] SET CURSOR_DEFAULT  GLOBAL
GO
ALTER DATABASE [QLThuVien] SET CONCAT_NULL_YIELDS_NULL OFF
GO
ALTER DATABASE [QLThuVien] SET NUMERIC_ROUNDABORT OFF
GO
ALTER DATABASE [QLThuVien] SET QUOTED_IDENTIFIER OFF
GO
ALTER DATABASE [QLThuVien] SET RECURSIVE_TRIGGERS OFF
GO
ALTER DATABASE [QLThuVien] SET  DISABLE_BROKER
GO
ALTER DATABASE [QLThuVien] SET AUTO_UPDATE_STATISTICS_ASYNC OFF
GO
ALTER DATABASE [QLThuVien] SET DATE_CORRELATION_OPTIMIZATION OFF
GO
ALTER DATABASE [QLThuVien] SET TRUSTWORTHY OFF
GO
ALTER DATABASE [QLThuVien] SET ALLOW_SNAPSHOT_ISOLATION OFF
GO
ALTER DATABASE [QLThuVien] SET PARAMETERIZATION SIMPLE
GO
ALTER DATABASE [QLThuVien] SET READ_COMMITTED_SNAPSHOT OFF
GO
ALTER DATABASE [QLThuVien] SET HONOR_BROKER_PRIORITY OFF
GO
ALTER DATABASE [QLThuVien] SET  READ_WRITE
GO
ALTER DATABASE [QLThuVien] SET RECOVERY SIMPLE
GO
ALTER DATABASE [QLThuVien] SET  MULTI_USER
GO
ALTER DATABASE [QLThuVien] SET PAGE_VERIFY CHECKSUM
GO
ALTER DATABASE [QLThuVien] SET DB_CHAINING OFF
GO
USE [QLThuVien]
GO
/****** Object:  Table [dbo].[Sach_TheLoai]    Script Date: 04/06/2018 15:57:13 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Sach_TheLoai](
	[MaTheLoai] [int] IDENTITY(1,1) NOT NULL,
	[TenTheLoai] [nvarchar](50) NOT NULL,
 CONSTRAINT [PK_Sach_TheLoai] PRIMARY KEY CLUSTERED 
(
	[MaTheLoai] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET IDENTITY_INSERT [dbo].[Sach_TheLoai] ON
INSERT [dbo].[Sach_TheLoai] ([MaTheLoai], [TenTheLoai]) VALUES (1, N'Văn học')
INSERT [dbo].[Sach_TheLoai] ([MaTheLoai], [TenTheLoai]) VALUES (2, N'Kỹ năng sống')
INSERT [dbo].[Sach_TheLoai] ([MaTheLoai], [TenTheLoai]) VALUES (3, N'Thường thức - Đời sống')
INSERT [dbo].[Sach_TheLoai] ([MaTheLoai], [TenTheLoai]) VALUES (4, N'Sách tham khảo')
INSERT [dbo].[Sach_TheLoai] ([MaTheLoai], [TenTheLoai]) VALUES (5, N'Kinh tế')
INSERT [dbo].[Sach_TheLoai] ([MaTheLoai], [TenTheLoai]) VALUES (6, N'Khác')
SET IDENTITY_INSERT [dbo].[Sach_TheLoai] OFF
/****** Object:  Table [dbo].[NhaXuatBan]    Script Date: 04/06/2018 15:57:13 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[NhaXuatBan](
	[MaNXB] [int] IDENTITY(1,1) NOT NULL,
	[TenNXB] [nvarchar](50) NOT NULL,
 CONSTRAINT [PK_NhaXuatBan] PRIMARY KEY CLUSTERED 
(
	[MaNXB] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET IDENTITY_INSERT [dbo].[NhaXuatBan] ON
INSERT [dbo].[NhaXuatBan] ([MaNXB], [TenNXB]) VALUES (1, N'Hội Nhà Văn')
INSERT [dbo].[NhaXuatBan] ([MaNXB], [TenNXB]) VALUES (2, N'Trẻ')
INSERT [dbo].[NhaXuatBan] ([MaNXB], [TenNXB]) VALUES (3, N'Tổng hợp TP.HCM')
INSERT [dbo].[NhaXuatBan] ([MaNXB], [TenNXB]) VALUES (4, N'Thế Giới')
INSERT [dbo].[NhaXuatBan] ([MaNXB], [TenNXB]) VALUES (5, N'Văn Học')
INSERT [dbo].[NhaXuatBan] ([MaNXB], [TenNXB]) VALUES (6, N'Trí Việt')
SET IDENTITY_INSERT [dbo].[NhaXuatBan] OFF
/****** Object:  Table [dbo].[KhachHang]    Script Date: 04/06/2018 15:57:13 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[KhachHang](
	[phoneNumber] [int] NOT NULL,
	[password] [varchar](50) NOT NULL,
	[Ho] [nvarchar](30) NOT NULL,
	[Ten] [nvarchar](20) NOT NULL,
 CONSTRAINT [PK_KhachHang] PRIMARY KEY CLUSTERED 
(
	[phoneNumber] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
INSERT [dbo].[KhachHang] ([phoneNumber], [password], [Ho], [Ten]) VALUES (123456789, N'123', N'Nguyễn Anh', N'Minh')
INSERT [dbo].[KhachHang] ([phoneNumber], [password], [Ho], [Ten]) VALUES (135792468, N'a12', N'Trần Phương', N'Thảo')
INSERT [dbo].[KhachHang] ([phoneNumber], [password], [Ho], [Ten]) VALUES (147852369, N'13579', N'Hoàng', N'Lễ')
INSERT [dbo].[KhachHang] ([phoneNumber], [password], [Ho], [Ten]) VALUES (246813579, N'abc@', N'Đinh Văn', N'Tín')
/****** Object:  Table [dbo].[Admin]    Script Date: 04/06/2018 15:57:13 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[Admin](
	[username] [varchar](10) NOT NULL,
	[password] [varchar](50) NOT NULL,
	[Ho] [nvarchar](30) NOT NULL,
	[Ten] [nvarchar](20) NOT NULL,
 CONSTRAINT [PK_Admin] PRIMARY KEY CLUSTERED 
(
	[username] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
INSERT [dbo].[Admin] ([username], [password], [Ho], [Ten]) VALUES (N'2222', N'2222', N'2222', N'2222')
INSERT [dbo].[Admin] ([username], [password], [Ho], [Ten]) VALUES (N'admin', N'admin', N'Lê', N'Linh')
INSERT [dbo].[Admin] ([username], [password], [Ho], [Ten]) VALUES (N'admin1', N'admin1', N'Đinh', N'Lễ')
/****** Object:  Table [dbo].[Sach]    Script Date: 04/06/2018 15:57:13 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Sach](
	[MaSach] [int] IDENTITY(1,1) NOT NULL,
	[TenSach] [nvarchar](100) NOT NULL,
	[TacGia] [nvarchar](50) NOT NULL,
	[MaTheLoai] [int] NULL,
	[MaNXB] [int] NULL,
	[NamXuatBan] [int] NULL,
 CONSTRAINT [PK_Sach] PRIMARY KEY CLUSTERED 
(
	[MaSach] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET IDENTITY_INSERT [dbo].[Sach] ON
INSERT [dbo].[Sach] ([MaSach], [TenSach], [TacGia], [MaTheLoai], [MaNXB], [NamXuatBan]) VALUES (2, N'Bắt trẻ đồng xanh', N'J. D. Salinger', 1, 5, 2017)
INSERT [dbo].[Sach] ([MaSach], [TenSach], [TacGia], [MaTheLoai], [MaNXB], [NamXuatBan]) VALUES (3, N'Oliver Phiêu Lưu Ký ', N'Sarah Mcintype, Philip Reeve', 1, 1, 2018)
INSERT [dbo].[Sach] ([MaSach], [TenSach], [TacGia], [MaTheLoai], [MaNXB], [NamXuatBan]) VALUES (4, N'Không Gia Đình', N'Hector Malot', 1, 5, 2014)
INSERT [dbo].[Sach] ([MaSach], [TenSach], [TacGia], [MaTheLoai], [MaNXB], [NamXuatBan]) VALUES (5, N'Trở Về Eden', N'Rosalind Miles', 1, 5, 2015)
INSERT [dbo].[Sach] ([MaSach], [TenSach], [TacGia], [MaTheLoai], [MaNXB], [NamXuatBan]) VALUES (6, N'Đảo Mộng Mơ', N'Nguyễn Nhật Ánh', 1, 2, 2016)
INSERT [dbo].[Sach] ([MaSach], [TenSach], [TacGia], [MaTheLoai], [MaNXB], [NamXuatBan]) VALUES (7, N'Nghĩ Từ Trái Tim', N'BS Đỗ Hồng Ngọc', 3, 3, 2010)
INSERT [dbo].[Sach] ([MaSach], [TenSach], [TacGia], [MaTheLoai], [MaNXB], [NamXuatBan]) VALUES (8, N'Món ngon ngày thường', N'Minh Huyền', 3, 6, 2015)
INSERT [dbo].[Sach] ([MaSach], [TenSach], [TacGia], [MaTheLoai], [MaNXB], [NamXuatBan]) VALUES (9, N'Không Chùn Bước', N'Joseph M. Marshall III', 2, 2, 2016)
INSERT [dbo].[Sach] ([MaSach], [TenSach], [TacGia], [MaTheLoai], [MaNXB], [NamXuatBan]) VALUES (10, N'Bí Quyết Để Đạt Được Ước Mơ', N'Jack Canfield, Mark Victor Hansen', 2, 3, 2016)
INSERT [dbo].[Sach] ([MaSach], [TenSach], [TacGia], [MaTheLoai], [MaNXB], [NamXuatBan]) VALUES (11, N'Chỉ Cần Một Người Hiểu Em Trong Đời', N'Thảo Xù', 1, 4, 2015)
INSERT [dbo].[Sach] ([MaSach], [TenSach], [TacGia], [MaTheLoai], [MaNXB], [NamXuatBan]) VALUES (12, N'Con sẽ là một em bé hạnh phúc', N'World Mommy', 3, 4, 2017)
INSERT [dbo].[Sach] ([MaSach], [TenSach], [TacGia], [MaTheLoai], [MaNXB], [NamXuatBan]) VALUES (13, N'Sổ Tay Chính Tả', N'Hoàng Anh ', 4, 6, 2012)
INSERT [dbo].[Sach] ([MaSach], [TenSach], [TacGia], [MaTheLoai], [MaNXB], [NamXuatBan]) VALUES (14, N'Cẩm Nang Tư Duy Đọc', N'Richard Paul - Linda Elder', 5, 3, 2015)
INSERT [dbo].[Sach] ([MaSach], [TenSach], [TacGia], [MaTheLoai], [MaNXB], [NamXuatBan]) VALUES (15, N'Nhấn Nút Tái Tạo', N'Satya Nadella', 5, 2, 2017)
INSERT [dbo].[Sach] ([MaSach], [TenSach], [TacGia], [MaTheLoai], [MaNXB], [NamXuatBan]) VALUES (39, N'Có một phố vừa đi qua phố', N'Đinh Vũ Hoàng Nguyên', 1, 1, 2013)
INSERT [dbo].[Sach] ([MaSach], [TenSach], [TacGia], [MaTheLoai], [MaNXB], [NamXuatBan]) VALUES (40, N'Gió đầu mùa', N'Thạch Lam', 1, 1, 2014)
SET IDENTITY_INSERT [dbo].[Sach] OFF
/****** Object:  Table [dbo].[KhachHang_MuonSach]    Script Date: 04/06/2018 15:57:13 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[KhachHang_MuonSach](
	[MaPhieuMuon] [int] IDENTITY(1,1) NOT NULL,
	[phoneNumber] [int] NOT NULL,
	[NgayMuon] [date] NOT NULL,
	[DaTra] [bit] NOT NULL,
 CONSTRAINT [PK_KhachHang_MuonSach] PRIMARY KEY CLUSTERED 
(
	[MaPhieuMuon] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET IDENTITY_INSERT [dbo].[KhachHang_MuonSach] ON
INSERT [dbo].[KhachHang_MuonSach] ([MaPhieuMuon], [phoneNumber], [NgayMuon], [DaTra]) VALUES (3, 123456789, CAST(0x421F0B00 AS Date), 1)
INSERT [dbo].[KhachHang_MuonSach] ([MaPhieuMuon], [phoneNumber], [NgayMuon], [DaTra]) VALUES (4, 123456789, CAST(0xA0390B00 AS Date), 1)
INSERT [dbo].[KhachHang_MuonSach] ([MaPhieuMuon], [phoneNumber], [NgayMuon], [DaTra]) VALUES (14, 135792468, CAST(0xF23D0B00 AS Date), 1)
INSERT [dbo].[KhachHang_MuonSach] ([MaPhieuMuon], [phoneNumber], [NgayMuon], [DaTra]) VALUES (15, 147852369, CAST(0xF23D0B00 AS Date), 1)
INSERT [dbo].[KhachHang_MuonSach] ([MaPhieuMuon], [phoneNumber], [NgayMuon], [DaTra]) VALUES (16, 135792468, CAST(0xF73D0B00 AS Date), 1)
INSERT [dbo].[KhachHang_MuonSach] ([MaPhieuMuon], [phoneNumber], [NgayMuon], [DaTra]) VALUES (17, 147852369, CAST(0xFA3D0B00 AS Date), 0)
INSERT [dbo].[KhachHang_MuonSach] ([MaPhieuMuon], [phoneNumber], [NgayMuon], [DaTra]) VALUES (19, 123456789, CAST(0x153E0B00 AS Date), 0)
SET IDENTITY_INSERT [dbo].[KhachHang_MuonSach] OFF
/****** Object:  Table [dbo].[Sach_SachDaMuon]    Script Date: 04/06/2018 15:57:13 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Sach_SachDaMuon](
	[MaSach] [int] NOT NULL,
	[MaPhieuMuon] [int] NOT NULL,
 CONSTRAINT [PK_Sach_SachDaMuon_1] PRIMARY KEY CLUSTERED 
(
	[MaSach] ASC,
	[MaPhieuMuon] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
INSERT [dbo].[Sach_SachDaMuon] ([MaSach], [MaPhieuMuon]) VALUES (2, 3)
INSERT [dbo].[Sach_SachDaMuon] ([MaSach], [MaPhieuMuon]) VALUES (2, 17)
INSERT [dbo].[Sach_SachDaMuon] ([MaSach], [MaPhieuMuon]) VALUES (3, 14)
INSERT [dbo].[Sach_SachDaMuon] ([MaSach], [MaPhieuMuon]) VALUES (3, 15)
INSERT [dbo].[Sach_SachDaMuon] ([MaSach], [MaPhieuMuon]) VALUES (3, 16)
INSERT [dbo].[Sach_SachDaMuon] ([MaSach], [MaPhieuMuon]) VALUES (4, 14)
INSERT [dbo].[Sach_SachDaMuon] ([MaSach], [MaPhieuMuon]) VALUES (5, 3)
INSERT [dbo].[Sach_SachDaMuon] ([MaSach], [MaPhieuMuon]) VALUES (5, 17)
INSERT [dbo].[Sach_SachDaMuon] ([MaSach], [MaPhieuMuon]) VALUES (6, 4)
INSERT [dbo].[Sach_SachDaMuon] ([MaSach], [MaPhieuMuon]) VALUES (6, 14)
INSERT [dbo].[Sach_SachDaMuon] ([MaSach], [MaPhieuMuon]) VALUES (7, 3)
INSERT [dbo].[Sach_SachDaMuon] ([MaSach], [MaPhieuMuon]) VALUES (9, 17)
INSERT [dbo].[Sach_SachDaMuon] ([MaSach], [MaPhieuMuon]) VALUES (10, 3)
INSERT [dbo].[Sach_SachDaMuon] ([MaSach], [MaPhieuMuon]) VALUES (10, 4)
INSERT [dbo].[Sach_SachDaMuon] ([MaSach], [MaPhieuMuon]) VALUES (10, 14)
INSERT [dbo].[Sach_SachDaMuon] ([MaSach], [MaPhieuMuon]) VALUES (10, 16)
INSERT [dbo].[Sach_SachDaMuon] ([MaSach], [MaPhieuMuon]) VALUES (10, 17)
INSERT [dbo].[Sach_SachDaMuon] ([MaSach], [MaPhieuMuon]) VALUES (11, 17)
INSERT [dbo].[Sach_SachDaMuon] ([MaSach], [MaPhieuMuon]) VALUES (12, 17)
INSERT [dbo].[Sach_SachDaMuon] ([MaSach], [MaPhieuMuon]) VALUES (13, 15)
INSERT [dbo].[Sach_SachDaMuon] ([MaSach], [MaPhieuMuon]) VALUES (13, 17)
INSERT [dbo].[Sach_SachDaMuon] ([MaSach], [MaPhieuMuon]) VALUES (14, 16)
INSERT [dbo].[Sach_SachDaMuon] ([MaSach], [MaPhieuMuon]) VALUES (15, 15)
INSERT [dbo].[Sach_SachDaMuon] ([MaSach], [MaPhieuMuon]) VALUES (15, 17)
INSERT [dbo].[Sach_SachDaMuon] ([MaSach], [MaPhieuMuon]) VALUES (39, 19)
INSERT [dbo].[Sach_SachDaMuon] ([MaSach], [MaPhieuMuon]) VALUES (40, 19)
/****** Object:  ForeignKey [FK_Sach_NhaXuatBan1]    Script Date: 04/06/2018 15:57:13 ******/
ALTER TABLE [dbo].[Sach]  WITH CHECK ADD  CONSTRAINT [FK_Sach_NhaXuatBan1] FOREIGN KEY([MaNXB])
REFERENCES [dbo].[NhaXuatBan] ([MaNXB])
GO
ALTER TABLE [dbo].[Sach] CHECK CONSTRAINT [FK_Sach_NhaXuatBan1]
GO
/****** Object:  ForeignKey [FK_Sach_Sach_TheLoai]    Script Date: 04/06/2018 15:57:13 ******/
ALTER TABLE [dbo].[Sach]  WITH CHECK ADD  CONSTRAINT [FK_Sach_Sach_TheLoai] FOREIGN KEY([MaTheLoai])
REFERENCES [dbo].[Sach_TheLoai] ([MaTheLoai])
GO
ALTER TABLE [dbo].[Sach] CHECK CONSTRAINT [FK_Sach_Sach_TheLoai]
GO
/****** Object:  ForeignKey [FK_KhachHang_MuonSach_KhachHang]    Script Date: 04/06/2018 15:57:13 ******/
ALTER TABLE [dbo].[KhachHang_MuonSach]  WITH CHECK ADD  CONSTRAINT [FK_KhachHang_MuonSach_KhachHang] FOREIGN KEY([phoneNumber])
REFERENCES [dbo].[KhachHang] ([phoneNumber])
GO
ALTER TABLE [dbo].[KhachHang_MuonSach] CHECK CONSTRAINT [FK_KhachHang_MuonSach_KhachHang]
GO
/****** Object:  ForeignKey [FK_Sach_SachDaMuon_KhachHang_MuonSach]    Script Date: 04/06/2018 15:57:13 ******/
ALTER TABLE [dbo].[Sach_SachDaMuon]  WITH CHECK ADD  CONSTRAINT [FK_Sach_SachDaMuon_KhachHang_MuonSach] FOREIGN KEY([MaPhieuMuon])
REFERENCES [dbo].[KhachHang_MuonSach] ([MaPhieuMuon])
GO
ALTER TABLE [dbo].[Sach_SachDaMuon] CHECK CONSTRAINT [FK_Sach_SachDaMuon_KhachHang_MuonSach]
GO
/****** Object:  ForeignKey [FK_Sach_SachDaMuon_Sach]    Script Date: 04/06/2018 15:57:13 ******/
ALTER TABLE [dbo].[Sach_SachDaMuon]  WITH CHECK ADD  CONSTRAINT [FK_Sach_SachDaMuon_Sach] FOREIGN KEY([MaSach])
REFERENCES [dbo].[Sach] ([MaSach])
GO
ALTER TABLE [dbo].[Sach_SachDaMuon] CHECK CONSTRAINT [FK_Sach_SachDaMuon_Sach]
GO
