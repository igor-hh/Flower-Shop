package com.accenture.flowershop.be.util;

import com.accenture.flowershop.be.business.service.FlowerStockWebService;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import java.net.URL;
import java.util.Random;

public class SOAPClient {
    public static void main(String[] args) {
        try {
            URL url = new URL("http://localhost:1234/flowerstock?wsdl");
            QName qname = new QName("http://impl.service.business.be.flowershop.accenture.com/", "FlowerStockWebServiceImplService");
            Service service = Service.create(url, qname);
            FlowerStockWebService flowerStockWebService = service.getPort(FlowerStockWebService.class);

            //infinite loop
            while(true) {
                try {
                    int quantity = new Random().nextInt(21) + 10; //random int [10-30]
                    System.out.println(flowerStockWebService.increaseFlowerStock(quantity) ? "Success, quantity: " + quantity : "Something went wrong");
                    Thread.sleep(600000); //1000 * 60 * 10 = 10 minutes
                } catch (InterruptedException i) {
                    i.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
