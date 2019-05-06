package com.andoresu.kelmarstore.utils;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import static com.andoresu.kelmarstore.utils.BaseFragment.BASE_TAG;

public abstract class PaginationScrollListener extends RecyclerView.OnScrollListener{

    public static final String TAG = BASE_TAG +  PaginationScrollListener.class.getSimpleName();

    private final LinearLayoutManager layoutManager;

    public PaginationScrollListener(LinearLayoutManager layoutManager) {
        this.layoutManager = layoutManager;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        int visibleItemCount = layoutManager.getChildCount();
        int totalItemCount = layoutManager.getItemCount();
        int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();

        Log.i(TAG, "onScrolled: asd");
        Log.i(TAG, "onScrolled: isLoading " + isLoading());
        Log.i(TAG, "onScrolled: isLastPage " + isLastPage());
        if (!isLoading() && !isLastPage()) {
            if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                    && firstVisibleItemPosition >= 0
                    && totalItemCount >= getTotalPageCount()) {
                loadMoreItems();
            }
        }

    }

    protected abstract void loadMoreItems();

    public abstract int getTotalPageCount();

    public abstract boolean isLastPage();

    public abstract boolean isLoading();

}
