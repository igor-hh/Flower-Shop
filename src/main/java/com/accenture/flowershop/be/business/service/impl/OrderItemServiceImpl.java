package com.accenture.flowershop.be.business.service.impl;

import com.accenture.flowershop.be.business.service.OrderItemService;
import com.accenture.flowershop.be.entity.Order.OrderItem;
import com.accenture.flowershop.be.repos.OrderItemRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderItemServiceImpl implements OrderItemService {

    private static final Logger logger = LoggerFactory.getLogger(OrderItemService.class);

    @Autowired
    private OrderItemRepo orderItemRepo;

    @Override
    public void save(OrderItem orderItem) {
        orderItemRepo.save(orderItem);

        logger.info("Saved order item id: \"{}\" to database. Order id: {}", orderItem.getId(), orderItem.getOrder().getId());
    }

    @Override
    public void saveAll(List<OrderItem> orderItemsList) {
        orderItemRepo.saveAll(orderItemsList);
    }
}
