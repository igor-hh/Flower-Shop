package com.accenture.flowershop;

import com.accenture.flowershop.be.entity.User;
import com.accenture.flowershop.be.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class GreetingController {
    @Autowired
    private UserRepo userRepo;

    @GetMapping("/greeting")
    public String greeting(
            @RequestParam(name = "name", required = false, defaultValue = "World!") String name,
            Model model
    ) {
        model.addAttribute("name", name);
        return "greeting";
    }

    @GetMapping("/")
    public String index(Model model) {
        Iterable<User> arg = userRepo.findAll();

        model.addAttribute("arg", arg);

        return "index";
    }

    @PostMapping
    public String addUser(@RequestParam String name, @RequestParam Integer orderId, Model model) {
        User user = new User(name, orderId);
        userRepo.save(user);

        Iterable<User> arg = userRepo.findAll();

        model.addAttribute("arg", arg);

        return "index";
    }
}
