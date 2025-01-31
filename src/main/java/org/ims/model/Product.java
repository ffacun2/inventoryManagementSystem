package org.ims.model;

public class Product {

    private Long id;
    private String name;
    private double price;
    private double cost;
    private String description;
    private int quantity;
    private String category;

    public Product(){}

    public Product(String name, double price, double cost, String description, int quantity, String category){
        this.name = name;
        this.price = price;
        this.cost = cost;
        this.description = description;
        this.quantity = quantity;
        this.category = category;
    }


    public String getCategory() {
        return category;
    }
    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }
    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void addStock(int quantity) {
        this.quantity += quantity;
    }
}
