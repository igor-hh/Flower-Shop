package com.accenture.flowershop.be.service.impl;

import com.accenture.flowershop.be.entity.Flower;
import com.accenture.flowershop.be.repos.FlowerRepo;
import com.accenture.flowershop.be.service.FlowerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class FlowerServiceImpl implements FlowerService {

    @Autowired
    private FlowerRepo flowerRepo;

    @Override
    public Flower findById(Long id) {
        return flowerRepo.findById(id).orElse(null);
    }

    @Override
    public List<Flower> findByPrices(BigDecimal priceFrom, BigDecimal priceTo) {
        return flowerRepo.findByPriceBetweenOrderByNameAsc(priceFrom, priceTo);
    }

    @Override
    public List<Flower> findByNameAndPrices(String findString, BigDecimal priceFrom, BigDecimal priceTo) {
        return flowerRepo.findByNameIgnoreCaseContainingAndPriceBetweenOrderByNameAsc(findString, priceFrom, priceTo);
    }

    @Override
    public void save(Flower flower) {
        flowerRepo.save(flower);
    }
}
