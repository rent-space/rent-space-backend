package com.rentspace.service;

import com.rentspace.model.payment.PaymentIntentReq;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Account;
import com.stripe.model.AccountLink;
import com.stripe.model.PaymentIntent;
import com.stripe.param.AccountCreateParams;
import com.stripe.param.AccountLinkCreateParams;
import com.stripe.param.PaymentIntentCreateParams;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class PaymentService {

    String STRIPE_API_KEY = System.getenv().get("STRIPE_API_KEY");

    public Map<String, Object> createStripeAccount(String email) throws StripeException {
        Stripe.apiKey = STRIPE_API_KEY;

        AccountCreateParams params = AccountCreateParams.builder()
                .setType(AccountCreateParams.Type.EXPRESS)
                .setCountry("BR")
                .setEmail(email)
                .build();

        Account account = Account.create(params);

        AccountLinkCreateParams linkParams = AccountLinkCreateParams.builder()
                .setAccount(account.getId())
                .setRefreshUrl("http://localhost:3000/")
                .setReturnUrl("http://localhost:3000/payment-account-created")
                .setType(AccountLinkCreateParams.Type.ACCOUNT_ONBOARDING)
                .build();

        AccountLink accountLink = AccountLink.create(linkParams);

        Map<String, Object> response = new HashMap<>();
        response.put("accountId", account.getId());
        response.put("accountLink", accountLink.getUrl());

        return response;
    }

    public Map<String, Object> createPaymentIntent(PaymentIntentReq request) throws StripeException {
        Stripe.apiKey = STRIPE_API_KEY;

        PaymentIntentCreateParams params = PaymentIntentCreateParams.builder()
                .setAmount(request.getAmount())
                .setCurrency("brl")
                .addPaymentMethodType("card")
                .setTransferData(
                        PaymentIntentCreateParams.TransferData.builder()
                                .setDestination(request.getConnectedAccountId())
                                .setAmount((long) (request.getAmount() * 0.95)) // 5% de taxa de aplicação
                                .build()
                )
                .setApplicationFeeAmount((long) (request.getAmount() * 0.05)) // 5% de taxa de aplicação
                .build();

        PaymentIntent paymentIntent = PaymentIntent.create(params);

        Map<String, Object> response = new HashMap<>();
        response.put("clientSecret", paymentIntent.getClientSecret());

        return response;
    }
}
