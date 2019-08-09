package com.accenture.flowershop.be.repos;

import com.accenture.flowershop.be.entity.OrderItem;
import org.springframework.data.repository.CrudRepository;

public interface OrderItemRepo extends CrudRepository<OrderItem, Long> {
}
