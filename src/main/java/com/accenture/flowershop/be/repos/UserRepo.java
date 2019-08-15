package com.accenture.flowershop.be.repos;

import com.accenture.flowershop.be.entity.User.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends CrudRepository<User, Long> {

    User findByLogin(String login);
}
