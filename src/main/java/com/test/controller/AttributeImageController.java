package com.test.controller;

import com.test.repo.AttributeImageRepo;
import com.test.utility.GlobalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
