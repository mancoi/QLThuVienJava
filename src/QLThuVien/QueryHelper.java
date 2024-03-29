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
        return "SELECT MaSach, TenSach, TacGia, TenTheLoai, TenNXB, NamXuatBan"
                + " FROM Sach, Sach_TheLoai, NhaXuatBan"
                + " WHERE Sach.MaTheLoai = Sach_TheLoai.MaTheLoai"
                + " AND   Sach.MaNXB = NhaXuatBan.MaNXB";
    }

    public static String searchBookById(int id) {
        return String.format(
                "SELECT MaSach, TenSach, TacGia, TenTheLoai, TenNXB, NamXuatBan"
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

        if (0 == Integer.parseInt(citeria[3]) || citeria[3].isEmpty()) {
            param[3] = IS_NOT_NULL;
        } else {
            param[3] = "LIKE N'%" + citeria[3] + "%'";
        }

        return String.format(
                "SELECT MaSach, TenSach, TacGia, TenTheLoai, TenNXB, NamXuatBan"
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
                + " VALUES ('%s', '%s', 'false')", phoneNum, today);
    }

    public static String addLendBooks(String bks) {
        return String.format("INSERT INTO Sach_SachDaMuon VALUES %s", bks);
    }

    public static String getLendNoteNotReturned(String phoneNum) {
        return String.format(
                "SELECT MaPhieuMuon,"
                + " CONVERT(varchar, [NgayMuon], 103) AS NgayMuon"
                + " FROM KhachHang_MuonSach"
                + " WHERE phoneNumber = '%s' AND DaTra = 'false'", phoneNum);
    }

    public static String getBooksOfLendNote(String lendNoteId) {
        return String.format(
                "SELECT Sach.TenSach"
                + " FROM KhachHang_MuonSach, Sach, Sach_SachDaMuon"
                + " WHERE KhachHang_MuonSach.MaPhieuMuon = Sach_SachDaMuon.MaPhieuMuon"
                + " AND Sach.MaSach = Sach_SachDaMuon.MaSach"
                + " AND KhachHang_MuonSach.MaPhieuMuon = '%s'", lendNoteId);
    }

    public static String getAllLendNote(String phoneNum) {
        return String.format("SELECT"
                + " MaPhieuMuon, phoneNumber"
                + " ,CONVERT(varchar, NgayMuon, 103) AS NgayMuon"
                + " ,CASE  WHEN DaTra = 1 THEN 'TRUE' ELSE 'FALSE' END AS DaTra"
                + " FROM KhachHang_MuonSach"
                + " WHERE phoneNumber = '%s'", phoneNum);
    }

    public static String returnLendNote(String lendNoteId) {
        return String.format("UPDATE KhachHang_MuonSach"
                + " SET DaTra = 'true'"
                + " WHERE MaPhieuMuon = '%s' ", lendNoteId);
    }

    public static String searchUserInAdmin(String id) {

        return String.format(
                "SELECT Ho, Ten FROM Admin"
                + " WHERE username = '%s'", id);

    }

    public static String searchUserInKhachHang(String id) {

        return String.format(
                "SELECT Ho, Ten FROM KhachHang"
                + " WHERE phoneNumber = '%s'", id);

    }

    public static String insertBook(String[] params) {

        return String.format(
                "INSERT INTO Sach VALUES (N'%s',N'%s',N'%s',N'%s',N'%s')", params[0], params[1], params[2], params[3], params[4]);
    }

    public static String updateBook(Book bk) {
        return String.format("UPDATE Sach"
                + " SET [TenSach] = N'%s'"
                + " ,[TacGia] = N'%s'"
                + " ,[MaTheLoai] = %d"
                + " ,[MaNXB] = %d"
                + " ,[NamXuatBan] = N'%s'"
                + " WHERE MaSach = %d", bk.getTitle(), bk.getAuthor(), bk.getGenreId(), bk.getPublisherId(), bk.getYearPublish(), bk.getBookId());
    }

    public static String deleteBook(int bookId) {
        return "DELETE FROM Sach WHERE MaSach = " + bookId;
    }

    //---
    //Query for statistical tab
    //---
    public static String countReader() {
        return "SELECT COUNT(phoneNumber) FROM KhachHang";
    }

    public static String countAdmin() {
        return "SELECT COUNT(username) FROM Admin";
    }

    public static String countLendNote() {
        return "SELECT COUNT(MaPhieuMuon) FROM KhachHang_MuonSach WHERE DaTra = 'false'";
    }

    public static String countBook() {
        return "SELECT COUNT(MaSach) FROM Sach";
    }

    public static String countPublisher() {
        return "SELECT COUNT(MaNXB) FROM NhaXuatBan";
    }

    public static String countGenre() {
        return "SELECT COUNT(MaTheLoai) FROM Sach_TheLoai";
    }

    public static String getListOfYears() {
        return "SELECT DISTINCT NamXuatBan FROM Sach";
    }

    public static String getBooksOfSpecificYear(String year) {
        return String.format(
                "SELECT COUNT(MaSach) FROM Sach WHERE NamXuatBan = '%s'", year);
    }

    public static String selectAllGenre() {
        return "SELECT TenTheLoai FROM Sach_TheLoai";
    }

    public static String selectAllPublisher() {
        return "SELECT TenNXB FROM NhaXuatBan";
    }

    public static String selectAllReader() {
        return "SELECT phoneNUmber, Ho, Ten FROM KhachHang";
    }
}
