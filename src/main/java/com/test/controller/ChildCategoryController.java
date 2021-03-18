package com.test.controller;

import com.test.bean.ChildAttribute;
import com.test.bean.ChildCategory;
import com.test.bean.ChildCategoryAttribute;
import com.test.bean.ParentAttributes;
import com.test.dto.ChildAttributeDto;
import com.test.dto.ParentAttributeDto;
import com.test.dto.childCategoryDto;
import com.test.repo.ChildAttributeRepo;
import com.test.repo.ChildCategoryAttributeRepo;
import com.test.repo.ParentAttributeRepo;
import com.test.service.ChildCategoryService;
import com.test.utility.GlobalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

    @PostMapping
    public ResponseEntity addCategory(@RequestBody ChildCategory cat){
        if(cat == null){
            return service.getErrorResponse("Enter all fields!");
        }
        if(cat.getAttributeList().size() == 0){
            return service.getErrorResponse("Please add attribute!");
        }
        List<ChildCategoryAttribute> list = new ArrayList<>();
        for ( ChildCategoryAttribute i: cat.getAttributeList()) {
           if(parentAttributeRepo.existsById(i.getParentAttributeId())){
               ParentAttributes pcat = parentAttributeRepo.getOne(i.getParentAttributeId());
               ChildCategoryAttribute att = new ChildCategoryAttribute();
               att.setParentAttributes(pcat);
               list.add(att);

           }
        }
        cat.setAttributeList(list);
        return (childCategoryService.addCategory(cat));

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
        System.out.println("Delete: "+ id);
        if(childCategoryService.deleteCategory(id)){
            return ResponseEntity.ok().build();
        }else{
            return service.getErrorResponse("Something went wrong!");
        }

    }



}
