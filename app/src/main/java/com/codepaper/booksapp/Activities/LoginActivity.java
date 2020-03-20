package com.codepaper.booksapp.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.codepaper.booksapp.Database.DAO.UserDao;
import com.codepaper.booksapp.Database.DataSource.BookDatabase;
import com.codepaper.booksapp.Database.ModelDB.User;
import com.codepaper.booksapp.Model.UserResponse;
import com.codepaper.booksapp.R;
import com.codepaper.booksapp.Storage.SharedPrefManager;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class LoginActivity extends AppCompatActivity {

    Button btnLogin;
    TextView btnSignUp,txtForgot;
    UserDao db;
    BookDatabase dataBase;
    EditText edtUName,edtPass;
    String email,password;
    ImageView btnBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
    }

    private void initView() {
        btnSignUp = findViewById(R.id.login_txtSignUp);
        btnLogin = findViewById(R.id.login_btnLogin);
        edtUName = findViewById(R.id.login_edtUName);
        edtPass = findViewById(R.id.login_edtPass);
        txtForgot = findViewById(R.id.login_txtForgot);
        btnBack = findViewById(R.id.login_imgBack);
        implementView();
    }

    private void implementView() {

        dataBase = Room.databaseBuilder(this, BookDatabase.class, "mi-database.db")
                .allowMainThreadQueries()
                .build();

        db = dataBase.getUserDao();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = edtUName.getText().toString().trim();
                password = edtPass.getText().toString().trim();
                if(email.equals(""))
                {
                    edtUName.setError("Username is required");
                    edtUName.requestFocus();
                    return;
                }
                else if(password.equals(""))
                {
                    edtPass.setError("Password is required");
                    edtPass.requestFocus();
                    return;
                }
                else
                {
                    User user = db.getUser(email, password);
                    if (user != null) {
                        SharedPrefManager.getInstance(LoginActivity.this).saveUser(user);
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        UserResponse userResponse = new UserResponse(user.getUser_id());
                        SharedPrefManager.getInstance(LoginActivity.this).saveUserId(userResponse);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    }else{
                        Toast.makeText(LoginActivity.this, "Unregistered user, or incorrect", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
            }
        });

        txtForgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(LoginActivity.this, "Forgot Password!!", Toast.LENGTH_SHORT).show();
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,MainActivity.class));
                finish();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
