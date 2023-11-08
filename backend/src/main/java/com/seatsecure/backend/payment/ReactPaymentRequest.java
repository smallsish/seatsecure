package com.seatsecure.backend.payment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReactPaymentRequest {
    private int amount;
    private String currency;
    private String email;
    private String name;
    private Long ticketId;
    private int ticketQuantity;
}