package com.example.bank.service;

import com.example.bank.enums.AccountStatus;
import com.example.bank.model.dto.AccountResponse;
import com.example.bank.model.dto.CreateAccountRequest;
import com.example.bank.model.entity.Account;
import com.example.bank.model.entity.User;
import com.example.bank.repository.AccountRepository;
import com.example.bank.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.iban4j.CountryCode;
import org.iban4j.Iban;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final AccountRepository accountRepository;
    private final UserRepository userRepository;

    public List<AccountResponse> getAccountsByUserId(Long userId) {
        return accountRepository.findByUserId(userId)
                .stream()
                .map(AccountResponse::from)
                .toList();
    }

    private String generateAccountNumber() {
        return String.format("%010d", accountRepository.getNextAccountNumber());
    }

    private String generateIban() {
        return new Iban.Builder()
                .countryCode(CountryCode.NL)
                .bankCode("ABNA")
                .accountNumber(generateAccountNumber())
                .build().toString();
    }

    @Transactional
    public AccountResponse createAccount(Long userId, CreateAccountRequest createAccountRequest) {
        Account account = new Account();
        account.setIban(generateIban());
        account.setBalance(BigDecimal.ZERO);
        account.setCurrency(createAccountRequest.currency());
        account.setStatus(AccountStatus.ACTIVE);
        account.setCreatedAt(Instant.now());

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        account.setUser(user);

        Account saved = accountRepository.save(account);
        return AccountResponse.from(saved);
    }
}
