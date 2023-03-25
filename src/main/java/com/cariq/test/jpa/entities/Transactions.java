package com.cariq.test.jpa.entities;

import com.fasterxml.jackson.annotation.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name="transactions")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Transactions {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty(value = "transaction_id")
    Long transactionId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "wallet_id")
    @JsonIgnore
    Wallets wallet;

    BigDecimal amount;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "event_date", nullable = false, updatable = false)
    @CreatedDate
    @JsonProperty(value = "event_date")
    Date eventDate;
}
