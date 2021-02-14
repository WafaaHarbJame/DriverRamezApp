package com.ramez.driver.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Location {
    @SerializedName("lat")
    @Expose
    private int lat;
    @SerializedName("lng")
    @Expose
    private int lng;

    public int getLat() {
        return lat;
    }
}
