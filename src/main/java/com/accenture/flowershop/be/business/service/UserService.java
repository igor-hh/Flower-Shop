package com.accenture.flowershop.be.business.service;

import com.accenture.flowershop.be.entity.User.User;

public interface UserService {

    boolean addUser(User user);
    void save(User user);
    User findById(Long id);
    User findByLogin(String login);
}
