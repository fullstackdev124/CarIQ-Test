package com.cariq.test.controllers;

import com.cariq.test.jpa.entities.Tickets;
import com.cariq.test.jpa.entities.Transactions;
import com.cariq.test.jpa.entities.Wallets;
import com.cariq.test.models.*;
import com.cariq.test.services.TicketService;
import com.cariq.test.services.TransactionService;
import com.cariq.test.services.WalletService;
import com.cariq.test.utils.ApiUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@CrossOrigin(value = "*")
@RestController
@Transactional
@RequestMapping(value = "/")
@Log4j2
public class ApiController {

    @Autowired
    WalletService walletService;

    @Autowired
    TransactionService transactionService;

    @Autowired
    TicketService ticketService;


    @PostMapping(value = "wallets")
    public ResponseEntity<String> createWallet() {
        return ResponseEntity.ok(walletService.createWallet().getWalletId());
    }

    @PostMapping(value = "wallets/{walletId}/deposit")
    public ResponseEntity<String> depositWallet(@PathVariable String walletId, @RequestBody DepositWalletRequest request) {
        Optional<Wallets> wallet = walletService.getWallet(walletId);
        if (!wallet.isPresent())
            return ResponseEntity.internalServerError().build();

        // check amount
        if (request.getAmount().compareTo(BigDecimal.ZERO)<=0)
            return ResponseEntity.badRequest().build();

        // update balance
        walletService.depositWallet(wallet.get(), request.getAmount());

        // create transaction
        transactionService.createTransaction(Transactions.builder()
                .wallet(Wallets.builder().walletId(walletId).build())
                .amount(request.getAmount())
                .build()
        );

        return ResponseEntity.ok("success");
    }

    @GetMapping(value = "fare")
    public ResponseEntity<RetrieveFareResponse> retrieveFare(@RequestBody RetrieveFareRequest request) {
        RestTemplate template = new RestTemplate();
        ResponseEntity<String> response = template.getForEntity("https://api.bart.gov/api/sched.aspx?cmd=fare&orig="+request.getOrigin()+"&dest="+request.getDestination()+"&date=today&key=MW9S-E7SL-26DU-VV8V&json=y", String.class);

        // api is failed
        if (response.getStatusCode() != HttpStatus.OK)
            return ResponseEntity.status(response.getStatusCode()).build();

        BigDecimal fare = ApiUtils.getFare(response.getBody());

        // check fare
        if (fare == null)
            return ResponseEntity.internalServerError().build();

        if (fare.compareTo(BigDecimal.ZERO)<=0)
            return ResponseEntity.badRequest().build();

        return ResponseEntity.ok(RetrieveFareResponse.builder()
                .origin(request.getOrigin())
                .destination(request.getDestination())
                .fare(fare)
                .build()
        );
    }

    @PostMapping(value = "wallets/{walletId}/ticket")
    public ResponseEntity<BuyTicketResponse> buyTicket(@PathVariable String walletId, @RequestBody BuyTicketRequest request) {
        Optional<Wallets> wallet = walletService.getWallet(walletId);
        if (!wallet.isPresent())
            return ResponseEntity.internalServerError().build();

        RestTemplate template = new RestTemplate();
        ResponseEntity<String> response = template.getForEntity("https://api.bart.gov/api/sched.aspx?cmd=fare&orig="+request.getOrigin()+"&dest="+request.getDestination()+"&date=today&key=MW9S-E7SL-26DU-VV8V&json=y", String.class);

        // api is failed
        if (response.getStatusCode() != HttpStatus.OK)
            return ResponseEntity.status(response.getStatusCode()).build();

        // check fare is null or below 0
        BigDecimal fare = ApiUtils.getFare(response.getBody());
        if (fare == null)
            return ResponseEntity.internalServerError().build();

        if (fare.compareTo(BigDecimal.ZERO)<=0)
            return ResponseEntity.badRequest().build();

        // check balance
        if (wallet.get().getBalance().compareTo(fare)<0)
            return ResponseEntity.badRequest().build();

        // update wallet
        walletService.depositWallet(wallet.get(), fare.multiply(new BigDecimal(-1)));

        // add transaction
        Transactions transactions = transactionService.createTransaction(Transactions.builder()
                .wallet(wallet.get())
                .amount(fare.multiply(new BigDecimal(-1)))
                .build()
        );

        // add ticket
        ticketService.createTicket(Tickets.builder()
                .origin(request.getOrigin())
                .destination(request.getDestination())
                .fare(fare)
                .wallet(wallet.get())
                .transaction(transactions)
                .build()
        );

        return ResponseEntity.ok(BuyTicketResponse.builder()
                .origin(request.getOrigin())
                .destination(request.getDestination())
                .build()
        );
    }

    @GetMapping(value = "wallets/{walletId}")
    public ResponseEntity<RetrieveWalletResponse> retrieveWallet(@PathVariable String walletId) {
        Optional<Wallets> wallet = walletService.getWallet(walletId);
        if (!wallet.isPresent())
            return ResponseEntity.internalServerError().build();

        RetrieveWalletResponse result = new RetrieveWalletResponse(wallet.get().getBalance());

        List<Tickets> tickets = ticketService.getTickets(walletId);
        if (tickets != null && !tickets.isEmpty()) {
            for (Tickets item : tickets)
                result.addTicket(RetrieveWalletResponse.Ticket.builder()
                        .origin(item.getOrigin())
                        .destination(item.getDestination())
                        .build()
                );
        }

        return ResponseEntity.ok(result);
    }

}
