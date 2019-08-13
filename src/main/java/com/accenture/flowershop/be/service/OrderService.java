package com.accenture.flowershop.be.service;

import com.accenture.flowershop.be.entity.Order;
import com.accenture.flowershop.be.entity.User;

import java.util.List;

public interface OrderService {

    List<Order> findByOwner(User user);
    List<Order> findByStatus(String status);
    Order findById(Long id);
    void createOrderFromCart();
    void payOrder(Order order);
    void closeOrder(Order order);
}
