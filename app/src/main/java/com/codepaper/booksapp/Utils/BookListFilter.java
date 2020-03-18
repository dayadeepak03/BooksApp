package com.codepaper.booksapp.Utils;

import android.widget.Filter;

import com.codepaper.booksapp.Adapter.BookListAdapter;
import com.codepaper.booksapp.Database.ModelDB.Post;
import com.codepaper.booksapp.Model.BookListModel;

import java.util.ArrayList;
import java.util.List;

public class BookListFilter extends Filter {

   private BookListAdapter adapter;
   private List<Post> filterList;

    public BookListFilter(BookListAdapter adapter, List<Post> filterList) {
        this.adapter = adapter;
        this.filterList = filterList;
    }

    @Override
    protected FilterResults performFiltering(CharSequence constraint) {
        FilterResults results=new FilterResults();

        if(constraint != null && constraint.length() > 0)
        {
            constraint=constraint.toString().toLowerCase();

            List<Post> filteredPlayers= new ArrayList<>();
            for (int i=0;i<filterList.size();i++)
            {
                if(filterList.get(i).getTitle().toLowerCase().contains(constraint) || filterList.get(i).getAuthor().toLowerCase().contains(constraint))
                {
                    filteredPlayers.add(filterList.get(i));
                }
            }
            results.count=filteredPlayers.size();
            results.values=filteredPlayers;
        }else
        {
            results.count=filterList.size();
            results.values=filterList;
        }
        return results;
    }

    @Override
    protected void publishResults(CharSequence constraint, FilterResults results) {
        adapter.bookModelList = (List<Post>) results.values;
        adapter.notifyDataSetChanged();
    }
}
