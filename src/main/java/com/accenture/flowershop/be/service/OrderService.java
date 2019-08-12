package com.accenture.flowershop.be.service;

import com.accenture.flowershop.be.entity.Order;

public interface OrderService {
    void createOrderFromCart(CartService cart);
    void payOrder(Order order);
    void closeOrder(Order order);
}
