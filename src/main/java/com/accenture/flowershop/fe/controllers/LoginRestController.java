package com.accenture.flowershop.fe.controllers;

import com.accenture.flowershop.be.business.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginRestController {

    @Autowired
    private UserService userService;

    //returns true if login exists
    @GetMapping("user/{login}")
    public boolean checkLoginExists(@PathVariable String login) {
        return !(userService.findByLogin(login) == null);
    }
}
