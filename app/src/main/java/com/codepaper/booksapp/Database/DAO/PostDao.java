package com.codepaper.booksapp.Database.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.codepaper.booksapp.Database.ModelDB.Post;

import java.util.List;

@Dao
public interface PostDao {

    @Query("SELECT * FROM Post")
    List<Post> getPost();

    @Query("SELECT * FROM Post where user_id= :id and post_type= :type")
    List<Post> getPostByType(int id, String type);

    @Query("UPDATE Post set status= :status where post_id IN (:ids) ")
    void UpdatePostStatus(List<Integer> ids, String status);

    @Query("UPDATE Post set status= :status where post_id= :id")
    void UpdateExchangePost(int id, String status);

    @Insert
    void insertPost(Post post);

    @Delete
    void deletePost(Post post);

    @Update
    void updatePost(Post post);
}
