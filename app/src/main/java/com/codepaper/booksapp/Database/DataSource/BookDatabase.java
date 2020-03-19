package com.codepaper.booksapp.Database.DataSource;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.codepaper.booksapp.Database.DAO.CartDao;
import com.codepaper.booksapp.Database.DAO.PostDao;
import com.codepaper.booksapp.Database.DAO.UserDao;
import com.codepaper.booksapp.Database.ModelDB.Cart;
import com.codepaper.booksapp.Database.ModelDB.Post;
import com.codepaper.booksapp.Database.ModelDB.User;

@Database(entities = {User.class, Post.class, Cart.class}, version = 1,exportSchema = false)
public abstract class BookDatabase extends RoomDatabase {

    private static BookDatabase instance;

    public abstract UserDao getUserDao();
    public abstract PostDao getPostDao();
    public abstract CartDao getCartDao();

    public static synchronized BookDatabase getInstance(Context context){
        if(instance==null){
            //If instance is null that's mean database is not created and create new database
            instance = Room.databaseBuilder(context.getApplicationContext(),BookDatabase.class,"book_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallBack)
                    .build();
        }

        return instance;
    }

    private static RoomDatabase.Callback roomCallBack = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
        }
    };
}
