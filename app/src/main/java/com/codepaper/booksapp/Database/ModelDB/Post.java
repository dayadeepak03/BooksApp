package com.codepaper.booksapp.Database.ModelDB;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Post")
public class Post {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "post_id")
    public int post_id;
    public int user_id;
    public String post_type;
    public String title;
    public String author;
    private String edition;
    private String isbn;
    public String condition;
    public double price;
    public String offer_title;
    public String offer_author;
    public String offer_condition;
    public String status;
    public String image;
    public String created_at;

    public Post(int user_id, String post_type, String title, String author, String edition, String isbn, String condition, double price, String offer_title, String offer_author, String offer_condition, String status, String image, String created_at) {
        this.user_id = user_id;
        this.post_type = post_type;
        this.title = title;
        this.author = author;
        this.edition = edition;
        this.isbn = isbn;
        this.condition = condition;
        this.price = price;
        this.offer_title = offer_title;
        this.offer_author = offer_author;
        this.offer_condition = offer_condition;
        this.status = status;
        this.image = image;
        this.created_at = created_at;
    }

    public int getPost_id() {
        return post_id;
    }

    public void setPost_id(int post_id) {
        this.post_id = post_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getPost_type() {
        return post_type;
    }

    public void setPost_type(String post_type) {
        this.post_type = post_type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getEdition() {
        return edition;
    }

    public void setEdition(String edition) {
        this.edition = edition;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getOffer_title() {
        return offer_title;
    }

    public void setOffer_title(String offer_title) {
        this.offer_title = offer_title;
    }

    public String getOffer_author() {
        return offer_author;
    }

    public void setOffer_author(String offer_author) {
        this.offer_author = offer_author;
    }

    public String getOffer_condition() {
        return offer_condition;
    }

    public void setOffer_condition(String offer_condition) {
        this.offer_condition = offer_condition;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }
}
