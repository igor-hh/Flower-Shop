package com.accenture.flowershop.be.controllers;

import com.accenture.flowershop.be.business.service.UserService;
import com.accenture.flowershop.be.entity.User.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import static org.apache.logging.log4j.util.Strings.isNotBlank;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    RestTemplate restTemplate;

    @GetMapping("/login")
    public String login(@RequestParam(value = "error", required = false) String error,
                        @AuthenticationPrincipal User user,
                        Model model) {
        if (error != null) {
            model.addAttribute("loginError", "Your login attempt was not successful, try again.");
        }
        if (user != null) {
            return "redirect:/";
        }
        return "login";
    }

    @GetMapping("/signup")
    public String signup() {
        return "signup";
    }

    @PostMapping("/signup")
    public String createUser(User user, Model model) {
        if (!isNotBlank(user.getPassword())) {
            model.addAttribute("passwordBlank", "Password cannot be blank");
            return "signup";
        }
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
