package com.andoresu.kelmarstore.core.product;

import com.andoresu.kelmarstore.core.product.data.ProductErrors;
import com.andoresu.kelmarstore.core.products.data.Product;
import com.andoresu.kelmarstore.utils.BaseView;

public interface ProductContract {
    interface View extends BaseView {

        void showProduct(Product product);

        void showProductErrors(ProductErrors productErrors);

    }

    interface  ActionsListener {

        void createProduct(Product product);

        void updateProduct(Product product);

        void deleteProduct(Product product);

    }

    interface InteractionListener {}
}
