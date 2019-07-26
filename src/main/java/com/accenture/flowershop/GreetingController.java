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

    @GetMapping("/")
    public String greeting(Model model) {
        return "greeting";
    }

    @GetMapping("/main")
    public String index(Model model) {
        Iterable<User> arg = userRepo.findAll();

        model.addAttribute("arg", arg);

        return "main";
    }

    @GetMapping("/signup")
    public String signup(Model model) {
        Iterable<User> arg = userRepo.findAll();

        model.addAttribute("arg", arg);

        return "signup";
    }

    @PostMapping("/signup")
    public String addUser(@RequestParam String login, @RequestParam String password,
                          @RequestParam(defaultValue = "Holop") String role, @RequestParam String fullName,
                          @RequestParam String phone, @RequestParam String address, Model model) {
        User user = new User(login, password, role, fullName, phone, address);
        userRepo.save(user);

        Iterable<User> arg = userRepo.findAll();

        model.addAttribute("arg", arg);

        return "signup";
    }
}
