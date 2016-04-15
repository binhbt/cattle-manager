package com.leo.cattle.data.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by leo on 3/24/2016.
 */
public class CattleEntity {
    @SerializedName("id")
    private int id;
    @SerializedName("name")
    private String name;
    @SerializedName("description")
    private String desCription;
    @SerializedName("blood")
    private String blood;
    @SerializedName("weight")
    private int weight;
    @SerializedName("buy_date")
    private String buyDate;
    @SerializedName("month_old")
    private int monthOld;
    @SerializedName("cost")
    private int cost;

    @SerializedName("events")
    private List<EventEntity> events;
    @SerializedName("weights")
    private List<WeightEntity> weights;
    //@SerializedName("photos")
    //private List<PhotoEntity> photos;

    //0:active 1:delete
    @SerializedName("status")
    private int status;
    //0: feed 1:sold
    @SerializedName("sale_status")
    private int saleStatus;
    @SerializedName("sale_date")
    private String saleDate;
    @SerializedName("sale_price")
    private int salePrice;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getBlood() {
        return blood;
    }

    public void setBlood(String blood) {
        this.blood = blood;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public String getBuyDate() {
        return buyDate;
    }

    public void setBuyDate(String buyDate) {
        this.buyDate = buyDate;
    }

    public int getMonthOld() {
        return monthOld;
    }

    public void setMonthOld(int monthOld) {
        this.monthOld = monthOld;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public List<EventEntity> getEvents() {
        return events;
    }

    public void setEvents(List<EventEntity> events) {
        this.events = events;
    }

    public List<WeightEntity> getWeights() {
        return weights;
    }

    public void setWeights(List<WeightEntity> weights) {
        this.weights = weights;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getSaleStatus() {
        return saleStatus;
    }

    public void setSaleStatus(int saleStatus) {
        this.saleStatus = saleStatus;
    }

    public String getSaleDate() {
        return saleDate;
    }

    public void setSaleDate(String saleDate) {
        this.saleDate = saleDate;
    }

    public int getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(int salePrice) {
        this.salePrice = salePrice;
    }
}
