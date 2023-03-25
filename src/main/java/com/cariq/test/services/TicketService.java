package com.cariq.test.services;

import com.cariq.test.jpa.entities.Tickets;
import com.cariq.test.jpa.repositories.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TicketService {

    @Autowired
    TicketRepository ticketRepository;

    public Tickets createTicket(Tickets tickets) {
        return ticketRepository.save(tickets);
    }

    public List<Tickets> getTickets(String walletId) {
        return ticketRepository.findAllByWallet_WalletId(walletId);
    }
}
