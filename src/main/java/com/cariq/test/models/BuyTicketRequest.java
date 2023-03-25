package com.cariq.test.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BuyTicketRequest {
    @JsonProperty(required = true)
    String origin;

    @JsonProperty(required = true)
    String destination;
}
