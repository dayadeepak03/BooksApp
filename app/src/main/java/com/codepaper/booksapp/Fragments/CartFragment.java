package com.codepaper.booksapp.Fragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.codepaper.booksapp.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class CartFragment extends Fragment {

    View view;
    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    TextView txtContinue;
    ImageView imgBack;
    BooksFragment booksFragment = new BooksFragment();
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
        layoutManager = new LinearLayoutManager(getActivity());
        implementView();
    }

    private void implementView() {

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
    }
}
