package com.codepaper.booksapp.Utils;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import com.codepaper.booksapp.Activities.MainActivity;
import com.codepaper.booksapp.Storage.SharedPrefManager;

public class LogoutMethod {

    Context mContext;

    public LogoutMethod(Context mContext) {
        this.mContext = mContext;
    }

    String msg = "Successfully Sign out!!!";

    public void logOutApp(View view){

                SharedPrefManager.getInstance(mContext).clear();
                SharedPrefManager.getInstance(mContext).clearUserId();
                Intent intent = new Intent(mContext, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                Toast.makeText(mContext, "You have successfully logout!!", Toast.LENGTH_SHORT).show();
                mContext.startActivity(intent);

    }
}
