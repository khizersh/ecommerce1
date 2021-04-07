package com.test.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.bean.banner.HomePageBanner;
import com.test.bean.product.AttributeImages;
import com.test.bean.product.ImageModel;
import com.test.repo.BannerRepo;
import com.test.utility.GlobalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static com.test.utility.GlobalService.compressBytes;

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
    public ResponseEntity add(@RequestParam String banner, @RequestParam MultipartFile file   ) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        HomePageBanner ban = mapper.readValue(banner, HomePageBanner.class);
        if(ban.getTitle().isEmpty()){
           return service.getErrorResponse("Enter title!");
        }
        if(ban.getDescription().isEmpty()){
            return service.getErrorResponse("Enter description!");
        }
        if(file == null){
            return service.getErrorResponse("Select image!");
        }
        ImageModel img = new ImageModel(file.getOriginalFilename(), file.getContentType() , compressBytes(file.getBytes()) );
        ban.setImage(img);
        return service.getSuccessResponse(bannerRepo.save(ban));
    }


}

