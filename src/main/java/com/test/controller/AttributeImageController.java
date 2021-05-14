package com.test.controller;

import com.test.repo.AttributeImageRepo;
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


    @GetMapping
    public ResponseEntity getAll(){
        return service.getSuccessResponse(attributeImageRepo.findAll());
    }

    @DeleteMapping("/{id}")
    public  ResponseEntity deleteAttributeImage(@PathVariable Integer id )  {

        if(!attributeImageRepo.existsById(id)){
            return service.getErrorResponse("Image not found!");
        }

        attributeImageRepo.deleteById(id);

        return service.getSuccessResponse("Product deleted");

    }
}
