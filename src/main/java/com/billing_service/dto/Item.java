package com.billing_service.dto;

import com.billing_service.enums.ItemCategory;

import java.math.BigDecimal;

public class Item {
    private String name;
    private ItemCategory category;
    private BigDecimal price;

    public Item(String name, ItemCategory category, BigDecimal price) {
        this.name = name;
        this.category = category;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public ItemCategory getCategory() {
        return category;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCategory(ItemCategory category) {
        this.category = category;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
