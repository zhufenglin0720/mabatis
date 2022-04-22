package com.example.pojo;

import java.util.Date;

/**
 * @author zfl
 * @create 2022/4/22 11:01
 * @description
 */
public class SysGoods {

    private int id;

    private int number;

    private double price;

    private String description;

    private String photo;

    private String createDate;

    private Date shelfDate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public Date getShelfDate() {
        return shelfDate;
    }

    public void setShelfDate(Date shelfDate) {
        this.shelfDate = shelfDate;
    }
}
