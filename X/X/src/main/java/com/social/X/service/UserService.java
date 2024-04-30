package com.social.X.service;

import com.social.X.repo.UserRepository;
import com.social.X.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    public void saveUser(User user) {
        Optional<User> optionalUser = userRepository.findByEmail(user.getEmail());
        if (optionalUser.isEmpty()) {
            // insert new user
            User savedUser = userRepository.save(user);
            System.out.println("User saved successfully: " + savedUser);
        }
    }

    public User getUserByID(int id) {
        return userRepository.findUserById(id);
    }

    public User getUserByEmail(String email) {
        Optional<User> optionalUser = userRepository.findByEmail(email);
        return optionalUser.orElse(null);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUser(String email) {
        return null;
    }
}