package com.codepaper.booksapp.Adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.codepaper.booksapp.R;

public class ShoppingCartAdapter extends RecyclerView.Adapter<ShoppingCartAdapter.ShoppingCartViewHolder> {

    @NonNull
    @Override
    public ShoppingCartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ShoppingCartViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ShoppingCartViewHolder extends RecyclerView.ViewHolder {
        ImageView imgBook,imgDelete;
        TextView txtBookName,txtAuthor,txtprice;
        public ShoppingCartViewHolder(@NonNull View itemView) {
            super(itemView);

            imgBook = itemView.findViewById(R.id.item_shopping_cart_imgBook);
            txtBookName = itemView.findViewById(R.id.item_shopping_cart_txtBookName);
            txtAuthor = itemView.findViewById(R.id.item_shopping_cart_txtAuthor);
            txtprice = itemView.findViewById(R.id.item_shopping_cart_txtprice);
            imgDelete = itemView.findViewById(R.id.item_shopping_cart_imgDelete);
        }
    }
}
