package com.ramez.driver.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

class ProductBrand  implements Serializable {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("brand_name")
    @Expose
    private String brandName;

    public Integer getUserId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }
}
