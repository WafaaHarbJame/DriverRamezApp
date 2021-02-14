package com.ramez.driver.Models;



public class LocalModel {

    private Integer countryId=17;
    private String countryName="Bahrain";
    private String shortname="BH";
    private Integer phonecode=973;
    private String currencyCode="BHD";
    private String cityId="7263";
    private int fractional=3;
    private int minimum_order_amount=0;


    public Integer getCountryId() {
        return countryId;
    }

    public void setCountryId(Integer countryId) {
        this.countryId = countryId;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
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

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public int getFractional() {
        return fractional;
    }

    public void setFractional(int fractional) {
        this.fractional = fractional;
    }

    public int getMinimum_order_amount() {
        return minimum_order_amount;
    }

    public void setMinimum_order_amount(int minimum_order_amount) {
        this.minimum_order_amount = minimum_order_amount;
    }
}