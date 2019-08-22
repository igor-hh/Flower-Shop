package com.accenture.flowershop.be.business.service.impl;

import com.accenture.flowershop.be.business.service.FlowerService;
import com.accenture.flowershop.be.entity.flower.Flower;
import com.accenture.flowershop.be.repos.FlowerRepo;
import com.accenture.flowershop.be.util.Filter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

import static org.apache.logging.log4j.util.Strings.isBlank;
import static org.apache.logging.log4j.util.Strings.isNotBlank;

@Service
public class FlowerServiceImpl implements FlowerService {

    private static final Logger logger = LoggerFactory.getLogger(FlowerService.class);

    @Autowired
    private FlowerRepo flowerRepo;

    @Override
    public Iterable<Flower> findAll() {
        Iterable<Flower> flowers = flowerRepo.findAll();
        return flowers;
    }

    @Override
    public Flower findById(Long id) {
        return flowerRepo.findById(id).orElse(null);
    }

    @Override
    public void save(Flower flower) {
        flowerRepo.save(flower);

        logger.info("Saved flower \"{}\" to database", flower.getName());
    }

    @Override
    public void saveAll(List<Flower> flowers) {
        flowerRepo.saveAll(flowers);
    }

    @Override
    public List<Flower> getFilteredFlowerList(Filter filter) throws Exception {
        List<Flower> flowers;
        if (filter.getPriceFrom() == null) {
            filter.setPriceFrom(new BigDecimal(Double.MIN_VALUE));
        }
        if (filter.getPriceTo() == null) {
            filter.setPriceTo(new BigDecimal(Double.MAX_VALUE));
        }
        if (isBlank(filter.getFindString())) {
            flowers = flowerRepo.findByPriceBetweenOrderByNameAsc(filter.getPriceFrom(), filter.getPriceTo());
        } else {
            flowers = flowerRepo.findByNameIgnoreCaseContainingAndPriceBetweenOrderByNameAsc(filter.getFindString(), filter.getPriceFrom(), filter.getPriceTo());
        }
        if(flowers.size() == 0) {
            throw new Exception("No flowers found.");
        }

        logger.info("User applied following filters: name: \"{}\" priceFrom: \"{}\" priceTo: \"{}\"", filter.getFindString(), filter.getPriceFrom(), filter.getPriceTo());

        return flowers;
    }
}
