package com.ramez.driver.Classes;

import android.graphics.Region;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.ramez.driver.Models.City;

public class LoginModel {

    @SerializedName("user_id")
    @Expose
    private int userId;
    @SerializedName("user_rate")
    @Expose
    private Object userRate;
    @SerializedName("user_name")
    @Expose
    private String userName;
    @SerializedName("user_img")
    @Expose
    private Object userImg;
    @SerializedName("user_email")
    @Expose
    private String userEmail;
    @SerializedName("user_mobile")
    @Expose
    private String userMobile;
    @SerializedName("role_id")
    @Expose
    private int roleId;
    @SerializedName("state_id")
    @Expose
    private int stateId;
    @SerializedName("city_id")
    @Expose
    private int cityId;
    @SerializedName("region_id")
    @Expose
    private int regionId;
    @SerializedName("user_street")
    @Expose
    private String userStreet;
    @SerializedName("user_building_num")
    @Expose
    private String userBuildingNum;
    @SerializedName("user_floor_num")
    @Expose
    private String userFloorNum;
    @SerializedName("user_flat_num")
    @Expose
    private String userFlatNum;
    @SerializedName("is_active")
    @Expose
    private int isActive;
    @SerializedName("is_suspended")
    @Expose
    private int isSuspended;
    @SerializedName("user_lat")
    @Expose
    private Object userLat;
    @SerializedName("user_lng")
    @Expose
    private Object userLng;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("email_verified_at")
    @Expose
    private Object emailVerifiedAt;
    @SerializedName("credit")
    @Expose
    private int credit;
    @SerializedName("confirm_code")
    @Expose
    private Object confirmCode;
    @SerializedName("recover")
    @Expose
    private Object recover;
    @SerializedName("notify")
    @Expose
    private int notify;
    @SerializedName("user_country_code")
    @Expose
    private Object userCountryCode;
    @SerializedName("coin")
    @Expose
    private Object coin;
    @SerializedName("ramez_login_id")
    @Expose
    private Object ramezLoginId;
    @SerializedName("customer_type")
    @Expose
    private Object customerType;
    @SerializedName("customer_type_comment")
    @Expose
    private Object customerTypeComment;
    @SerializedName("image_url")
    @Expose
    private String imageUrl;
    @SerializedName("state")
    @Expose
    private State state;
    @SerializedName("city")
    @Expose
    private City city;
    @SerializedName("region")
    @Expose
    private Region region;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Object getUserRate() {
        return userRate;
    }

    public void setUserRate(Object userRate) {
        this.userRate = userRate;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Object getUserImg() {
        return userImg;
    }

    public void setUserImg(Object userImg) {
        this.userImg = userImg;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserMobile() {
        return userMobile;
    }

    public void setUserMobile(String userMobile) {
        this.userMobile = userMobile;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public int getStateId() {
        return stateId;
    }

    public void setStateId(int stateId) {
        this.stateId = stateId;
    }

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    public int getRegionId() {
        return regionId;
    }

    public void setRegionId(int regionId) {
        this.regionId = regionId;
    }

    public String getUserStreet() {
        return userStreet;
    }

    public void setUserStreet(String userStreet) {
        this.userStreet = userStreet;
    }

    public String getUserBuildingNum() {
        return userBuildingNum;
    }

    public void setUserBuildingNum(String userBuildingNum) {
        this.userBuildingNum = userBuildingNum;
    }

    public String getUserFloorNum() {
        return userFloorNum;
    }

    public void setUserFloorNum(String userFloorNum) {
        this.userFloorNum = userFloorNum;
    }

    public String getUserFlatNum() {
        return userFlatNum;
    }

    public void setUserFlatNum(String userFlatNum) {
        this.userFlatNum = userFlatNum;
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

    public Object getUserLat() {
        return userLat;
    }

    public void setUserLat(Object userLat) {
        this.userLat = userLat;
    }

    public Object getUserLng() {
        return userLng;
    }

    public void setUserLng(Object userLng) {
        this.userLng = userLng;
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

    public Object getEmailVerifiedAt() {
        return emailVerifiedAt;
    }

    public void setEmailVerifiedAt(Object emailVerifiedAt) {
        this.emailVerifiedAt = emailVerifiedAt;
    }

    public int getCredit() {
        return credit;
    }

    public void setCredit(int credit) {
        this.credit = credit;
    }

    public Object getConfirmCode() {
        return confirmCode;
    }

    public void setConfirmCode(Object confirmCode) {
        this.confirmCode = confirmCode;
    }

    public Object getRecover() {
        return recover;
    }

    public void setRecover(Object recover) {
        this.recover = recover;
    }

    public int getNotify() {
        return notify;
    }

    public void setNotify(int notify) {
        this.notify = notify;
    }

    public Object getUserCountryCode() {
        return userCountryCode;
    }

    public void setUserCountryCode(Object userCountryCode) {
        this.userCountryCode = userCountryCode;
    }

    public Object getCoin() {
        return coin;
    }

    public void setCoin(Object coin) {
        this.coin = coin;
    }

    public Object getRamezLoginId() {
        return ramezLoginId;
    }

    public void setRamezLoginId(Object ramezLoginId) {
        this.ramezLoginId = ramezLoginId;
    }

    public Object getCustomerType() {
        return customerType;
    }

    public void setCustomerType(Object customerType) {
        this.customerType = customerType;
    }

    public Object getCustomerTypeComment() {
        return customerTypeComment;
    }

    public void setCustomerTypeComment(Object customerTypeComment) {
        this.customerTypeComment = customerTypeComment;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public Region getRegion() {
        return region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }
}
