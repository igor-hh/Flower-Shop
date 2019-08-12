package com.accenture.flowershop.be.repos;

import com.accenture.flowershop.be.entity.Flower;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface FlowerRepo extends CrudRepository<Flower, Long> {
    //find flowers by name containing input and by price range from-to, case insensitive, order by name ascending
    List<Flower> findByNameIgnoreCaseContainingAndPriceBetweenOrderByNameAsc(String name, Double priceFrom, Double priceTo);

    List<Flower> findByPriceBetweenOrderByNameAsc(Double priceFrom, Double priceTo);

    Flower findByName(String name);
}
