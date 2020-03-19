package com.codepaper.booksapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.codepaper.booksapp.Database.ModelDB.Post;
import com.codepaper.booksapp.R;

import java.util.List;

public class BookExchangeAdapter extends RecyclerView.Adapter<BookExchangeAdapter.BookExchangeViewHolder> {

    Context mContext;
    List<Post> postList;

    public BookExchangeAdapter(Context mContext, List<Post> postList) {
        this.mContext = mContext;
        this.postList = postList;
        setHasStableIds(true);
    }

    @NonNull
    @Override
    public BookExchangeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_exchange_list,parent,false);
        return new BookExchangeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookExchangeViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class BookExchangeViewHolder extends RecyclerView.ViewHolder {
        public BookExchangeViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
