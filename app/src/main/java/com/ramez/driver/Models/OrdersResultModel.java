package com.ramez.driver.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OrdersResultModel {


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
    @SerializedName("area")
    @Expose
    private String area;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("location")
    @Expose
    private Location location;
    @SerializedName("packer_end_date")
    @Expose
    private String packerEndDate;
    @SerializedName("start_date")
    @Expose
    private String startDate;
    @SerializedName("end_date")
    @Expose
    private String endDate;
    @SerializedName("timesTicks")
    @Expose
    private String timesTicks;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public boolean isExpressDelivery() {
        return expressDelivery;
    }

    public void setExpressDelivery(boolean expressDelivery) {
        this.expressDelivery = expressDelivery;
    }

    public String getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getPackerEndDate() {
        return packerEndDate;
    }

    public void setPackerEndDate(String packerEndDate) {
        this.packerEndDate = packerEndDate;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getTimesTicks() {
        return timesTicks;
    }

    public void setTimesTicks(String timesTicks) {
        this.timesTicks = timesTicks;
    }
}