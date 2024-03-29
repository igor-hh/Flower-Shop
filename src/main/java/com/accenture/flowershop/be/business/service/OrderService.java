package com.accenture.flowershop.be.business.service;

import com.accenture.flowershop.be.entity.order.Order;
import com.accenture.flowershop.be.entity.user.User;

import java.util.List;

public interface OrderService {

    List<Order> findByOwner(User user) throws Exception;
    List<Order> findManagerOrders() throws Exception;
    void createOrderFromCart();
    void payOrder(Long orderId) throws Exception;
    void closeOrder(Long orderId) throws Exception;
}
