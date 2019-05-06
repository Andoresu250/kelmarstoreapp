package com.andoresu.kelmarstore.core.products.data;

import com.andoresu.kelmarstore.utils.BaseListResponse;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ProductsResponse extends BaseListResponse implements Serializable {

    public List<Product> products = new ArrayList<>();
}
