package com.codepaper.booksapp.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.room.Room;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.codepaper.booksapp.Database.DAO.UserDao;
import com.codepaper.booksapp.Database.DataSource.BookDatabase;
import com.codepaper.booksapp.Database.ModelDB.User;
import com.codepaper.booksapp.R;

public class RegisterActivity extends AppCompatActivity {

    ImageView btnBack;
    EditText edtFName,edtLName,edtEmail,edtPhone,edtAddress,edtPostal;
    String fName,lName,email,phone,address,postal,pass,confirmPass;
    Button btnSignUp;
    private UserDao userDao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();
    }

    private void initView() {
        btnBack = findViewById(R.id.register_imgBack);
        edtFName = findViewById(R.id.register_edtFname);
        edtLName = findViewById(R.id.register_edtLname);
        edtEmail = findViewById(R.id.register_edtEmail);
        edtPhone = findViewById(R.id.register_edtPhoneNo);
        edtAddress = findViewById(R.id.register_edtAddress);
        edtPostal = findViewById(R.id.register_edtPostal);
        btnSignUp = findViewById(R.id.register_btnSignUp);

        implementView();
    }

    private void implementView() {

        userDao = Room.databaseBuilder(this, BookDatabase.class, "mi-database.db").allowMainThreadQueries()
                .build().getUserDao();

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fName = edtFName.getText().toString().trim();
                lName = edtLName.getText().toString().trim();
                email = edtEmail.getText().toString().trim();
                phone = edtPhone.getText().toString().trim();
                address = edtAddress.getText().toString().trim();
                postal = edtPostal.getText().toString().trim();

                if(fName.equals(""))
                {
                    edtFName.setError("FirstName is required");
                    edtFName.requestFocus();
                    return;
                }
                if(lName.equals(""))
                {
                    edtLName.setError("LastName is required");
                    edtLName.requestFocus();
                    return;
                }
                if(email.equals(""))
                {
                    edtEmail.setError("Email-Id is required");
                    edtEmail.requestFocus();
                    return;
                }
                if(phone.equals(""))
                {
                    edtPhone.setError("Phone No. is required");
                    edtPhone.requestFocus();
                    return;
                }

                OpenPasswordDialog();
            }
        });
    }

    public void OpenPasswordDialog() {

        final EditText edtPass,edtConfirmPass;
        Button sign;
        ImageView imgClose;
        final Dialog dialog=new Dialog(this);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_password);
        if (dialog.getWindow()!=null)
        {
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT);
            WindowManager.LayoutParams params=dialog.getWindow().getAttributes();
            params.gravity= Gravity.CENTER_VERTICAL;
        }


        imgClose = dialog.findViewById(R.id.dialog_password_imgBack);
        sign = dialog.findViewById(R.id.dialog_password_btnSignUp);
        edtPass = dialog.findViewById(R.id.dialog_password_edtPassword);
        edtConfirmPass = dialog.findViewById(R.id.dialog_password_edtConfirmPass);

        imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });

        sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pass = edtPass.getText().toString().trim();
                confirmPass = edtConfirmPass.getText().toString().trim();

                if(!pass.equals(""))
                {
                    if(pass.length() < 6)
                    {
                        edtPass.setError("Password must be of minimum 6 characters length");
                        edtPass.requestFocus();
                        return;
                    }

                    if (confirmPass.equals(""))
                    {
                        edtConfirmPass.setError("Confirm Password is required");
                        edtConfirmPass.requestFocus();
                        return;
                    }

                    if (!pass.equals(confirmPass))
                    {
                        edtConfirmPass.setError("Password and confirm password don't match");
                        edtConfirmPass.requestFocus();
                        return;
                    }
                }

                User user = new User(fName,lName,email,phone,address,postal,pass);
                userDao.insertUser(user);
                Toast.makeText(RegisterActivity.this, "SuccessFully SignUp!!!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
                dialog.cancel();
            }
        });

        dialog.show();
    }

}
