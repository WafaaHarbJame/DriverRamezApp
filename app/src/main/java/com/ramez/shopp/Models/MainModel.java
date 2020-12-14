package com.ramez.shopp.Models;

import com.google.android.material.slider.Slider;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class MainModel {
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("fav_count")
    @Expose
    private Integer favCount;
    @SerializedName("cart_count")
    @Expose
    private Integer cartCount;
    @SerializedName("delivery_charges")
    @Expose
    private Integer deliveryCharges;
    @SerializedName("minimum_order_amount")
    @Expose
    private Integer minimumOrderAmount;
    @SerializedName("data")
    @Expose
    private ArrayList<Object> data = null;
    @SerializedName("sliders")
    @Expose
    private ArrayList<Slider> sliders = null;
    @SerializedName("featured")
    @Expose
    private ArrayList<ProductModel> featured = null;
    @SerializedName("quick_products")
    @Expose
    private ArrayList<ProductModel> quickProducts = null;
    @SerializedName("offered_products")
    @Expose
    private ArrayList<ProductModel> offeredProducts = null;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getFavCount() {
        return favCount;
    }

    public void setFavCount(Integer favCount) {
        this.favCount = favCount;
    }

    public Integer getCartCount() {
        return cartCount;
    }

    public void setCartCount(Integer cartCount) {
        this.cartCount = cartCount;
    }

    public Integer getDeliveryCharges() {
        return deliveryCharges;
    }

    public void setDeliveryCharges(Integer deliveryCharges) {
        this.deliveryCharges = deliveryCharges;
    }

    public Integer getMinimumOrderAmount() {
        return minimumOrderAmount;
    }

    public void setMinimumOrderAmount(Integer minimumOrderAmount) {
        this.minimumOrderAmount = minimumOrderAmount;
    }

    public ArrayList<Object> getData() {
        return data;
    }

    public void setData(ArrayList<Object> data) {
        this.data = data;
    }

    public ArrayList<Slider> getSliders() {
        return sliders;
    }

    public void setSliders(ArrayList<Slider> sliders) {
        this.sliders = sliders;
    }

    public ArrayList<ProductModel> getFeatured() {
        return featured;
    }

    public void setFeatured(ArrayList<ProductModel> featured) {
        this.featured = featured;
    }

    public ArrayList<ProductModel> getQuickProducts() {
        return quickProducts;
    }

    public void setQuickProducts(ArrayList<ProductModel> quickProducts) {
        this.quickProducts = quickProducts;
    }

    public ArrayList<ProductModel> getOfferedProducts() {
        return offeredProducts;
    }

    public void setOfferedProducts(ArrayList<ProductModel> offeredProducts) {
        this.offeredProducts = offeredProducts;
    }

}

