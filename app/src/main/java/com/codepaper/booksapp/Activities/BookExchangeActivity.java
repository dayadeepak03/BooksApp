package com.codepaper.booksapp.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepaper.booksapp.Database.DAO.PostDao;
import com.codepaper.booksapp.Database.DataSource.BookDatabase;
import com.codepaper.booksapp.Database.ModelDB.Post;
import com.codepaper.booksapp.Model.UserResponse;
import com.codepaper.booksapp.R;
import com.codepaper.booksapp.Storage.SharedPrefManager;
import com.github.ybq.android.spinkit.SpinKitView;

import java.util.List;

public class BookExchangeActivity extends AppCompatActivity {

    Toolbar toolbar;
    ImageView imgAdd;
    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    SwipeRefreshLayout swipeRefreshLayout;
    Handler handler;
    SpinKitView progressBar;
    TextView txtEmptyView;
    private List<Post> postList;
    BookDatabase dataBase;
    PostDao db;
    UserResponse userResponse;
    int Userid;
    String curDate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_exchange);
        initView();
    }

    private void initView() {
        toolbar = findViewById(R.id.book_exchange_toolbar);
        imgAdd = findViewById(R.id.book_exchange_imgAdd);
        recyclerView = findViewById(R.id.book_exchange_recycler);
        swipeRefreshLayout = findViewById(R.id.book_exchange_swipeRefresh);
        progressBar = findViewById(R.id.book_exchange_spinKit);
        txtEmptyView = findViewById(R.id.book_exchange_txtEmptyView);
        layoutManager = new LinearLayoutManager(this);
        implementView();
    }

    private void implementView() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
        getSupportActionBar().setTitle("");

        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        handler = new Handler();
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fillRecyclerView();
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        dataBase = Room.databaseBuilder(this, BookDatabase.class, "mi-database.db")
                .allowMainThreadQueries()
                .build();
        db = dataBase.getPostDao();

        userResponse = SharedPrefManager.getInstance(this).getUserId();
        Userid = userResponse.getUser_id();

        imgAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenAddExchangeDialog();
            }
        });

        fillRecyclerView();
    }

    private void fillRecyclerView() {

    }

    private void OpenAddExchangeDialog() {

        Button btnNext;
        ImageView imgBtnClose;
        final Dialog dialog=new Dialog(BookExchangeActivity.this);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_post_exchange);
        if (dialog.getWindow()!=null)
        {
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT);
            WindowManager.LayoutParams params=dialog.getWindow().getAttributes();
            params.gravity= Gravity.CENTER_VERTICAL;
        }

        imgBtnClose = dialog.findViewById(R.id.dialog_post_exchange_imgClose);
        btnNext = dialog.findViewById(R.id.dialog_post_exchange_btnSave);
        imgBtnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dialog.cancel();
            }
        });

        dialog.show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}
