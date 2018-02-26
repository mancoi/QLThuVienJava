/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package QLThuVien;

import Data.Database;
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
    private String publisher;
    private String yearPublish;
    
    public int searchBook() {
        int rows = 0;
        if (-1 != getBookId()) {
                rows = Database.populateTable(QueryHelper.searchBookById(getBookId()));
            } else {
                String[] citeria = {getTitle(), getAuthor(), getGenre(), getYearPublish()};
                rows = Database.populateTable(QueryHelper.searchBook(citeria));
            }
        
        return rows;
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
    public String getYearPublish() {
        return yearPublish;
    }

    /**
     * @param yearPublish the yearPublish to set
     */
    public void setYearPublish(String yearPublish) {
        this.yearPublish = yearPublish;
    }
}
