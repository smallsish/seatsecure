package com.seatsecure.backend.payment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class PaymentResponse {
    private String paymentRequestURL;
    private String paymentRequestId;
}
