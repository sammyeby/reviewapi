package com.samsoft.reviewapi.controller;

import com.samsoft.reviewapi.entities.User;
import com.samsoft.reviewapi.service.UserService;
import com.samsoft.reviewapi.util.ErrorResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<?> addUser(@RequestBody User user) {
        try {
            userService.createUser(user);
            return ResponseEntity.ok().build();
        } catch (ResponseStatusException e) {
            return ErrorResponseUtil.createErrorResponse(e.getReason(), e.getStatusCode());
        } catch (Exception e) {
            log.error("Something went wrong adding user: ", e);
            return ErrorResponseUtil.createErrorResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{displayName}")
    public ResponseEntity<?> getUser(@PathVariable String displayName) {
        try {
            return ResponseEntity.ok(userService.getUserByDisplayName(displayName));
        } catch (ResponseStatusException e) {
            return ErrorResponseUtil.createErrorResponse(e.getReason(), e.getStatusCode());
        } catch (Exception e) {
            log.error("Error getting User", e);
            return ResponseEntity.internalServerError().build();
        }
    }

    @PutMapping("/{displayName}")
    public ResponseEntity<?> updateUser(@PathVariable String displayName, @RequestBody User user) {
        try {
            userService.updateUser(displayName, user);
            return ResponseEntity.noContent().build();
        } catch (ResponseStatusException e) {
            return ErrorResponseUtil.createErrorResponse(e.getReason(), e.getStatusCode());
        } catch (Exception e) {
            log.error("Error updating user", e);
            return ResponseEntity.internalServerError().build();
        }
    }
}
