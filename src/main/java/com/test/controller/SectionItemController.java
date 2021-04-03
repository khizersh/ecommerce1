package com.test.controller;

import com.test.bean.sections.SectionItems;
import com.test.repo.SectionItemsRepo;
import com.test.repo.SectionRepo;
import com.test.utility.GlobalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/section-item")
public class SectionItemController {

    @Autowired
    private GlobalService service;

    @Autowired
    private SectionItemsRepo itemsRepo;
    @Autowired
    private SectionRepo sectionRepo;


    @PostMapping
    public ResponseEntity getItemsBySectionId(@RequestBody SectionItems items){

        if(items.getProductId() == null){
            return service.getErrorResponse("Invalid request!");
        }
        if(items.getSectionId() == null){
            return service.getErrorResponse("Invalid request!");
        }
        List<SectionItems> list = new ArrayList<>();
        list =  itemsRepo.findBySectionId(items.getSectionId());

        items.setSequence(list.size() + 1);
       return  service.getSuccessResponse(itemsRepo.save(items));
    }


//    @GetMapping("/section/{id}")
//    public ResponseEntity getItemsBySectionId(@PathVariable Integer id){
//
//        if(id == null){
//            return service.getErrorResponse("Invalid request!");
//        }
//
//    }

}
