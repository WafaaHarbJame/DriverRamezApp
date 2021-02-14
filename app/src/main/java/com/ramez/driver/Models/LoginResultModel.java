package com.ramez.driver.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.ramez.driver.Classes.LoginModel;

public class LoginResultModel {

    @SerializedName("cart_count")
    @Expose
    private Integer cartCount;
    @SerializedName("fav_count")
    @Expose
    private Integer favCount;
    @SerializedName("otp")
    @Expose
    private String otp;
    @SerializedName("data")
    @Expose
    public MemberModel data;
    @SerializedName("user_address")
    @Expose
    private Object userAddress;
    @SerializedName("refer_message")
    @Expose
    private String referMessage;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;

    public Integer getCartCount() {
        return cartCount;
    }

    public void setCartCount(Integer cartCount) {
        this.cartCount = cartCount;
    }

    public Integer getFavCount() {
        return favCount;
    }

    public void setFavCount(Integer favCount) {
        this.favCount = favCount;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }


    public MemberModel getData() {
        return data;
    }

    public void setData(MemberModel data) {
        this.data = data;
    }

    public Object getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(Object userAddress) {
        this.userAddress = userAddress;
    }

    public String getReferMessage() {
        return referMessage;
    }

    public void setReferMessage(String referMessage) {
        this.referMessage = referMessage;
    }

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

}