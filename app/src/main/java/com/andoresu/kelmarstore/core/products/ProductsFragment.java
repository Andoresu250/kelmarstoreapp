package com.andoresu.kelmarstore.core.products;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.Menu;
import android.view.MenuInflater;

import com.andoresu.kelmarstore.R;
import com.andoresu.kelmarstore.client.ErrorResponse;
import com.andoresu.kelmarstore.core.products.data.Product;
import com.andoresu.kelmarstore.core.products.data.ProductsResponse;
import com.andoresu.kelmarstore.list.RecyclerViewFragment;
import com.andoresu.kelmarstore.utils.BaseListResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.OnClick;

public class ProductsFragment extends RecyclerViewFragment<Product> implements ProductsContract.View{

    private ProductsContract.InteractionListener interactionListener;
    private ProductsContract.ActionsListener actionsListener;
    private ProductsResponse productsResponse;

    public static ProductsFragment newInstance(@NonNull ProductsContract.InteractionListener interactionListener) {
        Bundle args = new Bundle();
        ProductsFragment fragment = new ProductsFragment();
        fragment.setArguments(args);
        fragment.setInteractionListener(interactionListener);
        fragment.setCustomTitle("Prestamos");
        fragment.addSearch = true;
        fragment.setHasOptionsMenu(true);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if(bundle != null){}
        actionsListener = new ProductsPresenter(this, getContext());
        init();
    }

    @Override
    public void handleView() {
        super.handleView();
        viewAdapter = new ProductAdapter(getContext(), item -> interactionListener.goToProductDetail(item));
        listRecyclerView.setAdapter(viewAdapter);
        onRefresh();
    }

    public void setInteractionListener(ProductsContract.InteractionListener interactionListener) {
        this.interactionListener = interactionListener;
    }

    private void init(){

    }

    private Map<String, String> getOptions(){
        Map<String, String> options = new HashMap<>();
        options.put("page", currentPage + "");
        options.put("per_page", BaseListResponse.PER_PAGE + "");
        if(searchQuery != null){
            options.put("search", searchQuery);
        }
        return options;
    }


    @Override
    public void showProducts(ProductsResponse productsResponse) {
        this.productsResponse = productsResponse;
        viewAdapter.addAll(productsResponse.products);
        isEmpty();
    }

    @Override
    public void onRefresh(boolean clear) {
        super.onRefresh(clear);
        if(clear){
            viewAdapter.set(new ArrayList<>());
        }
        actionsListener.getProducts(getOptions());
    }

    @Override
    public int getTotalItems() {
        return productsResponse.totalCount;
    }

    @Override
    public void showProgressIndicator(boolean active) {
        showLoading(active);
    }

    @Override
    public void showGlobalError(ErrorResponse errorResponse) {

    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_products;
    }

    @Override
    public void onLogoutFinish() {

    }

    @OnClick(R.id.newProductFloatingActionButton)
    public void newProduct(){
        interactionListener.goToProductDetail(new Product());
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        super.onCreateOptionsMenu(menu, menuInflater);
    }
}
