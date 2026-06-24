package com.example.bank.repository;

import com.example.bank.model.entity.TransactionEntry;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionEntryRepository extends JpaRepository<TransactionEntry, Long> { }
