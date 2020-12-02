package com.ramez.shopp.Classes;

public class CartModel {
    private int productDBID;

    private int shopID = 0;
    private int userID = 0;
    private int productID = 0;
    private int modelID = 0;
    private int productQuantity = 0;
    private String productName = "";
    private String productImage = "";
    private String productPrice = "0";
    private String shopType = "";
    private  int is_special=0;
    private  String pro_special_price="";
    private int pro_stock = 0;

    public CartModel(int productDBID, int shopID, int userID, int productID, int modelID, int productQuantity, String productName, String productImage, String productPrice, String shopType, int is_special, String pro_special_price, int pro_stock) {
        this.productDBID = productDBID;
        this.shopID = shopID;
        this.userID = userID;
        this.productID = productID;
        this.modelID = modelID;
        this.productQuantity = productQuantity;
        this.productName = productName;
        this.productImage = productImage;
        this.productPrice = productPrice;
        this.shopType = shopType;
        this.is_special = is_special;
        this.pro_special_price = pro_special_price;
        this.pro_stock = pro_stock;
    }

    public int getProductDBID() {
        return productDBID;
    }

    public void setProductDBID(int productDBID) {
        this.productDBID = productDBID;
    }

    public int getShopID() {
        return shopID;
    }

    public void setShopID(int shopID) {
        this.shopID = shopID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public int getModelID() {
        return modelID;
    }

    public void setModelID(int modelID) {
        this.modelID = modelID;
    }

    public int getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(int productQuantity) {
        this.productQuantity = productQuantity;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public String getShopType() {
        return shopType;
    }

    public void setShopType(String shopType) {
        this.shopType = shopType;
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

    public int getPro_stock() {
        return pro_stock;
    }

    public void setPro_stock(int pro_stock) {
        this.pro_stock = pro_stock;
    }
}
