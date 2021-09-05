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
import com.test.utility.PaymentMethod;
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
        try{

        return service.getSuccessResponse(orderService.getAllOrder());
        }catch (Exception e){
            e.printStackTrace();
        }
        return service.getErrorResponse("Something went wrong!");
    }


    @GetMapping("/{id}")
    public ResponseEntity getOrderById(@PathVariable Integer id){
        if(id == null){
            return service.getErrorResponse("Invalid request!");
        }
        try{

        Order order = orderRepo.getOne(id);

        return service.getSuccessResponse(orderService.convertDto(order));
        }catch (Exception e){
            e.printStackTrace();
        }
        return service.getErrorResponse("Something went wrong!");

    }

    @PostMapping("/stripe")
    public ResponseEntity createChargeStripe(@RequestBody Order order) {
        //validate data
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


        try{

        Order orderDb = orderRepo.findByCheckoutId(order.getCheckoutId());
        if(orderDb != null){
            return  service.getErrorResponse ( "Order already exist!");
        }


//        create charge
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
        }catch (Exception e){
            e.printStackTrace();
        }
        return service.getErrorResponse("Something went wrong!");

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
        }if(order.getPaymentMethod() == null){
            return  service.getErrorResponse ( "Select payment method!");
        }


        try{

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

        }catch (Exception e){
            e.printStackTrace();
        }
        return service.getErrorResponse("Something went wrong!");
    }

    @PostMapping("/card-initiate")
    public ResponseEntity createChargeCard(@RequestBody Order order) {
        //validate data

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
        }if(order.getPaymentMethod() == null){
            return  service.getErrorResponse ( "Select payment method!");
        }


        try{

        Order orderDb = orderRepo.findByCheckoutId(order.getCheckoutId());
        if(orderDb != null){
            return  service.getErrorResponse ( "Order already exist!");
        }


        //create charge
        order.setOrderStatus(Status.Pending);
        order.setShipStatus(Status.Pending);
        order.setOrderDate(new Date());
        // You may want to store charge id along with order information


        return  service.getSuccessResponse( orderRepo.save(order));

        }catch (Exception e){
            e.printStackTrace();
        }
        return service.getErrorResponse("Something went wrong!");
    }

    @GetMapping("/card-finalize/{id}")
    public ResponseEntity createChargeCardFinal(@PathVariable Integer id){
        try{

        if(id == null){
            return service.getErrorResponse("Invalid request!");
        }
        Order order = orderRepo.findByCheckoutId(id);
        if(order == null){
            return service.getErrorResponse("Invalid request!");
        }
        if(!order.getPaymentMethod().equals(PaymentMethod.Card)){
            return service.getErrorResponse("Invalid request!");
        }
        if(order.getOrderStatus().equals(Status.Paid)){
            return service.getErrorResponse("Already paid!");
        }
        order.setOrderStatus(Status.Paid);

        return service.getSuccessResponse(orderRepo.save(order));
        }catch (Exception e){
            return service.getErrorResponse("Something went wrong!");
        }
    }




    @GetMapping("/user/{id}")
    public ResponseEntity getOrderMainByUser(@PathVariable Long id){
        if(id == null){
            return service.getErrorResponse("Invalid request!");
        }

        try{
       List<OrderResponse> list =  orderService.getOrderByUserId(id);

       return service.getSuccessResponse(list);

        }catch (Exception e){
            e.printStackTrace();
        }
        return service.getErrorResponse("Something went wrong!");


    }

    @GetMapping("/detail/{id}")
    public ResponseEntity getOrderDetailByCheckoutId(@PathVariable Integer id){
        if(id == null){
            return service.getErrorResponse("Invalid request!");
        }

        try{
       List<CheckoutDetail> list =  orderService.getOrderDetailByCheckoutId(id);

       return service.getSuccessResponse(list);

        }catch (Exception e){
            e.printStackTrace();
        }
        return service.getErrorResponse("Something went wrong!");

    }


    @GetMapping("/checkout/{id}")
    public ResponseEntity getOrderByCheckoutId(@PathVariable Integer id){
        if(id == null){
            return service.getErrorResponse("Invalid request!");
        }
        try{
       Order order  =  orderRepo.findByCheckoutId(id);
        if(order == null){
            return service.getErrorResponse("No data found");
        }
       return service.getSuccessResponse(order);

        }catch (Exception e){
            e.printStackTrace();
        }
        return service.getErrorResponse("Something went wrong!");
    }

}
