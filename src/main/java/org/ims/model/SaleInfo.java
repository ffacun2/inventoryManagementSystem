package org.ims.model;

public class SaleInfo {

    private int id;
    private Sale sale;
    private Product product;
    private int quantity;
    private double unitPrice;

    public SaleInfo() {
    }

    public SaleInfo(int id, double unitPrice, int quantity, Product product, Sale sale) {
        this.id = id;
        this.unitPrice = unitPrice;
        this.quantity = quantity;
        this.product = product;
        this.sale = sale;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
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

    public Sale getSale() {
        return sale;
    }
    public void setSale(Sale sale) {
        this.sale = sale;
    }

}
