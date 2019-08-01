package com.accenture.flowershop.be.repos;

import com.accenture.flowershop.be.entity.Flower;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface FlowerRepo extends CrudRepository<Flower, Long> {
    //find flowers by name containing input, case insensitive
    List<Flower> findByNameIgnoreCaseContaining(String name);
}
