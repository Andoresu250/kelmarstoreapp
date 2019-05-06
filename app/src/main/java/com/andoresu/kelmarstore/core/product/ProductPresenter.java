package com.andoresu.kelmarstore.core.product;

import android.content.Context;

import com.andoresu.kelmarstore.client.ObserverResponse;
import com.andoresu.kelmarstore.client.ObserverResponseWhitError;
import com.andoresu.kelmarstore.client.ServiceGenerator;
import com.andoresu.kelmarstore.core.product.data.ProductErrorsResponse;
import com.andoresu.kelmarstore.core.products.ProductService;
import com.andoresu.kelmarstore.core.products.data.Product;

import java.util.HashMap;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

import static com.andoresu.kelmarstore.client.GsonBuilderUtils.getUserGson;

public class ProductPresenter implements ProductContract.ActionsListener{

    private final ProductContract.View view;
    private final Context context;
    private final ProductService productService;

    public ProductPresenter(ProductContract.View view, Context context) {
        this.view = view;
        this.context = context;
        this.productService = ServiceGenerator.createService(ProductService.class, getUserGson());
    }

    @Override
    public void createProduct(Product product) {
        HashMap<String, Product> hashMap = new HashMap<>();
        hashMap.put("product", product);
        productService.create(hashMap)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ObserverResponseWhitError<Response<Product>, ProductErrorsResponse>(view, ProductErrorsResponse.class){
                    @Override
                    public void onNext(Response<Product> response) {
                        super.onNext(response);
                        if(response.isSuccessful()){
                            Product product = response.body();
                            view.showProduct(product);
                            view.showMessage("producto Creado exitosamente");
                        }else{
                            if(getError() != null) {
                                view.showProductErrors(getError().errors);
                            }
                        }

                    }
                });
    }

    @Override
    public void updateProduct(Product product) {
        HashMap<String, Product> hashMap = new HashMap<>();
        hashMap.put("product", product);
        productService.update(product.id, hashMap)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ObserverResponseWhitError<Response<Product>, ProductErrorsResponse>(view, ProductErrorsResponse.class){
                    @Override
                    public void onNext(Response<Product> response) {
                        super.onNext(response);
                        if(response.isSuccessful()){
                            Product product = response.body();
                            view.showProduct(product);
                            view.showMessage("producto Actualizado exitosamente");
                        }else{
                            if(getError() != null) {
                                view.showProductErrors(getError().errors);
                            }
                        }
                    }
                });
    }

    @Override
    public void deleteProduct(Product product) {
        productService.delete(product.id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ObserverResponse<Response<Product>>(view){
                    @Override
                    public void onNext(Response<Product> response) {
                        super.onNext(response);
                        if(response.isSuccessful()){
                            //TODO: show successful message
                        }
                    }
                });
    }
}
