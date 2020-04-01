package com.codepaper.booksapp.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;

import com.codepaper.booksapp.Database.ModelDB.User;
import com.codepaper.booksapp.Fragments.BooksFragment;
import com.codepaper.booksapp.Fragments.CartFragment;
import com.codepaper.booksapp.Fragments.DiscoverFragment;
import com.codepaper.booksapp.Fragments.ProfileFragment;
import com.codepaper.booksapp.R;
import com.codepaper.booksapp.Storage.SharedPrefManager;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {


    BottomNavigationView bottomNavigationView;
    BooksFragment booksFragment = new BooksFragment();
    CartFragment cartFragment = new CartFragment();
    ProfileFragment profileFragment = new ProfileFragment();
    DiscoverFragment discoverFragment = new DiscoverFragment();
    User user;
    SharedPrefManager sharedPrefManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }

    private void initView() {
        bottomNavigationView = findViewById(R.id.home_bottomNavigation);
        user = SharedPrefManager.getInstance(MainActivity.this).getUser();
        implementView();
    }

    private void implementView() {

        if(user.getFname()==null)
         {
             bottomNavigationView.getMenu().removeItem(R.id.nav_profile);
         }
        else
        {
            bottomNavigationView.getMenu().removeItem(R.id.nav_login);
        }

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch (menuItem.getItemId()){
                    case R.id.nav_home:
                        getSupportFragmentManager().beginTransaction().replace(R.id.frameContainer,booksFragment).commit();
                        return true;
                    case R.id.nav_cart:
                        getSupportFragmentManager().beginTransaction().replace(R.id.frameContainer,cartFragment).commit();
                        return true;
                    case R.id.nav_login:
                        startActivity(new Intent(MainActivity.this,LoginActivity.class));
                        return true;
                    case R.id.nav_profile:
                        getSupportFragmentManager().beginTransaction().replace(R.id.frameContainer,profileFragment).commit();
                        return true;
                    case R.id.nav_filters:
                        getSupportFragmentManager().beginTransaction().replace(R.id.frameContainer,discoverFragment).commit();
                        return true;
                }
                return false;
            }
        });

        bottomNavigationView.setSelectedItemId(R.id.nav_home);
    }
}
