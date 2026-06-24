package com.example.bank.service;

import com.example.bank.model.dto.AccountResponse;
import com.example.bank.model.dto.CreateAccountRequest;
import com.example.bank.model.entity.Account;
import com.example.bank.model.entity.User;
import com.example.bank.repository.AccountRepository;
import com.example.bank.repository.UserRepository;
import com.example.bank.util.CreditCardNumberGenerator;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final AccountRepository accountRepository;
    private final UserRepository userRepository;
    private final CreditCardNumberGenerator creditCardNumberGenerator;

    public List<AccountResponse> getAccountsByUserId(Long userId) {
        return accountRepository.findByUserId(userId)
                .stream()
                .map(AccountResponse::from)
                .toList();
    }

    @Transactional
    public AccountResponse createAccount(Long userId, CreateAccountRequest createAccountRequest) {
        Account account = new Account();
        // bin and length in properties
        account.setAccountNumber(creditCardNumberGenerator.generate("400344", 16));
        account.setBalance(BigDecimal.ZERO);
        account.setCurrency(createAccountRequest.currency());
        account.setStatus("Active");
        account.setCreatedAt(Instant.now());

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        account.setUser(user);

        Account saved = accountRepository.save(account);
        return AccountResponse.from(saved);
    }
}
