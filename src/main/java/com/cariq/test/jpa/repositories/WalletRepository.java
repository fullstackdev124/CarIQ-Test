package com.cariq.test.jpa.repositories;

import com.cariq.test.jpa.entities.Wallets;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WalletRepository extends JpaRepository<Wallets, String> {
}
