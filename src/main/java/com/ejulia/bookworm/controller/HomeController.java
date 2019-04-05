package com.ejulia.bookworm.controller;

import com.ejulia.bookworm.model.Home;
import com.ejulia.bookworm.service.HomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

// TODO : All Home* classes don't respect our code structure

@RestController
public class HomeController {

    @Autowired
    private HomeService homeService;

    @GetMapping("/home")
    public String welcomeMessage(@RequestParam (defaultValue = "everyone") String userName) {
        return homeService.getWelcomeMessage(userName);
    }
}
