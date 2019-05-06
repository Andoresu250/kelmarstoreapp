package com.andoresu.kelmarstore.core.products;

import android.content.Context;

import com.andoresu.kelmarstore.client.ObserverResponse;
import com.andoresu.kelmarstore.client.ServiceGenerator;
import com.andoresu.kelmarstore.core.products.data.ProductsResponse;

import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

import static com.andoresu.kelmarstore.client.GsonBuilderUtils.getUserGson;

public class ProductsPresenter implements ProductsContract.ActionsListener{

    private final ProductsContract.View view;
    private final Context context;
    private final ProductService productService;

    public ProductsPresenter(ProductsContract.View view, Context context) {
        this.view = view;
        this.context = context;
        this.productService = ServiceGenerator.createService(ProductService.class, getUserGson());
    }

    @Override
    public void getProducts(Map<String, String> options) {
        productService.index(options)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ObserverResponse<Response<ProductsResponse>>(view){
                    @Override
                    public void onNext(Response<ProductsResponse> response) {
                        super.onNext(response);
                        if(response.isSuccessful()){
                            ProductsResponse productsResponse = response.body();
                            view.showProducts(productsResponse);
                        }
                    }
                });
    }
}
