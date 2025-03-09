package com.samsoft.reviewapi.service;

import com.samsoft.reviewapi.entities.User;
import com.samsoft.reviewapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User createUser(User user) {
        Optional<User> existingUser = userRepository.findByDisplayName(user.getDisplayName());
        if (existingUser.isPresent()) {
            throw new RuntimeException("User with the same display name already exists");
        }
        return userRepository.save(user);
    }

    public Optional<User> getUserByDisplayName(String displayName) {
        return userRepository.findByDisplayName(displayName);
    }

    public User updateUser(User user) {
        User existingUser = userRepository.findById(user.getId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setDisplayName(existingUser.getDisplayName());
        return userRepository.save(user);
    }

}
