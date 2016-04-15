package com.leo.cattle.presentation.model;

import java.util.List;

/**
 * Created by leo on 4/1/2016.
 */
public class CattleModel {

    private int id;

    private String name;

    private String desCription;

    private String blood;

    private int weight;

    private String buyDate;

    private int monthOld;

    private int cost;


    private List<EventModel> events;

    private List<WeightModel> weights;
    //@SerializedName("photos")
    //private List<PhotoEntity> photos;

    //0:active 1:delete

    public List<EventModel> getEvents() {
        return events;
    }

    public void setEvents(List<EventModel> events) {
        this.events = events;
    }

    public List<WeightModel> getWeights() {
        return weights;
    }

    public void setWeights(List<WeightModel> weights) {
        this.weights = weights;
    }

    private int status;
    //0: feed 1:sold

    private int saleStatus;

    private String saleDate;

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
    /*
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
    */
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
