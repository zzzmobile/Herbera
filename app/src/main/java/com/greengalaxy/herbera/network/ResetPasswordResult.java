package com.greengalaxy.herbera.network;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Wang on 9/6/2017.
 */

public class ResetPasswordResult {
    @SerializedName("status")
    private int status;
    @SerializedName("message")
    private String message;

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
}
