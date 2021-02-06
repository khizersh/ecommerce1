package com.test.controller;

import com.test.bean.ChildCategory;
import com.test.service.ChildCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/childCategory")
public class ChildCategoryController {

    @Autowired
    private ChildCategoryService childCategoryService;

    @GetMapping
    public ResponseEntity getAll(){
        return ResponseEntity.ok(childCategoryService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity getById(@PathVariable Integer id){
        if(id == null){
            return new ResponseEntity("Enter id!", HttpStatus.BAD_REQUEST);
        }

       return ResponseEntity.ok(childCategoryService.getById(id));
    }

    @PostMapping
    public ResponseEntity addCategory(@RequestBody ChildCategory cat){
        if(cat == null){
            return new ResponseEntity("Enter all fields!",HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok(childCategoryService.addCategory(cat));
    }

    @PutMapping("/{id}")
    public ResponseEntity updateCategory(@RequestBody ChildCategory cat , @PathVariable Integer id){
        if(cat == null){
            return new ResponseEntity("Enter all fields!",HttpStatus.BAD_REQUEST);
        }
        if(id == null){
            return new ResponseEntity("Enter id!",HttpStatus.BAD_REQUEST);
        }

        return ResponseEntity.ok(childCategoryService.updateCategory(id , cat));
    }

}
