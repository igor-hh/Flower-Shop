package com.accenture.flowershop.be.service.impl;

import com.accenture.flowershop.be.entity.User;
import com.accenture.flowershop.be.entity.UserRole;
import com.accenture.flowershop.be.repos.UserRepo;
import com.accenture.flowershop.be.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    @Autowired
    UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepo.findByLogin(username);
    }

    //returns true if user was successfully created
    @Override
    public boolean addUser(User user) {
        User userFromDB = userRepo.findByLogin(user.getLogin());
        if(userFromDB != null) {
            return false;
        }

        user.setActive(true);
        user.setRoles(Collections.singleton(UserRole.USER));
        userRepo.save(user);

        return true;
    }
}
