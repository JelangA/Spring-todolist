package org.test.todolistapps.services;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.test.todolistapps.dto.LoginRequest;
import org.test.todolistapps.dto.RegisterRequest;
import org.test.todolistapps.entities.User;
import org.test.todolistapps.repository.UserRepository;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthenticationServiceImpl(
            UserRepository userRepository,
            BCryptPasswordEncoder passwordEncoder,
            AuthenticationManager authenticationManager
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public User signup(RegisterRequest input) {
        User user = User.builder()
                .fullName(input.getFullName())
                .username(input.getUsername())
                .password(passwordEncoder.encode(input.getPassword()))
                .build();
        return userRepository.save(user);
    }

    @Override
    public User authenticate(LoginRequest input) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(input.getUsername(), input.getPassword())
        );
        return userRepository.findByUsername(input.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found: " + input.getUsername()));
    }
}
