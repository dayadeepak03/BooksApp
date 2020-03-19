package com.codepaper.booksapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.codepaper.booksapp.Database.ModelDB.Post;
import com.codepaper.booksapp.R;

import java.util.List;

public class BookExchangeAdapter extends RecyclerView.Adapter<BookExchangeAdapter.BookExchangeViewHolder> {

    Context mContext;
    List<Post> postList;
    Post post;

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
        post = postList.get(position);

        holder.txtTitle.setText(post.getTitle());
        holder.txtAuthor.setText("Author : "+post.getAuthor());
        holder.txtCondition.setText("Condition : "+post.getCondition());
        holder.txtTitle1.setText(post.getOffer_title());
        holder.txtAuthor1.setText("Author : "+post.getOffer_title());
        holder.txtCondition1.setText("Condition : "+post.getOffer_condition());
    }

    @Override
    public int getItemCount() {
        return postList.size();
    }

    public class BookExchangeViewHolder extends RecyclerView.ViewHolder {
        TextView txtTitle,txtAuthor,txtCondition,txtTitle1,txtAuthor1,txtCondition1;
        public BookExchangeViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTitle = itemView.findViewById(R.id.item_exchange_list_txtTitle);
            txtAuthor = itemView.findViewById(R.id.item_exchange_list_txtAuthor);
            txtCondition = itemView.findViewById(R.id.item_exchange_list_txtCondition);
            txtTitle1 = itemView.findViewById(R.id.item_exchange_list_txtTitle1);
            txtAuthor1 = itemView.findViewById(R.id.item_exchange_list_txtAuthor1);
            txtCondition1 = itemView.findViewById(R.id.item_exchange_list_txtCondition1);
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }
}
