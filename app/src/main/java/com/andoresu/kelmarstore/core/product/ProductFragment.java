package com.andoresu.kelmarstore.core.product;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.widget.EditText;
import android.widget.TextView;

import com.andoresu.kelmarstore.R;
import com.andoresu.kelmarstore.core.product.data.ProductErrors;
import com.andoresu.kelmarstore.core.products.data.Product;
import com.andoresu.kelmarstore.utils.BaseFragment;
import com.andoresu.kelmarstore.utils.CurrencyEditText;

import butterknife.BindView;
import butterknife.OnClick;

import static com.andoresu.kelmarstore.utils.MyUtils.getFirst;

public class ProductFragment extends BaseFragment implements ProductContract.View {

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

    @BindView(R.id.productNameTextInputLayout)
    public TextInputLayout productNameTextInputLayout;
    @BindView(R.id.productStockTextInputLayout)
    public TextInputLayout productStockTextInputLayout;
    @BindView(R.id.productUnitsTextInputLayout)
    public TextInputLayout productUnitsTextInputLayout;
    @BindView(R.id.productProviderTextInputLayout)
    public TextInputLayout productProviderTextInputLayout;

    private ProductContract.ActionsListener actionsListener;

    private Product product;

    public ProductFragment(){}

    public static ProductFragment newInstance(Product product) {
        Bundle args = new Bundle();
        args.putSerializable(Product.NAME, product);
        ProductFragment fragment = new ProductFragment();
        if(product == null || product.id == null){
            fragment.setCustomTitle("Nuevo Producto");
        }else{
            fragment.setCustomTitle("Detalle Producto");
        }
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if(bundle != null){
            product = (Product) bundle.getSerializable(Product.NAME);
        }
        actionsListener = new ProductPresenter(this, getContext());
    }

    @Override
    public void handleView() {
        showProduct(product);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_product;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void showProduct(Product product) {
        if(product == null){
            return;
        }
        if(product.id != null){
            setCustomTitle("Detalle Producto");
        }
        productNameEditText.setText(product.name);
        if(product.stock != null) {
            productStockEditText.setText(product.stock.toString());
        }
        if(product.units != null) {
            productUnitsEditText.setText(product.units.toString());
        }
        productPurchasePriceEditText.setValue(product.purchasePrice);
        productSalePriceEditText.setValue(product.salePrice);
        productProviderEditText.setText(product.provider);
    }

    @Override
    public void showProductErrors(ProductErrors productErrors) {
        if(productErrors == null){
            showMessage("Ha ocurrido un error");
            return;
        }
        productNameTextInputLayout.setError(getFirst(productErrors.name));
        productStockTextInputLayout.setError(getFirst(productErrors.stock));
        productUnitsTextInputLayout.setError(getFirst(productErrors.units));
        productProviderTextInputLayout.setError(getFirst(productErrors.provider));
        productPurchasePriceEditText.setError(getFirst(productErrors.purchasePrice));
        productSalePriceEditText.setError(getFirst(productErrors.salePrice));

    }

    @Override
    public void onLogoutFinish() {

    }

    @OnClick(R.id.saveButton)
    public void saveProduct(){
        assignProductAttributes();
        removeProductErrors();
        if(product.id != null){
            actionsListener.updateProduct(product);
        }else{
            actionsListener.createProduct(product);
        }
    }

    public void assignProductAttributes(){
        if(product == null){
            product = new Product();
        }
        product.name = productNameEditText.getText().toString();
        try {
            product.stock = Integer.parseInt(productStockEditText.getText().toString());
        }catch (Exception e){e.printStackTrace();}

        try {
            product.units = Integer.parseInt(productUnitsEditText.getText().toString());
        }catch (Exception e){e.printStackTrace();}

        product.purchasePrice = productPurchasePriceEditText.getDoubleValue();
        product.salePrice = productSalePriceEditText.getDoubleValue();
        product.provider = productProviderEditText.getText().toString();

    }

    public void removeProductErrors(){
        showProductErrors(new ProductErrors());
    }
}
