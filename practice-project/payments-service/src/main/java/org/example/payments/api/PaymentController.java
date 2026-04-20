package org.example.payments.api;

import org.apache.coyote.Response;
import org.example.payments.model.Payment;
import org.example.payments.repository.PaymentRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/payments")
public class PaymentController {

    private final PaymentRepository paymentRepository;

    public PaymentController(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    @PostMapping
    public ResponseEntity<Void> createPayment(@RequestBody PaymentRequest request) {
        Payment payment = new Payment(
                request.getClientId(),
                request.getAmount(),
                "SUCCESS"
        );

        paymentRepository.save(payment);

        return ResponseEntity.ok().build();
    }
}
