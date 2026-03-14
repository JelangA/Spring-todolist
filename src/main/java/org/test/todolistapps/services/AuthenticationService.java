package org.test.todolistapps.services;

import org.test.todolistapps.dto.LoginRequest;
import org.test.todolistapps.dto.RegisterRequest;
import org.test.todolistapps.entities.User;

public interface AuthenticationService {
    User signup(RegisterRequest input);

    User authenticate(LoginRequest input);
}
