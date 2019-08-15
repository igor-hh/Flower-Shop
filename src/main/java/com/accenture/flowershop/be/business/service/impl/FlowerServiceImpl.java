package com.accenture.flowershop.be.business.service.impl;

import com.accenture.flowershop.be.entity.Flower.Flower;
import com.accenture.flowershop.be.repos.FlowerRepo;
import com.accenture.flowershop.be.business.service.FlowerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class FlowerServiceImpl implements FlowerService {

    private static final Logger logger = LoggerFactory.getLogger(FlowerService.class);

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

        logger.info("Saved flower \"{}\" to database", flower.getName());
    }
}
