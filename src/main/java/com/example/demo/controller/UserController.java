package com.example.demo.controller;

import com.example.demo.entity.User;
import com.example.demo.entity.UserDto;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * The {@code UserController} class handles HTTP requests related to user management.
 *
 * It provides endpoints to create a new user and to retrieve a list of all users.
 * The controller uses the {@code UserService} to perform business logic related to users.
 */
@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * Creates a new user.
     *
     * This endpoint accepts a {@code UserDto} object in the request body, which contains
     * the details of the user to be created. It returns the created {@code User} object.
     *
     * @param userDto The data transfer object containing user details.
     * @return A {@code ResponseEntity} containing the created {@code User} object.
     */
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody UserDto userDto) {
        User createdUser = userService.createUser(userDto);
        return ResponseEntity.ok(createdUser);
    }

    /**
     * Login existing user
     *
     * This endpoint accepts the email .
     * @return A {@code ResponseEntity} containing the created {@code User} object.
     */
    @GetMapping("/login")
    public ResponseEntity<User> loginUser(@RequestParam("email") String email) {
        User user = userService.findByEmail(email);
        return ResponseEntity.ok(user);
    }

    /**
     * Retrieves a list of all users.
     *
     * This endpoint returns a {@code List} of {@code User} objects representing all users
     * in the system.
     *
     * @return A {@code ResponseEntity} containing a list of {@code User} objects.
     */
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUser();
        return ResponseEntity.ok(users);
    }
}
