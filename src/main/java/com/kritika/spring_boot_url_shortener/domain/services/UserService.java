package com.kritika.spring_boot_url_shortener.domain.services;

import com.kritika.spring_boot_url_shortener.domain.entities.User;
import com.kritika.spring_boot_url_shortener.domain.models.CreateUserCmd;
import com.kritika.spring_boot_url_shortener.domain.models.Role;
import com.kritika.spring_boot_url_shortener.domain.repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder) {

        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public void createUser(CreateUserCmd cmd) {

        if (userRepository.existsByEmail(cmd.email())) {
            throw new RuntimeException(
                    "An account with this email already exists."
            );
        }

        User user = new User();

        user.setEmail(cmd.email());
        user.setPassword(
                passwordEncoder.encode(cmd.password())
        );
        user.setName(cmd.name());

        // Default role for new users
        user.setRole(
                cmd.role() != null
                        ? cmd.role()
                        : Role.ROLE_USER
        );

        user.setCreatedAt(
                Instant.now()
        );

        userRepository.save(user);
    }
}