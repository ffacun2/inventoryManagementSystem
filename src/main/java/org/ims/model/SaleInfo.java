package org.ims.model;

public class SaleInfo {

    private long id;
    private Product product;
    private int quantity;
    private double unitPrice;

    public SaleInfo() {
    }

    public SaleInfo (int id, double unitPrice, int quantity, Product product) {
        this.id = id;
        this.unitPrice = unitPrice;
        this.quantity = quantity;
        this.product = product;
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


}
