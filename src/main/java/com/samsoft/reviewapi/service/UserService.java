package com.samsoft.reviewapi.service;

import com.samsoft.reviewapi.entities.User;
import com.samsoft.reviewapi.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void createUser(User user) {
        validateUser(user);
        userRepository.save(user);
    }

    public User getUserByDisplayName(String displayName) {
        validateDisplayName(displayName);

        Optional<User> optionalExistingUser = userRepository.findUserByDisplayName(displayName);
        if (optionalExistingUser.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }

        User existingUser = optionalExistingUser.get();
        existingUser.setId(null); // Remove the ID from the response

        return existingUser;
    }

    public void updateUser(String displayName, User user) {
        validateDisplayName(displayName);

        User existingUser = getUserByDisplayName(displayName);
        applyUpdateUserInfoToExistingUser(user, existingUser);
        userRepository.save(existingUser);
    }

    private void validateUser(User user) {
        validateDisplayName(user.getDisplayName());

        Optional<User> existingUser = userRepository.findUserByDisplayName(user.getDisplayName());
        if (existingUser.isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "User with the same display name already exists");
        }
    }

    private void validateDisplayName(String displayName) {
        if (ObjectUtils.isEmpty(displayName)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Display name is required");
        }
    }

    private void applyUpdateUserInfoToExistingUser(User updatedUser, User existingUser) {
        if (ObjectUtils.isEmpty(updatedUser.getDisplayName())) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Display name is required");
        }

        if (!ObjectUtils.isEmpty(updatedUser.getCity())) {
            existingUser.setCity(updatedUser.getCity());
        }

        if (!ObjectUtils.isEmpty(updatedUser.getState())) {
            existingUser.setState(updatedUser.getState());
        }

        if (!ObjectUtils.isEmpty(updatedUser.getZipcode())) {
            existingUser.setZipcode(updatedUser.getZipcode());
        }

        if (!ObjectUtils.isEmpty(updatedUser.getPeanutCheck())) {
            existingUser.setPeanutCheck(updatedUser.getPeanutCheck());
        }

        if (!ObjectUtils.isEmpty(updatedUser.getDairyCheck())) {
            existingUser.setDairyCheck(updatedUser.getDairyCheck());
        }

        if (!ObjectUtils.isEmpty(updatedUser.getEggCheck())) {
            existingUser.setEggCheck(updatedUser.getEggCheck());
        }
    }

}
