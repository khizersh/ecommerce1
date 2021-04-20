package com.test.controller;

import com.test.bean.category.ChildCategory;
import com.test.bean.category.ParentCategory;
import com.test.bean.product.ImageModel;
import com.test.dto.CategoryWithChild;
import com.test.dto.ChildCat;
import com.test.repo.ChildCategoryRepo;
import com.test.service.ParentCategoryService;
import com.test.utility.GlobalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;

import static com.test.utility.GlobalService.compressBytes;
import static com.test.utility.GlobalService.decompressBytes;

@RestController
@RequestMapping("api/parentCategory")
public class ParentCategoryController {

    @Autowired
    private ParentCategoryService service;

    @Autowired
    private ChildCategoryRepo childRepo;

    @Autowired
    private GlobalService responseService;

    @GetMapping
    public ResponseEntity getAllCategories(){

        return responseService.getSuccessResponse(service.getAll());
    }

    @GetMapping("/withChild")
    public ResponseEntity getAllCategoriesWithChild(){

        List<ParentCategory> list = service.getAll();
        List<CategoryWithChild> finalList = new ArrayList<>();

        for (ParentCategory i: list) {
           List<ChildCat>  childList = new ArrayList<>();
            CategoryWithChild parent = new CategoryWithChild();
            for (ChildCategory j:childRepo.findByParentCategory(i) ) {
                ChildCat child = new ChildCat();
                child.setId(j.getId());
                child.setChildTitle(j.getCategoryName());
                child.setActive(j.getActive());
                childList.add(child);

            }
            parent.setId(i.getId());
            parent.setChildList(childList);
            parent.setActive(i.getActive());
            parent.setTitle(i.getCategoryName());
            finalList.add(parent);
        }
        return responseService.getSuccessResponse(finalList);
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

    public ChildCat convertDto(ChildCategory cat , boolean detail){
        ChildCat dto = new ChildCat();
        dto.setId(cat.getId());
        dto.setActive(cat.getActive());
        dto.setChildTitle(cat.getCategoryName());

        if(detail){
            if(cat.getImage() != null){
                ImageModel img = new ImageModel(cat.getImage().getName(), cat.getImage().getType() , decompressBytes(cat.getImage().getPicByte()) );
                ImageModel banner = new ImageModel(cat.getBanner().getName(), cat.getBanner().getType() , decompressBytes(cat.getBanner().getPicByte()) );
                dto.setImage(img);
                dto.setBanner(banner);

            }
        }

        return dto;

    }
}
