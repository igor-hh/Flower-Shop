package com.accenture.flowershop;

import com.accenture.flowershop.be.entity.Flower;
import com.accenture.flowershop.be.entity.Order;
import com.accenture.flowershop.be.entity.UserRole;
import com.accenture.flowershop.be.entity.User;
import com.accenture.flowershop.be.repos.FlowerRepo;
import com.accenture.flowershop.be.repos.OrderRepo;
import com.accenture.flowershop.be.repos.UserRepo;
import com.accenture.flowershop.be.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collections;
import java.util.Date;

@Controller
public class MainController {
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private OrderRepo orderRepo;

    @Autowired
    private FlowerRepo flowerRepo;

    @Autowired
    CartService cart;

    @GetMapping("/")
    public String indexPage(@RequestParam(required = false) String findString,
                            @RequestParam(required = false) Double priceFrom,
                            @RequestParam(required = false) Double priceTo,
                            Model model) {
        Iterable<Flower> flowers;

        if(priceFrom == null) {
            priceFrom = Double.MIN_VALUE;
        }
        if(priceTo == null) {
            priceTo = Double.MAX_VALUE;
        }
        if(findString == null || findString.isEmpty() == true) {
            flowers = flowerRepo.findByPriceBetween(priceFrom, priceTo);
        } else {
            flowers = flowerRepo.findByNameIgnoreCaseContainingAndPriceBetween(findString, priceFrom, priceTo);
        }

        if(priceFrom == Double.MIN_VALUE) {
            priceFrom = null;
        }
        if(priceTo == Double.MAX_VALUE) {
            priceTo = null;
        }

        model.addAttribute("flowers", flowers);
        model.addAttribute("findString", findString);
        model.addAttribute("priceFrom", priceFrom);
        model.addAttribute("priceTo", priceTo);

        return "index";
    }

    @GetMapping("/main")
    public String mainPage(Model model) {
        Iterable<User> users = userRepo.findAll();

        model.addAttribute("users", users);

        return "main";
    }

    @PostMapping("/main")
    public String createOrder(Order order, Model model) {
        order.setCreationDate(new Date());
        order.setCloseDate(new Date());
        order.setStatus("test created");
        orderRepo.save(order);

        return "main";
    }

    @GetMapping("/signup")
    public String signup() {
        return "signup";
    }

    @PostMapping("/signup")
    public String createUser (User user, Model model) {
        User userDB = userRepo.findByLogin(user.getLogin());

        if (userDB != null) {
            model.addAttribute("message", "User exists!");
            return "signup";
        }

        user.setActive(true);
        user.setRoles(Collections.singleton(UserRole.USER));
        userRepo.save(user);

        return "redirect:/login";
    }

    @GetMapping("/cart")
    public String showCart(Model model) {
        cart.getFlowersInCart().clear();
        cart.addFlower(flowerRepo.findByName("Rose"), 1);
        cart.addFlower(flowerRepo.findByName("Tulip"), 2);
        cart.addFlower(flowerRepo.findByName("Narcissus"), 3);

        model.addAttribute("flowersInCart", cart.getFlowersInCart());
        return "cart";
    }

    @GetMapping("/order")
    public String orderList(Model model) {

        Iterable<Order> orders = orderRepo.findAll();

        model.addAttribute("orders", orders);

        return "order";
    }
}
