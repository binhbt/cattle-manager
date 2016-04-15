package com.leo.cattle.domain;

/**
 * Created by leo on 3/31/2016.
 */
public class Message {

    private int id;

    private String message;


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