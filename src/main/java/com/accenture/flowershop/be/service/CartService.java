package com.accenture.flowershop.be.service;

import com.accenture.flowershop.be.entity.Flower;

import java.math.BigDecimal;
import java.util.Map;

public interface CartService {

    void addFlower(Flower flower, Integer quantity);

    void removeFlower(Flower flower);

    Map<Long, Integer> getFlowersInCart();

    BigDecimal getItemPrice(Long id);

    BigDecimal getTotalPrice();
}
