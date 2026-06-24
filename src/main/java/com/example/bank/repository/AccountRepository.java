package com.example.bank.repository;

import com.example.bank.model.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {
    List<Account> findByUserId(Long userId);
    Optional<Account> findByAccountNumberAndUserId(String accountNumber, Long userId);
    Optional<Account> findByAccountNumber(String accountNumber);
}
