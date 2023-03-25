package com.cariq.test.models;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RetrieveFareResponse {
    String origin;
    String destination;
    BigDecimal fare;
}
