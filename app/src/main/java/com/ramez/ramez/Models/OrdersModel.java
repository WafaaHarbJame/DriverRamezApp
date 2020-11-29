package com.ramez.ramez.Models;

import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.ramez.ramez.R;


public class OrdersModel {

    @SerializedName("inv_id")
    @Expose
    private int invID;
    @SerializedName("shop_name")
    @Expose
    private String shopName;
    @SerializedName("shop_name")
    @Expose
    private String shopAddress;
    @SerializedName("shop_name")
    @Expose
    private String orderDate;
    private String orderPrice;
    private String orderStatus;
    private String orderLat;
    private String orderLng;
    private String shopType;
    private String orderDayName;
    private String orderDayTime;
    private String DeliveryDate;
    private String DeliveryDay;
    private String DeliveryTime;
    private int shopID;
    private  Integer delivery_id;
    private String delivery_name;
    private String delivery_phone;

    private String placed_step_name_en;
    private String placed_step_name_ar;
    private int placed_is_step_finished;

    private String Processing_step_name_en;
    private String Processing_step_name_ar;
    private int Processing_is_step_finished;

    private String Shipped_step_name_en;
    private String Shipped_step_name_ar;
    private int Shipped_is_step_finished;

    private String Completed_step_name_en;
    private String Completed_step_name_ar;
    private int Completed_is_step_finished;
    private String placedOrderStatusName,ProcessingStatusName,ShippedOrderStatusName,CompletedOrderStatusName;
    private String history_date,history_en_comments,history_ar_comments;

    public OrdersModel(int invID, String shopName, String shopAddress, String orderDate, String orderPrice, String orderStatus,
                    String orderLat, String orderLng, String shopType) {
        this.invID = invID;
        this.shopName = shopName;
        this.shopAddress = shopAddress;
        this.orderDate = orderDate;
        this.orderPrice = orderPrice;
        this.orderStatus = orderStatus;
        this.orderLat = orderLat;
        this.orderLng = orderLng;
        this.shopType = shopType;

    }

    public String getHistory_date() {
        return history_date;
    }

    public void setHistory_date(String history_date) {
        this.history_date = history_date;
    }

    public String getHistory_en_comments() {
        return history_en_comments;
    }

    public void setHistory_en_comments(String history_en_comments) {
        this.history_en_comments = history_en_comments;
    }

    public String getHistory_ar_comments() {
        return history_ar_comments;
    }

    public void setHistory_ar_comments(String history_ar_comments) {
        this.history_ar_comments = history_ar_comments;
    }

    public String getPlacedOrderStatusName() {
        return placedOrderStatusName;
    }

    public void setPlacedOrderStatusName(String placedOrderStatusName) {
        this.placedOrderStatusName = placedOrderStatusName;
    }

    public String getProcessingStatusName() {
        return ProcessingStatusName;
    }

    public void setProcessingStatusName(String processingStatusName) {
        ProcessingStatusName = processingStatusName;
    }

    public String getShippedOrderStatusName() {
        return ShippedOrderStatusName;
    }

    public void setShippedOrderStatusName(String shippedOrderStatusName) {
        ShippedOrderStatusName = shippedOrderStatusName;
    }

    public String getCompletedOrderStatusName() {
        return CompletedOrderStatusName;
    }

    public void setCompletedOrderStatusName(String completedOrderStatusName) {
        CompletedOrderStatusName = completedOrderStatusName;
    }

    public String getPlaced_step_name_en() {
        return placed_step_name_en;
    }

    public void setPlaced_step_name_en(String placed_step_name_en) {
        this.placed_step_name_en = placed_step_name_en;
    }

    public String getPlaced_step_name_ar() {
        return placed_step_name_ar;
    }

    public void setPlaced_step_name_ar(String placed_step_name_ar) {
        this.placed_step_name_ar = placed_step_name_ar;
    }

    public int getPlaced_is_step_finished() {
        return placed_is_step_finished;
    }

    public void setPlaced_is_step_finished(int placed_is_step_finished) {
        this.placed_is_step_finished = placed_is_step_finished;
    }

    public String getProcessing_step_name_en() {
        return Processing_step_name_en;
    }

