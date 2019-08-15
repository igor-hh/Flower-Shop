package com.accenture.flowershop.be.repos;

import com.accenture.flowershop.be.entity.Order.Order;
import com.accenture.flowershop.be.entity.User.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepo extends CrudRepository<Order, Long> {

    List<Order> findByStatus(String status);

    List<Order> findByOwnerOrderByIdAsc(User owner);
}
