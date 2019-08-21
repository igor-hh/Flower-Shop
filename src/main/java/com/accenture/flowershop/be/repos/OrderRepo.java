package com.accenture.flowershop.be.repos;

import com.accenture.flowershop.be.entity.order.Order;
import com.accenture.flowershop.be.entity.user.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface OrderRepo extends CrudRepository<Order, Long> {

    List<Order> findByStatus(String status);
    List<Order> findByOwnerOrderByIdDesc(User owner);
    List<Order> findAllByOrderByStatusDescCreationDateDesc();

    @Modifying
    @Transactional
    @Query(value = "update orders set status = 'CLOSED', close_date = LOCALTIMESTAMP where id = :orderid and status = 'PAID'", nativeQuery = true)
    int updateOrderStatus(@Param("orderid") Long orderid);
}
