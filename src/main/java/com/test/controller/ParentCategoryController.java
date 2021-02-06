package com.test.controller;

import com.test.bean.ParentCategory;
import com.test.service.ParentCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/parentCategory")
public class ParentCategoryController {

    @Autowired
    private ParentCategoryService service;

    @GetMapping
    public ResponseEntity getAllCategories(){
        return ResponseEntity.ok(service.getAll());
    }

    @PostMapping
    public ResponseEntity addCategory(@RequestBody ParentCategory category){
        return ResponseEntity.ok(service.add(category));
    }

    @PutMapping("/{id}")
    public ResponseEntity updateCategory(@PathVariable Integer id , @RequestBody ParentCategory category){
        return ResponseEntity.ok(service.update(id , category));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity deleteCategory(@PathVariable Integer id ){
        return ResponseEntity.ok(service.delete(id));
    }
}
