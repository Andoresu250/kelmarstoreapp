package com.andoresu.kelmarstore.core.products.data;

import com.andoresu.kelmarstore.utils.BaseDataModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import static com.andoresu.kelmarstore.utils.MyUtils.toMoney;

public class Product extends BaseDataModel implements Serializable {

    public static final String NAME = Product.class.getSimpleName();

    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("stock")
    @Expose
    public Integer stock;
    @SerializedName("purchasePrice")
    @Expose
    public Double purchasePrice;
    @SerializedName("salePrice")
    @Expose
    public Double salePrice;
    @SerializedName("unitPrice")
    @Expose
    public Double unitPrice;
    @SerializedName("units")
    @Expose
    public Integer units;
    @SerializedName("photo")
    @Expose
    public String photo;
    @SerializedName("provider")
    @Expose
    public String provider;

    public String getSalePrice(){
        return toMoney(salePrice);
    }

    public String getPurchasePrice(){
        return toMoney(purchasePrice);
    }

}
