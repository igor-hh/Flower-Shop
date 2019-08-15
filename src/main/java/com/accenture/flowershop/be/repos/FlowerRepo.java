package com.accenture.flowershop.be.repos;

import com.accenture.flowershop.be.entity.Flower.Flower;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface FlowerRepo extends CrudRepository<Flower, Long> {

    List<Flower> findByNameIgnoreCaseContainingAndPriceBetweenOrderByNameAsc(String name, BigDecimal priceFrom, BigDecimal priceTo);

    List<Flower> findByPriceBetweenOrderByNameAsc(BigDecimal priceFrom, BigDecimal priceTo);

    Flower findByName(String name);
}
