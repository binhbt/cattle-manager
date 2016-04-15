package com.leo.cattle.data.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by leo on 3/31/2016.
 */
public class MessageEntity {
    @SerializedName("id")
    private int id;

    @SerializedName("message")
    private String message;

    @SerializedName("status")
    private int status;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
