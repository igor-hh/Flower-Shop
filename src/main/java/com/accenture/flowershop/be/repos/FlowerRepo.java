package com.accenture.flowershop.be.repos;

import com.accenture.flowershop.be.entity.Flower;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface FlowerRepo extends CrudRepository<Flower, Long> {
    //find flowers by name containing input, case insensitive and by price range from-to
    List<Flower> findByNameIgnoreCaseContainingAndPriceBetween(String name, Double priceFrom, Double priceTo);
    List<Flower> findByPriceBetween(Double priceFrom, Double priceTo);
    Flower findByName(String name);
}
