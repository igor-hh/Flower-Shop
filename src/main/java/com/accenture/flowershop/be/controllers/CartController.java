package com.accenture.flowershop.be.controllers;

import com.accenture.flowershop.be.entity.Flower;
import com.accenture.flowershop.be.service.CartService;
import com.accenture.flowershop.be.service.FlowerService;
import org.atteo.evo.inflector.English;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Map;

@Controller
public class CartController {

    @Autowired
    private FlowerService flowerService;
    @Autowired
    private CartService cartService;

    @GetMapping("/cart")
    public String showCart(Model model) {
        if (cartService.getFlowersInCart().isEmpty()) {
            model.addAttribute("cartEmpty", "Your cart is empty.");
            return "cart";
        }

        model.addAttribute("cart", cartService);
        model.addAttribute("flowersInCart", cartService.getFlowersInCart());
        return "cart";
    }

    @PostMapping("/cart")
    public String addToCart(Flower flowerInCart, Integer cartQuantity, Model model, RedirectAttributes redirectAttributes) {
        Flower flower = flowerService.findById(flowerInCart.getId());
        Map<Long, Integer> flowersInCart = cartService.getFlowersInCart();

        if (flower.getQuantity() < cartQuantity) {
            model.addAttribute("quantityError1", "Not enough flowers on stock.");
            return "index";
        }
        if (flowersInCart.containsKey(flower) && (flower.getQuantity() < flowersInCart.get(flower) + cartQuantity)) {
            model.addAttribute("quantityError2", "Not enough flower " +
                    flower.getName() +
                    " on stock. You already have " +
                    flowersInCart.get(flower) + " of that flower in your cart.");
            return "index";
        }

        cartService.addFlower(flower, cartQuantity);
        redirectAttributes.addFlashAttribute("cartSuccess", "Added " +
                cartQuantity + " " +
                (cartQuantity > 1 ? English.plural(flower.getName()) : flower.getName()) + //plural form for flower if quantity > 1
                " to cart!");
        redirectAttributes.addFlashAttribute("addedFlower", flower.getId().toString());

        return "redirect:/";
    }

    @PostMapping("/cart/remove")
    public String removeFromCart(Flower flowerInCart) {
        Flower flower = flowerService.findById(flowerInCart.getId());

        cartService.removeFlower(flower);

        return "redirect:/cart";
    }
}
