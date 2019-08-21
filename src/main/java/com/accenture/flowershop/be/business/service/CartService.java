package com.accenture.flowershop.be.business.service;

import com.accenture.flowershop.be.entity.flower.Flower;
import com.accenture.flowershop.be.entity.cart.Cart;

import java.math.BigDecimal;

public interface CartService {

    Flower addFlower(Long flowerId, Integer quantity) throws Exception;
    void removeFlower(Long flowerId) throws Exception;
    BigDecimal getTotalPrice();
    Cart getCart();
}
