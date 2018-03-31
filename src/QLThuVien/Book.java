/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package QLThuVien;

import Data.Database;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;

/**
 *
 * @author Doctor
 */
public class Book {

    private int bookId;
    private String title;
    private String author;
    private String genre;
    private int genreId;
    private String publisher;
    private int publisherId;
    private int yearPublish;

    public int searchBook() {
        int rows = 0;
        if (-1 != getBookId()) {
            rows = Database.populateTable(QueryHelper.searchBookById(getBookId()));
        } else {
            String[] citeria = {title, author, genre, String.valueOf(yearPublish)};
            rows = Database.populateTable(QueryHelper.searchBook(citeria));
        }

        return rows;
    }

    public int getGenreId(String gName) {
        String query = "SELECT MaTheLoai From Sach_TheLoai WHERE TenTheLoai = N'%s'";
        return Database.getGenreOrPublsherId(String.format(query, gName));
    }

    public int getPublisherId(String pName) {
        String query = "SELECT MaNXB From NhaXuatBan WHERE TenNXB = N'%s'";
        return Database.getGenreOrPublsherId(String.format(query, pName));
    }

    public void addBook(Label lbStatus) {

        // If genre is not selected, set it to "Khác"
        if (genre.isEmpty()) {
            genreId = 6;
        } else {
            genreId = getGenreId(genre);
        }

        // Check the publisher to find out ì it already exists or not.
        // If not, ask user to add the publisher.
        checkPublisher(publisher);
        // Check the publisher again to make sure user already added if it 
        // not exists.
        // If the publisher is not exists and user didn't add it, stop.
        int pId = getPublisherId(publisher);
        if (-1 != pId) {
            publisherId = pId;
        } else return;
        
        // If the yearPublish equal to 0, it's mean the user has typed 
        // invalid input.
        if (0 == yearPublish) {
            return;
        }
        
        String[] params = {
                title
                , author
                , String.valueOf(genreId)
                , String.valueOf(publisherId)
                , String.valueOf(yearPublish)};

        int row = Database.insertData(QueryHelper.insertBook(params));
        if (row > 0) {
            lbStatus.setText("Thêm sách " + title + " thành công");
        }
    }

    private void checkPublisher(String publisherName) {
        String query = "SELECT * FROM NhaXuatBan WHERE TenNXB = N'%s'";

        if (!Database.publisherIsExist(String.format(query, publisherName))) {
            Alert alert = new Alert(
                    Alert.AlertType.CONFIRMATION, "Nhà xuất bản này hiện không có trong cơ sở dữ liệu, bạn có muốn thêm?");
            alert.setHeaderText("Chưa tồn tại nhà xuất bản " + publisherName);
            alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    String isrtPublsherQuery = "INSERT INTO NhaXuatBan VALUES ('%s')";
                    Database.insertData(String.format(isrtPublsherQuery, publisherName));
                }
            });
        }

    }

    @Override
    public String toString() {
        return bookId + " - " + title;
    }

    /**
     * @return the bookId
     */
    public int getBookId() {
        return bookId;
    }

    /**
     * @param bookId the bookId to set
     */
    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    /**
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return the author
     */
    public String getAuthor() {
        return author;
    }

    /**
     * @param author the author to set
     */
    public void setAuthor(String author) {
        this.author = author;
    }

    /**
     * @return the genre
     */
    public String getGenre() {
        return genre;
    }

    /**
     * @param genre the genre to set
     */
    public void setGenre(String genre) {
        this.genre = genre;
    }

    /**
     * @return the publisher
     */
    public String getPublisher() {
        return publisher;
    }

    /**
     * @param publisher the publisher to set
     */
    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    /**
     * @return the yearPublish
     */
    public int getYearPublish() {
        return yearPublish;
    }

    /**
     * @param yearPublish the yearPublish to set
     */
    public void setYearPublish(String yearPublish) {

        if (yearPublish.isEmpty()) {
            return;
        }

        try {
            this.yearPublish = Integer.parseInt(yearPublish);
        } catch (NumberFormatException ex) {
            Utils.showAlertWarn("Năm xuất bản sai định dạng.\n" + ex.getMessage());

        }

    }

    /**
     * @return the genreId
     */
    public int getGenreId() {
        return genreId;
    }

    /**
     * @param genreId the genreId to set
     */
    public void setGenreId(int genreId) {
        this.genreId = genreId;
    }

    /**
     * @return the publisherId
     */
    public int getPublisherId() {
        return publisherId;
    }

    /**
     * @param publisherId the publisherId to set
     */
    public void setPublisherId(int publisherId) {
        this.publisherId = publisherId;
    }
}
