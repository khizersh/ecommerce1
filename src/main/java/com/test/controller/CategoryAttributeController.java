package com.test.controller;


import com.test.bean.ChildAttribute;
import com.test.bean.ChildCategoryAttribute;
import com.test.repo.ChildCategoryAttributeRepo;
import com.test.repo.ParentAttributeRepo;
import com.test.utility.GlobalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/category-attribute")
public class CategoryAttributeController {

    @Autowired
    private ChildCategoryAttributeRepo repo;

    @Autowired
    private ParentAttributeRepo attributeRepo;

    @Autowired
    private GlobalService service;


    @GetMapping
    public ResponseEntity getAll(){
        return service.getSuccessResponse(repo.findAll());
    }


    @GetMapping("/{id}")
    public ResponseEntity getById(@PathVariable Integer id){
        if(id == null){
            return service.getErrorResponse("Invalid Request!");
        }

        ChildCategoryAttribute categoryattribute = repo.getOne(id);
        return service.getSuccessResponse(categoryattribute);
    }



}
