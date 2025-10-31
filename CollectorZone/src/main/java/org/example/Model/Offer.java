package org.example.Model;

import java.util.UUID;

public class Offer {

    private String name;
    private String email;
    private UUID id;
    private double price;
    private String itemId;

    public Offer() {}

    public Offer(String name, String email, UUID id, double price, String itemId) {
        this.name = name;
        this.email = email;
        this.id = id;
        this.price = price;
        this.itemId = itemId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }
}


