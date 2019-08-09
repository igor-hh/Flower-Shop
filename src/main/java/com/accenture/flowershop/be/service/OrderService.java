package com.accenture.flowershop.be.service;

import com.accenture.flowershop.be.entity.User;

public interface OrderService {
    public void createOrderFromCart(CartService cart);
}
