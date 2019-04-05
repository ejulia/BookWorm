package com.ejulia.bookworm.service;

import com.ejulia.bookworm.model.Home;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HomeService {

    public String getWelcomeMessage(String userName) {
        Home home = new Home();
        return home.getWelcomeMessage(userName);
    }
}
