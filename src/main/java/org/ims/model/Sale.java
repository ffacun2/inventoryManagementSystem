package org.ims.model;

import java.util.Date;
import java.util.List;

public class Sale {

    private long id;
    private User user;
    private Date date;
    private double total;
    private List<SaleInfo> items;

    public Sale() {
    }

    public Sale(long id, List<SaleInfo> items, double price, Date date, User user) {
        this.id = id;
        this.items = items;
        this.total = price;
        this.date = date;
        this.user = user;
    }

    public long getId() {
        return id;
    }
    public void setId(long id) {
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

    public double getTotal() {
        return total;
    }
    public void setTotal(double total) {
        this.total = total;
    }

    public List<SaleInfo> getItems() {
        return items;
    }
    public void setItems(List<SaleInfo> items) {
        this.items = items;
    }
}
