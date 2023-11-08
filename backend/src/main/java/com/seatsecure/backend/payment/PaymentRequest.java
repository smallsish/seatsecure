package com.seatsecure.backend.payment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaymentRequest {
    private PaymentRequestBody requestBody;
    private String xBusinessApiKey;
    private String contentType;
}
