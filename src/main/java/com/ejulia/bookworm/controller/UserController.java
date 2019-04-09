package com.ejulia.bookworm.controller;

import com.ejulia.bookworm.model.User;
import com.ejulia.bookworm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController     //Means the following class is a controller
@RequestMapping(path = "/users")      //Means URL start with /demo (after Application path)
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping(path = "/add") //Map ONLY GET Requests, adress "/demo/add"
    public String addUser(@RequestParam(defaultValue="") String firstName, @RequestParam(defaultValue="") String lastName,
            @RequestParam(defaultValue="") String phone, @RequestParam(defaultValue="") String email) {
    //@RequestParam means it's a parameter form the GET or POST request
        // TODO_suggestion : check input fields
        return userService.addUser(firstName, lastName, phone, email);
    }

    @GetMapping(path = "/delete")
    public String deleteUser(@RequestParam Integer userId) {
        return userService.deleteUser(userId);
    }

    @GetMapping(path= "/find", params="firstName")
    public List<User> getByFirstName(@RequestParam String firstName) {
        return userService.getByFirstName(firstName);
    }

    @GetMapping(path= "/find", params="lastName")
    public List<User> getByLastName(@RequestParam String lastName) {
        return userService.getByLastName(lastName);
    }

    @GetMapping(path = "/all")
    public List<User> getAllUsers() {
        //Returns a JSON or XML with the users
        return userService.getAllUsers();
    }

    @GetMapping(path = "/edit")
    public String editUser(@RequestParam Integer userId, @RequestParam(required=false) String firstName, @RequestParam(required=false) String lastName,
            @RequestParam(required=false) String phone, @RequestParam(required=false) String email) throws Exception {
        if (firstName!="") { userService.editUserFirstName(userId, firstName); }
        if (lastName!="") { userService.editUserLastName(userId, lastName); }
        if (phone!="") { userService.editUserPhone(userId, phone); }
        if (email!="") { userService.editUserEmail(userId, email); }
        return "User edited";
    }
}
