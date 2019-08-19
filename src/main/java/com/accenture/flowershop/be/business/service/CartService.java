package com.accenture.flowershop.be.business.service;

import com.accenture.flowershop.be.entity.Flower.Flower;
import com.accenture.flowershop.be.entity.cart.Cart;

import java.math.BigDecimal;
import java.util.Map;

public interface CartService {

    Flower addFlower(Long flowerId, Integer quantity) throws Exception;
    void removeFlower(Long flowerId) throws Exception;
    BigDecimal getTotalPrice();
    Cart getCart();
}
