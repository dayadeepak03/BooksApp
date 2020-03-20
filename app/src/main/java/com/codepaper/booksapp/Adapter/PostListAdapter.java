package com.codepaper.booksapp.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Environment;
import android.util.Log;
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
import androidx.room.Room;

import com.codepaper.booksapp.Database.DAO.CartDao;
import com.codepaper.booksapp.Database.DAO.PostDao;
import com.codepaper.booksapp.Database.DataSource.BookDatabase;
import com.codepaper.booksapp.Database.ModelDB.Cart;
import com.codepaper.booksapp.Database.ModelDB.Post;
import com.codepaper.booksapp.Model.UserResponse;
import com.codepaper.booksapp.R;
import com.codepaper.booksapp.Storage.SharedPrefManager;
import com.codepaper.booksapp.Utils.PostListFilter;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

public class PostListAdapter extends RecyclerView.Adapter<PostListAdapter.PostListViewHolder> implements Filterable {

    Context mContext;
    public List<Post> postModelList,filterList;
    Post postList;
    PostListFilter filter;
    private static final String IMAGE_DIRECTORY = "/BOOK_IMAGE";
    File file1;
    BookDatabase dataBase;
    CartDao db;

    public PostListAdapter(Context mContext, List<Post> bookModelList) {
        this.mContext = mContext;
        this.postModelList = bookModelList;
        this.filterList = bookModelList;
        setHasStableIds(true);
    }

    @NonNull
    @Override
    public PostListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home_recycler,parent,false);
        return new PostListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostListViewHolder holder, final int position) {
        postList = postModelList.get(position);
        file1 = new File(Environment.getExternalStorageDirectory() + IMAGE_DIRECTORY);

        if(postList.getImage().equals("No_image"))
        {
            if(postList.getPost_type().equals("exchange"))
            {
                Picasso.with(mContext)
                        .load(R.drawable.book_exchange)
                        .placeholder(R.drawable.load)
                        .into(holder.imgBook);
            }
            else
            {
                Picasso.with(mContext)
                        .load(R.drawable.no_image)
                        .placeholder(R.drawable.load)
                        .into(holder.imgBook);
            }
        }
        else {
            try {
                File f = new File(file1, postList.getImage() + ".jpg");
                Bitmap bitmap1 = BitmapFactory.decodeStream(new FileInputStream(f));
                holder.imgBook.setImageBitmap(bitmap1);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        holder.txtBookName.setText(postList.getTitle());
        holder.txtAuthor.setText(postList.getAuthor());

        if(postList.getPost_type().equals("sell"))
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
                OpenShowBookDialog(postModelList.get(position).getTitle(),postModelList.get(position).getUser_id(),postModelList.get(position).getPost_id(), postModelList.get(position).getAuthor(), postModelList.get(position).getPrice(), postModelList.get(position).getImage(), postModelList.get(position).getPost_type());
            }
        });
    }

    @Override
    public int getItemCount() {
        return postModelList.size();
    }

    @Override
    public Filter getFilter() {
        if(filter==null)
        {
            filter = new PostListFilter(this,filterList);
        }
        return filter;
    }

    public class PostListViewHolder extends RecyclerView.ViewHolder {
        TextView txtBookName,txtAuthor;
        ImageView imgBook,imgSell,imgExchange;
        public PostListViewHolder(@NonNull View itemView) {
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

    public void OpenShowBookDialog(final String book, final int uId, final int pId, final String author, final double p, final String img, String type) {

        TextView txtBookName,txtAuthor;
        final Button btnBuy,btnLocation,btnDetails;
        ImageView imgPost,imgClose;
        UserResponse userResponse;
        final int Userid;
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
        btnDetails = dialog.findViewById(R.id.dialog_showBook_btnDetails);
        txtBookName = dialog.findViewById(R.id.dialog_showBook_txtBookName);
        txtAuthor = dialog.findViewById(R.id.dialog_showBook_txtAuthor);
        imgPost = dialog.findViewById(R.id.dialog_showBook_imgBook);
        imgClose = dialog.findViewById(R.id.dialog_showBook_imgClose);

        userResponse = SharedPrefManager.getInstance(mContext).getUserId();
        Userid = userResponse.getUser_id();

        dataBase = Room.databaseBuilder(mContext, BookDatabase.class, "mi-database.db")
                .allowMainThreadQueries()
                .build();
        db = dataBase.getCartDao();

        txtBookName.setText(book);
        txtAuthor.setText("By : "+author);
        btnBuy.setText("BUY"+" | $"+p);
        if(img.equals("No_image"))
        {
            if(type.equals("exchange"))
            {
                Picasso.with(mContext)
                        .load(R.drawable.book_exchange)
                        .placeholder(R.drawable.load)
                        .into(imgPost);
            }
            else
            {
                Picasso.with(mContext)
                        .load(R.drawable.no_image)
                        .placeholder(R.drawable.load)
                        .into(imgPost);
            }
        }
        else {
            try {
                File f = new File(file1, img + ".jpg");
                Bitmap bitmap1 = BitmapFactory.decodeStream(new FileInputStream(f));
                imgPost.setImageBitmap(bitmap1);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

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
                if(btnBuy.getText().toString().equals("Book add to cart"))
                {
                    Toast.makeText(mContext, "Book already add to the cart!!", Toast.LENGTH_SHORT).show();
                }
                else {
                    if (Userid == uId) {
                        Toast.makeText(mContext, "You can't buy your own Book!!", Toast.LENGTH_SHORT).show();
                    } else if (Userid <= 0) {
                        Toast.makeText(mContext, "You can Login First!!", Toast.LENGTH_SHORT).show();
                    } else {
                        Cart cart = new Cart(pId, Userid, book, author, p, img);
                        db.insertCart(cart);
                        btnBuy.setText("Book add to cart");
                    }
                }
            }
        });
        dialog.show();
    }
}