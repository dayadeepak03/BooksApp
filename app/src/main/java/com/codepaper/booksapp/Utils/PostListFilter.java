package com.codepaper.booksapp.Utils;

import android.widget.Filter;

import com.codepaper.booksapp.Adapter.PostListAdapter;
import com.codepaper.booksapp.Database.ModelDB.Post;

import java.util.ArrayList;
import java.util.List;

public class PostListFilter extends Filter {

   private PostListAdapter adapter;
   private List<Post> filterList;

    public PostListFilter(PostListAdapter adapter, List<Post> filterList) {
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
        adapter.postModelList = (List<Post>) results.values;
        adapter.notifyDataSetChanged();
    }
}
