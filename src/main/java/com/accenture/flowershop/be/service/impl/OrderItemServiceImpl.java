package com.accenture.flowershop.be.service.impl;

import com.accenture.flowershop.be.entity.OrderItem;
import com.accenture.flowershop.be.repos.OrderItemRepo;
import com.accenture.flowershop.be.service.OrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderItemServiceImpl implements OrderItemService {

    @Autowired
    private OrderItemRepo orderItemRepo;

    @Override
    public void save(OrderItem orderItem) {
        orderItemRepo.save(orderItem);
    }
}
