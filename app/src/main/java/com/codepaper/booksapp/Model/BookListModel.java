package com.codepaper.booksapp.Model;

public class BookListModel {

    private int imgName;
    private String bookName;
    private String author;
    private String sell;
    private String exchange;

    public BookListModel(int imgName, String bookName, String author, String sell, String exchange) {
        this.imgName = imgName;
        this.bookName = bookName;
        this.author = author;
        this.sell = sell;
        this.exchange = exchange;
    }

    public int getImgName() {
        return imgName;
    }

    public void setImgName(int imgName) {
        this.imgName = imgName;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getSell() {
        return sell;
    }

    public void setSell(String sell) {
        this.sell = sell;
    }

    public String getExchange() {
        return exchange;
    }

    public void setExchange(String exchange) {
        this.exchange = exchange;
    }
}
