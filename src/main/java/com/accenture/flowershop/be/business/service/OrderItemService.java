package com.accenture.flowershop.be.business.service;

import com.accenture.flowershop.be.entity.Order.OrderItem;

import java.util.List;

public interface OrderItemService {
    void save(OrderItem orderItem);
    void saveAll(List<OrderItem> orderItemsList);
}
