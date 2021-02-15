package com.ramez.driver.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.ramez.driver.Classes.State;

import java.io.Serializable;

public class MemberModel implements Serializable {
    public int lastSelectedAddress;




    @SerializedName("registerType")
    @Expose
    private String registerType;
    @SerializedName("role_id")
    @Expose
    private Integer role_id=6;


    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("mobile_number")
    @Expose
    private String mobileNumber;
    @SerializedName("device_id")
    @Expose
    private String deviceId;
    @SerializedName("device_type")
    @Expose
    private String deviceType;
    @SerializedName("country")
    @Expose
    private String country;
    @SerializedName("city")
    @Expose
    private int city;
    @SerializedName("prefix")
    @Expose
    private String prefix;
    @SerializedName("device_token")
    @Expose
    private String deviceToken;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("profile_picture")
    @Expose
    private String profilePicture;

    @SerializedName("password")
    @Expose
    private String password;

    @SerializedName("new_password")
    @Expose
    private String new_password;


    @SerializedName("user_type")
    @Expose
    private String userType;

    private String lat_location = "";
    private  String lan_location = "";

    public String getLat_location() {
        return lat_location;
    }

    public void setLat_location(String lat_location) {
        this.lat_location = lat_location;
    }

    public String getLan_location() {
        return lan_location;
    }

    public void setLan_location(String lan_location) {
        this.lan_location = lan_location;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNew_password() {
        return new_password;
    }

    public void setNew_password(String new_password) {
        this.new_password = new_password;
    }

    public int getLastSelectedAddress() {
        return lastSelectedAddress;
    }

    public void setLastSelectedAddress(int lastSelectedAddress) {
        this.lastSelectedAddress = lastSelectedAddress;
    }

    public String getRegisterType() {
        return registerType;
    }

    public void setRegisterType(String registerType) {
        this.registerType = registerType;
    }

    public Integer getRole_id() {
        return role_id;
    }

    public void setRole_id(Integer role_id) {
        this.role_id = role_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getCity() {
        return city;
    }

    public void setCity(int city) {
        this.city = city;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getDeviceToken() {
        return deviceToken;
    }

    public void setDeviceToken(String deviceToken) {
        this.deviceToken = deviceToken;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }
}