package com.codepaper.booksapp.Fragments;


import android.app.Dialog;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.codepaper.booksapp.Adapter.PostListAdapter;
import com.codepaper.booksapp.Database.DAO.PostDao;
import com.codepaper.booksapp.Database.DataSource.BookDatabase;
import com.codepaper.booksapp.Database.ModelDB.Post;
import com.codepaper.booksapp.R;
import com.github.ybq.android.spinkit.SpinKitView;

import java.util.Collections;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class BooksFragment extends Fragment {
    View view;
    RecyclerView recyclerView;
    SwipeRefreshLayout swipeRefreshLayout;
    private List<Post> postList;
    PostListAdapter adapter;
    EditText edtSearch;
    TextView homeHeading;
    BookDatabase dataBase;
    PostDao db;
    Handler handler;
    SpinKitView progressBar;
    TextView txtEmptyView;
    ImageView imgNotification,imgFilter;

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
        swipeRefreshLayout = view.findViewById(R.id.home_swipeRefresh);
        edtSearch = view.findViewById(R.id.edt_search);
        homeHeading = view.findViewById(R.id.home_heading);
        progressBar = view.findViewById(R.id.home_spinKit);
        txtEmptyView = view.findViewById(R.id.home_txtEmptyView);
        imgNotification = view.findViewById(R.id.home_notification);
        imgFilter = view.findViewById(R.id.home_filter);

        implementView();
    }

    private void implementView() {
        handler = new Handler();
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fillRecyclerView();
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        dataBase = Room.databaseBuilder(getActivity(), BookDatabase.class, "mi-database.db")
                .allowMainThreadQueries()
                .build();

        db = dataBase.getPostDao();

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new BooksFragment.GridSpacingItemDecoration(2, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        fillRecyclerView();

        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.equals("")) {
                    adapter.getFilter().filter(s);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        imgNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenNotificationDialog();
            }
        });

        imgFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenFilterDialog();
            }
        });
    }

    private void fillRecyclerView() {

        progressBar.setVisibility(View.VISIBLE);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                postList = db.getPost();
                Collections.reverse(postList);
                if(postList.size()>0) {

                    adapter = new PostListAdapter(getActivity(), postList);
                    recyclerView.setAdapter(adapter);
                    recyclerView.smoothScrollToPosition(0);
                    adapter.notifyDataSetChanged();
                    progressBar.setVisibility(View.GONE);
                }
                else
                {
                    progressBar.setVisibility(View.GONE);
                    txtEmptyView.setVisibility(View.VISIBLE);
                }
            }
        },1000);
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

    public void OpenFilterDialog() {

        Spinner spnLocation,spnPrice,spnCondition,spnSortBy;
        Button btnApply;
        ImageView imgClose;
        final Dialog dialog=new Dialog(getActivity());
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_filter);
        if (dialog.getWindow()!=null)
        {
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT);
            WindowManager.LayoutParams params=dialog.getWindow().getAttributes();
            params.gravity= Gravity.CENTER_VERTICAL;
        }


        imgClose = dialog.findViewById(R.id.dialog_filter_imgBack);
        btnApply = dialog.findViewById(R.id.dialog_filter_btnApply);
        spnLocation = dialog.findViewById(R.id.dialog_filter_spnLocation);
        spnPrice = dialog.findViewById(R.id.dialog_filter_spnPrice);
        spnCondition = dialog.findViewById(R.id.dialog_filter_spnCondition);
        spnSortBy = dialog.findViewById(R.id.dialog_filter_spnSortBy);

        imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });

        btnApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });

        dialog.show();
    }

    public void OpenNotificationDialog() {

        Spinner spnLocation,spnPrice,spnCondition,spnSortBy;
        Button btnApply;
        ImageView imgClose;
        final Dialog dialog=new Dialog(getActivity());
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_notification);
        if (dialog.getWindow()!=null)
        {
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT);
            WindowManager.LayoutParams params=dialog.getWindow().getAttributes();
            params.gravity= Gravity.CENTER_VERTICAL;
        }


        imgClose = dialog.findViewById(R.id.dialog_notification_imgClose);

        imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });

        dialog.show();
    }

}
