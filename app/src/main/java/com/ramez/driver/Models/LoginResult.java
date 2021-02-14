package com.ramez.driver.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginResult {

    @SerializedName("user")
    @Expose
    private MemberModel user;

    public MemberModel getUser() {
        return user;
    }
}
