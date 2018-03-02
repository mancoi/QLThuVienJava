/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package QLThuVien;

import java.sql.PreparedStatement;

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

        final String IS_NOT_NULL = "IS NOT NULL";
        //Check if there is a null citeria or not
        String[] param = new String[4];
        param[0] = citeria[0].isEmpty() ? IS_NOT_NULL : "LIKE N'%" + citeria[0] + "%'";
        param[1] = citeria[1].isEmpty() ? IS_NOT_NULL : "LIKE N'%" + citeria[1] + "%'";
        param[2] = citeria[2].isEmpty() ? IS_NOT_NULL : "LIKE N'%" + citeria[2] + "%'";
        param[3] = citeria[3].isEmpty() ? IS_NOT_NULL : "LIKE N'%" + citeria[3] + "%'";

        return String.format(
                "SELECT MaSach, TenSach, TacGia, TenTheLoai, TenNXB, NamXuatBan, SoLuong"
                + " FROM Sach, Sach_TheLoai, NhaXuatBan"
                + " WHERE Sach.MaTheLoai = Sach_TheLoai.MaTheLoai"
                + " AND Sach.MaNXB = NhaXuatBan.MaNXB"
                + " AND Sach.TenSach %s"
                + " AND Sach.TacGia %s"
                + " AND Sach_TheLoai.TenTheLoai %s"
                + " AND Sach.NamXuatBan %s", param[0], param[1], param[2], param[3]);
    }

    public static String addUser(String[] params, String tblToInsert) {
        return String.format(
                "INSERT INTO %s"
                + " VALUES ('%s', '%s', N'%s', N'%s')", tblToInsert, params[0], params[1], params[2], params[3]);
    }
    
    public static String checkBorrower(String phoneNum) {
        return String.format(
                "SELECT * FROM KhachHang_MuonSach"
                + " WHERE phoneNumber = '%s'"
                + " AND	 DaTra = 'false'", phoneNum);
    }
    
    public static String addLendNote(String phoneNum, String today) {
        return String.format(
                "INSERT INTO KhachHang_MuonSach"
                + " VALUES ('%s', '%s', 'false')"
                , phoneNum, today);
    }
    
    public static String addLendBooks(String bks) {
        return String.format("INSERT INTO Sach_SachDaMuon VALUES %s", bks);
    }
}
