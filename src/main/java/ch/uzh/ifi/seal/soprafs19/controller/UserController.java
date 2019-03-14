package ch.uzh.ifi.seal.soprafs19.controller;

import ch.uzh.ifi.seal.soprafs19.entity.User;
import ch.uzh.ifi.seal.soprafs19.repository.UserRepository;
import ch.uzh.ifi.seal.soprafs19.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
public class UserController {

    private final UserService service;

    UserController(UserService service) {
        this.service = service;
    }

    // get method to fetch all users //
    @GetMapping("/users")
    Iterable<User> all() {
        return service.getUsers();
    }

    //post method to create a new user //
    @PostMapping("/users")
    User createUser(@RequestBody User newUser) {
        return this.service.createUser(newUser);
    }
    // post method to check for user and changes his status //
    @PostMapping("/login")
    User checkUser(@RequestBody User newUser) {
        return this.service.checkUser(newUser);
    }
    // get method to check a user by id //
    @GetMapping("/users/{id}")
    User getUser(@PathVariable String id) {
        return this.service.getUser(Long.parseLong(id));
    }
    // put method to logout a user & store his status as offline //
    @CrossOrigin
    @PutMapping("/users")
    User logoutUser(@RequestBody User newUser) {
        return this.service.logoutUser(newUser);
    }
    // edit & store users account information //
    @CrossOrigin
    @PutMapping("/users/{id}")
    User editUser(@RequestBody User newUser) {
        return this.service.editUser(newUser);
    }

}