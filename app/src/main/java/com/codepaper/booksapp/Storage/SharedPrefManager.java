package com.codepaper.booksapp.Storage;

import android.content.Context;
import android.content.SharedPreferences;

import com.codepaper.booksapp.Database.ModelDB.User;

public class SharedPrefManager {

    private static final String SHARED_PREF_NAME = "book_user";
    private static SharedPrefManager mInstance;
    private Context mContext;

    private SharedPrefManager(Context mContext) {
        this.mContext = mContext;
    }

    public static synchronized SharedPrefManager getInstance(Context mContext){
        if(mInstance == null)
        {
            mInstance = new SharedPrefManager(mContext);
        }
        return mInstance;
    }

    public void saveUser(User user){

        SharedPreferences sharedPreferences = mContext.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString("first_name", user.getFname());
        editor.putString("last_name", user.getLname());
        editor.putString("email", user.getEmail());
        editor.putString("phone", user.getPhone());
        editor.putString("address", user.getAddress());
        editor.putString("postal", user.postal_code);
        editor.putString("password", user.password);
        editor.apply();
    }

    public boolean isLoggedIn(){
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        return sharedPreferences.getInt("id", -1) != -1;
    }

    public User getUser(){
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        return new User(
                sharedPreferences.getString("first_name",null),
                sharedPreferences.getString("last_name",null),
                sharedPreferences.getString("email",null),
                sharedPreferences.getString("phone",null),
                sharedPreferences.getString("address",null),
                sharedPreferences.getString("postal",null),
                sharedPreferences.getString("password",null)
        );
    }

    public void clear(){
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.clear();
        editor.apply();
    }
}
