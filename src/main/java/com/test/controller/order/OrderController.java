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
import java.util.Date;
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


    @GetMapping
    public ResponseEntity getAllOrder(){
        return service.getSuccessResponse(orderService.getAllOrder());
    }


    @GetMapping("/{id}")
    public ResponseEntity getOrderById(@PathVariable Integer id){
        if(id == null){
            return service.getErrorResponse("Invalid request!");
        }
        Order order = orderRepo.getOne(id);

        return service.getSuccessResponse(orderService.convertDto(order));

    }

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
        Order orderDb = orderRepo.findByCheckoutId(order.getCheckoutId());
        if(orderDb != null){
            return  service.getErrorResponse ( "Order already exist!");
        }


        //create charge
        String chargeId = stripeService.createCharge(order.getEmail(),order.getToken(), 100 , "usd"); //$9.99 USD
        if (chargeId == null) {
            return  service.getErrorResponse ( "An error occurred while trying to create a charge.");
        }

        order.setChargeId(chargeId);
        order.setOrderStatus(Status.Paid);
        order.setShipStatus(Status.Pending);
        order.setOrderDate(new Date());
        // You may want to store charge id along with order information


        return  service.getSuccessResponse( orderRepo.save(order));
    }


    @PostMapping("/paypal")
    public ResponseEntity createChargePaypal(@RequestBody Order order) {
        //validate data
        if(order.getOrderStatus() == null || !order.getOrderStatus().equals(Status.COMPLETED)){
            return  service.getErrorResponse ( "Order not completed! please try again");
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
        Order orderDb = orderRepo.findByCheckoutId(order.getCheckoutId());
        if(orderDb != null){
            return  service.getErrorResponse ( "Order already exist!");
        }


        //create charge
        order.setOrderStatus(Status.Paid);
        order.setShipStatus(Status.Pending);
        order.setOrderDate(new Date());
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


    @GetMapping("/checkout/{id}")
    public ResponseEntity getOrderByCheckoutId(@PathVariable Integer id){
        if(id == null){
            return service.getErrorResponse("Invalid request!");
        }
       Order order  =  orderRepo.findByCheckoutId(id);
        if(order == null){
            return service.getErrorResponse("No data found");
        }
       return service.getSuccessResponse(order);
    }

}
