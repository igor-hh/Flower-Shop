package com.accenture.flowershop.be.repos;

import com.accenture.flowershop.be.entity.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepo extends CrudRepository<User, Long> {
    User findByLogin(String login);
}
