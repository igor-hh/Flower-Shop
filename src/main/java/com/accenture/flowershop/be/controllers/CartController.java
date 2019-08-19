package com.accenture.flowershop.be.controllers;

import com.accenture.flowershop.be.business.service.CartService;
import com.accenture.flowershop.be.business.service.FlowerService;
import com.accenture.flowershop.be.entity.Flower.Flower;
import com.accenture.flowershop.be.entity.cart.CartItem;
import org.atteo.evo.inflector.English;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class CartController {

    @Autowired
    private FlowerService flowerService;
    @Autowired
    private CartService cartService;

    @GetMapping("/cart")
    public String showCart(Model model) {
        if (cartService.getCart().getFlowersInCart().isEmpty()) {
            model.addAttribute("cartEmpty", "Your cart is empty.");
            return "cart";
        }

        List<CartItem> cartItems = new ArrayList<>();
        for (Map.Entry<Long, Integer> entry : cartService.getCart().getFlowersInCart().entrySet()) {
            Flower flower = flowerService.findById(entry.getKey());
            CartItem cartItem = new CartItem(entry.getKey(), flower.getName(),
                    flower.getPrice().multiply(new BigDecimal(entry.getValue())), entry.getValue());
            cartItems.add(cartItem);
        }
        model.addAttribute("cartItems", cartItems);
        model.addAttribute("totalPrice", cartService.getTotalPrice());
        return "cart";
    }

    @PostMapping("/cart")
    public String addToCart(Long flowerId, Integer cartQuantity,
                            Model model, RedirectAttributes redirectAttributes) {
        try {
            Flower flower = cartService.addFlower(flowerId, cartQuantity);
            redirectAttributes.addFlashAttribute("cartSuccess", "Added " +
                    cartQuantity + " " +
                    (cartQuantity > 1 ? English.plural(flower.getName()) : flower.getName()) + //plural form for flower if quantity > 1
                    " to cart!");
            redirectAttributes.addFlashAttribute("addedFlower", flower.getId().toString());
            return "redirect:/";
        } catch (Exception e) {
            model.addAttribute("cartError", e.getMessage());
        }
        return "index";
    }

    @PostMapping("/cart/remove")
    public String removeFromCart(Long flowerId) {
        try {
            cartService.removeFlower(flowerId);
        } catch (Exception e) {
            return "redirect:/cart";
        }
        return "redirect:/cart";
    }
}
