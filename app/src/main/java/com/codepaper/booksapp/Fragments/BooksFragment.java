package com.codepaper.booksapp.Fragments;


import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.codepaper.booksapp.Activities.MainActivity;
import com.codepaper.booksapp.Adapter.BookListAdapter;
import com.codepaper.booksapp.Model.BookListModel;
import com.codepaper.booksapp.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class BooksFragment extends Fragment {
    View view;
    RecyclerView recyclerView;
    List<BookListModel> bookListModelList = new ArrayList<>();;
    BookListAdapter adapter;
    EditText edtSearch;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_books, container, false);
        initView();
        return view;
    }

    private void initView() {
        recyclerView = view.findViewById(R.id.home_recycler);
        edtSearch = view.findViewById(R.id.edt_search);

        implementView();
    }

    private void implementView() {

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new BooksFragment.GridSpacingItemDecoration(2, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                adapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        fillRecyclerView();
    }

    private void fillRecyclerView() {

        bookListModelList.add(new BookListModel(R.drawable.cover1,"The design of Everyday things","Don Normon","true","false"));
        bookListModelList.add(new BookListModel(R.drawable.cover2,"Boing versus Airbus","John Newhouse","false","true"));
        bookListModelList.add(new BookListModel(R.drawable.cover3,"The design of Everyday things","Deepak Daya","true","true"));
        bookListModelList.add(new BookListModel(R.drawable.cover4,"The design of Everyday things","Deepak Daya","true","false"));
        bookListModelList.add(new BookListModel(R.drawable.cover1,"The design of Everyday things","Deepak Daya","true","true"));
        bookListModelList.add(new BookListModel(R.drawable.cover2,"The design of Everyday things","Deepak Daya","true","false"));

        adapter = new BookListAdapter(getActivity(),bookListModelList);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }

    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }

}
