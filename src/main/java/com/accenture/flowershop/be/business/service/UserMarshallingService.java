package com.accenture.flowershop.be.business.service;

import com.accenture.flowershop.be.entity.User.User;

import javax.xml.bind.JAXBException;
import java.io.IOException;

public interface UserMarshallingService {
    void convertUserToXML(User user) throws JAXBException, IOException;
}
