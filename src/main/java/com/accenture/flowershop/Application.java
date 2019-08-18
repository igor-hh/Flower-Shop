package com.accenture.flowershop;

import com.accenture.flowershop.be.business.service.impl.FlowerStockWebServiceImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jms.annotation.EnableJms;

import javax.xml.ws.Endpoint;

@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);

        Endpoint.publish("http://localhost:1234/flowerstock", new FlowerStockWebServiceImpl());
    }
}