package com.andoresu.kelmarstore.core.products;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.andoresu.kelmarstore.R;
import com.andoresu.kelmarstore.core.products.data.Product;
import com.andoresu.kelmarstore.list.RecyclerViewAdapter;

public class ProductAdapter extends RecyclerViewAdapter<Product> {

    public ProductAdapter(Context context, OnItemClickListener<Product> listener) {
        super(context, listener);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void setData(BaseViewHolder<Product> holder, int position) {
        ProductViewHolder viewHolder = (ProductViewHolder) holder;
        Product product = get(position);
        viewHolder.productNameEditText.setText(product.name);
        viewHolder.productStockEditText.setText(product.stock.toString());
        viewHolder.productUnitsEditText.setText(product.units.toString());
        viewHolder.productPurchasePriceEditText.setValue(product.purchasePrice);
        viewHolder.productSalePriceEditText.setValue(product.salePrice);
        viewHolder.productProviderEditText.setText(product.provider);
        viewHolder.detailButton.setOnClickListener(view -> {
            if(listener != null){
                listener.onItemClick(product);
            }
        });
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.item_product;
    }

    @NonNull
    @Override
    public BaseViewHolder<Product> onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(getLayoutResId(), parent, false);
        return new ProductViewHolder(view);
    }
}
