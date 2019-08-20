package com.accenture.flowershop.be.business.service.impl;

import com.accenture.flowershop.be.business.service.UserMarshallingService;
import com.accenture.flowershop.be.entity.User.User;
import com.accenture.flowershop.be.entity.User.UserRole;
import com.accenture.flowershop.be.repos.UserRepo;
import com.accenture.flowershop.be.business.service.UserService;
import com.accenture.flowershop.be.util.JMS.UserDiscountMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserRepo userRepo;
    @Autowired
    private UserMarshallingService userMarshallingService;
    @Autowired
    private JmsTemplate jmsTemplate;
    @Autowired
    private RestTemplate restTemplate;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByLogin(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found!");
        }
        return user;
    }

    //returns true if user was successfully created
    @Override
    public boolean addUser(User user) {

        User userFromDb = userRepo.findByLogin(user.getLogin());
        if(userFromDb != null) {
            return false;
        }

//        Boolean response = restTemplate.getForObject("http://localhost:8080/user/" + user.getLogin(), Boolean.class);
//        if(response) {
//            return false;
//        }

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

        UserDiscountMessage userDiscountMessage = new UserDiscountMessage();
        userDiscountMessage.setId(user.getId());
        userDiscountMessage.setDiscount(user.getDiscount() + 1);
        jmsTemplate.convertAndSend("discount", userDiscountMessage);

        logger.info("Sent JMS message to \"discount\" topic with new user discount");

        return true;
    }

    @Override
    public void save(User user) {
        userRepo.save(user);
    }

    @Override
    public User findById(Long id) {
        return userRepo.findById(id).orElse(null);
    }

    @Override
    public User findByLogin(String login) {
        return userRepo.findByLogin(login);
    }
}
