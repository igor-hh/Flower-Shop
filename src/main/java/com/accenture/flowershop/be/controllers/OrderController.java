package com.accenture.flowershop.be.controllers;

import com.accenture.flowershop.be.entity.Order;
import com.accenture.flowershop.be.entity.OrderStatus;
import com.accenture.flowershop.be.entity.User;
import com.accenture.flowershop.be.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/order")
    public String orderList(@AuthenticationPrincipal User user, Model model) {

        List<Order> orders = orderService.findByOwner(user);

        if(orders.size() == 0) {
            model.addAttribute("ordersEmpty", "You have no orders :(");
            return "order";
        }

        model.addAttribute("orders", orders);

        return "order";
    }

    @PostMapping("/order")
    public String createOrder() {

        orderService.createOrderFromCart();

        return "redirect:/order";
    }

    @PostMapping("/order/pay")
    public String payOrder(@AuthenticationPrincipal User user, Order orderToPay, Model model) {
        Order order = orderService.findById(orderToPay.getId());

        if(order.getTotalPrice().compareTo(user.getBalance()) == 1) {
            model.addAttribute("payError", "Not enough money to pay. You " +
                    (order.getTotalPrice().doubleValue() - user.getBalance().doubleValue()) + " balance short");
            return "order";
        }
        orderService.payOrder(order);

        return "redirect:/order";
    }

    @GetMapping("/manageOrders")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String manageOrderList(Model model) {

        List<Order> orders = orderService.findByStatus(OrderStatus.PAID.name());

        if (orderService.findByStatus(OrderStatus.PAID.name()).size() == 0) {
            model.addAttribute("ordersEmpty", "No paid orders to display");
            return "manageOrders";
        }

        model.addAttribute("orders", orders);

        return "manageOrders";
    }

    @PostMapping("/manageOrders")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String closeOrder(Order orderToClose) {
        Order order = orderService.findById(orderToClose.getId());

        orderService.closeOrder(order);

        return "redirect:/manageOrders";
    }
}
