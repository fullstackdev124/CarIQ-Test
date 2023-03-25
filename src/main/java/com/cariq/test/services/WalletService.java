package com.cariq.test.services;

import com.cariq.test.jpa.entities.Wallets;
import com.cariq.test.jpa.repositories.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class WalletService {

    @Autowired
    WalletRepository walletRepository;

    public Wallets createWallet() {
        return walletRepository.save(new Wallets());
    }

    public Optional<Wallets> getWallet(String walletId) {
        return walletRepository.findById(walletId);
    }

    public Wallets depositWallet(Wallets wallet, BigDecimal amount) {
        wallet.setBalance(wallet.getBalance().add(amount));
        return walletRepository.save(wallet);
    }
}
