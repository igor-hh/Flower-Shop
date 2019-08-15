package com.accenture.flowershop.be.business.service;

import com.accenture.flowershop.be.entity.Order.Order;
import com.accenture.flowershop.be.entity.User.User;

import java.util.List;

public interface OrderService {

    List<Order> findByOwner(User user);
    List<Order> findByStatus(String status);
    Order findById(Long id);
    void createOrderFromCart();
    void payOrder(Order order);
    void closeOrder(Order order);
}
