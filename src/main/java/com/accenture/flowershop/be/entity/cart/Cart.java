package com.accenture.flowershop.be.entity.cart;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import java.util.HashMap;
import java.util.Map;

@Component
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class Cart {
    private Map<Long, Integer> flowersInCart;

    public Cart() {
        flowersInCart = new HashMap<>();
    }

    public Map<Long, Integer> getFlowersInCart() {
        return flowersInCart;
    }

    public void setFlowersInCart(Map<Long, Integer> flowersInCart) {
        this.flowersInCart = flowersInCart;
    }
}
