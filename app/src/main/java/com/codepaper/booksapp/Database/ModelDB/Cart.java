package com.codepaper.booksapp.Database.ModelDB;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Cart")
public class Cart {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "cart_id")
    public int cart_id;
    public int post_id;
    public int user_id;
    public String title;
    public String author;
    public double price;
    public String image;

    public Cart(int post_id, int user_id, String title, String author, double price, String image) {
        this.post_id = post_id;
        this.user_id = user_id;
        this.title = title;
        this.author = author;
        this.price = price;
        this.image = image;
    }

    public int getCart_id() {
        return cart_id;
    }

    public void setCart_id(int cart_id) {
        this.cart_id = cart_id;
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
