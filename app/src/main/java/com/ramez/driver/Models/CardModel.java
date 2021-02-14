package com.ramez.driver.Models;

import com.ramez.driver.Classes.UtilityApp;

public class CardModel {
    private int id;
    private String name_ar;
    private String name_en;

    public CardModel(int id, String name_ar, String name_en) {
        this.id = id;
        this.name_ar = name_ar;
        this.name_en = name_en;
    }

    public int getUserId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName_ar() {
        return name_ar;
    }

    public void setName_ar(String name_ar) {
        this.name_ar = name_ar;
    }

    public String getUserName_en() {
        return name_en;
    }

    public void setName_en(String name_en) {
        this.name_en = name_en;
    }
    public String getUserName() {

        return UtilityApp.isEnglish()? name_en : name_ar;
    }
}
