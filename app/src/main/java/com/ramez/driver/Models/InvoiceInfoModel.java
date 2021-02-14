package com.ramez.driver.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class InvoiceInfoModel {
    @SerializedName("image_url")
    @Expose
    private String imageUrl;
    @SerializedName("total_amount")
    @Expose
    private double totalAmount;
    @SerializedName("client_name")
    @Expose
    private String clientName;
    @SerializedName("client_mobile_number")
    @Expose
    private String clientMobileNumber;
    @SerializedName("adrs_mobile_number")
    @Expose
    private String adrsMobileNumber;
    @SerializedName("adrs_name")
    @Expose
    private String adrsName;
    @SerializedName("prefix")
    @Expose
    private String prefix;
    @SerializedName("order_id")
    @Expose
    private int orderId;
    @SerializedName("order_code")
    @Expose
    private String orderCode;
    @SerializedName("full_address")
    @Expose
    private String fullAddress;
    @SerializedName("delivery_date")
    @Expose
    private String deliveryDate;
    @SerializedName("payment_method")
    @Expose
    private String paymentMethod;
    @SerializedName("packer_start")
    @Expose
    private String packerStart;
    @SerializedName("packer_end")
    @Expose
    private String packerEnd;
    @SerializedName("driver_start")
    @Expose
    private String driverStart;
    @SerializedName("driver_end")
    @Expose
    private String driverEnd;
    @SerializedName("orderItemDetails")
    @Expose
    private List<OrderItemDetail> orderItemDetails = null;

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getClientMobileNumber() {
        return clientMobileNumber;
    }

    public void setClientMobileNumber(String clientMobileNumber) {
        this.clientMobileNumber = clientMobileNumber;
    }

    public String getAdrsMobileNumber() {
        return adrsMobileNumber;
    }

    public void setAdrsMobileNumber(String adrsMobileNumber) {
        this.adrsMobileNumber = adrsMobileNumber;
    }

    public String getAdrsName() {
        return adrsName;
    }

    public void setAdrsName(String adrsName) {
        this.adrsName = adrsName;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getFullAddress() {
        return fullAddress;
    }

    public void setFullAddress(String fullAddress) {
        this.fullAddress = fullAddress;
    }

    public String getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getPackerStart() {
        return packerStart;
    }

    public void setPackerStart(String packerStart) {
        this.packerStart = packerStart;
    }

    public String getPackerEnd() {
        return packerEnd;
    }

    public void setPackerEnd(String packerEnd) {
        this.packerEnd = packerEnd;
    }

    public String getDriverStart() {
        return driverStart;
    }

    public void setDriverStart(String driverStart) {
        this.driverStart = driverStart;
    }

    public String getDriverEnd() {
        return driverEnd;
    }

    public void setDriverEnd(String driverEnd) {
        this.driverEnd = driverEnd;
    }

    public List<OrderItemDetail> getOrderItemDetails() {
        return orderItemDetails;
    }

    public void setOrderItemDetails(List<OrderItemDetail> orderItemDetails) {
        this.orderItemDetails = orderItemDetails;
    }

}
