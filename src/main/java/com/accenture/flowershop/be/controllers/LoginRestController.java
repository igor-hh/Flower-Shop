package com.accenture.flowershop.be.controllers;

import com.accenture.flowershop.be.business.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("user")
public class LoginRestController {

    @Autowired
    private UserService userService;

    //returns true if login is exists
    @GetMapping("{login}")
    public boolean checkLoginExists(@PathVariable String login) {
        return !(userService.findByLogin(login) == null);
    }
}
