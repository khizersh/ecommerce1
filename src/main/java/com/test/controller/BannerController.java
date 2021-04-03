package com.test.controller;

import com.test.repo.BannerRepo;
import com.test.utility.GlobalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/banner")
public class BannerController {

    @Autowired
    private GlobalService service;

    @Autowired
    private BannerRepo bannerRepo;

    @GetMapping
    public ResponseEntity getAll(){
       return service.getSuccessResponse(bannerRepo.findAll());
    }

    @PostMapping
    public ResponseEntity add(){

        return service.getSuccessResponse(bannerRepo.findAll());
    }
}
