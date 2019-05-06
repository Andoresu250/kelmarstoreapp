package com.andoresu.kelmarstore.core.products;

import android.support.design.widget.TextInputLayout;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.andoresu.kelmarstore.R;
import com.andoresu.kelmarstore.core.products.data.Product;
import com.andoresu.kelmarstore.utils.BaseRecyclerViewAdapter;
import com.andoresu.kelmarstore.utils.CurrencyEditText;

import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;

import static com.andoresu.kelmarstore.utils.MyUtils.DISABLE_VIEW;
import static com.andoresu.kelmarstore.utils.MyUtils.HIDE_VIEW;
import static com.andoresu.kelmarstore.utils.MyUtils.NON_FOCUSABLE_VIEW;

public class ProductViewHolder extends  BaseRecyclerViewAdapter.BaseViewHolder<Product> {



    @BindView(R.id.productNameEditText)
    public EditText productNameEditText;
    @BindView(R.id.productStockEditText)
    public EditText productStockEditText;
    @BindView(R.id.productUnitsEditText)
    public EditText productUnitsEditText;
    @BindView(R.id.productPurchasePriceLabelTextView)
    public TextView productPurchasePriceLabelTextView;
    @BindView(R.id.productPurchasePriceEditText)
    public CurrencyEditText productPurchasePriceEditText;
    @BindView(R.id.productSalePriceLabelTextView)
    public TextView productSalePriceLabelTextView;
    @BindView(R.id.productSalePriceEditText)
    public CurrencyEditText productSalePriceEditText;
    @BindView(R.id.productProviderEditText)
    public EditText productProviderEditText;

//    @BindView(R.id.productNameTextInputLayout)
//    public TextInputLayout productNameTextInputLayout;
//    @BindView(R.id.productStockTextInputLayout)
//    public TextInputLayout productStockTextInputLayout;
//    @BindView(R.id.productUnitsTextInputLayout)
//    public TextInputLayout productUnitsTextInputLayout;
//    @BindView(R.id.productProviderTextInputLayout)
//    public TextInputLayout productProviderTextInputLayout;

    @BindView(R.id.detailButton)
    public Button detailButton;

    @BindViews({
            R.id.productNameTextInputLayout,
            R.id.productNameEditText,
            R.id.productStockTextInputLayout,
            R.id.productStockEditText,
            R.id.productUnitsTextInputLayout,
            R.id.productUnitsEditText,
            R.id.productPurchasePriceLabelTextView,
            R.id.productPurchasePriceEditText,
            R.id.productSalePriceLabelTextView,
            R.id.productSalePriceEditText,
            R.id.productProviderTextInputLayout,
            R.id.productProviderEditText,
    })
    public List<View> views;

    @BindViews({
            R.id.productUnitsTextInputLayout,
            R.id.productUnitsEditText,
            R.id.productPurchasePriceLabelTextView,
            R.id.productPurchasePriceEditText
    })
    public List<View> toHide;

    public ProductViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        ButterKnife.apply(views, NON_FOCUSABLE_VIEW);
        ButterKnife.apply(views, DISABLE_VIEW);
        ButterKnife.apply(toHide, HIDE_VIEW);
    }
}
