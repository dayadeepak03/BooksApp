package com.codepaper.booksapp.Fragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.codepaper.booksapp.Adapter.PostListAdapter;
import com.codepaper.booksapp.Adapter.ShoppingCartAdapter;
import com.codepaper.booksapp.Database.DAO.CartDao;
import com.codepaper.booksapp.Database.DataSource.BookDatabase;
import com.codepaper.booksapp.Database.ModelDB.Cart;
import com.codepaper.booksapp.Model.UserResponse;
import com.codepaper.booksapp.R;
import com.codepaper.booksapp.Storage.SharedPrefManager;
import com.codepaper.booksapp.Utils.callTotalUpdate;
import com.github.ybq.android.spinkit.SpinKitView;

import java.util.Collections;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class CartFragment extends Fragment implements callTotalUpdate {

    View view;
    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    TextView txtContinue,txtEmptyCart,txtTotal;
    ImageView imgBack,imgEmpty;
    BooksFragment booksFragment = new BooksFragment();
    private List<Cart> cartList;
    BookDatabase dataBase;
    CartDao db;
    ShoppingCartAdapter adapter;
    Handler handler;
    SpinKitView progressBar;
    UserResponse userResponse;
    int Userid;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_cart, container, false);
        initView();
        return view;
    }

    private void initView() {
        recyclerView = view.findViewById(R.id.fragment_cart_recycler);
        txtContinue = view.findViewById(R.id.fragment_cart_txtContinue);
        imgBack = view.findViewById(R.id.fragment_cart_imgBack);
        imgEmpty = view.findViewById(R.id.fragment_cart_imgEmpty);
        txtEmptyCart = view.findViewById(R.id.fragment_cart_txtEmptyCart);
        txtTotal = view.findViewById(R.id.fragment_cart_txtTotal);
        progressBar = view.findViewById(R.id.fragment_cart_spinKit);
        layoutManager = new LinearLayoutManager(getActivity());
        implementView();
    }

    private void implementView() {
        handler = new Handler();
        dataBase = Room.databaseBuilder(getActivity(), BookDatabase.class, "mi-database.db")
                .allowMainThreadQueries()
                .build();
        db = dataBase.getCartDao();

        userResponse = SharedPrefManager.getInstance(getActivity()).getUserId();
        Userid = userResponse.getUser_id();

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        txtContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameContainer,booksFragment).commit();
            }
        });

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameContainer,booksFragment).commit();
            }
        });

        fillRecyclerView();
    }

    public void fillRecyclerView() {

        progressBar.setVisibility(View.VISIBLE);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                cartList = db.getCart(Userid);
                Log.d("SIZE",String.valueOf(cartList.size()));
                Collections.reverse(cartList);
                if(cartList.size()>0) {

                    adapter = new ShoppingCartAdapter(getActivity(), cartList,CartFragment.this);
                    recyclerView.setAdapter(adapter);
                    recyclerView.smoothScrollToPosition(0);
                    adapter.notifyDataSetChanged();
                    progressBar.setVisibility(View.GONE);
                }
                else
                {
                    totalPrice(0.0);
                    progressBar.setVisibility(View.GONE);
                    imgEmpty.setVisibility(View.VISIBLE);
                    txtEmptyCart.setVisibility(View.VISIBLE);
                }
            }
        },1000);
    }

    @Override
    public void totalPrice(double price) {
        txtTotal.setText(String.format("$ %.2f",price));
    }
}
