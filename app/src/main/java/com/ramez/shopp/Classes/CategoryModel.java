package com.ramez.shopp.Classes;

import java.io.Serializable;

public class CategoryModel implements Serializable {
    private int catId;
    private String name_ar;
    private String name_en;
    private String icon;

    public CategoryModel(int catId, String name_ar, String name_en, String icon) {

        this.catId = catId;
        this.name_ar = name_ar;
        this.name_en = name_en;
        this.icon = icon;
    }

    public int getCatId() {
        return catId;
    }

    public void setCatId(int catId) {
        this.catId = catId;
    }

    public String getName_ar() {
        return name_ar;
    }

    public void setName_ar(String name_ar) {
        this.name_ar = name_ar;
    }

    public String getName_en() {
        return name_en;
    }

    public void setName_en(String name_en) {
        this.name_en = name_en;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}
