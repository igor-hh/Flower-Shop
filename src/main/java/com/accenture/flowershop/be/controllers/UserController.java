package com.accenture.flowershop.be.controllers;

import com.accenture.flowershop.be.entity.User;
import com.accenture.flowershop.be.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String login(@RequestParam(value = "error", required = false) String error, Model model) {
        if (error != null) {
            model.addAttribute("loginError", "Your login attempt was not successful, try again.");
        }
        return "login";
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
}
