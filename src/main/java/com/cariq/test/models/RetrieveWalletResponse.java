package com.cariq.test.models;

import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class RetrieveWalletResponse {
    BigDecimal balance;
    List<Ticket> tickets;

    public RetrieveWalletResponse(BigDecimal balance) {
        this.balance = balance;
        tickets = new ArrayList<>();
    }

    public void addTicket(RetrieveWalletResponse.Ticket ticket) {
        tickets.add(ticket);
    }

    @Builder
    @Getter
    @Setter
    public static class Ticket {
        String origin;
        String destination;
    }
}
