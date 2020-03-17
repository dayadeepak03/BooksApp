package com.codepaper.booksapp.Fragments;


import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.codepaper.booksapp.Activities.BookActivity;
import com.codepaper.booksapp.Database.ModelDB.User;
import com.codepaper.booksapp.R;
import com.codepaper.booksapp.Storage.SharedPrefManager;
import com.codepaper.booksapp.Utils.LogoutMethod;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {
    View view;
    Button btnSignOut;
    LogoutMethod logoutMethod;
    User user;
    TextView txtName,txtEmail,txtSale,txtExchange;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_profile, container, false);
        initView();
        return view;
    }

    private void initView() {
        btnSignOut = view.findViewById(R.id.profile_btnSignOut);
        txtName = view.findViewById(R.id.profile_txtName);
        txtEmail = view.findViewById(R.id.profile_txtEmail);
        txtSale = view.findViewById(R.id.profile_txtSale);
        txtExchange = view.findViewById(R.id.profile_txtExchange);
        logoutMethod = new LogoutMethod(getActivity());
        user = SharedPrefManager.getInstance(getActivity()).getUser();
        implementView();
    }

    private void implementView() {

        txtName.setText(user.getFname()+" "+user.getLname());
        txtEmail.setText(user.getEmail());

        btnSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenSignOutDialog();
            }
        });

        txtSale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), BookActivity.class));
            }
        });
    }

    private void OpenSignOutDialog() {

        Button btnSignOut,btnCancel;
        final Dialog dialog=new Dialog(getActivity());
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_signout);
        if (dialog.getWindow()!=null)
        {
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT);
            WindowManager.LayoutParams params=dialog.getWindow().getAttributes();
            params.gravity= Gravity.CENTER_VERTICAL;
        }

        btnSignOut = dialog.findViewById(R.id.dialog_signOut_btndelete);
        btnCancel = dialog.findViewById(R.id.dialog_signOut_btnCancel);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });

        btnSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logoutMethod.logOutApp(view);
                dialog.cancel();
            }
        });

        dialog.show();
    }

}
