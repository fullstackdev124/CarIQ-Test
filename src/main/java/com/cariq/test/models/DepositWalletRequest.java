package com.cariq.test.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class DepositWalletRequest {
    @JsonProperty(required = true)
    BigDecimal amount;
}
