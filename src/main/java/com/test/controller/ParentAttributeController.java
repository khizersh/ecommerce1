package com.test.controller;

import com.test.bean.attribute.ParentAttributes;
import com.test.repo.ParentAttributeRepo;
import com.test.utility.GlobalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/parent-attribute")
public class ParentAttributeController {

    @Autowired
    private ParentAttributeRepo attributeRepo;

    @Autowired
    private GlobalService responseService;


    @GetMapping
    public ResponseEntity getAll(){
        return responseService.getSuccessResponse(attributeRepo.findAll());

    }



    @GetMapping("/{id}")
    public ResponseEntity getById(@PathVariable Integer id){
        if(!attributeRepo.existsById(id)){
            return responseService.getErrorResponse("Invalid Request");
        }
        ParentAttributes pAtt =  attributeRepo.getOne(id);
        return responseService.getSuccessResponse(pAtt);

    }


    @PostMapping
    public ResponseEntity addAttribute(@RequestBody ParentAttributes attribute){

        if(attribute.getTitle().isEmpty()){
            return responseService.getErrorResponse("Enter title please!");
        }
        if(attribute.getActive() == null){
           attribute.setActive(false);
        }


        ParentAttributes p =  attributeRepo.save(attribute);
        return responseService.getSuccessResponse(p);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity deleteById(@PathVariable Integer id){
        if(!attributeRepo.existsById(id)){
            return responseService.getErrorResponse("Invalid Request");
        }
        attributeRepo.deleteById(id);
        return responseService.getSuccessResponse("Delete successfully!");

    }


    @PutMapping("/{id}")
    public ResponseEntity edit(@RequestBody ParentAttributes attribute){
        if(!attributeRepo.existsById(attribute.getId())){
            return responseService.getErrorResponse("Invalid Request");
        }
        ParentAttributes p = attributeRepo.getOne(attribute.getId());

        if(attribute.getTitle() != null){
            p.setTitle(attribute.getTitle());
        }
        if(attribute.getActive() != null){
            p.setActive(attribute.getActive());
        }
        attributeRepo.save(p);
        return responseService.getSuccessResponse(p);

    }
}
