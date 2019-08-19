package com.accenture.flowershop.be.business.service;

import com.accenture.flowershop.be.entity.Order.Order;
import com.accenture.flowershop.be.entity.Order.OrderItem;
import com.accenture.flowershop.be.entity.User.User;

import java.util.List;

public interface OrderService {

    List<Order> findByOwner(User user) throws Exception;
    List<Order> findByStatus(String status) throws Exception;
    Order findById(Long id) throws Exception;
    void createOrderFromCart();
    void payOrder(Long orderId) throws Exception;
    void closeOrder(Long orderId) throws Exception;
//    void save(OrderItem orderItem);
//    void saveAll(List<OrderItem> orderItemsList);
}
