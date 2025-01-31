package org.ims.model;

import org.ims.utils.StatePurchase;

import java.time.LocalDate;
import java.util.List;

public class Purchase {

    private Long id;
    private Supplier supplier;
    private User user; //user that generated the purchase
    private LocalDate date;
    private List<PurchaseInfo> purchaseInfo;
    private double total;
    private StatePurchase state;

    public Purchase() {
    }

    public Purchase(User user, Supplier supplier , LocalDate date,List<PurchaseInfo> purchaseInfo, double total) {
        this.date = date;
        this.user = user;
        this.purchaseInfo = purchaseInfo;
        this.supplier = supplier;
        this.total = total;
        this.state = StatePurchase.PENDING;
    }

    public Purchase(Long id, User user, Supplier supplier, LocalDate date, List<PurchaseInfo> purchaseInfo, double total, StatePurchase state) {
        this.id = id;
        this.supplier = supplier;
        this.user = user;
        this.date = date;
        this.purchaseInfo = purchaseInfo;
        this.total = total;
        this.state = state;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public Supplier getSupplier() {
        return supplier;
    }
    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    public LocalDate getDate() {
        return date;
    }
    public void setDate(LocalDate date) {
        this.date = date;
    }

    public double getTotal() {
        return total;
    }
    public void setTotal(double total) {
        this.total = total;
    }

    public List<PurchaseInfo> getPurchaseInfo() {
        return purchaseInfo;
    }
    public void setPurchaseInfo(List<PurchaseInfo> purchaseInfo) {
        this.purchaseInfo = purchaseInfo;
    }

    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }

    public StatePurchase getState() {
        return state;
    }

    public void setState(StatePurchase state) {
        this.state = state;
    }
}