    public void setProcessing_step_name_en(String processing_step_name_en) {
        Processing_step_name_en = processing_step_name_en;
    }

    public String getProcessing_step_name_ar() {
        return Processing_step_name_ar;
    }

    public void setProcessing_step_name_ar(String processing_step_name_ar) {
        Processing_step_name_ar = processing_step_name_ar;
    }

    public int getProcessing_is_step_finished() {
        return Processing_is_step_finished;
    }

    public void setProcessing_is_step_finished(int processing_is_step_finished) {
        Processing_is_step_finished = processing_is_step_finished;
    }

    public String getShipped_step_name_en() {
        return Shipped_step_name_en;
    }

    public void setShipped_step_name_en(String shipped_step_name_en) {
        Shipped_step_name_en = shipped_step_name_en;
    }

    public String getShipped_step_name_ar() {
        return Shipped_step_name_ar;
    }

    public void setShipped_step_name_ar(String shipped_step_name_ar) {
        Shipped_step_name_ar = shipped_step_name_ar;
    }

    public int getShipped_is_step_finished() {
        return Shipped_is_step_finished;
    }

    public void setShipped_is_step_finished(int shipped_is_step_finished) {
        Shipped_is_step_finished = shipped_is_step_finished;
    }

    public String getCompleted_step_name_en() {
        return Completed_step_name_en;
    }

    public void setCompleted_step_name_en(String completed_step_name_en) {
        Completed_step_name_en = completed_step_name_en;
    }

    public String getCompleted_step_name_ar() {
        return Completed_step_name_ar;
    }

    public void setCompleted_step_name_ar(String completed_step_name_ar) {
        Completed_step_name_ar = completed_step_name_ar;
    }

    public int getCompleted_is_step_finished() {
        return Completed_is_step_finished;
    }

    public void setCompleted_is_step_finished(int completed_is_step_finished) {
        Completed_is_step_finished = completed_is_step_finished;
    }

    public String getDelivery_phone() {
        return delivery_phone;
    }

    public void setDelivery_phone(String delivery_phone) {
        this.delivery_phone = delivery_phone;
    }

    public Integer getDelivery_id() {
        return delivery_id;
    }

    public void setDelivery_id(Integer delivery_id) {
        this.delivery_id = delivery_id;
    }

    public String getDelivery_name() {
        return delivery_name;
    }

    public void setDelivery_name(String delivery_name) {
        this.delivery_name = delivery_name;
    }

    public int getInvID() {
        return invID;
    }

    public void setInvID(int invID) {
        this.invID = invID;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getShopAddress() {
        return shopAddress;
    }

    public void setShopAddress(String shopAddress) {
        this.shopAddress = shopAddress;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(String orderPrice) {
        this.orderPrice = orderPrice;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getOrderLat() {
        return orderLat;
    }

    public void setOrderLat(String orderLat) {
        this.orderLat = orderLat;
    }

    public String getOrderLng() {
        return orderLng;
    }

    public void setOrderLng(String orderLng) {
        this.orderLng = orderLng;
    }

    public String getShopType() {
        return shopType;
    }

    public void setShopType(String shopType) {
        this.shopType = shopType;
    }


    public int getShopID() {
        return shopID;
    }

    public void setShopID(int shopID) {
        this.shopID = shopID;
    }


    public String getOrderDayName() {
        return orderDayName;
    }

    public void setOrderDayName(String orderDayName) {
        this.orderDayName = orderDayName;
    }

    public String getOrderDayTime() {
        return orderDayTime;
    }

    public void setOrderDayTime(String orderDayTime) {
        this.orderDayTime = orderDayTime;
    }

    public String getDeliveryDay() {
        return DeliveryDay;
    }

    public void setDeliveryDay(String deliveryDay) {
        DeliveryDay = deliveryDay;
    }

    public String getDeliveryTime() {
        return DeliveryTime;
    }

    public void setDeliveryTime(String deliveryTime) {
        DeliveryTime = deliveryTime;
    }

    public String getDeliveryDate() {
        return DeliveryDate;
    }

    public void setDeliveryDate(String deliveryDate) {
        DeliveryDate = deliveryDate;
    }
}
