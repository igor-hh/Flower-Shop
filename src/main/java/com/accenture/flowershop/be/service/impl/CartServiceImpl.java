package com.accenture.flowershop.be.service.impl;

import com.accenture.flowershop.be.entity.Flower;
import com.accenture.flowershop.be.entity.User;
import com.accenture.flowershop.be.service.CartService;
import com.accenture.flowershop.be.service.FlowerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;

@Service
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class CartServiceImpl implements CartService {

    @Autowired
    private FlowerService flowerService;

    //Map<Flower id, quantity in cart>
    private Map<Long, Integer> flowersInCart;

    public CartServiceImpl() {
        flowersInCart = new HashMap<>();
    }

    @Override
    public void addFlower(Flower flower, Integer quantity) {

        if(flowersInCart.containsKey(flower.getId())) {
            flowersInCart.replace(flower.getId(), flowersInCart.get(flower) + quantity);
        } else {
            flowersInCart.put(flower.getId(), quantity);
        }
    }

    @Override
    public void removeFlower(Flower flower) {
        flowersInCart.remove(flower.getId());
    }

    @Override
    public Map<Long, Integer> getFlowersInCart() {
        return flowersInCart;
    }

    @Override
    public BigDecimal getItemPrice(Long id) {
        Flower flower = flowerService.findById(id);

        return flower.getPrice().multiply(new BigDecimal(flowersInCart.get(flower.getId())));
    }

    @Override
    public BigDecimal getTotalPrice() {
        Double total = 0.0;
        Flower flower;
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        for(Map.Entry<Long, Integer> entry: flowersInCart.entrySet()) {
            flower = flowerService.findById(entry.getKey());
            Integer quantity = entry.getValue();
            total += flower.getPrice().doubleValue() * quantity;
        }

        total = total * ((100.0 - user.getDiscount()) / 100);

        return new BigDecimal(total).setScale(2, RoundingMode.CEILING);
    }
}
