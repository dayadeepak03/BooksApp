package com.codepaper.booksapp.Database.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.codepaper.booksapp.Database.ModelDB.Cart;
import java.util.List;

@Dao
public interface CartDao {

    @Query("SELECT * FROM Cart")
    List<Cart> getCart();

    @Query("SELECT COUNT(*) FROM Cart")
    Cart getCount();

    @Query("DELETE FROM Cart where cart_id=:id")
    Cart deleteCart(int id);

    @Insert
    void insertCart(Cart cart);

    @Delete
    void deleteCart(Cart cart);

    @Update
    void updateCart(Cart cart);
}
