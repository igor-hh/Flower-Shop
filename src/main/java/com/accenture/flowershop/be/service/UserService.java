package com.accenture.flowershop.be.service;

import com.accenture.flowershop.be.entity.User;

public interface UserService {

    boolean addUser(User user);
    void save(User user);
}
