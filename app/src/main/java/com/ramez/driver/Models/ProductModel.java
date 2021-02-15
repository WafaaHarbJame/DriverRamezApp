package com.ramez.driver.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.ramez.driver.Classes.Constants;
import com.ramez.driver.Classes.UtilityApp;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ProductModel  implements Serializable, Comparable<ProductModel>{

    @SerializedName("brand_id")
    @Expose
    private Integer brandId;
    @SerializedName("category_id")
    @Expose
    private Integer categoryId;
    @SerializedName("description")
    @Expose
    private Object description;
    @SerializedName("favourite")
    @Expose
    private Boolean favourite;
    @SerializedName("product_barcodes")
    @Expose
    private List<ProductBarcode> productBarcodes = null;
    @SerializedName("product_units")
    @Expose
    private Object productUnits;
    @SerializedName("h_description")
    @Expose
    private Object hDescription;
    @SerializedName("h_name")
    @Expose
    private String hName;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("images")
    @Expose
    private ArrayList<String> images = null;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("product_brand")
    @Expose
    private ProductBrand productBrand;
    @SerializedName("rate")
    @Expose
    private Integer rate;

    public Integer getRate() {
        return rate;
    }

    public void setRate(Integer rate) {
        this.rate = rate;
    }

    public Integer getBrandId() {
        return brandId;
    }

    public void setBrandId(Integer brandId) {
        this.brandId = brandId;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public Object getDescription() {
        return description;
    }

    public void setDescription(Object description) {
        this.description = description;
    }

    public Boolean getFavourite() {
        return favourite;
    }

    public void setFavourite(Boolean favourite) {
        this.favourite = favourite;
    }

    public List<ProductBarcode> getProductBarcodes() {
        return productBarcodes;
    }

    public void setProductBarcodes(List<ProductBarcode> productBarcodes) {
        this.productBarcodes = productBarcodes;
    }

    public Object getProductUnits() {
        return productUnits;
    }

    public void setProductUnits(Object productUnits) {
        this.productUnits = productUnits;
    }

    public Object getHDescription() {
        return hDescription;
    }

    public void setHDescription(Object hDescription) {
        this.hDescription = hDescription;
    }

    public String getHName() {
        return hName;
    }

    public void setHName(String hName) {
        this.hName = hName;
    }

    public Integer getUserId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public ArrayList<String> getImages() {
        return images;
    }

    public void setImages(ArrayList<String> images) {
        this.images = images;
    }

    public String getUserName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ProductBrand getProductBrand() {
        return productBrand;
    }

    public void setProductBrand(ProductBrand productBrand) {
        this.productBrand = productBrand;
    }

    public String getProductName(){
        if(UtilityApp.getLanguage().equals(Constants.English)){
            return name;

        }
        else {
            return hName;
        }
    }


    @Override
    public int compareTo(ProductModel productModel) {
        return 0;
    }
}