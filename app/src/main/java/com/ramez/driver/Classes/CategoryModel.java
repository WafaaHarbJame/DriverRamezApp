package com.ramez.driver.Classes;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.ramez.driver.Models.ChildCat;

import java.io.Serializable;
import java.util.ArrayList;

public class CategoryModel implements Serializable {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("h_name")
    @Expose
    private String hName;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("parent_id")
    @Expose
    private Object parentId;
    @SerializedName("child_cat")
    @Expose
    private ArrayList<ChildCat> childCat = null;

    public Integer getUserId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getHName() {
        return hName;
    }

    public void setHName(String hName) {
        this.hName = hName;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getUserName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getParentId() {
        return parentId;
    }

    public void setParentId(Object parentId) {
        this.parentId = parentId;
    }

    public ArrayList<ChildCat> getChildCat() {
        return childCat;
    }

    public void setChildCat(ArrayList<ChildCat> childCat) {
        this.childCat = childCat;
    }

    public String getCatName(){
        if(UtilityApp.getLanguage().equals(Constants.English)){
            return name;

        }
        else {
            return hName;
        }
    }
}
