package com.exercise.rest.model;

import org.springframework.data.annotation.Id;

import java.math.BigDecimal;

public class Product {

    @Id
    public String id;

    public String name;

    public BigDecimal price;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
