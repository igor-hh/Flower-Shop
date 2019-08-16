package com.accenture.flowershop.be.util;

import com.accenture.flowershop.be.business.service.FlowerStockWebService;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import java.net.URL;

public class SOAPClient {
    public static void main(String[] args) {
        try {
            URL url = new URL("http://localhost:1234/flowerstock?wsdl");
            QName qname = new QName("http://impl.service.business.be.flowershop.accenture.com/", "FlowerStockWebServiceImplService");
            Service service = Service.create(url, qname);
            FlowerStockWebService flowerStockWebService = service.getPort(FlowerStockWebService.class);
            System.out.println(flowerStockWebService.increaseFlowerStock(5) ? "Success" : "Something went wrong");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
