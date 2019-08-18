package com.accenture.flowershop.be.business.service.impl;

import com.accenture.flowershop.be.business.service.FlowerService;
import com.accenture.flowershop.be.business.service.FlowerStockWebService;
import com.accenture.flowershop.be.entity.Flower.Flower;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@WebService(endpointInterface = "com.accenture.flowershop.be.business.service.FlowerStockWebService")
@SOAPBinding(style = SOAPBinding.Style.RPC)
public class FlowerStockWebServiceImpl implements FlowerStockWebService {

    private static FlowerService flowerService;

    @Autowired
    public void setFlowerService(FlowerService flowerService) {
        this.flowerService = flowerService;
    }

    @Override
    @Transactional
    @WebMethod
    public boolean increaseFlowerStock(int count) {
        Iterable<Flower> flowers = flowerService.findAll();
        List<Flower> flowerList = new ArrayList<>();

        for(Flower flower : flowers) {
            flower.setQuantity(flower.getQuantity() + count);
            flowerList.add(flower);
        }
        try {
            flowerService.saveAll(flowerList);
        } catch(Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
