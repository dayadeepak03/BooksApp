package com.codepaper.booksapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.codepaper.booksapp.Activities.BookExchangeActivity;
import com.codepaper.booksapp.Database.DAO.PostDao;
import com.codepaper.booksapp.Database.DataSource.BookDatabase;
import com.codepaper.booksapp.Database.ModelDB.Post;
import com.codepaper.booksapp.R;

import java.util.List;

public class BookExchangeAdapter extends RecyclerView.Adapter<BookExchangeAdapter.BookExchangeViewHolder> {

    Context mContext;
    List<Post> postList;
    Post post;
   BookDatabase database;
   PostDao pd;

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
    public void onBindViewHolder(@NonNull BookExchangeViewHolder holder, final int position) {
        post = postList.get(position);

        holder.txtTitle.setText(post.getTitle());
        holder.txtAuthor.setText("Author : "+post.getAuthor());
        holder.txtCondition.setText("Condition : "+post.getCondition());
        holder.txtTitle1.setText(post.getOffer_title());
        holder.txtAuthor1.setText("Author : "+post.getOffer_title());
        holder.txtCondition1.setText("Condition : "+post.getOffer_condition());

        if(post.getStatus().equals("exchange")) {
            holder.btnExchanged.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    UpdatePostStatus(postList.get(position).getPost_id());
                }
            });
        }
        else {
            holder.btnExchanged.setTextColor(mContext.getResources().getColor(R.color.colorAccent));
            holder.btnExchanged.setText("EXCHANGED");
            holder.btnExchanged.setEnabled(false);
        }
    }

    @Override
    public int getItemCount() {
        return postList.size();
    }

    public class BookExchangeViewHolder extends RecyclerView.ViewHolder {
        TextView txtTitle,txtAuthor,txtCondition,txtTitle1,txtAuthor1,txtCondition1;
        Button btnExchanged;
        public BookExchangeViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTitle = itemView.findViewById(R.id.item_exchange_list_txtTitle);
            txtAuthor = itemView.findViewById(R.id.item_exchange_list_txtAuthor);
            txtCondition = itemView.findViewById(R.id.item_exchange_list_txtCondition);
            txtTitle1 = itemView.findViewById(R.id.item_exchange_list_txtTitle1);
            txtAuthor1 = itemView.findViewById(R.id.item_exchange_list_txtAuthor1);
            txtCondition1 = itemView.findViewById(R.id.item_exchange_list_txtCondition1);
            btnExchanged = itemView.findViewById(R.id.item_exchange_list_btnExchanged);
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

    private void UpdatePostStatus(int post_id) {
        database = Room.databaseBuilder(mContext, BookDatabase.class, "mi-database.db")
                .allowMainThreadQueries()
                .build();
        pd = database.getPostDao();
        String status = "exchanged";
        pd.UpdateExchangePost(post_id,status);
        if (mContext instanceof BookExchangeActivity) {
            ((BookExchangeActivity)mContext).fillRecyclerView();
        }
    }
}
