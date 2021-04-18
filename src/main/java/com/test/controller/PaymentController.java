package com.test.controller;

import com.test.service.StripeService;
import com.test.utility.GlobalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import com.stripe.model.Coupon;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@RestController
public class PaymentController {
    @Value("${stripe.keys.public}")
    private String API_PUBLIC_KEY;

    @Autowired
    private StripeService stripeService;

    @Autowired
    private GlobalService service;


    @PostMapping("/create-charge")
    public @ResponseBody
    ResponseEntity createCharge(String email, String token) {
        //validate data
        if (token == null) {
            return  service.getErrorResponse ( "Stripe payment token is missing. Please, try again later.");
        }

        //create charge
        String chargeId = stripeService.createCharge(email, token, 1); //$9.99 USD
        if (chargeId == null) {
            return  service.getErrorResponse ( "An error occurred while trying to create a charge.");
        }

        // You may want to store charge id along with order information

        return  service.getSuccessResponse( "Success! Your charge id is " + chargeId);
    }


}
