package com.codepaper.booksapp.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.codepaper.booksapp.Database.ModelDB.Post;
import com.codepaper.booksapp.Model.BookListModel;
import com.codepaper.booksapp.R;
import com.codepaper.booksapp.Utils.BookListFilter;
import com.squareup.picasso.Picasso;

import java.util.List;

public class BookListAdapter extends RecyclerView.Adapter<BookListAdapter.BookListViewHolder> implements Filterable {

    Context mContext;
    public List<Post> bookModelList,filterList;
    Post bookListModel;
    BookListFilter filter;

    public BookListAdapter(Context mContext, List<Post> bookModelList) {
        this.mContext = mContext;
        this.bookModelList = bookModelList;
        this.filterList = bookModelList;
        setHasStableIds(true);
    }

    @NonNull
    @Override
    public BookListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home_recycler,parent,false);
        return new BookListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookListViewHolder holder, final int position) {
        bookListModel = bookModelList.get(position);

        /*Picasso.with(mContext)
                .load(bookListModel.getImgName())
                .placeholder(R.drawable.load)
                .into(holder.imgBook);*/

        holder.txtBookName.setText(bookListModel.getTitle());
        holder.txtAuthor.setText(bookListModel.getAuthor());

        if(bookListModel.getPost_type().equals("sell"))
        {
            holder.imgSell.setVisibility(View.VISIBLE);
            holder.imgExchange.setVisibility(View.GONE);
        }
        else
        {
            holder.imgExchange.setVisibility(View.VISIBLE);
            holder.imgSell.setVisibility(View.GONE);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenShowBookDialog(bookModelList.get(position).getTitle(),bookModelList.get(position).getAuthor(),bookModelList.get(position).getPrice());
            }
        });
    }

    @Override
    public int getItemCount() {
        return bookModelList.size();
    }

    @Override
    public Filter getFilter() {
        if(filter==null)
        {
            filter = new BookListFilter(this,filterList);
        }
        return filter;
    }

    public class BookListViewHolder extends RecyclerView.ViewHolder {
        TextView txtBookName,txtAuthor;
        ImageView imgBook,imgSell,imgExchange;
        public BookListViewHolder(@NonNull View itemView) {
            super(itemView);

            imgBook = itemView.findViewById(R.id.item_home_imgBook);
            txtBookName = itemView.findViewById(R.id.item_home_txtBookName);
            txtAuthor = itemView.findViewById(R.id.item_home_txtAuthor);
            imgSell = itemView.findViewById(R.id.item_home_imgSell);
            imgExchange = itemView.findViewById(R.id.item_home_imgExchange);
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

    public void OpenShowBookDialog(String book,String author,double p) {

        TextView txtBookName,txtAuthor;
        Button btnBuy,btnLocation,btnDetails;
        ImageView imgBook,imgClose;
        final Dialog dialog=new Dialog(mContext);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_showbook);
        if (dialog.getWindow()!=null)
        {
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT);
            WindowManager.LayoutParams params=dialog.getWindow().getAttributes();
            params.gravity= Gravity.CENTER_VERTICAL;
        }

        btnBuy = dialog.findViewById(R.id.dialog_showBook_btnBuy);
        btnLocation = dialog.findViewById(R.id.dialog_showBook_btnLocation);
        btnDetails = dialog.findViewById(R.id.dialog_showBook_btnDetails);
        txtBookName = dialog.findViewById(R.id.dialog_showBook_txtBookName);
        txtAuthor = dialog.findViewById(R.id.dialog_showBook_txtAuthor);
        imgBook = dialog.findViewById(R.id.dialog_showBook_imgBook);
        imgClose = dialog.findViewById(R.id.dialog_showBook_imgClose);

        txtBookName.setText(book);
        txtAuthor.setText("By : "+author);
        btnBuy.setText("BUY"+" | $"+p);

        btnLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "Location", Toast.LENGTH_SHORT).show();
            }
        });

        btnDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "Details", Toast.LENGTH_SHORT).show();
            }
        });

        imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });

        btnBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                dialog.cancel();
            }
        });

        dialog.show();
    }
}
