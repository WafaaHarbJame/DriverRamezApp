package com.ramez.ramez.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CityModel {

    @SerializedName("id")
    @Expose
    public int id;
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("status")
    @Expose
    public int status;
    @SerializedName("created_at")
    @Expose
    public Object createdAt;
    @SerializedName("updated_at")
    @Expose
    public String updatedAt;
    @SerializedName("deleted_at")
    @Expose
    public Object deletedAt;


}
