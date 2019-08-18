package com.accenture.flowershop.be.util.JMS;

import com.accenture.flowershop.be.business.service.UserService;
import com.accenture.flowershop.be.entity.User.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
@EnableJms
public class UserDiscountMessageReceiver {

    private static final Logger logger = LoggerFactory.getLogger(UserDiscountMessage.class);

    @Autowired
    private UserService userService;

    @JmsListener(destination = "discount")
    public void receive(UserDiscountMessage userDiscountMessage) {

        User user;
        user = userService.findById(userDiscountMessage.getId());
        user.setDiscount(userDiscountMessage.getDiscount());
        userService.save(user);

        logger.info("Saved new discount for user {} via JMS Message", user.getLogin());
    }
}
