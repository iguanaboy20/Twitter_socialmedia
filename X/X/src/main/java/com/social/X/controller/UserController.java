package com.social.X.controller;

import com.social.X.model.User;
import com.social.X.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/user-details")
    public ResponseEntity<?> getUserById(@RequestParam(value = "userID", required = false) Integer userID) {
        if (userID != null) {
            // If userID is provided, get user by ID
            User user = userService.getUserByID(userID);
            if (user != null) {
                return ResponseEntity.ok(user);
            } else {
                return ResponseEntity.notFound().build();
            }
        } else {
            // If userID is not provided, get all users
            List<User> users = userService.getAllUsers();
            return ResponseEntity.ok(users);
        }
    }

    @PostMapping("/saveUser")
    public ResponseEntity<?> saveUserInfo(@RequestBody UserRequest userRequest) {
        User user = new User();
        user.setName(userRequest.getName());
        user.setEmail(userRequest.getEmail());
        user.setPassword(userRequest.getPassword());

        userService.saveUser(user);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequest loginRequest) {
        // Retrieve user by email
        User user = userService.getUserByEmail(loginRequest.getEmail());

        // Check if the user exists
        if (user != null) {
            // Check if the password matches
            if (user.getPassword().equals(loginRequest.getPassword())) {
                // If password matches, return login successful message
                return ResponseEntity.ok("Login Successful");
            } else {
                // If password doesn't match, return username/password incorrect message
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorResponse("Username/Password Incorrect"));
            }
        } else {
            // If user doesn't exist, return user does not exist message
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("User does not exist"));
        }
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signupUser(@RequestBody SignupRequest signupRequest) {
        // Check if the user already exists
        if (userService.getUserByEmail(signupRequest.getEmail()) != null) {
            // If user already exists, return forbidden error message
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ErrorResponse("Forbidden, Account already exists"));
        }

        // Create a new user object
        User newUser = new User();
        newUser.setEmail(signupRequest.getEmail());
        newUser.setName(signupRequest.getName());
        newUser.setPassword(signupRequest.getPassword());

        // Save the new user
        userService.saveUser(newUser);

        // Return successful creation message
        return ResponseEntity.ok("Account Creation Successful");
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    // Inner class to represent the login request body
    static class LoginRequest {
        private String email;
        private String password;

        // Getters and setters
        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }

    // Inner class to represent the user request body
    static class UserRequest {
        private String name;
        private String email;
        private String password;

        // Getters and setters
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }

    // Inner class to represent the signup request body
    static class SignupRequest {
        private String email;
        private String name;
        private String password;

        // Getters and setters
        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }

    // Inner class to represent the error response
    // Inner class to represent the error response
    static class ErrorResponse {
        private String Error; // Change 'error' to 'Error'

        public ErrorResponse(String error) {
            this.Error = error; // Change 'error' to 'Error'
        }

        // Getters and setters
        public String getError() {
            return Error; // Change 'error' to 'Error'
        }

        public void setError(String error) {
            this.Error = error; // Change 'error' to 'Error'
        }
    }
}
