package org.test.todolistapps.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.test.todolistapps.dto.LoginRequest;
import org.test.todolistapps.dto.LoginResponse;
import org.test.todolistapps.dto.RegisterRequest;
import org.test.todolistapps.entities.User;
import org.test.todolistapps.repository.UserRepository;
import org.test.todolistapps.utils.JwtUtil;

@Slf4j
@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    private final JwtUtil jwtUtil;

    public AuthenticationServiceImpl(
            UserRepository userRepository,
            AuthenticationManager authenticationManager,
            PasswordEncoder passwordEncoder,
            JwtUtil jwtUtil
    ) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public User signup(RegisterRequest input) {
        User user = new User();
        user.setFullName(input.getFullName());
        user.setUsername(input.getUsername());
        user.setPassword(passwordEncoder.encode(input.getPassword()));
        log.info("Saving user {} to the database", user.getUsername());
        return userRepository.save(user);
    }

    @Override
    public LoginResponse authenticate(LoginRequest input) {
        log.info(
                "Authenticating user {} with password {}",
                input.getUsername(),
                input.getPassword()
        );
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        input.getUsername(),
                        input.getPassword()
                )
        );

        log.info(
                "User {} authenticated successfully",
                input.getUsername()
        );

        User user = userRepository.findByUsername(input.getUsername())
                .orElseThrow();

        String jwtToken = jwtUtil.generateToken(user.getUsername());

        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setToken(jwtToken);
        loginResponse.setExpiresIn(jwtUtil.getExpirationTime());

        return loginResponse;
    }
}
