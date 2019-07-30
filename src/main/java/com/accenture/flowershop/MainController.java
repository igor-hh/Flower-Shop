package com.accenture.flowershop;

import com.accenture.flowershop.be.entity.Role;
import com.accenture.flowershop.be.entity.User;
import com.accenture.flowershop.be.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collections;

@Controller
public class MainController {
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
        user.setRoles(Collections.singleton(Role.USER));
        userRepo.save(user);

        return "redirect:/login";
    }
}
