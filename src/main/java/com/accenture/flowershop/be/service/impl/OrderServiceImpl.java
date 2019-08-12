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

import java.util.Date;
import java.util.Map;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderRepo orderRepo;
    @Autowired
    OrderItemRepo orderItemRepo;
    @Autowired
    FlowerRepo flowerRepo;
    @Autowired
    UserRepo userRepo;

    @Override
    public void createOrderFromCart(CartService cart) {
        //get user from context
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Flower flower;
        Integer quantity;
        Order order = new Order();
        order.setOwner(user);
        order.setStatus(OrderStatus.CREATED.name());
        order.setTotalPrice(cart.getTotalPrice());
        orderRepo.save(order);

        for(Map.Entry<Flower, Integer> entry: cart.getFlowersInCart().entrySet()) {
            flower = flowerRepo.findByName(entry.getKey().getName());
            quantity = entry.getValue();
            OrderItem orderItem = new OrderItem(order, flower, quantity);
            flower.setQuantity(flower.getQuantity() - quantity);

            orderItemRepo.save(orderItem);
        }
        cart.getFlowersInCart().clear();
    }

    @Override
    public void payOrder(Order order) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        //round to avoid principal.balance and database values inconsistency
        double total = user.getBalance() - order.getTotalPrice();
        total = Math.round(total * 100.0) / 100.0;
        user.setBalance(total);

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
