package com.cariq.test.services;

import com.cariq.test.jpa.entities.Transactions;
import com.cariq.test.jpa.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionService {

    @Autowired
    TransactionRepository transactionRepository;

    public Transactions createTransaction(Transactions transactions) {
        return transactionRepository.save(transactions);
    }
}
