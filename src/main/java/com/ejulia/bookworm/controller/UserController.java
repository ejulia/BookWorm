package com.ejulia.bookworm.controller;

import com.ejulia.bookworm.model.User;
import com.ejulia.bookworm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController     //Means the following class is a controller
@RequestMapping(path = "/users")      //Means URL start with /demo (after Application path)
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping(path = "/add") //Map ONLY GET Requests, adress "/demo/add"
    public String addUser(@RequestParam String firstName, @RequestParam String lastName,@RequestParam String phone, @RequestParam String email) {
    //@RequestParam means it's a parameter form the GET or POST request
        // TODO_suggestion : check input fields
        return userService.createUser(firstName, lastName, phone, email);
    }

    @GetMapping(path = "/delete")
    public String deleteUser(@RequestParam Integer id) {
        return userService.deleteUser(id);
    }

    @GetMapping(path = "/all")
    public List<User> getAllUsers() {
        //Returns a JSON or XML with the users
        return userService.displayAllUsers();
    }

}
