package com.codepaper.booksapp.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Environment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.codepaper.booksapp.Database.DAO.CartDao;
import com.codepaper.booksapp.Database.DataSource.BookDatabase;
import com.codepaper.booksapp.Database.ModelDB.Cart;
import com.codepaper.booksapp.Fragments.CartFragment;
import com.codepaper.booksapp.R;
import com.codepaper.booksapp.Utils.callTotalUpdate;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

public class ShoppingCartAdapter extends RecyclerView.Adapter<ShoppingCartAdapter.ShoppingCartViewHolder> {

    Context mContext;
    List<Cart> cartList;
    private static final String IMAGE_DIRECTORY = "/BOOK_IMAGE";
    File file1;
    CartFragment fragment;
    double tot=0.0;
    BookDatabase dataBase;
    CartDao db;

    public ShoppingCartAdapter(Context mContext, List<Cart> cartList,CartFragment fragment) {
        this.mContext = mContext;
        this.cartList = cartList;
        this.fragment = fragment;
    }

    @NonNull
    @Override
    public ShoppingCartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_shopping_cart,parent,false);
        return new ShoppingCartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ShoppingCartViewHolder holder, final int position) {

        file1 = new File(Environment.getExternalStorageDirectory() + IMAGE_DIRECTORY);
        if(cartList.get(position).getImage().equals("No_image"))
        {
                Picasso.with(mContext)
                        .load(R.drawable.no_image)
                        .placeholder(R.drawable.load)
                        .into(holder.imgBook);
        }
        else {
            try {
                File f = new File(file1, cartList.get(position).getImage() + ".jpg");
                Bitmap bitmap1 = BitmapFactory.decodeStream(new FileInputStream(f));
                holder.imgBook.setImageBitmap(bitmap1);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        holder.txtTitle.setText(cartList.get(position).getTitle());
        holder.txtAuthor.setText(cartList.get(position).getAuthor());
        holder.txtPrice.setText("$"+String.valueOf(cartList.get(position).getPrice()));

        tot = tot+cartList.get(position).getPrice();

        if(cartList.size()-1==position) {
            fragment.totalPrice(tot);
        }

        holder.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenDeleteDialog(cartList.get(position),position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return cartList.size();
    }

    public class ShoppingCartViewHolder extends RecyclerView.ViewHolder {
        ImageView imgBook,imgDelete;
        TextView txtTitle,txtAuthor, txtPrice;
        public ShoppingCartViewHolder(@NonNull View itemView) {
            super(itemView);

            imgBook = itemView.findViewById(R.id.item_shopping_cart_imgBook);
            txtTitle = itemView.findViewById(R.id.item_shopping_cart_txtBookName);
            txtAuthor = itemView.findViewById(R.id.item_shopping_cart_txtAuthor);
            txtPrice = itemView.findViewById(R.id.item_shopping_cart_txtprice);
            imgDelete = itemView.findViewById(R.id.item_shopping_cart_imgDelete);
        }
    }

    private void removeItem(int position) {
        cartList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, cartList.size());
    }

    private void OpenDeleteDialog(final Cart cart,final int pos) {

        Button btnYes,btnNo;
        final Dialog dialog=new Dialog(mContext);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_delete);
        if (dialog.getWindow()!=null)
        {
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT);
            WindowManager.LayoutParams params=dialog.getWindow().getAttributes();
            params.gravity= Gravity.CENTER_VERTICAL;
        }

        btnYes = dialog.findViewById(R.id.dialog_delete_btnYes);
        btnNo = dialog.findViewById(R.id.dialog_delete_btnNo);

        btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });

        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dataBase = Room.databaseBuilder(mContext, BookDatabase.class, "mi-database.db")
                        .allowMainThreadQueries()
                        .build();
                db = dataBase.getCartDao();
                db.deleteCartItem(cart);
                removeItem(pos);
                fragment.fillRecyclerView();
                dialog.cancel();
            }
        });
        dialog.show();
    }
}
