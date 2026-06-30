package com.example.bank.security;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class BCryptUtility {
    private static final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public String bcryptEncryptor(String plainText) {
        return passwordEncoder.encode(plainText);
    }

    public Boolean doPasswordsMatch(String rawPassword,String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }
}
