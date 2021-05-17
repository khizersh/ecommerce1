package com.test.controller;

import com.test.bean.product.AttributeImages;
import com.test.repo.AttributeImageRepo;
import com.test.service.AmazonClient;
import com.test.utility.GlobalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/attribute-image")
public class AttributeImageController {

    @Autowired
    private AttributeImageRepo attributeImageRepo;
    @Autowired
    private GlobalService service;
    @Autowired
    private AmazonClient amazonClient;


    @GetMapping
    public ResponseEntity getAll(){
        return service.getSuccessResponse(attributeImageRepo.findAll());
    }

    @DeleteMapping("/{id}")
    public  ResponseEntity deleteAttributeImage(@PathVariable Integer id )  {

        AttributeImages atm = attributeImageRepo.getOne(id);
        if(atm == null){
            return service.getErrorResponse("Image not found!");
        }

        amazonClient.deleteFileFromS3Bucket(atm.getImage());
        attributeImageRepo.deleteById(id);

        return service.getSuccessResponse("Product deleted");

    }
}
