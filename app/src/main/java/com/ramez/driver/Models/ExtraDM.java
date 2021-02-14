package com.ramez.driver.Models;

public class ExtraDM {
    int extraID, extraQuantity, productID;
    double extraPrice, extraTotalPrice;
    String extraName, extraImage;
    boolean isCheck;

    public ExtraDM() {
    }

    public int getExtraID() {
        return extraID;
    }

    public void setExtraID(int extraID) {
        this.extraID = extraID;
    }

    public int getExtraQuantity() {
        return extraQuantity;
    }

    public void setExtraQuantity(int extraQuantity) {
        this.extraQuantity = extraQuantity;
    }

    public double getExtraPrice() {
        return extraPrice;
    }

    public void setExtraPrice(double extraPrice) {
        this.extraPrice = extraPrice;
    }

    public double getExtraTotalPrice() {
        return extraTotalPrice;
    }

    public void setExtraTotalPrice(double extraTotalPrice) {
        this.extraTotalPrice = extraTotalPrice;
    }

    public String getExtraName() {
        return extraName;
    }

    public void setExtraName(String extraName) {
        this.extraName = extraName;
    }

    public String getExtraImage() {
        return extraImage;
    }

    public void setExtraImage(String extraImage) {
        this.extraImage = extraImage;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }
}
