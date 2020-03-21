package com.codepaper.booksapp.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.codepaper.booksapp.Database.ModelDB.Post;
import com.codepaper.booksapp.R;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

public class BookSellAdapter extends RecyclerView.Adapter<BookSellAdapter.BookSellViewHolder> {

    Context mContext;
    List<Post> postList;
    Post post;
    private static final String IMAGE_DIRECTORY = "/BOOK_IMAGE";
    File file1;

    public BookSellAdapter(Context mContext, List<Post> postList) {
        this.mContext = mContext;
        this.postList = postList;
        setHasStableIds(true);
    }

    @NonNull
    @Override
    public BookSellViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_book_list,parent,false);
        return new BookSellViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookSellViewHolder holder, int position) {
        post = postList.get(position);

        file1 = new File(Environment.getExternalStorageDirectory() + IMAGE_DIRECTORY);

        if(post.getImage().equals("No_image"))
        {
            Picasso.with(mContext)
                    .load(R.drawable.no_image)
                    .placeholder(R.drawable.load)
                    .into(holder.imgView);
        }
        else {
            try {
                File f = new File(file1, post.getImage() + ".jpg");
                Bitmap bitmap1 = BitmapFactory.decodeStream(new FileInputStream(f));
                holder.imgView.setImageBitmap(bitmap1);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        holder.txtTitle.setText(post.getTitle());
        holder.txtAuthor.setText(post.getAuthor());
        holder.txtPrice.setText(String.valueOf(post.getPrice()));
        holder.txtDate.setText(post.getCreated_at());

        if(post.getStatus().equals("sell"))
        {
            holder.txtTag.setText("POSTED");
            holder.txtTag.setTextColor(mContext.getResources().getColor(R.color.correct));

        }
        else
        {
            holder.txtTag.setVisibility(View.GONE);
            holder.imgSold.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public int getItemCount() {
        return postList.size();
    }

    public class BookSellViewHolder extends RecyclerView.ViewHolder {
        ImageView imgView,imgSold;
        TextView txtTitle,txtAuthor,txtPrice,txtTag,txtDate;
        public BookSellViewHolder(@NonNull View itemView) {
            super(itemView);
            imgView = itemView.findViewById(R.id.item_book_sale_imgBook);
            txtTitle = itemView.findViewById(R.id.item_book_sale_txtTitle);
            txtAuthor = itemView.findViewById(R.id.item_book_sale_txtAuthor);
            txtPrice = itemView.findViewById(R.id.item_book_sale_txtPrice);
            txtTag = itemView.findViewById(R.id.item_book_sale_txtTag);
            txtDate = itemView.findViewById(R.id.item_book_sale_txtDate);
            imgSold = itemView.findViewById(R.id.item_book_sale_imgSold);
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
