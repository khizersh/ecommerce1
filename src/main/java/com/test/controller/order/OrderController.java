package com.test.controller.order;

import com.test.bean.User;
import com.test.bean.checkout.Checkout;
import com.test.bean.checkout.CheckoutDetail;
import com.test.bean.checkout.Status;
import com.test.bean.order.Order;
import com.test.bean.order.OrderResponse;
import com.test.bean.order.OrderResponseDetail;
import com.test.repo.CheckoutRepo;
import com.test.repo.OrderRepo;
import com.test.repo.UserRepository;
import com.test.service.OrderService;
import com.test.service.StripeService;
import com.test.utility.GlobalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/order")
public class OrderController {

    @Value("${stripe.keys.public}")
    private String API_PUBLIC_KEY;

    @Autowired
    private StripeService stripeService;

    @Autowired
    private GlobalService service;

    @Autowired
    private OrderRepo orderRepo;

    @Autowired
    private CheckoutRepo checkoutRepo;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private OrderService orderService;


    @PostMapping("/stripe")
    public ResponseEntity createCharge(@RequestBody Order order) {
        //validate data
        if (order.getToken() == null) {
            return  service.getErrorResponse ( "Stripe payment token is missing. Please, try again later.");
        }
        if(order.getAddressLine1().isEmpty()){
            return  service.getErrorResponse ( "Address required");
        }
        if(order.getCheckoutId() == null){
            return  service.getErrorResponse ( "Checkout required!");
        }
        if(order.getCountry() == null){
            return  service.getErrorResponse ( "Country required!");
        }
        if(order.getState() == null){
            return  service.getErrorResponse ( "State required!");
        }
        if(order.getCity() == null){
            return  service.getErrorResponse ( "City required!");
        }
        if(order.getEmail() == null){
            return  service.getErrorResponse ( "email required!");
        }
        if(order.getFullName() == null){
            return  service.getErrorResponse ( "name required!");
        }

        //create charge
        String chargeId = stripeService.createCharge(order.getEmail(),order.getToken(), 100 , "usd"); //$9.99 USD
        if (chargeId == null) {
            return  service.getErrorResponse ( "An error occurred while trying to create a charge.");
        }

        order.setChargeId(chargeId);
        order.setOrderStatus(Status.Paid);
        // You may want to store charge id along with order information


        return  service.getSuccessResponse( orderRepo.save(order));
    }


    @GetMapping("/user/{id}")
    public ResponseEntity getOrderMainByUser(@PathVariable Long id){
        if(id == null){
            return service.getErrorResponse("Invalid request!");
        }

       List<OrderResponse> list =  orderService.getOrderByUserId(id);

       return service.getSuccessResponse(list);

    }

    @GetMapping("/detail/{id}")
    public ResponseEntity getOrderDetailByCheckoutId(@PathVariable Integer id){
        if(id == null){
            return service.getErrorResponse("Invalid request!");
        }

       List<CheckoutDetail> list =  orderService.getOrderDetailByCheckoutId(id);

       return service.getSuccessResponse(list);

    }

}
