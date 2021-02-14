package com.ramez.driver.Models;

import java.io.Serializable;

public class ChatMessage implements Serializable, Comparable<ChatMessage> {
    //implements Comparable<ChatMessage>
    private String senderID, receiverID;
    private String messageBody, senderName,messageKey, receiverName, senderImage, receiverImage,
            messageType, inputType, imageUrl,location_image;
    private String lat, lng;
    private long messageTime;
    private boolean is_read;
    private int count=0;


    public ChatMessage() {
    }


    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getLocation_image() {
        return location_image;
    }

    public void setLocation_image(String location_image) {
        this.location_image = location_image;
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

    public boolean isIs_read() {
        return is_read;
    }

    public void setIs_read(boolean is_read) {
        this.is_read = is_read;
    }

    public String getSenderID() {
        return senderID;
    }

    public void setSenderID(String senderID) {
        this.senderID = senderID;
    }

    public String getReceiverID() {
        return receiverID;
    }

    public void setReceiverID(String receiverID) {
        this.receiverID = receiverID;
    }

    public String getMessageBody() {
        return messageBody;
    }

    public void setMessageBody(String messageBody) {
        this.messageBody = messageBody;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public String getSenderImage() {
        return senderImage;
    }

    public void setSenderImage(String senderImage) {
        this.senderImage = senderImage;
    }

    public String getReceiverImage() {
        return receiverImage;
    }

    public void setReceiverImage(String receiverImage) {
        this.receiverImage = receiverImage;
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getInputType() {
        return inputType;
    }

    public void setInputType(String inputType) {
        this.inputType = inputType;
    }

    public Long getMessageTime() {
        return  Long.valueOf(messageTime);
    }
    public void setMessageTime(long messageTime) {
        this.messageTime = messageTime;
    }


    @Override
    public int compareTo(ChatMessage chatMessage) {
        if (getMessageTime() == 0 || chatMessage.getMessageTime() == 0) {
            return 0;
        }
        return getMessageTime().compareTo(chatMessage.getMessageTime());
    }


}
