package com.accenture.flowershop.be.service.impl;

import com.accenture.flowershop.be.entity.*;
import com.accenture.flowershop.be.repos.FlowerRepo;
import com.accenture.flowershop.be.repos.OrderItemRepo;
import com.accenture.flowershop.be.repos.OrderRepo;
import com.accenture.flowershop.be.repos.UserRepo;
import com.accenture.flowershop.be.service.CartService;
import com.accenture.flowershop.be.service.OrderService;
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

    @Autowired
    private OrderRepo orderRepo;
    @Autowired
    private OrderItemRepo orderItemRepo;
    @Autowired
    private FlowerRepo flowerRepo;
    @Autowired
    private UserRepo userRepo;
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

        for(Map.Entry<Flower, Integer> entry: cartService.getFlowersInCart().entrySet()) {
            flower = flowerRepo.findByName(entry.getKey().getName());
            quantity = entry.getValue();

            OrderItem orderItem = new OrderItem(order, flower, quantity);
            flower.setQuantity(flower.getQuantity() - quantity);

            flowerRepo.save(flower);
            orderItemRepo.save(orderItem);
        }
        cartService.getFlowersInCart().clear();
    }

    @Override
    @Transactional
    public void payOrder(Order order) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        double total = user.getBalance().doubleValue() - order.getTotalPrice().doubleValue();
        user.setBalance(new BigDecimal(total).setScale(2, RoundingMode.CEILING));
        order.setStatus(OrderStatus.PAID.name());

        orderRepo.save(order);
        userRepo.save(user);
    }

    @Override
    public void closeOrder(Order order) {
        order.setStatus(OrderStatus.CLOSED.name());
        order.setCloseDate(new Date());
        orderRepo.save(order);
    }
}
