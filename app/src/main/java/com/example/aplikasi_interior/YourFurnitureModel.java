package com.example.aplikasi_interior;
public class YourFurnitureModel {
    private String name;
    private int price;
    private String description;

    public YourFurnitureModel(String name, int price, String description) {
        this.name = name;
        this.price = price;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }
}
