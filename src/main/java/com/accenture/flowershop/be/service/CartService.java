package com.accenture.flowershop.be.service;

import com.accenture.flowershop.be.entity.Flower;

import java.util.Map;

public interface CartService {

    void addFlower(Flower flower, Integer quantity);

    void removeFlower(Flower flower);

    Map<Flower, Integer> getFlowersInCart();
}
