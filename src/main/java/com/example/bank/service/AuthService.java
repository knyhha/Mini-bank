package com.example.bank.service;

import com.example.bank.model.dto.CreateUserRequest;
import com.example.bank.model.dto.LoginRequest;
import com.example.bank.model.dto.SessionData;
import com.example.bank.model.dto.UserResponse;
import com.example.bank.model.entity.User;
import com.example.bank.repository.UserRepository;
import com.example.bank.security.BCryptUtility;
import com.example.bank.security.TokenUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final BCryptUtility bCryptUtility;

    private final SessionService sessionService;
    private final TokenUtils tokenUtils;

    public UserResponse createUser(CreateUserRequest createUserRequest) {
        User user = new User();
        user.setEmail(createUserRequest.email());
        user.setFirstName(createUserRequest.firstName());
        user.setLastName(createUserRequest.lastName());
        user.setPasswordHash(bCryptUtility.bcryptEncryptor(createUserRequest.password()));
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

    public String login(LoginRequest loginRequest) {
        User user = userRepository.findByEmail(loginRequest.email())
                .orElseThrow();

        boolean isMatch = bCryptUtility.doPasswordsMatch(loginRequest.password(), user.getPasswordHash());
        if (!isMatch) {
            throw new RuntimeException();
        }

        String token = tokenUtils.generateToken();
        String tokenHash = tokenUtils.hashToken(token);
        SessionData sessionData = new SessionData(
                user.getId(),
                user.getEmail()
        );
        sessionService.save(tokenHash, sessionData);

        return token;
    }

    public void logout(String authHeader) {
        String token = authHeader.replace("Bearer ", "");
        String tokenHash = tokenUtils.hashToken(token);
        sessionService.delete(tokenHash);
    }
}
