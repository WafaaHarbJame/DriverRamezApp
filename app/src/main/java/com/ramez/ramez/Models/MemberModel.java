package com.ramez.ramez.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MemberModel {

    @SerializedName("id")
    @Expose
    public int id;
    @SerializedName("full_name")
    @Expose
    public String fullName;
    //    @SerializedName("first_name")
//    @Expose
//    public String firstName;
//    @SerializedName("last_name")
//    @Expose
//    public String lastName;
    @SerializedName("email")
    @Expose
    public String email;
    @SerializedName("mobile")
    @Expose
    public String mobile;
    @SerializedName("contact_mobile")
    @Expose
    public String contactMobile;
    @SerializedName("mobile_verified_at")
    @Expose
    public String mobileVerifiedAt;
    @SerializedName("mobile_verification_code")
    @Expose
    public String mobileVerificationCode;
    @SerializedName("city_id")
    @Expose
    public int cityId;
    @SerializedName("logo")
    @Expose
    public String logo;
    @SerializedName("device_token")
    @Expose
    public String deviceToken;
    @SerializedName("device_id")
    @Expose
    public String deviceId;
    @SerializedName("api_token")
    @Expose
    public String apiToken;
    @SerializedName("status")
    @Expose
    public Object status;
    @SerializedName("created_at")
    @Expose
    public String createdAt;
    @SerializedName("updated_at")
    @Expose
    public String updatedAt;
    @SerializedName("deleted_at")
    @Expose
    public Object deletedAt;
    @SerializedName("city")
    @Expose
    public CityModel city;
    @SerializedName("city_name")
    @Expose
    public String cityName;
    public String fcmToken;
    public String password;


    public String getName() {
        return fullName;
    }

    public int getId() {
        return id;
    }


    public boolean getStatus() {
        if (status instanceof Boolean)
            return (boolean) status;
        else if (status instanceof Double)
            return (double) status == 1;
        else
            return false;
    }

    public boolean isVerified() {
        return mobileVerifiedAt != null && !mobileVerifiedAt.isEmpty();
    }

}
