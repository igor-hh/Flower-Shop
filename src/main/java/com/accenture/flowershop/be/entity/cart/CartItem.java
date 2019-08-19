package com.accenture.flowershop.be.entity.cart;

import java.math.BigDecimal;

public class CartItem {
    private Long id;
    private String flowerName;
    private BigDecimal price;
    private Integer quantity;

    public CartItem(Long id, String flowerName, BigDecimal price, Integer quantity) {
        this.id = id;
        this.flowerName = flowerName;
        this.price = price;
        this.quantity = quantity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFlowerName() {
        return flowerName;
    }

    public void setFlowerName(String flowerName) {
        this.flowerName = flowerName;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
