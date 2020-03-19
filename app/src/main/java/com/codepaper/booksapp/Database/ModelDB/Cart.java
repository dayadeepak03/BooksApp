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
    public String cart_status;

    public Cart(int post_id, String cart_status) {
        this.post_id = post_id;
        this.cart_status = cart_status;
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

    public String getCart_status() {
        return cart_status;
    }

    public void setCart_status(String cart_status) {
        this.cart_status = cart_status;
    }
}
