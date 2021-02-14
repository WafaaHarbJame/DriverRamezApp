package com.ramez.driver.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OrderNewModel {


    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("order_code")
    @Expose
    private String orderCode;
    @SerializedName("payment_method")
    @Expose
    private String paymentMethod;
    @SerializedName("expressDelivery")
    @Expose
    private boolean expressDelivery;
    @SerializedName("delivery_date")
    @Expose
    private String deliveryDate;

}
