package com.test.controller;

import com.test.bean.product.Product;
import com.test.bean.sections.PositionClass;
import com.test.bean.sections.ProductSections;
import com.test.bean.sections.SectionItems;
import com.test.bean.sections.SortBySequence;
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
       List<SectionItems> listItems = itemsRepo.findBySectionId(id);
        Collections.sort(listItems, new SortBySequence());

        for (SectionItems j : listItems) {
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


    @PostMapping("/position")
    public ResponseEntity changePosition(@RequestBody PositionClass pos){

        if(pos.getFrom() == null){
            return service.getErrorResponse("Select Product!");
        }
        if(pos.getTo() == null){
            return service.getErrorResponse("Enter position!");
        }
        if(pos.getId() == null){
            return service.getErrorResponse("Select Product!");
        }

        List<SectionItems>  listFrom =  itemsRepo.findBySequence(pos.getFrom());
        List<SectionItems>  listTo =  itemsRepo.findBySequence(pos.getTo());
        SectionItems itemTo = new SectionItems();
        SectionItems itemFrom = new SectionItems();

        if(listTo.size() > 0){
            itemTo = listTo.get(0);
        }
        if(listFrom.size() > 0){
            itemFrom = listFrom.get(0);
        }


        if(itemTo ==  null) {
            itemFrom.setSequence(pos.getTo());
            itemsRepo.save(itemFrom);
        }else{
            itemFrom.setSequence(pos.getTo());
            itemTo.setSequence(pos.getFrom());
            itemsRepo.save(itemFrom);
            itemsRepo.save(itemTo);
        }

        return service.getSuccessResponse("Position changed successfully!");
    }

}

