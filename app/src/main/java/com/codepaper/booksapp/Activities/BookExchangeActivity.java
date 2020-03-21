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
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.codepaper.booksapp.Adapter.BookExchangeAdapter;
import com.codepaper.booksapp.Adapter.BookSellAdapter;
import com.codepaper.booksapp.Database.DAO.PostDao;
import com.codepaper.booksapp.Database.DataSource.BookDatabase;
import com.codepaper.booksapp.Database.ModelDB.Post;
import com.codepaper.booksapp.Model.UserResponse;
import com.codepaper.booksapp.R;
import com.codepaper.booksapp.Storage.SharedPrefManager;
import com.github.ybq.android.spinkit.SpinKitView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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
    String imgName = "No_image";
    BookExchangeAdapter adapter;
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

    public void fillRecyclerView() {

        progressBar.setVisibility(View.VISIBLE);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                postList = db.getPostByType(Userid,"exchange");
                if(postList.size()>0) {
                    Log.d("SIZE", String.valueOf(postList.size()));

                    adapter = new BookExchangeAdapter(BookExchangeActivity.this, postList);
                    recyclerView.setAdapter(adapter);
                    recyclerView.smoothScrollToPosition(0);
                    adapter.notifyDataSetChanged();
                    progressBar.setVisibility(View.GONE);
                }
                else
                {
                    progressBar.setVisibility(View.GONE);
                    txtEmptyView.setVisibility(View.VISIBLE);
                }
            }
        },1000);
    }

    private void OpenAddExchangeDialog() {

        Button btnSave;
        ImageView imgBtnClose;
        final EditText edtTitle,edtAuthor,edtTitle1,edtAuthor1;
        final Spinner spnCondition,spnCondition1;
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
        btnSave = dialog.findViewById(R.id.dialog_post_exchange_btnSave);
        edtTitle = dialog.findViewById(R.id.dialog_post_exchange_txtTitle);
        edtAuthor = dialog.findViewById(R.id.dialog_post_exchange_txtAuthor);
        spnCondition = dialog.findViewById(R.id.dialog_post_exchange_spnCondition);
        edtTitle1 = dialog.findViewById(R.id.dialog_post_exchange_txtTitle1);
        edtAuthor1 = dialog.findViewById(R.id.dialog_post_exchange_txtAuthor1);
        spnCondition1 = dialog.findViewById(R.id.dialog_post_exchange_spnCondition1);

        imgBtnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                curDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
                String postType = "exchange";
                String title = edtTitle.getText().toString().trim();
                String author = edtAuthor.getText().toString().trim();
                String title1 = edtTitle1.getText().toString().trim();
                String author1 = edtAuthor1.getText().toString().trim();
                String condition = spnCondition.getSelectedItem().toString();
                String condition1 = spnCondition1.getSelectedItem().toString();
                double price = 0;

                if(title.equals("")) {
                    edtTitle.setError("Title is Required");
                    edtTitle.requestFocus();
                    return;
                }else if(author.equals("")) {
                    edtAuthor.setError("Author is Required");
                    edtAuthor.requestFocus();
                    return;
                }else if(condition.equals("Select Condition")) {
                    TextView errorText = (TextView) spnCondition.getSelectedView();
                    errorText.setError("");
                    errorText.setTextColor(Color.RED);//just to highlight that this is an error
                    errorText.setText("Please Select Condition");
                    return;
                }else if(title1.equals("")) {
                    edtTitle1.setError("Offering title is Required");
                    edtTitle1.requestFocus();
                        return;
                }else if(author1.equals("")) {
                    edtAuthor1.setError("Offering author is Required");
                    edtAuthor1.requestFocus();
                        return;
                }else if(condition1.equals("Select Condition")) {
                        TextView errorText = (TextView)spnCondition1.getSelectedView();
                        errorText.setError("");
                        errorText.setTextColor(Color.RED);//just to highlight that this is an error
                        errorText.setText("Please Select Condition");
                        return;
                }else{
                    Post post = new Post(Userid,postType,title,author,"null","null",condition,price,title1,author1,condition1,"exchange",imgName,curDate);
                    db.insertPost(post);
                    Toast.makeText(BookExchangeActivity.this, "Post Saved SuccessFully!!", Toast.LENGTH_SHORT).show();
                    fillRecyclerView();
                    dialog.cancel();
                }

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
