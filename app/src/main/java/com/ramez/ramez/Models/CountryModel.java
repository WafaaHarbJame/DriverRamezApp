package com.ramez.ramez.Models;

import android.os.Parcel;


import java.io.Serializable;

/**
 * Created by  on 8/9/2017.
 */

public class CountryModel implements Serializable {

    private int country_id;
    private int isd_code;
    private String country;
    private String currency;
    private String flag;
    private String country_code;
    private String country_code_ar;
    private String country_ar;
    private String created_at;

    public CountryModel(int country_id, int isd_code, String country, String currency, String flag, String country_code, String country_code_ar, String country_ar, String created_at) {
        this.country_id = country_id;
        this.isd_code = isd_code;
        this.country = country;
        this.currency = currency;
        this.flag = flag;
        this.country_code = country_code;
        this.country_code_ar = country_code_ar;
        this.country_ar = country_ar;
        this.created_at = created_at;
    }

    public int getCountry_id() {
        return country_id;
    }

    public void setCountry_id(int country_id) {
        this.country_id = country_id;
    }

    public int getIsd_code() {
        return isd_code;
    }

    public void setIsd_code(int isd_code) {
        this.isd_code = isd_code;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getCountry_code() {
        return country_code;
    }

    public void setCountry_code(String country_code) {
        this.country_code = country_code;
    }

    public String getCountry_code_ar() {
        return country_code_ar;
    }

    public void setCountry_code_ar(String country_code_ar) {
        this.country_code_ar = country_code_ar;
    }

    public String getCountry_ar() {
        return country_ar;
    }

    public void setCountry_ar(String country_ar) {
        this.country_ar = country_ar;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }





    protected CountryModel(Parcel in) {
        this.country_id = in.readInt();
        this.isd_code = in.readInt();
        this.country = in.readString();
        this.currency = in.readString();
        this.flag = in.readString();
        this.country_code = in.readString();
        this.country_code_ar = in.readString();
        this.country_ar = in.readString();
        this.created_at = in.readString();
    }


}
