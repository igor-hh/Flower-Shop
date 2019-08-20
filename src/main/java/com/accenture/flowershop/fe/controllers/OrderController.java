package com.accenture.flowershop.fe.controllers;

import com.accenture.flowershop.be.business.service.OrderService;
import com.accenture.flowershop.be.entity.Order.Order;
import com.accenture.flowershop.be.entity.Order.OrderStatus;
import com.accenture.flowershop.be.entity.User.User;
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
        try {
            List<Order> orders = orderService.findByOwner(user);
            model.addAttribute("orders", orders);
            return "order";
        } catch (Exception e) {
            model.addAttribute("ordersEmpty", e.getMessage());
            return "order";
        }
    }

    @PostMapping("/order")
    public String createOrder() {

        orderService.createOrderFromCart();

        return "redirect:/order";
    }

    @PostMapping("/order/pay")
    public String payOrder(Long orderId, Model model) {
        try {
            orderService.payOrder(orderId);
            return "redirect:/order";
        } catch (Exception e) {
            model.addAttribute("payError", e.getMessage());
            return "order";
        }
    }

    @GetMapping("/manageOrders")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String manageOrderList(Model model) {
        try {
            List<Order> orders = orderService.findByStatus(OrderStatus.PAID.name());
            model.addAttribute("orders", orders);
            return "manageOrders";
        } catch (Exception e) {
            model.addAttribute("ordersEmpty", e.getMessage());
            return "manageOrders";
        }
    }

    @PostMapping("/manageOrders")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String closeOrder(Long orderId, Model model) {
        try {
            orderService.closeOrder(orderId);
            return "redirect:/manageOrders";
        } catch (Exception e) {
            model.addAttribute("closeError", e.getMessage());
            return "manageOrders";
        }
    }
}
