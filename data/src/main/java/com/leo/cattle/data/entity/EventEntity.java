package com.leo.cattle.data.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by leo on 3/24/2016.
 */
public class EventEntity {
    @SerializedName("id")
    private int id;
    @SerializedName("cattle_id")
    private int cattleId;
    @SerializedName("name")
    private String name;
    @SerializedName("description")
    private String desCription;
    @SerializedName("cost")
    private int cost;
    @SerializedName("date")
    private String date;
    @SerializedName("user_id")
    private int authorId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCattleId() {
        return cattleId;
    }

    public void setCattleId(int cattleId) {
        this.cattleId = cattleId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesCription() {
        return desCription;
    }

    public void setDesCription(String desCription) {
        this.desCription = desCription;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getAuthorId() {
        return authorId;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }
}
