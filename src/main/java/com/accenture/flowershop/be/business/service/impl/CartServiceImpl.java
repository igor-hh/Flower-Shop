package com.accenture.flowershop.be.business.service.impl;

import com.accenture.flowershop.be.business.service.CartService;
import com.accenture.flowershop.be.business.service.FlowerService;
import com.accenture.flowershop.be.entity.Flower.Flower;
import com.accenture.flowershop.be.entity.User.User;
import com.accenture.flowershop.be.entity.cart.Cart;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;

@Service
public class CartServiceImpl implements CartService {

    private static final Logger logger = LoggerFactory.getLogger(CartService.class);

    @Autowired
    private FlowerService flowerService;
    @Autowired
    private Cart cart;

    public CartServiceImpl() {
    }

    @Override
    public Flower addFlower(Long flowerId, Integer quantity) throws Exception {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Flower flower = flowerService.findById(flowerId);
        if(flower == null || quantity <= 0) {
            throw new Exception("Flower not found.");
        }
        if (flower.getQuantity() < quantity) {
            throw new Exception("Not enough flowers on stock.");
        }
        if (cart.getFlowersInCart().containsKey(flowerId) && (flower.getQuantity() < cart.getFlowersInCart().get(flowerId) + quantity)) {
            throw new Exception("Not enough flower " +
                    flower.getName() +
                    " on stock. You already have " +
                    cart.getFlowersInCart().get(flowerId) + " of that flower in your cart.");
        }
        if(cart.getFlowersInCart().containsKey(flower.getId())) {
            cart.getFlowersInCart().replace(flower.getId(), cart.getFlowersInCart().get(flower.getId()) + quantity);
        } else {
            cart.getFlowersInCart().put(flower.getId(), quantity);
        }
        logger.info("Added flower \"{}\" to user's \"{}\" cart, quantity: {}", flower.getName(), user.getLogin(), quantity);
        return flower;
    }

    @Override
    public void removeFlower(Long flowerId) throws Exception {
        Flower flower = flowerService.findById(flowerId);
        if(flower == null) {
            throw new Exception("No flower with id " + flowerId + " in cart.");
        }
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        cart.getFlowersInCart().remove(flowerId);

        logger.info("Removed flower \"{}\" from user's \"{}\" cart", flower.getName(), user.getLogin());
    }

    @Override
    public BigDecimal getTotalPrice() {
        BigDecimal total = BigDecimal.ZERO;
        Flower flower;
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        for(Map.Entry<Long, Integer> entry: cart.getFlowersInCart().entrySet()) {
            flower = flowerService.findById(entry.getKey());
            Integer quantity = entry.getValue();
            total = total.add(flower.getPrice().multiply(new BigDecimal(quantity)));
        }

        total = total.multiply(new BigDecimal((100.0 - user.getDiscount()) / 100));

        return total.setScale(2, RoundingMode.CEILING);
    }

    @Override
    public Cart getCart() {
        return cart;
    }
}
