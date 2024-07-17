package com.customer.arc.repositories;
import org.springframework.data.jpa.repository.JpaRepository;

import com.customer.arc.entities.User;

import java.util.Optional;

public interface UserRepositoryJPA extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
}

