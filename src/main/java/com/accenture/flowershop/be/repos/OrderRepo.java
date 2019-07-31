package com.accenture.flowershop.be.repos;

import com.accenture.flowershop.be.entity.Order;
import org.springframework.data.repository.CrudRepository;

public interface OrderRepo extends CrudRepository<Order, Long> {
}
