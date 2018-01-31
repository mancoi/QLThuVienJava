/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package QLThuVien;

/**
 *
 * @author Doctor
 */
public class QueryHelper {

    private QueryHelper() {
    }

    public static String selectAllBooks() {
        return "SELECT MaSach, TenSach, TacGia, TenTheLoai, TenNXB, NamXuatBan, SoLuong"
                + " FROM Sach, Sach_TheLoai, NhaXuatBan"
                + " WHERE Sach.MaTheLoai = Sach_TheLoai.MaTheLoai"
                + " AND   Sach.MaNXB = NhaXuatBan.MaNXB";
    }

    public static String searchBookById(int id) {
        return String.format(
                "SELECT MaSach, TenSach, TacGia, TenTheLoai, TenNXB, NamXuatBan, SoLuong"
                + " FROM Sach, Sach_TheLoai, NhaXuatBan"
                + " WHERE Sach.MaTheLoai = Sach_TheLoai.MaTheLoai"
                + " AND Sach.MaNXB = NhaXuatBan.MaNXB"
                + " AND Sach.MaSach = %d", id);
    }

    public static String searchBook(String[] citeria) {
        return String.format(
                "SELECT MaSach, TenSach, TacGia, TenTheLoai, TenNXB, NamXuatBan, SoLuong"
                + " FROM Sach, Sach_TheLoai, NhaXuatBan"
                + " WHERE Sach.MaTheLoai = Sach_TheLoai.MaTheLoai"
                + " AND Sach.MaNXB = NhaXuatBan.MaNXB"
                + " AND (Sach.TacGia = %s"
                + "	 OR Sach.MaTheLoai = %s"
                + "	 OR Sach.MaNXB = %s"
                + "	 OR Sach.NamXuatBan = %s)", citeria[0], citeria[1], citeria[2], citeria[3]);
    }
}
