package com.test.controller.search;

import com.test.repo.ProductRepo;
import com.test.utility.GlobalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/search")
public class SearchController {

    @Autowired
    ProductRepo productRepo;

    @Autowired
    GlobalService service;


    @GetMapping("/{keyword}")
    public ResponseEntity getResultByKeyword(@PathVariable String keyword){
        return service.getSuccessResponse(productRepo.findByKeywordsContainingIgnoreCase(keyword));
    }
}

