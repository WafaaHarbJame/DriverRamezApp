package com.ramez.driver.Models;

public class RamesOrders {

    private int id;
    private String order_code;
    private String payment_method;
    private boolean expressDelivery;
    private String delivery_date;
    private int mode;
    private int type;
    private String lat;
    private String lng;
    private String address;




    public RamesOrders(int id, String order_code, String payment_method, boolean expressDelivery, String delivery_date, int mode, int type, String address, String lat, String lng) {
        this.id = id;
        this.order_code = order_code;
        this.payment_method = payment_method;
        this.expressDelivery = expressDelivery;
        this.delivery_date = delivery_date;
        this.mode=mode;
        this.type=type;
        this.address=address;
        this.lng=lng;
        this.lat=lat;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getMode() {
        return mode;
    }

    public void setMode(int mode) {
        this.mode = mode;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOrder_code() {
        return order_code;
    }

    public void setOrder_code(String order_code) {
        this.order_code = order_code;
    }

    public String getPayment_method() {
        return payment_method;
    }

    public void setPayment_method(String payment_method) {
        this.payment_method = payment_method;
    }

    public boolean isExpressDelivery() {
        return expressDelivery;
    }

    public void setExpressDelivery(boolean expressDelivery) {
        this.expressDelivery = expressDelivery;
    }

    public String getDelivery_date() {
        return delivery_date;
    }

    public void setDelivery_date(String delivery_date) {
        this.delivery_date = delivery_date;
    }
}
