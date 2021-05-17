package com.test.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.bean.banner.HomePageBanner;
import com.test.bean.product.AttributeImages;
import com.test.bean.product.ImageModel;
import com.test.repo.BannerRepo;
import com.test.service.AmazonClient;
import com.test.service.FileStorageService;
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
    @Autowired
    private FileStorageService fileService;
    @Autowired
    private AmazonClient amazonClient;

    @GetMapping
    public ResponseEntity getAll(){
       return service.getSuccessResponse(bannerRepo.findAll());
    }

    @GetMapping("/awake")
    public ResponseEntity awake(){
       return service.getSuccessResponse("awake");
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

//        String img = fileService.storeAndReturnFile(file);
        String img = amazonClient.uploadFile(file);
        ban.setImage(img);
        return service.getSuccessResponse(bannerRepo.save(ban));
    }

    @PostMapping("/edit")
    public ResponseEntity edit(@RequestParam String banner, @RequestParam(value = "file" , required = false) MultipartFile file   ) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        HomePageBanner ban = mapper.readValue(banner, HomePageBanner.class);
        if(ban.getId() == null){
            return service.getErrorResponse("Invalid request!");
        }
        HomePageBanner db = bannerRepo.getOne(ban.getId());
        if(!ban.getTitle().isEmpty()){
            db.setTitle(ban.getTitle());
        }
        if(!ban.getDescription().isEmpty()){
            db.setDescription(ban.getDescription());
        }
        if(!ban.getUrl().isEmpty()){
            db.setUrl(ban.getUrl());
        }
        if(file != null){
            String img = amazonClient.uploadFile(file);
            db.setImage(img);
        }

        return service.getSuccessResponse(bannerRepo.save(db));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity deleteBanner(@PathVariable Integer id  ) throws IOException {

        if(id == null){
            return service.getErrorResponse("Invalid request!");
        }
        if(!bannerRepo.existsById(id)){
            return service.getErrorResponse("Invalid request!");
        }

        bannerRepo.deleteById(id);

        return service.getSuccessResponse("Delete successfully!");
    }


}

