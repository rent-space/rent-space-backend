package com.rentspace.model.payment;

import jakarta.persistence.Entity;
import lombok.Data;

@Data
public class PaymentIntentReq {
    private Long amount;
    private String customerId;
    private String connectedAccountId;
}
