package org.ims.model;

import java.util.Date;
import java.util.List;

public class Sale {

    private int id;
    private User user;
    private Date date;
    private double price;
    private List<SaleInfo> items;

    public Sale() {
    }

    public Sale(int id, List<SaleInfo> items, double price, Date date, User user) {
        this.id = id;
        this.items = items;
        this.price = price;
        this.date = date;
        this.user = user;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
    }

    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }

    public double getPrice() {
        return price;
    }
    public void setPrice(double price) {
        this.price = price;
    }

    public List<SaleInfo> getItems() {
        return items;
    }
    public void setItems(List<SaleInfo> items) {
        this.items = items;
    }
}
