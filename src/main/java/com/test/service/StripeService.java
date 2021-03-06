package com.test.service;

import com.stripe.exception.StripeException;
import com.stripe.model.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import com.stripe.Stripe;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;


@Service
public class StripeService {



        @Value("${stripe.keys.secret}")
        private String API_SECRET_KEY;

        public StripeService() {
        }

        public String createCustomer(String email, String token) {
            String id = null;
            try {
                Stripe.apiKey = API_SECRET_KEY;
                Map<String, Object> customerParams = new HashMap<>();
                // add customer unique id here to track them in your web application
                customerParams.put("description", "Customer for " + email);
                customerParams.put("email", email);
                customerParams.put("source", token); // ^ obtained with Stripe.js
                //create a new customer
                Customer customer = Customer.create(customerParams);
                id = customer.getId();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return id;
        }

        public String createCharge(String email, String token, int amount , String currency) {
            String id = null;
            try {
                Stripe.apiKey = API_SECRET_KEY;
                Map<String, Object> chargeParams = new HashMap<>();
                chargeParams.put("amount", amount);
                chargeParams.put("currency", currency);
                chargeParams.put("description", "Charge for " + email);
                chargeParams.put("source", token); // ^ obtained with Stripe.js
//                createCustomer(email , token);
                //create a charge
                Charge charge = Charge.create(chargeParams);
                id = charge.getId();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return id;
        }

//        public Token createToken() throws StripeException {
//            Map<String, Object> card = new HashMap<>();
//            card.put("number", "4242424242424242");
//            card.put("exp_month", 6);
//            card.put("exp_year", 2022);
//            card.put("cvc", "314");
//            Map<String, Object> params = new HashMap<>();
//            params.put("card", card);
//
//            Token token = Token.create(params);
//            return token;
//        }

        public String createSubscription(String customerId, String plan, String coupon) {
            String id = null;
            try {
                Stripe.apiKey = API_SECRET_KEY;
                Map<String, Object> item = new HashMap<>();
                item.put("plan", plan);

                Map<String, Object> items = new HashMap<>();
                items.put("0", item);

                Map<String, Object> params = new HashMap<>();
                params.put("customer", customerId);
                params.put("items", items);

                //add coupon if available
                if (!coupon.isEmpty()) {
                    params.put("coupon", coupon);
                }

                Subscription sub = Subscription.create(params);
                id = sub.getId();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return id;
        }

        public boolean cancelSubscription(String subscriptionId) {
            boolean status;
            try {
                Stripe.apiKey = API_SECRET_KEY;
                Subscription sub = Subscription.retrieve(subscriptionId);
                sub.cancel(null);
                status = true;
            } catch (Exception ex) {
                ex.printStackTrace();
                status = false;
            }
            return status;
        }

        public Coupon retrieveCoupon(String code) {
            try {
                Stripe.apiKey = API_SECRET_KEY;
                return Coupon.retrieve(code);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return null;
        }


}
