package org.test.todolistapps.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.test.todolistapps.entities.User;


import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Boolean existsByUsername(String username);
}
