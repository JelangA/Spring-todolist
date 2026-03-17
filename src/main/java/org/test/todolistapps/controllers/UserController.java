package org.test.todolistapps.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.test.todolistapps.entities.User;
import org.test.todolistapps.services.UserService;
import org.test.todolistapps.utils.ApiResponse;

import java.util.List;

@RequestMapping("/users")
@RestController
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<User>> authenticatedUser() {
        User currentUser = userService.getAuthenticatedUser();
        return ResponseEntity.ok(ApiResponse.success("Authenticated user retrieved", currentUser));
    }

    @GetMapping("/")
    public ResponseEntity<ApiResponse<List<User>>> allUsers() {
        List<User> users = userService.allUsers();

        return ResponseEntity.ok(ApiResponse.success("All users retrieved", users));
    }
}
