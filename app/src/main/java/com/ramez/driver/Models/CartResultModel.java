package com.ramez.driver.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class CartResultModel implements Serializable {
    @SerializedName("data")
    @Expose
    private Data data;
    @SerializedName("delivery_charges")
    @Expose
    private Double deliveryCharges;
    @SerializedName("minimum_order_amount")
    @Expose
    private Integer minimumOrderAmount;
    @SerializedName("promo_amount")
    @Expose
    private Integer promoAmount;
    @SerializedName("total_amount")
    @Expose
    private Double totalAmount;
    @SerializedName("cost")
    @Expose
    private Double cost;
    @SerializedName("coupon_code_id")
    @Expose
    private Object couponCodeId;
    @SerializedName("coupon_code")
    @Expose
    private Object couponCode;
    @SerializedName("delivery_times")
    @Expose
    private List<DeliveryTime> deliveryTimes = null;
    @SerializedName("is_delivery_time")
    @Expose
    private String isDeliveryTime;
    @SerializedName("cart_count")
    @Expose
    private Integer cartCount;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public Double getDeliveryCharges() {
        return deliveryCharges;
    }

    public void setDeliveryCharges(Double deliveryCharges) {
        this.deliveryCharges = deliveryCharges;
    }

    public Integer getMinimumOrderAmount() {
        return minimumOrderAmount;
    }

    public void setMinimumOrderAmount(Integer minimumOrderAmount) {
        this.minimumOrderAmount = minimumOrderAmount;
    }

    public Integer getPromoAmount() {
        return promoAmount;
    }

    public void setPromoAmount(Integer promoAmount) {
        this.promoAmount = promoAmount;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public Object getCouponCodeId() {
        return couponCodeId;
    }

    public void setCouponCodeId(Object couponCodeId) {
        this.couponCodeId = couponCodeId;
    }

    public Object getCouponCode() {
        return couponCode;
    }

    public void setCouponCode(Object couponCode) {
        this.couponCode = couponCode;
    }

    public List<DeliveryTime> getDeliveryTimes() {
        return deliveryTimes;
    }

    public void setDeliveryTimes(List<DeliveryTime> deliveryTimes) {
        this.deliveryTimes = deliveryTimes;
    }

    public String getIsDeliveryTime() {
        return isDeliveryTime;
    }

    public void setIsDeliveryTime(String isDeliveryTime) {
        this.isDeliveryTime = isDeliveryTime;
    }

    public Integer getCartCount() {
        return cartCount;
    }

    public void setCartCount(Integer cartCount) {
        this.cartCount = cartCount;
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
