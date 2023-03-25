package com.cariq.test.jpa.repositories;

import com.cariq.test.jpa.entities.Tickets;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TicketRepository extends JpaRepository<Tickets, Long> {
    List<Tickets> findAllByWallet_WalletId(String walletId);
}
