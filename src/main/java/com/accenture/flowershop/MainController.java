package com.accenture.flowershop;

import com.accenture.flowershop.be.entity.Flower;
import com.accenture.flowershop.be.entity.Order;
import com.accenture.flowershop.be.entity.UserRole;
import com.accenture.flowershop.be.entity.User;
import com.accenture.flowershop.be.repos.FlowerRepo;
import com.accenture.flowershop.be.repos.OrderRepo;
import com.accenture.flowershop.be.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collections;
import java.util.Date;
import java.util.List;

@Controller
public class MainController {
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private OrderRepo orderRepo;

    @Autowired
    private FlowerRepo flowerRepo;

    @GetMapping("/")
    public String indexPage(Model model) {
        Iterable<Flower> flowers = flowerRepo.findAll();

        model.addAttribute("flowers", flowers);

        return "index";
    }

    @PostMapping("find")
    public String findFlowers(@RequestParam String find, Model model) {
        Iterable<Flower> flowers = flowerRepo.findByNameIgnoreCaseContaining(find);

        if(find == "") {
            flowers = flowerRepo.findAll();
        }

        model.addAttribute("flowers", flowers);

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
}
