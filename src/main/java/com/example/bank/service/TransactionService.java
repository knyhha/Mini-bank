package com.example.bank.service;

import com.example.bank.enums.TransactionStatus;
import com.example.bank.enums.TransactionType;
import com.example.bank.model.dto.CreateTransferRequest;
import com.example.bank.model.dto.TransferResponse;
import com.example.bank.model.entity.Account;
import com.example.bank.model.entity.Transaction;
import com.example.bank.model.entity.TransactionEntry;
import com.example.bank.repository.AccountRepository;
import com.example.bank.repository.TransactionEntryRepository;
import com.example.bank.repository.TransactionRepository;
import com.example.bank.service.validation.TransferValidator;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;

@Service
@RequiredArgsConstructor
public class TransactionService {
    private final AccountRepository accountRepository;
    private final TransferValidator transferValidator;
    private final TransactionRepository transactionRepository;
    private final TransactionEntryRepository transactionEntryRepository;

    private Transaction createTransaction(TransactionType type) {
        Transaction tx = new Transaction();
        tx.setType(type);
        tx.setStatus(TransactionStatus.PENDING);
        tx.setCreatedAt(Instant.now());

        return transactionRepository.save(tx);
    }

    private void createTransactionEntry(
            Transaction tx,
            Account account,
            BigDecimal amount
    ) {
        TransactionEntry entry = new TransactionEntry();
        entry.setTransaction(tx);
        entry.setAccount(account);
        entry.setAmount(amount);

        transactionEntryRepository.save(entry);
    }

    private void applyTransfer(
            Account from,
            Account to,
            BigDecimal amount,
            Transaction tx
    ) {
            createTransactionEntry(tx, from, amount.negate());
            from.setBalance(from.getBalance().subtract(amount));

            createTransactionEntry(tx, to, amount);
            to.setBalance(to.getBalance().add(amount));
    }

    /**
     * @param fromId
     * @param transferRequest
     */
    @Transactional
    public TransferResponse transfer(
            Long fromId,
            CreateTransferRequest transferRequest
    ) {
        Account from = accountRepository.findByAccountNumberAndUserId(transferRequest.fromCardNumber(), fromId).orElseThrow();
        Account to   = accountRepository.findByAccountNumber(transferRequest.toCardNumber()).orElseThrow();
        BigDecimal amount = transferRequest.amount();

        transferValidator.validate(from, to, amount);

        Transaction transaction = createTransaction(TransactionType.TRANSFER);
        try {
            applyTransfer(from, to, amount, transaction);
            transaction.setStatus(TransactionStatus.SUCCESS);
        } catch (Exception e) {
            transaction.setStatus(TransactionStatus.FAILED);
            throw new RuntimeException(e);
        }

        return new TransferResponse(
            transaction.getStatus()
        );
    }
}
