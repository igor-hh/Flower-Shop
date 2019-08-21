package com.accenture.flowershop.be.business.service;

import com.accenture.flowershop.be.entity.flower.Flower;
import com.accenture.flowershop.be.util.Filter;

import java.util.List;

public interface FlowerService {

    Iterable<Flower> findAll();
    Flower findById(Long id);
    void save(Flower flower);
    void saveAll(List<Flower> flowers);
    List<Flower> getFilteredFlowerList(Filter filter) throws Exception;
}