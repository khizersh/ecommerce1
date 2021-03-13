package com.test.controller;

import com.test.bean.ChildAttribute;
import com.test.bean.ParentAttributes;
import com.test.repo.ChildAttributeRepo;
import com.test.repo.ParentAttributeRepo;
import com.test.utility.GlobalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/child-attribute")
public class ChildAttributeController {

    @Autowired
    private GlobalService service;

    @Autowired
    private ChildAttributeRepo childAttributeRepo;

    @Autowired
    private ParentAttributeRepo parentAttributeRepo;

    @GetMapping
    public ResponseEntity getAll(){
        return service.getSuccessResponse(childAttributeRepo.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity getById(@PathVariable Integer id){
        if(id == null){
           return service.getErrorResponse("Invalid Request!");
        }

        ChildAttribute child = childAttributeRepo.getOne(id);
        return service.getSuccessResponse(child);
    }

    @PostMapping
    public ResponseEntity addAttribute(@RequestBody ChildAttribute child){
        if(child == null){
            return service.getErrorResponse("Empty Form");
        }
        if(child.getTitle().isEmpty()){
            return service.getErrorResponse("Enter title please!");
        }

        if(child.getParentId() == null){
            return service.getErrorResponse("Select parent attribute!");
        }


        ParentAttributes p = parentAttributeRepo.getOne(child.getParentId());

        if(p == null){
            return service.getErrorResponse("Parent not found!");
        }

        child.setParentAttributes(p);
        ChildAttribute childRes = childAttributeRepo.save(child);
        return service.getSuccessResponse(childRes);
    }



    @DeleteMapping("/{id}")
    public ResponseEntity deleteById(@PathVariable Integer id){
        if(id == null){
            return service.getErrorResponse("Invalid Request!");
        }

        ChildAttribute child = childAttributeRepo.getOne(id);
        return service.getSuccessResponse(child);
    }
}
