package com.ramez.shopp.Classes;
public class MessageEvent {

    public static final String TYPE_invoice = "invoice";
    public static final String TYPE_main = "main";

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

