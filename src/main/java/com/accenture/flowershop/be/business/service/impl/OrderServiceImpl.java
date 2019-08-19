package com.accenture.flowershop.be.business.service.impl;

import com.accenture.flowershop.be.business.service.CartService;
import com.accenture.flowershop.be.business.service.FlowerService;
import com.accenture.flowershop.be.business.service.OrderService;
import com.accenture.flowershop.be.entity.Flower.Flower;
import com.accenture.flowershop.be.entity.Order.Order;
import com.accenture.flowershop.be.entity.Order.OrderItem;
import com.accenture.flowershop.be.entity.Order.OrderStatus;
import com.accenture.flowershop.be.entity.User.User;
import com.accenture.flowershop.be.repos.OrderItemRepo;
import com.accenture.flowershop.be.repos.OrderRepo;
import com.accenture.flowershop.be.repos.UserRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class OrderServiceImpl implements OrderService {

    private static final Logger logger = LoggerFactory.getLogger(OrderService.class);

    @Autowired
    private OrderRepo orderRepo;
    @Autowired
    private FlowerService flowerService;
    @Autowired
    private UserRepo userService;
    @Autowired
    private CartService cartService;
    @Autowired
    private OrderItemRepo orderItemRepo;

    @Override
    public List<Order> findByOwner(User user) throws Exception {
        List<Order> orders = orderRepo.findByOwnerOrderByIdAsc(user);
        if(orders.size() == 0) {
            throw new Exception ("You have no orders :(");
        }
        return orders;
    }

    @Override
    public List<Order> findByStatus(String status) throws Exception {
        List<Order> orders = orderRepo.findByStatus(status);
        if (orders.size() == 0) {
            throw new Exception("No paid orders to display");
        }
        return orders;
    }

    @Override
    @Transactional
    public void createOrderFromCart() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Flower flower;
        Integer quantity;
        List<OrderItem> orderItemsList = new ArrayList<>();
        Order order = new Order();
        order.setOwner(user);
        order.setStatus(OrderStatus.CREATED.name());
        order.setTotalPrice(cartService.getTotalPrice());

        for(Map.Entry<Long, Integer> entry: cartService.getCart().getFlowersInCart().entrySet()) {
            flower = flowerService.findById(entry.getKey());
            quantity = entry.getValue();

            OrderItem orderItem = new OrderItem(order, flower, quantity);
            flower.setQuantity(flower.getQuantity() - quantity);

            flowerService.save(flower);
            logger.info("Flower's {} quantity updated to {}", flower.getName(), flower.getQuantity());
            orderItemsList.add(orderItem);
        }
        orderRepo.save(order);
        orderItemRepo.saveAll(orderItemsList);

        cartService.getCart().getFlowersInCart().clear();

        logger.info("Order id: {} created", order.getId());
        logger.info("User's {} cart cleared", user.getLogin());
    }

    @Override
    @Transactional
    public void payOrder(Long orderId) throws Exception {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Order order = orderRepo.findById(orderId).orElseThrow(() -> new Exception("Order not found"));
        if(!user.getLogin().equals(order.getOwner().getLogin())) {
            throw new Exception("You are not owner of the order " + order.getId());
        }
        if(!order.getStatus().equals(OrderStatus.CREATED.name())) {
            throw new Exception("Order is already paid");
        }
        if(order.getTotalPrice().compareTo(user.getBalance()) == 1) {
            throw new Exception("Not enough money to pay. You " +
                    (order.getTotalPrice().subtract(user.getBalance())) + " balance short");
        }
        BigDecimal total = user.getBalance().subtract(order.getTotalPrice());
        user.setBalance(total.setScale(2, RoundingMode.CEILING));
        order.setStatus(OrderStatus.PAID.name());

        orderRepo.save(order);
        userService.save(user);

        logger.info("User {} paid order id: {}, total order price: {}", user.getLogin(), order.getId(), order.getTotalPrice());
    }

    @Override
    public void closeOrder(Long orderId) throws Exception {
        Order order = orderRepo.findById(orderId).orElseThrow(() -> new Exception("Order not found."));
        if(!order.getStatus().equals(OrderStatus.PAID.name())) {
            throw new Exception("You can close only paid orders.");
        }
        order.setStatus(OrderStatus.CLOSED.name());
        order.setCloseDate(new Date());
        orderRepo.save(order);

        logger.info("Order id: {} closed by admin", order.getId());
    }
}
