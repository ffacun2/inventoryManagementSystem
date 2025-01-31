package org.ims.model;

public class PurchaseInfo {

    private long id;
    private Product product;
    private int quantity;
    private double unitPrice;

    public PurchaseInfo() {
    }

    public PurchaseInfo(long id, Product product, int quantity, double unitprice) {
        this.id = id;
        this.product = product;
        this.quantity = quantity;
        this.unitPrice = unitprice;
    }

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }

    public double getUnitPrice() {
        return unitPrice;
    }
    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }
    public void setProduct(Product product) {
        this.product = product;
    }

    public double getSubTotal() {
        return quantity * unitPrice;
    }

}
