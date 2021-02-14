package com.ramez.driver.Models;

import com.google.android.gms.analytics.ecommerce.Product;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DeliveryStatusResponse {

    @SerializedName("invoice")
    @Expose
    private Invoice invoice;
    @SerializedName("products")
    @Expose
    private List<Product> products = null;

    public Invoice getInvoice() {
        return invoice;
    }

    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
