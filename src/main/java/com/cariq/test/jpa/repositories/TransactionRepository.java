package com.cariq.test.jpa.repositories;

import com.cariq.test.jpa.entities.Transactions;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transactions, Long> {
}
