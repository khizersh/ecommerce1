package com.test.controller;

import com.test.bean.category.ChildCategory;
import com.test.bean.category.ParentCategory;
import com.test.dto.childCategoryDto;
import com.test.repo.*;
import com.test.service.ChildCategoryService;
import com.test.utility.GlobalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.*;

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
    public ResponseEntity addCategory(@RequestBody ChildCategory cat){
        if(cat == null){
            return service.getErrorResponse("Enter all fields!");
        }
        if(cat.getActive() != null){
            cat.setActive(false);
        }
        if(cat.getCategoryName() == null){
            return service.getErrorResponse("Enter title!");
        }
        return  service.getSuccessResponse(childCategoryService.addCategory(cat));

    }

    @PutMapping("/{id}")
    public ResponseEntity updateCategory(@RequestBody ChildCategory cat , @PathVariable Integer id){
        if(cat == null){
            return service.getErrorResponse("Enter all fields!");
        }
        if(id == null){
            return service.getErrorResponse("Enter id!");
        }

        return service.getSuccessResponse(childCategoryService.updateCategory(id , cat));

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
