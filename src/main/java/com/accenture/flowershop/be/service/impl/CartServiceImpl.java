package com.accenture.flowershop.be.service.impl;

import com.accenture.flowershop.be.entity.Flower;
import com.accenture.flowershop.be.entity.User;
import com.accenture.flowershop.be.repos.FlowerRepo;
import com.accenture.flowershop.be.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
//@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class CartServiceImpl implements CartService {

    @Autowired
    FlowerRepo flowerRepo;

    private Map<Flower, Integer> flowersInCart;

    public CartServiceImpl() {
        flowersInCart = new HashMap<>();
    }

    @Override
    public void addFlower(Flower flower, Integer quantity) {

        if(flower.getQuantity() < quantity) {
            //todo: show error "flowers out of stock" somewhere else
            System.out.println("Error: out of stock 1");
        }

        if(flowersInCart.containsKey(flower)) {
            if(flower.getQuantity() < flowersInCart.get(flower) + quantity) {
                //todo: show error "flowers out of stock" can't add to cart
                System.out.println("Error: out of stock 2");
            }
            flowersInCart.replace(flower, flowersInCart.get(flower) + quantity);
        } else {
            flowersInCart.put(flower, quantity);
        }
    }

    @Override
    public void removeFlower(Flower flower) {
        flowersInCart.remove(flower);
    }

    @Override
    public Map<Flower, Integer> getFlowersInCart() {
        return flowersInCart;
    }

    @Override
    public Double getTotalPrice() {
        Double total = 0.0;
        Flower flower;
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        for(Map.Entry<Flower, Integer> entry: flowersInCart.entrySet()) {
            flower = flowerRepo.findByName(entry.getKey().getName());
            Integer quantity = entry.getValue();
            //total += (flower.getPrice() * quantity) * ((100.0 - user.getDiscount()) / 100);
            total += flower.getPrice() * quantity;
        }

        total = total * ((100.0 - user.getDiscount()) / 100);

        return total;
    }
}
