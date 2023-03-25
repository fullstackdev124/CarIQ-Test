package com.cariq.test.jpa.entities;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name="wallets")
@Getter
@Setter
@Builder
@AllArgsConstructor
public class Wallets {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    String walletId;

    BigDecimal balance;

    public Wallets() {
        balance = new BigDecimal(0);
    }
}
