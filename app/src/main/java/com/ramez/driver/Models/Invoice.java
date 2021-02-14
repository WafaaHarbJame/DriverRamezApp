package com.ramez.driver.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Invoice {

    @SerializedName("inv_id")
    @Expose
    private int invId;
    @SerializedName("user_id")
    @Expose
    private int userId;
    @SerializedName("shop_id")
    @Expose
    private int shopId;
    @SerializedName("inv_form")
    @Expose
    private String invForm;
    @SerializedName("pres_img")
    @Expose
    private Object presImg;
    @SerializedName("inv_qua")
    @Expose
    private int invQua;
    @SerializedName("payment_type")
    @Expose
    private String paymentType;
    @SerializedName("inv_type")
    @Expose
    private String invType;
    @SerializedName("payment_status")
    @Expose
    private String paymentStatus;
    @SerializedName("inv_status")
    @Expose
    private String invStatus;
    @SerializedName("delivery_id")
    @Expose
    private int deliveryId;
    @SerializedName("picker_id")
    @Expose
    private int pickerId;
    @SerializedName("picker_status")
    @Expose
    private int pickerStatus;
    @SerializedName("delivery_status")
    @Expose
    private int deliveryStatus;
    @SerializedName("delivery_cost")
    @Expose
    private String deliveryCost;
    @SerializedName("delivery_profit_percent")
    @Expose
    private String deliveryProfitPercent;
    @SerializedName("delivery_profit")
    @Expose
    private String deliveryProfit;
    @SerializedName("app_delivery_profit")
    @Expose
    private String appDeliveryProfit;
    @SerializedName("inv_cancel_note")
    @Expose
    private Object invCancelNote;
    @SerializedName("inv_lat")
    @Expose
    private String invLat;
    @SerializedName("inv_lng")
    @Expose
    private String invLng;
    @SerializedName("inv_total")
    @Expose
    private double invTotal;
    @SerializedName("inv_sub")
    @Expose
    private double invSub;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("total_profit")
    @Expose
    private double totalProfit;
    @SerializedName("shop_type")
    @Expose
    private String shopType;
    @SerializedName("inv_note")
    @Expose
    private Object invNote;
    @SerializedName("inv_address")
    @Expose
    private String invAddress;
    @SerializedName("inv_address_en")
    @Expose
    private String invAddressEn;
    @SerializedName("session_chat_provider")
    @Expose
    private int sessionChatProvider;
    @SerializedName("session_chat_customer")
    @Expose
    private int sessionChatCustomer;
    @SerializedName("address_id")
    @Expose
    private int addressId;
    @SerializedName("used_credits")
    @Expose
    private String usedCredits;
    @SerializedName("is_exprees")
    @Expose
    private int isExprees;
    @SerializedName("delivery_express_id")
    @Expose
    private Object deliveryExpressId;
    @SerializedName("normal_timing_id")
    @Expose
    private int normalTimingId;
    @SerializedName("delivery_comment")
    @Expose
    private Object deliveryComment;
    @SerializedName("invoice_status_id")
    @Expose
    private int invoiceStatusId;
    @SerializedName("coupon_id")
    @Expose
    private Object couponId;
    @SerializedName("coupon_amount")
    @Expose
    private Object couponAmount;
    @SerializedName("points")
    @Expose
    private Object points;
    @SerializedName("redeem_point_amount")
    @Expose
    private String redeemPointAmount;
    @SerializedName("packer")
    @Expose
    private Packer packer;

    public int getInvId() {
        return invId;
    }

    public void setInvId(int invId) {
        this.invId = invId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getShopId() {
        return shopId;
    }

    public void setShopId(int shopId) {
        this.shopId = shopId;
    }

    public String getInvForm() {
        return invForm;
    }

    public void setInvForm(String invForm) {
        this.invForm = invForm;
    }

    public Object getPresImg() {
        return presImg;
    }

    public void setPresImg(Object presImg) {
        this.presImg = presImg;
    }

    public int getInvQua() {
        return invQua;
    }

    public void setInvQua(int invQua) {
        this.invQua = invQua;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public String getInvType() {
        return invType;
    }

    public void setInvType(String invType) {
        this.invType = invType;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getInvStatus() {
        return invStatus;
    }

    public void setInvStatus(String invStatus) {
        this.invStatus = invStatus;
    }

    public int getDeliveryId() {
        return deliveryId;
    }

    public void setDeliveryId(int deliveryId) {
        this.deliveryId = deliveryId;
    }

    public int getPickerId() {
        return pickerId;
    }

    public void setPickerId(int pickerId) {
        this.pickerId = pickerId;
    }

    public int getPickerStatus() {
        return pickerStatus;
    }

    public void setPickerStatus(int pickerStatus) {
        this.pickerStatus = pickerStatus;
    }

    public int getDeliveryStatus() {
        return deliveryStatus;
    }

    public void setDeliveryStatus(int deliveryStatus) {
        this.deliveryStatus = deliveryStatus;
    }

    public String getDeliveryCost() {
        return deliveryCost;
    }

    public void setDeliveryCost(String deliveryCost) {
        this.deliveryCost = deliveryCost;
    }

    public String getDeliveryProfitPercent() {
        return deliveryProfitPercent;
    }

    public void setDeliveryProfitPercent(String deliveryProfitPercent) {
        this.deliveryProfitPercent = deliveryProfitPercent;
    }

    public String getDeliveryProfit() {
        return deliveryProfit;
    }

    public void setDeliveryProfit(String deliveryProfit) {
        this.deliveryProfit = deliveryProfit;
    }

    public String getAppDeliveryProfit() {
        return appDeliveryProfit;
    }

    public void setAppDeliveryProfit(String appDeliveryProfit) {
        this.appDeliveryProfit = appDeliveryProfit;
    }

    public Object getInvCancelNote() {
        return invCancelNote;
    }

    public void setInvCancelNote(Object invCancelNote) {
        this.invCancelNote = invCancelNote;
    }

    public String getInvLat() {
        return invLat;
    }

    public void setInvLat(String invLat) {
        this.invLat = invLat;
    }

    public String getInvLng() {
        return invLng;
    }

    public void setInvLng(String invLng) {
        this.invLng = invLng;
    }

    public double getInvTotal() {
        return invTotal;
    }

    public void setInvTotal(double invTotal) {
        this.invTotal = invTotal;
    }

    public double getInvSub() {
        return invSub;
    }

    public void setInvSub(double invSub) {
        this.invSub = invSub;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public double getTotalProfit() {
        return totalProfit;
    }

    public void setTotalProfit(double totalProfit) {
        this.totalProfit = totalProfit;
    }

    public String getShopType() {
        return shopType;
    }

    public void setShopType(String shopType) {
        this.shopType = shopType;
    }

    public Object getInvNote() {
        return invNote;
    }

    public void setInvNote(Object invNote) {
        this.invNote = invNote;
    }

    public String getInvAddress() {
        return invAddress;
    }

    public void setInvAddress(String invAddress) {
        this.invAddress = invAddress;
    }

    public String getInvAddressEn() {
        return invAddressEn;
    }

    public void setInvAddressEn(String invAddressEn) {
        this.invAddressEn = invAddressEn;
    }

    public int getSessionChatProvider() {
        return sessionChatProvider;
    }

    public void setSessionChatProvider(int sessionChatProvider) {
        this.sessionChatProvider = sessionChatProvider;
    }

    public int getSessionChatCustomer() {
        return sessionChatCustomer;
    }

    public void setSessionChatCustomer(int sessionChatCustomer) {
        this.sessionChatCustomer = sessionChatCustomer;
    }

    public int getAddressId() {
        return addressId;
    }

    public void setAddressId(int addressId) {
        this.addressId = addressId;
    }

    public String getUsedCredits() {
        return usedCredits;
    }

    public void setUsedCredits(String usedCredits) {
        this.usedCredits = usedCredits;
    }

    public int getIsExprees() {
        return isExprees;
    }

    public void setIsExprees(int isExprees) {
        this.isExprees = isExprees;
    }

    public Object getDeliveryExpressId() {
        return deliveryExpressId;
    }

    public void setDeliveryExpressId(Object deliveryExpressId) {
        this.deliveryExpressId = deliveryExpressId;
    }

    public int getNormalTimingId() {
        return normalTimingId;
    }

    public void setNormalTimingId(int normalTimingId) {
        this.normalTimingId = normalTimingId;
    }

    public Object getDeliveryComment() {
        return deliveryComment;
    }

    public void setDeliveryComment(Object deliveryComment) {
        this.deliveryComment = deliveryComment;
    }

    public int getInvoiceStatusId() {
        return invoiceStatusId;
    }

    public void setInvoiceStatusId(int invoiceStatusId) {
        this.invoiceStatusId = invoiceStatusId;
    }

    public Object getCouponId() {
        return couponId;
    }

    public void setCouponId(Object couponId) {
        this.couponId = couponId;
    }

    public Object getCouponAmount() {
        return couponAmount;
    }

    public void setCouponAmount(Object couponAmount) {
        this.couponAmount = couponAmount;
    }

    public Object getPoints() {
        return points;
    }

    public void setPoints(Object points) {
        this.points = points;
    }

    public String getRedeemPointAmount() {
        return redeemPointAmount;
    }

    public void setRedeemPointAmount(String redeemPointAmount) {
        this.redeemPointAmount = redeemPointAmount;
    }

    public Packer getPacker() {
        return packer;
    }

    public void setPacker(Packer packer) {
        this.packer = packer;
    }
}
