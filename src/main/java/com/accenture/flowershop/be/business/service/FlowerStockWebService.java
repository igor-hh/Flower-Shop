package com.accenture.flowershop.be.business.service;

import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

@WebService
@SOAPBinding(style = SOAPBinding.Style.RPC)
public interface FlowerStockWebService {
    @WebResult(name = "result")
    boolean increaseFlowerStock(@WebParam(name = "quantity") int count);
}
