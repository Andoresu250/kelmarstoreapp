package com.andoresu.kelmarstore.core.products;

import com.andoresu.kelmarstore.core.products.data.Product;
import com.andoresu.kelmarstore.core.products.data.ProductsResponse;
import com.andoresu.kelmarstore.utils.BaseView;

import java.util.Map;

public interface ProductsContract {
    interface View extends BaseView {

        void showProducts(ProductsResponse productsResponse);

    }

    interface  ActionsListener {

        void getProducts(Map<String, String> options);

    }

    interface InteractionListener {

        void goToProductDetail(Product product);

    }
}
