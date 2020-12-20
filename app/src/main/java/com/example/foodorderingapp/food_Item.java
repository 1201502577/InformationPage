package com.example.foodorderingapp;

public class food_Item {
    private String name, icon, price, rating;

    public food_Item(String name, String icon, String price, String rating) {
        this.name = name;
        this.icon = icon;
        this.price = price;
        this.rating = rating;
    }

    public String getName() {
        return name;
    }

    public String getIcon() {
        return icon;
    }

    public String getPrice() {
        return price;
    }

    public String getRating() {
        return rating;
    }
}
