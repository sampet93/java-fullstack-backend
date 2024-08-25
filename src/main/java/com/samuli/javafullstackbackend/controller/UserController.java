package com.samuli.javafullstackbackend.controller;

import com.samuli.javafullstackbackend.exception.UserAlreadyExistsException;
import com.samuli.javafullstackbackend.exception.UserNotFoundException;
import com.samuli.javafullstackbackend.model.User;
import com.samuli.javafullstackbackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("http://localhost:5173")
public class UserController {
    @Autowired
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/user")
    User newUser(@RequestBody User newUser) {
        try {
            return userService.registerUser(newUser);
        } catch (UserAlreadyExistsException e) {
            throw new UserAlreadyExistsException();
        }
    }

    @GetMapping("/users")
    List<User> findAllUsers() {
        return userService.findAll();
    }

    @GetMapping("/user/{id}")
    User findUserById(@PathVariable Long id) {
        return userService.findUserById(id);
    }

    @PutMapping("/user/{id}")
    User updateUser(@RequestBody User updatedUser, @PathVariable Long id) {
        try {
            return userService.updateUser(updatedUser, id);
        } catch (UserNotFoundException e) {
            throw new UserNotFoundException(id);
        }
    }

    @DeleteMapping("/user/{id}")
    String deleteUser(@PathVariable Long id) {
        try {
            return userService.deleteUser(id);
        } catch (UserNotFoundException e) {
            throw new UserNotFoundException(id);
        }
    }
}
