package com.accenture.flowershop.be.repos;

import com.accenture.flowershop.be.entity.Order;
import com.accenture.flowershop.be.entity.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface OrderRepo extends CrudRepository<Order, Long> {

    List<Order> findByStatus(String status);

    List<Order> findByOwnerOrderByIdAsc(User owner);
}
