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
@RequestMapping("/api/section-item")
public class SectionItemController {

    @Autowired
    private GlobalService service;

    @Autowired
    private SectionItemsRepo itemsRepo;
    @Autowired
    private SectionRepo sectionRepo;

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRepo productRepo;

    @PostMapping
    public ResponseEntity addSectionItem(@RequestBody SectionItems items){

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
    @PostMapping("/all")
    public ResponseEntity addSectionItemList(@RequestBody List<SectionItems>  items){

        int seq = 0;
        if(items.size() > 0){
            List<SectionItems> list = new ArrayList<>();
            list =  itemsRepo.findBySectionId(items.get(0).getSectionId());
            seq = list.size();
            for (SectionItems i : items ) {
                if(i.getProductId() == null){
                    return service.getErrorResponse("Invalid request!");
                }
                if(i.getSectionId() == null){
                    return service.getErrorResponse("Invalid request!");
                }
                i.setSequence(seq);
                seq++;
                itemsRepo.save(i);
            }
        }
       return service.getSuccessResponse("data added succesfully!");
    }


    @GetMapping("/section/{id}")
    public ResponseEntity getItemsBySectionId(@PathVariable Integer id){

        if(id == null){
            return service.getErrorResponse("Invalid request!");
        }
        ProductSectionDto dto = new ProductSectionDto();
        ProductSections sec = sectionRepo.getOne(id);
        dto.setId(id);
        dto.setTitle(sec.getTitle());
//        dto.set
       List<ProductDto> list = new ArrayList<>();
        for (SectionItems j : itemsRepo.findBySectionId(id)) {
            Product product = productRepo.getOne(j.getProductId());
            if(product != null){
            ProductDto pro = productService.convertDto(product , false);
            pro.setSequence(j.getSequence());
            list.add(pro);
            }
        }
        dto.setProductList(list);

        return service.getSuccessResponse(dto);

    }

}
