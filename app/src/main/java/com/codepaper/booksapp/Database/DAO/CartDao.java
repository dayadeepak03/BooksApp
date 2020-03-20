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

    @Query("SELECT * from Cart where user_id=:id")
    List<Cart> getCart(int id);

    @Query("SELECT COUNT(*) FROM Cart where user_id=:id")
    int CountItem(int id);

    @Insert
    void insertCart(Cart cart);

    @Delete
    void deleteCartItem(Cart...cart);

    @Update
    void updateCart(Cart cart);
}
