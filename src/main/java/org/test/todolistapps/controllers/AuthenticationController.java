package org.test.todolistapps.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.test.todolistapps.dto.LoginRequest;
import org.test.todolistapps.dto.LoginResponse;
import org.test.todolistapps.dto.RegisterRequest;
import org.test.todolistapps.entities.User;
import org.test.todolistapps.services.AuthenticationService;
import org.test.todolistapps.utils.ApiResponse;

@RequestMapping("/auth")
@RestController
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<User>> register(@RequestBody RegisterRequest registerUserDto) {
        User registeredUser = authenticationService.signup(registerUserDto);

        return ResponseEntity.ok(ApiResponse.success("User registered successfully", registeredUser, 201));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>> authenticate(@RequestBody LoginRequest loginUserDto) {
        LoginResponse loginResponse = authenticationService.authenticate(loginUserDto);

        return ResponseEntity.ok(ApiResponse.success("Login successful", loginResponse));
    }
}
