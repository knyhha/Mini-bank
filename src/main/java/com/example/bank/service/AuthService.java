package com.example.bank.service;

import com.example.bank.model.dto.CreateUserRequest;
import com.example.bank.model.dto.UserResponse;
import com.example.bank.model.entity.User;
import com.example.bank.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserResponse createUser(CreateUserRequest createUserRequest) {
        User user = new User();
        user.setEmail(createUserRequest.email());
        user.setFirstName(createUserRequest.firstName());
        user.setLastName(createUserRequest.lastName());
        user.setPasswordHash(passwordEncoder.encode(createUserRequest.password()));
        user.setCreatedAt(Instant.now());

        User saved = userRepository.save(user);
        return new UserResponse(
                saved.getId(),
                saved.getEmail(),
                saved.getFirstName(),
                saved.getLastName(),
                saved.getCreatedAt()
        );
    }
}
