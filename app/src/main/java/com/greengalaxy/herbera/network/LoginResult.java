package com.greengalaxy.herbera.network;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Wang on 9/6/2017.
 */

public class LoginResult {
    @SerializedName("status")
    private int status;
    @SerializedName("message")
    private String message;
    @SerializedName("user_sl_no")
    private String userSerialNumber;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUserSerialNumber() {
        return userSerialNumber;
    }

    public void setUserSerialNumber(String userSerialNumber) {
        this.userSerialNumber = userSerialNumber;
    }
}
