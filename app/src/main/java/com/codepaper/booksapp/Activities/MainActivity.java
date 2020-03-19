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
import com.codepaper.booksapp.Fragments.ProfileFragment;
import com.codepaper.booksapp.R;
import com.codepaper.booksapp.Storage.SharedPrefManager;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {


    BottomNavigationView bottomNavigationView;
    BooksFragment booksFragment = new BooksFragment();
    CartFragment cartFragment = new CartFragment();
    ProfileFragment profileFragment = new ProfileFragment();
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
                        OpenFilterDialog();
                        return true;
                }
                return false;
            }
        });

        bottomNavigationView.setSelectedItemId(R.id.nav_home);
    }

    public void OpenFilterDialog() {

        Spinner spnLocation,spnPrice,spnCondition,spnSortBy;
        Button btnApply;
        ImageView imgClose;
        final Dialog dialog=new Dialog(this);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_filter);
        if (dialog.getWindow()!=null)
        {
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT);
            WindowManager.LayoutParams params=dialog.getWindow().getAttributes();
            params.gravity= Gravity.CENTER_VERTICAL;
        }


        imgClose = dialog.findViewById(R.id.dialog_filter_imgBack);
        btnApply = dialog.findViewById(R.id.dialog_filter_btnApply);
        spnLocation = dialog.findViewById(R.id.dialog_filter_spnLocation);
        spnPrice = dialog.findViewById(R.id.dialog_filter_spnPrice);
        spnCondition = dialog.findViewById(R.id.dialog_filter_spnCondition);
        spnSortBy = dialog.findViewById(R.id.dialog_filter_spnSortBy);

        imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });

        btnApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });

        dialog.show();
    }

}
