package com.andoresu.kelmarstore.core.products;

import com.andoresu.kelmarstore.core.products.data.Product;
import com.andoresu.kelmarstore.core.products.data.ProductsResponse;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

public interface ProductService {

    @GET("products/")
    Observable<Response<ProductsResponse>> index(@QueryMap Map<String, String> options);

    @GET("products/{id}")
    Observable<Response<Product>> get(@Path("id") Integer id);

    @POST("products/")
    Observable<Response<Product>> create(@Body HashMap<String, Product> productHashMap);

    @PUT("products/{id}")
    Observable<Response<Product>> update(@Path("id") Integer id, @Body HashMap<String, Product> productHashMap);

    @DELETE("products/{id}")
    Observable<Response<Product>> delete(@Path("id") Integer id);

}
