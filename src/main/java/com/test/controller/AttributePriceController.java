package com.test.controller;

import com.test.bean.AttributePrice;
import com.test.bean.Product;
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


    @PutMapping()
    public ResponseEntity updatePrice(@RequestBody AttributePrice price){
        if(price.getId() == null){
            return service.getErrorResponse("Invalid request!");
        }
        if(!priceRepo.existsById(price.getId())){
            return service.getErrorResponse("Attribute not found!");
        }
        AttributePrice attributePrice = priceRepo.getOne(price.getId());

        if(attributePrice.getProductId() != price.getProductId()){
            return service.getErrorResponse("Invalid request!");
        }
        if(price.getPrice() <= 0){
            return service.getErrorResponse("Invalid amount!");
        }
        attributePrice.setPrice(price.getPrice());
        priceRepo.save(attributePrice);

        return service.getSuccessResponse(attributePrice);
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
