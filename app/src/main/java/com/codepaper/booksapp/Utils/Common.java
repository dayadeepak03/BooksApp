package com.codepaper.booksapp.Utils;

import android.text.Html;
import android.view.View;

import androidx.core.content.ContextCompat;

import com.codepaper.booksapp.R;
import com.google.android.material.snackbar.Snackbar;

public class Common {

    public static void showSnackBar(View view, String msg)
    {
        Snackbar snac = Snackbar.make(view, Html.fromHtml("<font color=\"#FFFFFF\"><b>" + msg + "</b></font>"),Snackbar.LENGTH_SHORT);
        snac.getView().setBackgroundColor(ContextCompat.getColor(view.getContext(), R.color.logoColor2));
        snac.show();
    }
}
