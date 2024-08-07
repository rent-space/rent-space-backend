package com.rentspace.controller;

import com.rentspace.model.payment.PaymentIntentReq;
import com.rentspace.service.PaymentService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping(path = "/charge")
@AllArgsConstructor
public class PaymentController {

    private PaymentService paymentService;

    @PostMapping("/create-express-account")
    public Map<String, Object> createExpressAccount(@RequestBody Map<String, Object> request) throws Exception {
        String email = (String) request.get("email");

        return paymentService.createStripeAccount(email);
    }

    @PostMapping("/create-payment")
    public Map<String, Object> createPaymentIntent(@RequestBody PaymentIntentReq request) throws Exception {
        return paymentService.createPaymentIntent(request);
    }
}
