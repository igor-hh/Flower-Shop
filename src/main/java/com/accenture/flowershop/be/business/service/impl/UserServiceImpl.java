package com.accenture.flowershop.be.business.service.impl;

import com.accenture.flowershop.be.business.service.UserMarshallingService;
import com.accenture.flowershop.be.entity.User.User;
import com.accenture.flowershop.be.entity.User.UserRole;
import com.accenture.flowershop.be.repos.UserRepo;
import com.accenture.flowershop.be.business.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private UserMarshallingService userMarshallingService;

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

        logger.info("Added user {} with id: {} to database", user.getLogin(), user.getId());

        try {
            userMarshallingService.convertUserToXML(user);
            logger.info("Converted user {} to XML", user.getLogin());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return true;
    }

    @Override
    public void save(User user) {
        userRepo.save(user);
    }
}
