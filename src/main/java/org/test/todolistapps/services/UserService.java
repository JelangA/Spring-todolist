package org.test.todolistapps.services;

import org.springframework.stereotype.Service;
import org.test.todolistapps.entities.User;
import org.test.todolistapps.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> allUsers() {

        return new ArrayList<>(userRepository.findAll());
    }
}