package com.ramez.shopp.Classes;
public class MessageEvent {

    public static final String TYPE_invoice = "invoice";
    public static final String TYPE_cart = "cart";
    public static final String TYPE_main = "main";
    public static final String TYPE_ADDRESS = "address";
    public static final String TYPE_REFRESH = "refresh";

    //    public int PagerPosition;
    public Object data;
    public String type;

    public MessageEvent(String type, Object msgData) {
        this.data = msgData;
        this.type = type;
    }

    public MessageEvent(String type) {
        this.type = type;
    }

    public MessageEvent() {
    }

}

