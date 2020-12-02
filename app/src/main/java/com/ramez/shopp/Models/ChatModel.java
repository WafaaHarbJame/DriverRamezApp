package com.ramez.shopp.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by wokman on 5/7/2017.
 */

public class ChatModel {

    @SerializedName("id")
    @Expose
    public int id;
    @SerializedName("chat_id")
    @Expose
    public int chatId;
    @SerializedName("customer_id")
    @Expose
    public int customerId;
    @SerializedName("provider_id")
    @Expose
    public int providerId;
    @SerializedName("provider_name")
    @Expose
    public String providerName;
    @SerializedName("customer_name")
    @Expose
    public String customerName;
    @SerializedName("user_avatar")
    @Expose
    public String customerAvatar;
    @SerializedName("provider_avatar")
    @Expose
    public String providerAvatar;
    @SerializedName("updated_at")
    @Expose
    public String updatedAt;

    @SerializedName("messageTxt")
    @Expose
    public String messageTxt;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getChatId() {
        return chatId;
    }

    public void setChatId(int chatId) {
        this.chatId = chatId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getProviderId() {
        return providerId;
    }

    public void setProviderId(int providerId) {
        this.providerId = providerId;
    }

    public String getProviderName() {
        return providerName;
    }

    public void setProviderName(String providerName) {
        this.providerName = providerName;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerAvatar() {
        return customerAvatar;
    }

    public void setCustomerAvatar(String customerAvatar) {
        this.customerAvatar = customerAvatar;
    }

    public String getProviderAvatar() {
        return providerAvatar;
    }

    public void setProviderAvatar(String providerAvatar) {
        this.providerAvatar = providerAvatar;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getMessageTxt() {
        return messageTxt;
    }

    public void setMessageTxt(String messageTxt) {
        this.messageTxt = messageTxt;
    }

    public ChatModel(int id, int chatId, int customerId, int providerId, String providerName, String customerName, String customerAvatar, String providerAvatar, String updatedAt, String messageTxt) {
        this.id = id;
        this.chatId = chatId;
        this.customerId = customerId;
        this.providerId = providerId;
        this.providerName = providerName;
        this.customerName = customerName;
        this.customerAvatar = customerAvatar;
        this.providerAvatar = providerAvatar;
        this.updatedAt = updatedAt;
        this.messageTxt = messageTxt;
    }
}
