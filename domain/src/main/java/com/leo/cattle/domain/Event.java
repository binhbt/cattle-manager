package com.leo.cattle.domain;

/**
 * Created by leo on 4/1/2016.
 */
public class Event {
    private int id;
    private int cattleId;
    private String name;
    private String desCription;
    private int cost;
    private String date;
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

    private String cattleIds;

    public String getCattleIds() {
        return cattleIds;
    }

    public void setCattleIds(String cattleIds) {
        this.cattleIds = cattleIds;
    }
}
