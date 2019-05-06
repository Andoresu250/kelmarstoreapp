package com.andoresu.kelmarstore.list;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.andoresu.kelmarstore.R;
import com.andoresu.kelmarstore.utils.BaseFragment;
import com.andoresu.kelmarstore.utils.BaseListResponse;
import com.andoresu.kelmarstore.utils.PaginationScrollListener;

import butterknife.BindView;

public class RecyclerViewFragment<T> extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {

    public static final String TAG = BASE_TAG + RecyclerViewFragment.class.getSimpleName();

    private static final int PAGE_START = 1;
    private boolean isLoading = false;

    @BindView(R.id.listRecyclerView)
    public RecyclerView listRecyclerView;

    @BindView(R.id.listSwipeRefreshLayout)
    public SwipeRefreshLayout swipeRefreshLayout;

    @BindView(R.id.emptyTextView)
    public TextView emptyTextView;

    private LinearLayoutManager linearLayoutManager;

    public int currentPage = PAGE_START;

    public RecyclerViewAdapter<T> viewAdapter;

    public boolean addSearch = false;

    public SearchView searchView;

    public String searchQuery = null;

    public RecyclerViewFragment(){}

    public static <T>RecyclerViewFragment<T> newInstance() {
        Bundle args = new Bundle();
        RecyclerViewFragment<T> fragment = new RecyclerViewFragment<>();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void handleView() {
        swipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);
        swipeRefreshLayout.setOnRefreshListener(this);
        linearLayoutManager = new LinearLayoutManager(this.getContext(), 1, false);
        listRecyclerView.setLayoutManager(linearLayoutManager);
        listRecyclerView.setAdapter(viewAdapter);
        listRecyclerView.addOnScrollListener(getPaginationScrollListener());
        isEmpty();
    }

    public void setViewAdapter(RecyclerViewAdapter<T> viewAdapter){
        this.viewAdapter = viewAdapter;
    }

    @Override
    public  void onRefresh(){
        onRefresh(false);
    }

    public  void onRefresh(boolean clean){
        showLoading(true);
    }

    public void showLoading(boolean show){
        isLoading = show;
        if(swipeRefreshLayout != null){
            swipeRefreshLayout.setRefreshing(show);
        }
    }

    public void isEmpty(){
        if(emptyTextView == null){
            return;
        }
        if(viewAdapter == null || viewAdapter.items == null || viewAdapter.items.isEmpty()){
            emptyTextView.setVisibility(View.VISIBLE);
        }else{
            emptyTextView.setVisibility(View.GONE);
        }
    }

    public int getTotalPageCount(){
        Log.i(TAG, "getTotalPageCount: getTotalItems() " + getTotalItems());
        Log.i(TAG, "getTotalPageCount: BaseListResponse.PER_PAGE " + BaseListResponse.PER_PAGE);
        double a = (double) getTotalItems()/ (double) BaseListResponse.PER_PAGE;
        double totalPages = Math.ceil(a);
        Log.i(TAG, "getTotalPageCount totalPages : " + totalPages);
        return (int) totalPages;
    }

    public int getTotalItems(){
        return 0;
    }


    @Override
    public int getLayoutId(){
        return R.layout.fragment_list;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        Log.i(TAG, "onCreateOptionsMenu: " + addSearch);
        if(addSearch){
            menuInflater.inflate(R.menu.main, menu);
            MenuItem item = menu.findItem(R.id.action_search);
            searchView = (SearchView) item.getActionView();
            searchView.setMaxWidth(Integer.MAX_VALUE);

            item.setOnActionExpandListener(new MenuItem.OnActionExpandListener() {
                @Override
                public boolean onMenuItemActionExpand(MenuItem menuItem) {
                    return true;
                }

                @Override
                public boolean onMenuItemActionCollapse(MenuItem menuItem) {
                    currentPage = 1;
                    searchQuery = null;
                    onRefresh(true);
                    return true;
                }
            });
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    currentPage = 1;
                    searchQuery = query;
                    onRefresh(true);
                    return true;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
//                    currentPage = 1;
//                    searchQuery = newText;
//                    onRefresh(true);
                    return true;
                }
            });
        }else{
            super.onCreateOptionsMenu(menu, menuInflater);
        }
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
    }

    private PaginationScrollListener getPaginationScrollListener(){
        return new PaginationScrollListener(linearLayoutManager) {
            @Override
            protected void loadMoreItems() {
                Log.i(TAG, "loadMoreItems: ");
                if(!isLastPage()){
                    Log.i(TAG, "loadMoreItems: no last page");
                    isLoading = true;
                    currentPage++;
                    onRefresh();
                }else{
                    Log.i(TAG, "loadMoreItems: last page");
                }
            }

            @Override
            public int getTotalPageCount() {
                return RecyclerViewFragment.this.getTotalPageCount();
            }

            @Override
            public boolean isLastPage() {
                Log.i(TAG, "isLastPage: currentPage " + currentPage);
                Log.i(TAG, "isLastPage: getTotalPageCount " + getTotalPageCount());
                return currentPage >= getTotalPageCount();
            }

            @Override
            public boolean isLoading() {
                return isLoading;
            }
        };
    }


}
