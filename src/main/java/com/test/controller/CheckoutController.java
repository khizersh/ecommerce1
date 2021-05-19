package com.test.controller;

import com.test.bean.checkout.Checkout;
import com.test.bean.checkout.CheckoutDetail;
import com.test.bean.product.AttributePrice;
import com.test.bean.product.Points;
import com.test.bean.product.Product;
import com.test.repo.AttributePriceRepo;
import com.test.repo.CheckoutRepo;
import com.test.repo.ProductRepo;
import com.test.utility.GlobalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/checkout")
public class CheckoutController {

    @Autowired
    private CheckoutRepo checkoutRepo;
    @Autowired
    private AttributePriceRepo attributePriceRepo;
    @Autowired
    private ProductRepo productRepo;

    @Autowired
    private GlobalService service;


    @GetMapping
    public ResponseEntity getAll(){
        return service.getSuccessResponse(checkoutRepo.findAll());

    }
    @GetMapping("/{id}")
    public ResponseEntity getById(@PathVariable Integer id){
        if(id == null){
            return service.getErrorResponse("Invalid request!");
        }
        return service.getSuccessResponse(checkoutRepo.getOne(id));

    }

    @PostMapping
    public ResponseEntity addCheckout(@RequestBody Checkout checkout){

        if(checkout.getCoupon()){
            if(checkout.getCouponId() == null){
            return service.getErrorResponse("Invalid request!");
            }else{
                if(Double.compare(checkout.getCouponAmount() + checkout.getTotalAmount() , checkout.getNetAmount())  != 0){
                    return service.getErrorResponse("amount not matched!");
                }
            }
        }else{
            if( Double.compare(checkout.getTotalAmount() ,  checkout.getNetAmount()) != 0){
                System.out.println("total: "+checkout.getTotalAmount() + " net: "+checkout.getNetAmount());
                return service.getErrorResponse("amount not matched");
            }
        }

        if(checkout.getTotalAmount() <= 0){
            return service.getErrorResponse("Invalid total amount!");
        }

        if(checkout.getNetAmount() <= 0){
            return service.getErrorResponse("Invalid net amount!");
        }
        double productPrice = 0.0;
        List<CheckoutDetail> list = new ArrayList<>();
        for (CheckoutDetail detail : checkout.getProductList()) {

            CheckoutDetail detail1 = new CheckoutDetail();
            if(detail.getProductId() == null){
                return service.getErrorResponse("Invalid product id!");
            }
            if(detail.getPriceId() == null){
                return service.getErrorResponse("Invalid price id!");
            }
            if(detail.getQuantity() <= 0){
                return service.getErrorResponse("Invalid quantity!");
            }
            AttributePrice price = attributePriceRepo.getOne(detail.getPriceId());
            Product product = productRepo.getOne(detail.getProductId());

            if(price == null){
                return service.getErrorResponse("Invalid price id!");
            }
            if(product == null){
                return service.getErrorResponse("Invalid product!");
            }
            if(price.getDiscount()){
                productPrice += price.getDiscountPrice() * detail.getQuantity();
                detail1.setPrice(price.getDiscountPrice());
            }else{

                detail1.setPrice(price.getPrice());
                productPrice += price.getPrice() * detail.getQuantity();
            }


            detail1.setProductId(detail.getProductId());
            detail1.setQuantity(detail.getQuantity());
            detail1.setAttributePrice(price);
            detail1.setProductTitle(product.getTitle());
            list.add(detail1);

        }
        if(Double.compare(productPrice , checkout.getTotalAmount())  != 0){
            System.out.println("productPrice: "+productPrice);
            return service.getErrorResponse("Invalid amount");
        }
        checkout.setProductList(list);

        return service.getSuccessResponse(checkoutRepo.save(checkout));

    }



}
