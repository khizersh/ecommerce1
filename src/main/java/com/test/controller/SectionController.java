package com.test.controller;

import com.test.bean.product.Product;
import com.test.bean.sections.ProductSections;
import com.test.bean.sections.SectionItems;
import com.test.dto.ProductDto;
import com.test.dto.ProductSectionDto;
import com.test.repo.ProductRepo;
import com.test.repo.SectionItemsRepo;
import com.test.repo.SectionRepo;
import com.test.service.ProductService;
import com.test.utility.GlobalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/section")
public class SectionController {

    @Autowired
    private GlobalService service;

    @Autowired
    private SectionRepo sectionRepo;

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRepo productRepo;

    @Autowired
    private SectionItemsRepo itemsRepo;

    @GetMapping
    public ResponseEntity getAll(){

        List<ProductSectionDto> list = new ArrayList<>();

        for (ProductSections i: sectionRepo.findAll()) {
            ProductSectionDto dto = new ProductSectionDto();
            dto.setId(i.getId());
            dto.setTitle(i.getTitle());
             List<ProductDto> productList = new ArrayList<>();

            for (SectionItems j : itemsRepo.findBySectionId(i.getId())) {
                Product product = productRepo.getOne(j.getProductId());
                if(product != null){
                    productList.add(productService.convertDto(product , false));
                }
            }
          dto.setProductList(productList);
            list.add(dto);
        }

        return service.getSuccessResponse(list);
    }



    @PostMapping
    public ResponseEntity addSection(@RequestBody ProductSections pro){

        if(pro.getTitle().isEmpty()){
            return service.getErrorResponse("Enter title!");
        }

        return service.getSuccessResponse(sectionRepo.save(pro));
    }

    @PutMapping
    public ResponseEntity editSection(@RequestBody ProductSections pro){

            if(pro.getId() == null){
                return service.getErrorResponse("Invalid request!");
            }
            ProductSections db = sectionRepo.getOne(pro.getId());
        if(!pro.getTitle().isEmpty()){
            db.setTitle(pro.getTitle());
        }

            return service.getSuccessResponse(sectionRepo.save(db));
        }



}
