package com.ejulia.bookworm.controller;

import com.ejulia.bookworm.model.User;
import com.ejulia.bookworm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController     //Means the following class is a controller
@RequestMapping(path = "/demo")      //Means URL start with /demo (after Application path)
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping(path = "/add") //Map ONLY GET Requests, adress "/demo/add"
    public String addNewUser(@RequestParam String name, @RequestParam String email) {
        //@RequestParam means it's a parameter form the GET or POST request

        // TODO : check input fields

        userService.createUser(name,email);

        // If createUser returns a String
        // return userService.createUser(name, email);

        return "new user registered";
    }

    @GetMapping(path = "/all")
    public List<User> getAllUsers() {

        //Returns a JSON or XML with the users
        return userService.displayAllUsers();

    }

}
