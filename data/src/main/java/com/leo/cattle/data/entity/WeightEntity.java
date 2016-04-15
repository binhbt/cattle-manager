package com.leo.cattle.data.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by leo on 3/25/2016.
 */
public class WeightEntity {
    @SerializedName("id")
    private int id;
    @SerializedName("cattle_id")
    private int cattleId;
    @SerializedName("description")
    private String desCription;
    @SerializedName("weight")
    private int weight;
    @SerializedName("date")
    private String date;
    @SerializedName("user_id")
    private int authorId;
    @SerializedName("name")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

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

    public String getDesCription() {
        return desCription;
    }

    public void setDesCription(String desCription) {
        this.desCription = desCription;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
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
