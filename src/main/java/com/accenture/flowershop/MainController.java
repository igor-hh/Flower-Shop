package com.accenture.flowershop;

import com.accenture.flowershop.be.entity.Flower;
import com.accenture.flowershop.be.entity.Order;
import com.accenture.flowershop.be.entity.OrderStatus;
import com.accenture.flowershop.be.entity.User;
import com.accenture.flowershop.be.repos.FlowerRepo;
import com.accenture.flowershop.be.repos.OrderRepo;
import com.accenture.flowershop.be.service.CartService;
import com.accenture.flowershop.be.service.OrderService;
import com.accenture.flowershop.be.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;


@Controller
public class MainController {
    @Autowired
    private UserService userService;
    @Autowired
    private OrderRepo orderRepo;
    @Autowired
    private FlowerRepo flowerRepo;
    @Autowired
    private CartService cart;
    @Autowired
    private OrderService orderService;

    @GetMapping("/")
    public String indexPage(@RequestParam(required = false) String findString,
                            @RequestParam(required = false) Double priceFrom,
                            @RequestParam(required = false) Double priceTo,
                            Model model) {
        Iterable<Flower> flowers;

        if (priceFrom == null) {
            priceFrom = Double.MIN_VALUE;
        }
        if (priceTo == null) {
            priceTo = Double.MAX_VALUE;
        }
        if (findString == null || findString.isEmpty()) {
            flowers = flowerRepo.findByPriceBetweenOrderByNameAsc(priceFrom, priceTo);
        } else {
            flowers = flowerRepo.findByNameIgnoreCaseContainingAndPriceBetweenOrderByNameAsc(findString, priceFrom, priceTo);
        }

        if (priceFrom == Double.MIN_VALUE) {
            priceFrom = null;
        }
        if (priceTo == Double.MAX_VALUE) {
            priceTo = null;
        }

        model.addAttribute("flowers", flowers);
        model.addAttribute("findString", findString);
        model.addAttribute("priceFrom", priceFrom);
        model.addAttribute("priceTo", priceTo);

        return "index";
    }

    @GetMapping("/signup")
    public String signup() {
        return "signup";
    }

    @PostMapping("/signup")
    public String createUser(User user, Model model) {

        if (user.getPassword() != null && !user.getPassword().equals(user.getPassword2())) {
            model.addAttribute("passwordError", "Passwords must me equal");
            return "signup";
        }

        if (!userService.addUser(user)) {
            model.addAttribute("userError", "User " + user.getLogin() + " already exists!");
            return "signup";
        }

        return "redirect:/login";
    }

    @GetMapping("/login")
    public String login(@RequestParam(value = "error", required = false) String error, Model model) {
        if (error != null) {
            model.addAttribute("loginError", "Your login attempt was not successful, try again.");
        }
        return "login";
    }

    @GetMapping("/cart")
    public String showCart(Model model) {
        if (cart.getFlowersInCart().isEmpty()) {
            model.addAttribute("cartEmpty", "Your cart is empty.");
            return "cart";
        }

        model.addAttribute("cart", cart);
        model.addAttribute("flowersInCart", cart.getFlowersInCart());
        return "cart";
    }

    @PostMapping("/cart")
    public String addToCart(Flower flowerInCart, Integer cartQuantity, Model model) {
        Flower flower = flowerRepo.findByName(flowerInCart.getName());
        Map<Flower, Integer> flowersInCart = cart.getFlowersInCart();

        if (flower.getQuantity() < cartQuantity) {
            model.addAttribute("quantityError1", "Not enough flowers on stock.");
            return "index";
        }
        if (flowersInCart.containsKey(flower) && (flower.getQuantity() < flowersInCart.get(flower) + cartQuantity)) {
            model.addAttribute("quantityError2", "Not enough flower " +
                    flower.getName() +
                    " on stock. You already have " +
                    flowersInCart.get(flower) + " of that flower in your cart");
            return "index";
        }

        cart.addFlower(flower, cartQuantity);

        return "redirect:/cart";
    }

    @PostMapping("/cart/remove")
    public String removeFromCart(Flower flowerInCart) {
        Flower flower = flowerRepo.findByName(flowerInCart.getName());

        cart.removeFlower(flower);

        return "redirect:/cart";
    }

    @GetMapping("/order")
    public String orderList(@AuthenticationPrincipal User user, Model model) {

        Iterable<Order> orders = orderRepo.findByOwnerOrderByIdAsc(user);

        if (orderRepo.findByOwnerOrderByIdAsc(user).size() == 0) {
            model.addAttribute("ordersEmpty", "You have no orders :(");
            return "order";
        }

        model.addAttribute("orders", orders);

        return "order";
    }

    @PostMapping("/order")
    public String createOrder() {

        orderService.createOrderFromCart(cart);

        return "redirect:/order";
    }

    @PostMapping("/order/pay")
    public String payOrder(@AuthenticationPrincipal User user, Order orderToPay, Model model) {
        Order order = orderRepo.findById(orderToPay.getId()).orElse(null);
        if(order.getTotalPrice() > user.getBalance()) {
            model.addAttribute("payError", "Not enough money to pay. You " +
                    (order.getTotalPrice() - user.getBalance()) + " balance short");
            return "order";
        }
        orderService.payOrder(order);

        return "redirect:/order";
    }

    @GetMapping("/manageOrders")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String manageOrderList(Model model) {

        Iterable<Order> orders = orderRepo.findByStatus(OrderStatus.PAID.name());

        if (orderRepo.findByStatus(OrderStatus.PAID.name()).size() == 0) {
            model.addAttribute("ordersEmpty", "No paid orders to display");
            return "manageOrders";
        }

        model.addAttribute("orders", orders);

        return "manageOrders";
    }

    @PostMapping("/manageOrders")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String closeOrder(Order orderToClose) {
        Order order = orderRepo.findById(orderToClose.getId()).orElse(null);

        orderService.closeOrder(order);

        return "redirect:/manageOrders";
    }

    @GetMapping("/403")
    public String accessDenied() {

        return "403";
    }
}
