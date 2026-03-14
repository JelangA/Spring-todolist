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
import org.test.todolistapps.utils.JwtUtil;

@RequestMapping("/auth")
@RestController
public class AuthenticationController {
    private final JwtUtil jwtUtil;

    private final AuthenticationService authenticationService;

    public AuthenticationController(JwtUtil jwtUtil, AuthenticationService authenticationService) {
        this.jwtUtil = jwtUtil;
        this.authenticationService = authenticationService;
    }

    @PostMapping("/signup")
    public ResponseEntity<User> register(@RequestBody RegisterRequest registerUserDto) {
        User registeredUser = authenticationService.signup(registerUserDto);

        return ResponseEntity.ok(registeredUser);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticate(@RequestBody LoginRequest loginUserDto) {
        User authenticatedUser = authenticationService.authenticate(loginUserDto);

        String jwtToken = jwtUtil.generateToken(String.valueOf(authenticatedUser));

        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setToken(jwtToken);
        loginResponse.setExpiresIn(jwtUtil.getExpirationTime());

        return ResponseEntity.ok(loginResponse);
    }
}
