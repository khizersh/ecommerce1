package com.test.controller;

import com.test.bean.product.AttributePrice;
import com.test.repo.AttributePriceRepo;
import com.test.utility.GlobalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/price-attribute")
public class AttributePriceController {

    @Autowired
    private AttributePriceRepo priceRepo;
    @Autowired
    private GlobalService service;

    @GetMapping("/{id}")
    public ResponseEntity getById(@PathVariable Integer id){
        if(id == null){
            return service.getErrorResponse("Invalid request!");
        }
        if(!priceRepo.existsById(id)){
            return service.getErrorResponse("Product not found!");
        }
        AttributePrice price = priceRepo.getOne(id);

        return service.getSuccessResponse(price);
    }


    @PostMapping("/update")
    public ResponseEntity updatePrice(@RequestBody List<AttributePrice> priceList){

        if(priceList.size() > 0) {

            for (AttributePrice price: priceList) {
                if (price.getId() == null) {
                    return service.getErrorResponse("Invalid request!");
                }
                if (priceRepo.existsById(price.getId())) {

                AttributePrice attributePrice = priceRepo.getOne(price.getId());

                if(price.getDiscount() != null && price.getDiscount() != false){
                    if(price.getPrice() != null && price.getDiscountPrice() != null){
                            attributePrice.setDiscountPrice(price.getDiscountPrice());
                            attributePrice.setPrice(price.getPrice());
                            attributePrice.setDiscount(true);
                            priceRepo.save(attributePrice);

                    }
                }else{
                    if(price.getPrice() != null){
                        attributePrice.setPrice(price.getPrice());
                        priceRepo.save(attributePrice);
                    }
                }


                }
            }


        }
        return service.getSuccessResponse(priceList);
    }

    @GetMapping("/product/{id}")
    public ResponseEntity getListOfAttributeByProductId(@PathVariable Integer id){
        if(id == null){
            return service.getErrorResponse("Invalid request!");
        }
        List<AttributePrice> list = priceRepo.findByProductId(id);
        if(list.size() == 0){
            return service.getErrorResponse("Attribute not found!");
        }


        return service.getSuccessResponse(list);
    }

}
