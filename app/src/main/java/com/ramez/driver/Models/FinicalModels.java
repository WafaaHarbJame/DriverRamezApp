package com.ramez.driver.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FinicalModels {
    @SerializedName("shop_id")
    @Expose
    private int shopId;
    @SerializedName("shop_rate")
    @Expose
    private Object shopRate;
    @SerializedName("shop_admin_name")
    @Expose
    private String shopAdminName;
    @SerializedName("shop_mobile")
    @Expose
    private String shopMobile;
    @SerializedName("shop_name")
    @Expose
    private String shopName;
    @SerializedName("shop_name_en")
    @Expose
    private String shopNameEn;
    @SerializedName("region_id")
    @Expose
    private int regionId;
    @SerializedName("cat_id")
    @Expose
    private int catId;
    @SerializedName("is_active")
    @Expose
    private int isActive;
    @SerializedName("is_suspended")
    @Expose
    private int isSuspended;
    @SerializedName("shop_img")
    @Expose
    private String shopImg;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("user_id")
    @Expose
    private int userId;
    @SerializedName("shop_lat")
    @Expose
    private String shopLat;
    @SerializedName("shop_lng")
    @Expose
    private String shopLng;
    @SerializedName("shop_profit")
    @Expose
    private int shopProfit;
    @SerializedName("shop_delivery_cost")
    @Expose
    private int shopDeliveryCost;
    @SerializedName("shop_type")
    @Expose
    private String shopType;
    @SerializedName("static_delivery_distance")
    @Expose
    private String staticDeliveryDistance;
    @SerializedName("static_distance_cost")
    @Expose
    private String staticDistanceCost;
    @SerializedName("extra_distance_cost")
    @Expose
    private String extraDistanceCost;
    @SerializedName("delivery_profit")
    @Expose
    private int deliveryProfit;
    @SerializedName("delivery_cost_option")
    @Expose
    private String deliveryCostOption;
    @SerializedName("banner")
    @Expose
    private String banner;
    @SerializedName("shop_slogan")
    @Expose
    private String shopSlogan;
    @SerializedName("shop_slogan_en")
    @Expose
    private String shopSloganEn;
    @SerializedName("minimum_amount_for_free_shipping")
    @Expose
    private String minimumAmountForFreeShipping;

    public int getShopId() {
        return shopId;
    }

    public void setShopId(int shopId) {
        this.shopId = shopId;
    }

    public Object getShopRate() {
        return shopRate;
    }

    public void setShopRate(Object shopRate) {
        this.shopRate = shopRate;
    }

    public String getShopAdminName() {
        return shopAdminName;
    }

    public void setShopAdminName(String shopAdminName) {
        this.shopAdminName = shopAdminName;
    }

    public String getShopMobile() {
        return shopMobile;
    }

    public void setShopMobile(String shopMobile) {
        this.shopMobile = shopMobile;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getShopNameEn() {
        return shopNameEn;
    }

    public void setShopNameEn(String shopNameEn) {
        this.shopNameEn = shopNameEn;
    }

    public int getRegionId() {
        return regionId;
    }

    public void setRegionId(int regionId) {
        this.regionId = regionId;
    }

    public int getCatId() {
        return catId;
    }

    public void setCatId(int catId) {
        this.catId = catId;
    }

    public int getIsActive() {
        return isActive;
    }

    public void setIsActive(int isActive) {
        this.isActive = isActive;
    }

    public int getIsSuspended() {
        return isSuspended;
    }

    public void setIsSuspended(int isSuspended) {
        this.isSuspended = isSuspended;
    }

    public String getShopImg() {
        return shopImg;
    }

    public void setShopImg(String shopImg) {
        this.shopImg = shopImg;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getShopLat() {
        return shopLat;
    }

    public void setShopLat(String shopLat) {
        this.shopLat = shopLat;
    }

    public String getShopLng() {
        return shopLng;
    }

    public void setShopLng(String shopLng) {
        this.shopLng = shopLng;
    }

    public int getShopProfit() {
        return shopProfit;
    }

    public void setShopProfit(int shopProfit) {
        this.shopProfit = shopProfit;
    }

    public int getShopDeliveryCost() {
        return shopDeliveryCost;
    }

    public void setShopDeliveryCost(int shopDeliveryCost) {
        this.shopDeliveryCost = shopDeliveryCost;
    }

    public String getShopType() {
        return shopType;
    }

    public void setShopType(String shopType) {
        this.shopType = shopType;
    }

    public String getStaticDeliveryDistance() {
        return staticDeliveryDistance;
    }

    public void setStaticDeliveryDistance(String staticDeliveryDistance) {
        this.staticDeliveryDistance = staticDeliveryDistance;
    }

    public String getStaticDistanceCost() {
        return staticDistanceCost;
    }

    public void setStaticDistanceCost(String staticDistanceCost) {
        this.staticDistanceCost = staticDistanceCost;
    }

    public String getExtraDistanceCost() {
        return extraDistanceCost;
    }

    public void setExtraDistanceCost(String extraDistanceCost) {
        this.extraDistanceCost = extraDistanceCost;
    }

    public int getDeliveryProfit() {
        return deliveryProfit;
    }

    public void setDeliveryProfit(int deliveryProfit) {
        this.deliveryProfit = deliveryProfit;
    }

    public String getDeliveryCostOption() {
        return deliveryCostOption;
    }

    public void setDeliveryCostOption(String deliveryCostOption) {
        this.deliveryCostOption = deliveryCostOption;
    }

    public String getBanner() {
        return banner;
    }

    public void setBanner(String banner) {
        this.banner = banner;
    }

    public String getShopSlogan() {
        return shopSlogan;
    }

    public void setShopSlogan(String shopSlogan) {
        this.shopSlogan = shopSlogan;
    }

    public String getShopSloganEn() {
        return shopSloganEn;
    }

    public void setShopSloganEn(String shopSloganEn) {
        this.shopSloganEn = shopSloganEn;
    }

    public String getMinimumAmountForFreeShipping() {
        return minimumAmountForFreeShipping;
    }

    public void setMinimumAmountForFreeShipping(String minimumAmountForFreeShipping) {
        this.minimumAmountForFreeShipping = minimumAmountForFreeShipping;
    }

}
