package com.accenture.flowershop.be.business.service.impl;

import com.accenture.flowershop.be.business.service.UserMarshallingService;
import com.accenture.flowershop.be.entity.User.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@Service
public class UserMarshallingServiceImpl implements UserMarshallingService {

    @Value("${marshallingPath}")
    private String filepath;

    @Override
    public void convertUserToXML(User user) throws JAXBException, IOException {
        File file = new File(filepath + "user-" + user.getId() + "-" + user.getLogin());
        file.getParentFile().mkdirs();
        FileOutputStream os = new FileOutputStream(file);
        JAXBContext jaxbContext = JAXBContext.newInstance(User.class);
        Marshaller marshaller = jaxbContext.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.marshal(user, file);
        os.close();
    }
}
