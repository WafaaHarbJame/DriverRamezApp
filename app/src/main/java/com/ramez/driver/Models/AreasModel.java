package com.ramez.driver.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AreasModel {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name_en")
    @Expose
    private String nameEn;
    @SerializedName("name_ae")
    @Expose
    private String nameAe;

    public Integer getUserId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserNameEn() {
        return nameEn;
    }

    public void setNameEn(String nameEn) {
        this.nameEn = nameEn;
    }

    public String getUserNameAe() {
        return nameAe;
    }

    public void setNameAe(String nameAe) {
        this.nameAe = nameAe;
    }

    public AreasModel(Integer id, String nameEn, String nameAe) {
        this.id = id;
        this.nameEn = nameEn;
        this.nameAe = nameAe;
    }
}
