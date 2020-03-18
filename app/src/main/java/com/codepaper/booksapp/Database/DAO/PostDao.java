package com.codepaper.booksapp.Database.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.codepaper.booksapp.Database.ModelDB.Post;

@Dao
public interface PostDao {

    @Query("SELECT * FROM Post")
    Post getPost();

    @Insert
    void insertPost(Post post);

    @Delete
    void deletePost(Post post);

    @Update
    void updatePost(Post post);
}
