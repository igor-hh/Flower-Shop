package com.accenture.flowershop.be.util.JMS;

public class UserDiscountMessage {

    private Long id;
    private Integer discount;

    public UserDiscountMessage() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getDiscount() {
        return discount;
    }

    public void setDiscount(Integer discount) {
        this.discount = discount;
    }
}
