package com.ramez.ramez.Models;

public class ProductModel {

    private int pro_id = 0;
    private String pro_name_ar = "pro_name";
    private String  pro_name_en;
    private String pro_price = "";
    private String pro_img = "";
    private  String discountPercentage;
    private  int is_special=0;
    private  String pro_special_price="";
    private int IsFavorite;

    public ProductModel(int pro_id, String pro_name_ar, String pro_name_en, String pro_price, String pro_img, String discountPercentage, int is_special, String pro_special_price, int isFavorite) {
        this.pro_id = pro_id;
        this.pro_name_ar = pro_name_ar;
        this.pro_name_en = pro_name_en;
        this.pro_price = pro_price;
        this.pro_img = pro_img;
        this.discountPercentage = discountPercentage;
        this.is_special = is_special;
        this.pro_special_price = pro_special_price;
        IsFavorite = isFavorite;
    }

    public int getPro_id() {
        return pro_id;
    }

    public void setPro_id(int pro_id) {
        this.pro_id = pro_id;
    }

    public String getPro_name_ar() {
        return pro_name_ar;
    }

    public void setPro_name_ar(String pro_name_ar) {
        this.pro_name_ar = pro_name_ar;
    }

    public String getPro_name_en() {
        return pro_name_en;
    }

    public void setPro_name_en(String pro_name_en) {
        this.pro_name_en = pro_name_en;
    }

    public String getPro_price() {
        return pro_price;
    }

    public void setPro_price(String pro_price) {
        this.pro_price = pro_price;
    }

    public String getPro_img() {
        return pro_img;
    }

    public void setPro_img(String pro_img) {
        this.pro_img = pro_img;
    }

    public String getDiscountPercentage() {
        return discountPercentage;
    }

    public void setDiscountPercentage(String discountPercentage) {
        this.discountPercentage = discountPercentage;
    }

    public int getIs_special() {
        return is_special;
    }

    public void setIs_special(int is_special) {
        this.is_special = is_special;
    }

    public String getPro_special_price() {
        return pro_special_price;
    }

    public void setPro_special_price(String pro_special_price) {
        this.pro_special_price = pro_special_price;
    }

    public int getIsFavorite() {
        return IsFavorite;
    }

    public void setIsFavorite(int isFavorite) {
        IsFavorite = isFavorite;
    }
}
