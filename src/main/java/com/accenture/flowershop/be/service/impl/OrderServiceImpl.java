package com.accenture.flowershop.be.service.impl;

import com.accenture.flowershop.be.entity.Flower;
import com.accenture.flowershop.be.entity.Order;
import com.accenture.flowershop.be.entity.OrderItem;
import com.accenture.flowershop.be.entity.User;
import com.accenture.flowershop.be.repos.FlowerRepo;
import com.accenture.flowershop.be.repos.OrderItemRepo;
import com.accenture.flowershop.be.repos.OrderRepo;
import com.accenture.flowershop.be.service.CartService;
import com.accenture.flowershop.be.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderRepo orderRepo;
    @Autowired
    OrderItemRepo orderItemRepo;
    @Autowired
    FlowerRepo flowerRepo;

    @Override
    public void createOrderFromCart(CartService cart) {
        //get user from context
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Flower flower;
        Order order = new Order();
        order.setOwner(user);
        order.setTotalPrice(cart.getTotalPrice());
        orderRepo.save(order);
        Integer quantity;
        for(Map.Entry<Flower, Integer> entry: cart.getFlowersInCart().entrySet()) {
            flower = flowerRepo.findByName(entry.getKey().getName());
            quantity = entry.getValue();
            OrderItem orderItem = new OrderItem(order, flower, quantity);
            flower.setQuantity(flower.getQuantity() - quantity);
            orderItemRepo.save(orderItem);
        }
        cart.getFlowersInCart().clear();
    }
}
