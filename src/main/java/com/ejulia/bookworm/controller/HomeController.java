package com.ejulia.bookworm.controller;

import com.ejulia.bookworm.model.Home;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    private static final String welcomeTemplate = "Welcome %s!";

    @GetMapping("/home")
    public Home home(@RequestParam(value="userName", defaultValue="everyone") String userName) {
        return new Home(String.format(welcomeTemplate, userName));
    }
}
