package com.test.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.bean.category.ChildCategory;
import com.test.bean.category.ParentCategory;
import com.test.bean.product.ImageModel;
import com.test.bean.product.Product;
import com.test.dto.childCategoryDto;
import com.test.repo.*;
import com.test.service.ChildCategoryService;
import com.test.service.FileStorageService;
import com.test.utility.GlobalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.*;

import static com.test.utility.GlobalService.compressBytes;

@RestController
@RequestMapping("/api/childCategory")
public class ChildCategoryController {

    @Autowired
    private ChildCategoryService childCategoryService;

    @Autowired
    private ChildCategoryAttributeRepo attributeRepo;

    @Autowired
    private ParentAttributeRepo parentAttributeRepo;

    @Autowired
    private ChildAttributeRepo childAttributeRepo;

    @Autowired
    private GlobalService service;

    @Autowired
    private ChildCategoryRepo repo;

    @Autowired
    private ParentCategoryRepo parentRepo;

    @Autowired
    private FileStorageService fileService;

    @GetMapping
    public ResponseEntity getAll(){

        List<childCategoryDto> list = new ArrayList<>();

        for (ChildCategory i:childCategoryService.getAll()) {
            list.add(service.convertChildCategoryDto(i));
        }

        return service.getSuccessResponse(list);

    }

    @GetMapping("/{id}")
    public ResponseEntity getById(@PathVariable Integer id){
        if(id == null){
            return service.getErrorResponse("Enter id!");
        }

        return service.getSuccessResponse(childCategoryService.getById(id));

    }
    @GetMapping("/parent/{id}")
    public ResponseEntity getListByParentId(@PathVariable Integer id){
        if(!parentRepo.existsById(id)){
            return service.getErrorResponse("Parent category not found!");
        }
        ParentCategory parent = parentRepo.getOne(id);
        List<ChildCategory> list = repo.findByParentCategory(parent);

        return service.getSuccessResponse(list);

    }


    @PostMapping
    public ResponseEntity addCategory(@RequestParam String category ,  @RequestParam(value = "image") MultipartFile image , @RequestParam(value = "banner") MultipartFile banner ) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        ChildCategory cat = mapper.readValue(category, ChildCategory.class);
        if(cat == null){
            return service.getErrorResponse("Enter all fields!");
        }
        if(cat.getActive() != null){
            cat.setActive(false);
        }
        if(cat.getCategoryName() == null){
            return service.getErrorResponse("Enter title!");
        }
//        ImageModel img = new ImageModel(image.getOriginalFilename(), image.getContentType() , compressBytes(image.getBytes()) );
//        ImageModel ban = new ImageModel(banner.getOriginalFilename(), banner.getContentType() , compressBytes(banner.getBytes()) );
//
//        cat.setImage(img);
//        cat.setBanner(ban);
        String im =  fileService.storeAndReturnFile(image);
        String ban =  fileService.storeAndReturnFile(banner);
        cat.setImage(im);
        cat.setBanner(ban);
        childCategoryService.addCategory(cat);
        return  service.getSuccessResponse("success");

    }

    @PostMapping("/update")
    public ResponseEntity updateCategory(@RequestParam String category ,  @RequestParam(value = "image" , required = false) MultipartFile image , @RequestParam(value = "banner" , required = false) MultipartFile banner ) throws IOException {

        ObjectMapper mapper = new ObjectMapper();
        ChildCategory cat = mapper.readValue(category, ChildCategory.class);
        if(cat == null){
            return service.getErrorResponse("Enter all fields!");
        }
        if(cat.getId()  == null){
            return service.getErrorResponse("Enter id!");
        }
        childCategoryService.updateCategory(cat , image , banner);
        return service.getSuccessResponse("success");

    }



    @DeleteMapping("/{id}")
    public ResponseEntity deleteCategory(@PathVariable Integer id ){

        if(childCategoryService.deleteCategory(id)){
            return ResponseEntity.ok().build();
        }else{
            return service.getErrorResponse("Something went wrong!");
        }

    }



}
