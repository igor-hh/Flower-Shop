package com.accenture.flowershop.be.business.service;

import com.accenture.flowershop.be.entity.Flower.Flower;

import java.math.BigDecimal;
import java.util.List;

public interface FlowerService {

    Iterable<Flower> findAll();
    Flower findById(Long id);
    List<Flower> findByPrices(BigDecimal priceFrom, BigDecimal priceTo);
    List<Flower> findByNameAndPrices(String findString, BigDecimal priceFrom, BigDecimal priceTo);
    void save(Flower flower);
    void saveAll(List<Flower> flowers);
}