package com.seatsecure.backend.payment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("api/v1")
@RestController
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping("/initiate-payment")
    public ResponseEntity<PaymentResponse> initiatePayment(@RequestBody ReactPaymentRequest reactPaymentRequest) {
        ResponseEntity<PaymentResponse> response = paymentService.initiatePayment(reactPaymentRequest);

        if (response.getStatusCode() == HttpStatus.OK) {
            // If payment initiation is successful, return the PaymentResponse to the client
            return ResponseEntity.ok(response.getBody());
        } else {
            // Handle the case where payment initiation was not successful
            return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
        }
    }
}
