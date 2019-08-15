package com.accenture.flowershop.be.business.service.impl;

import com.accenture.flowershop.be.entity.Flower.Flower;
import com.accenture.flowershop.be.entity.Order.Order;
import com.accenture.flowershop.be.entity.Order.OrderItem;
import com.accenture.flowershop.be.entity.Order.OrderStatus;
import com.accenture.flowershop.be.entity.User.User;
import com.accenture.flowershop.be.repos.OrderRepo;
import com.accenture.flowershop.be.repos.UserRepo;
import com.accenture.flowershop.be.business.service.CartService;
import com.accenture.flowershop.be.business.service.FlowerService;
import com.accenture.flowershop.be.business.service.OrderItemService;
import com.accenture.flowershop.be.business.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class OrderServiceImpl implements OrderService {

    private static final Logger logger = LoggerFactory.getLogger(OrderService.class);

    @Autowired
    private OrderRepo orderRepo;
    @Autowired
    private OrderItemService orderItemService;
    @Autowired
    private FlowerService flowerService;
    @Autowired
    private UserRepo userService;
    @Autowired
    private CartService cartService;

    @Override
    public Order findById(Long id) {
        return orderRepo.findById(id).orElse(null);
    }

    @Override
    public List<Order> findByOwner(User user) {
        return orderRepo.findByOwnerOrderByIdAsc(user);
    }

    @Override
    public List<Order> findByStatus(String status) {
        return orderRepo.findByStatus(status);
    }

    @Override
    @Transactional
    public void createOrderFromCart() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Flower flower;
        Integer quantity;
        Order order = new Order();
        order.setOwner(user);
        order.setStatus(OrderStatus.CREATED.name());
        order.setTotalPrice(cartService.getTotalPrice());
        orderRepo.save(order);

        for(Map.Entry<Long, Integer> entry: cartService.getFlowersInCart().entrySet()) {
            flower = flowerService.findById(entry.getKey());
            quantity = entry.getValue();

            OrderItem orderItem = new OrderItem(order, flower, quantity);
            flower.setQuantity(flower.getQuantity() - quantity);

            flowerService.save(flower);
            logger.info("Flower's {} quantity updated to {}", flower.getName(), flower.getQuantity());
            orderItemService.save(orderItem);
        }
        cartService.getFlowersInCart().clear();

        logger.info("User's {} cart cleared", user.getLogin());
    }

    @Override
    @Transactional
    public void payOrder(Order order) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        double total = user.getBalance().doubleValue() - order.getTotalPrice().doubleValue();
        user.setBalance(new BigDecimal(total).setScale(2, RoundingMode.CEILING));
        order.setStatus(OrderStatus.PAID.name());

        orderRepo.save(order);
        userService.save(user);

        logger.info("User {} paid order id: {}, total order price: {}", user.getLogin(), order.getId(), order.getTotalPrice());
    }

    @Override
    public void closeOrder(Order order) {
        order.setStatus(OrderStatus.CLOSED.name());
        order.setCloseDate(new Date());
        orderRepo.save(order);

        logger.info("Order id: {} closed by admin", order.getId());
    }
}
