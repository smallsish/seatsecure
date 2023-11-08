package com.seatsecure.backend.payment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class PaymentService {

    
    @Autowired
    private RestTemplate restTemplate;

    public ResponseEntity<PaymentResponse> initiatePayment(ReactPaymentRequest reactPaymentRequest) {
        // Extract data from ReactPaymentRequest
        int amount = reactPaymentRequest.getAmount();
        String currency = reactPaymentRequest.getCurrency();
        String email = reactPaymentRequest.getEmail();
        String name = reactPaymentRequest.getName();

        // Create a PaymentRequestBody instance
        PaymentRequestBody requestBody = new PaymentRequestBody();
        requestBody.setAmount(amount);
        requestBody.setCurrency(currency);
        requestBody.setEmail(email);
        requestBody.setName(name);

        // Create a PaymentRequest instance and set the request body and headers
        PaymentRequest paymentRequest = new PaymentRequest();
        paymentRequest.setRequestBody(requestBody);
        paymentRequest.setXBusinessApiKey("7e5e0a3c0e8f17964e778df5e6df4c15efcbeb8ec67d50060788d56181eb0a16");
        paymentRequest.setContentType("application/json");

        // When making the POST request using RestTemplate, create an HttpEntity that includes both the request body and headers
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-BUSINESS-API-KEY", paymentRequest.getXBusinessApiKey());
        headers.setContentType(MediaType.valueOf(paymentRequest.getContentType()));

        HttpEntity<PaymentRequestBody> requestEntity = new HttpEntity<>(requestBody, headers);

        ResponseEntity<PaymentResponse> responseEntity = restTemplate.postForEntity("https://api.sandbox.hit-pay.com/v1/payment-requests", requestEntity, PaymentResponse.class);

        // Extract the URL and payment_request_id from the response
        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            PaymentResponse paymentResponse = responseEntity.getBody();
            System.out.println(responseEntity.getBody());
            // Deduct tickets from the repository and other business logic
            // ...

            // return ResponseEntity.ok(paymentResponse);
            return ResponseEntity.ok(paymentResponse);
        } else {
            // Handle the case where the payment request was not successful
            return ResponseEntity.status(responseEntity.getStatusCode()).body(responseEntity.getBody());
        }
    }
}