package com.ramez.driver.Models;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by  on 8/9/2017.
 */

public class CountryModel implements Serializable {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("shortname")
    @Expose
    private String shortname;
    @SerializedName("phonecode")
    @Expose
    private Integer phonecode;
    @SerializedName("currency_code")
    @Expose
    private String currencyCode;
    @SerializedName("fractional")
    @Expose
    private Integer fractional;
    @SerializedName("flag")
    @Expose
    private int flag;

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public Integer getUserId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortname() {
        return shortname;
    }

    public void setShortname(String shortname) {
        this.shortname = shortname;
    }

    public Integer getPhonecode() {
        return phonecode;
    }

    public void setPhonecode(Integer phonecode) {
        this.phonecode = phonecode;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public Integer getFractional() {
        return fractional;
    }

    public void setFractional(Integer fractional) {
        this.fractional = fractional;
    }

    public CountryModel(Integer id, String name, String shortname, Integer phonecode, String currencyCode, Integer fractional, int flag) {
        this.id = id;
        this.name = name;
        this.shortname = shortname;
        this.phonecode = phonecode;
        this.currencyCode = currencyCode;
        this.fractional = fractional;
        this.flag = flag;
    }
}






