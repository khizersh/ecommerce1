package com.test.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.bean.*;
import com.test.dto.ChildAttributeDto;
import com.test.dto.ParentAttributeDto;
import com.test.dto.ProductDto;
import com.test.dto.childCategoryDto;
import com.test.repo.AttributePriceRepo;
import com.test.repo.ChildCategoryRepo;
import com.test.repo.ProductRepo;
import com.test.utility.GlobalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.*;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    @Autowired
    private ProductRepo productRepo;

    @Autowired
    private GlobalService service;
    @Autowired
    private ChildCategoryRepo childCatRepo;

    @Autowired
    private AttributePriceRepo priceRepo;

    @GetMapping
    public ResponseEntity getAll(){
        List<ProductDto> list = new ArrayList<>();
        for (Product i:productRepo.findAll() ) {
            list.add(convertDto(i , false));
        }
        return service.getSuccessResponse(list);
    }

    @GetMapping("/detail/{id}")
    public ResponseEntity getProductDetailById(@PathVariable Integer id){
        if(id == null){
            return service.getErrorResponse("Invalid request!");
        }
        if(!productRepo.existsById(id)){
            return service.getErrorResponse("Product not found!");
        }
        Product pro = productRepo.getOne(id);

        ProductDto dto = convertDto(pro , true);

        if(pro.getCategory() != null){
        dto.setCategoryDto(service.convertChildCategoryDto(pro.getCategory()));

        }


        return service.getSuccessResponse(dto);
    }

    @GetMapping("/{id}")
    public ResponseEntity getById(@PathVariable Integer id){
        if(id == null){
            return service.getErrorResponse("Invalid request!");
        }
        if(!productRepo.existsById(id)){
            return service.getErrorResponse("Product not found!");
        }

        Product pro = productRepo.getOne(id);
        return service.getSuccessResponse(pro);
    }


    @PostMapping
    public ResponseEntity addProduct(@RequestParam String productString , @RequestParam(value = "imageList") List<MultipartFile> imageList) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        Product product = mapper.readValue(productString, Product.class);

        if(product.getTitle().isEmpty()){
            service.getErrorResponse("Title is empty!");
        }
        if(product.getDescription().isEmpty()){
            service.getErrorResponse("Description is empty!");
        }
        if(!childCatRepo.existsById(product.getCategoryId())){
            service.getErrorResponse("Invalid category!");
        }

        ChildCategory childCategory = childCatRepo.getOne(product.getCategoryId());

        product.setCategory(childCategory);

        List<ImageModel> imageModels = new ArrayList<>();

        if(imageList.size() == 0){
            return service.getErrorResponse("Please select images!");
        }


        for (MultipartFile i: imageList){
        ImageModel img = new ImageModel(i.getOriginalFilename(), i.getContentType() ,service.compressBytes(i.getBytes()) );
        imageModels.add(img);
        }

        product.setImageList(imageModels);

        Product savedProduct =  productRepo.save(product);

        boolean flag =  setAttributePrice(savedProduct);

        if(flag){
            return service.getSuccessResponse("Product has uploaded, now you can set price!");
        }else{
            return service.getErrorResponse("Attribute not set!");
        }


    }



    public Boolean setAttributePrice(Product pro){

        int loopNo = pro.getCategory().getAttributeList().size();
        childCategoryDto dto = service.convertChildCategoryDto(pro.getCategory());
       boolean flag = attributeLoops(loopNo , dto  , pro.getId() , pro.getTitle());
       if(flag){
           return true;
       }
        return false;
    }


    public Boolean attributeLoops(int count , childCategoryDto category , Integer pid , String pName){

        switch(count) {
            case 1:
                for(ChildAttributeDto i: category.getAttributeList().get(0).getChildAttributeList()){
                       AttributePrice price = new AttributePrice();
                       price.setAttribute_1(i.getTitle());
                       price.setProductId(pid);
                       price.setProductName(pName);
                       priceRepo.save(price);
               }
                return true;

            case 2:

               for(ChildAttributeDto i: category.getAttributeList().get(0).getChildAttributeList()){
                   for(ChildAttributeDto j: category.getAttributeList().get(1).getChildAttributeList()){
                       AttributePrice price = new AttributePrice();
                       price.setAttribute_1(i.getTitle());
                       price.setAttribute_1(j.getTitle());
                       price.setProductId(pid);
                       price.setProductName(pName);
                       priceRepo.save(price);
                   }
               }
                return true;

            case 3:
                for(ChildAttributeDto i: category.getAttributeList().get(0).getChildAttributeList()){
                    for(ChildAttributeDto j: category.getAttributeList().get(1).getChildAttributeList()){
                        for(ChildAttributeDto k: category.getAttributeList().get(2).getChildAttributeList()){
                        AttributePrice price = new AttributePrice();
                        price.setAttribute_1(i.getTitle());
                        price.setAttribute_2(j.getTitle());
                        price.setAttribute_3(k.getTitle());
                            price.setProductId(pid);
                            price.setProductName(pName);
                            priceRepo.save(price);
                        }
                    }
                }
                return true;
            case 4:
                for(ChildAttributeDto i: category.getAttributeList().get(0).getChildAttributeList()){
                    for(ChildAttributeDto j: category.getAttributeList().get(1).getChildAttributeList()){
                        for(ChildAttributeDto k: category.getAttributeList().get(2).getChildAttributeList()) {
                            for (ChildAttributeDto l : category.getAttributeList().get(2).getChildAttributeList()) {
                                AttributePrice price = new AttributePrice();
                                price.setAttribute_1(i.getTitle());
                                price.setAttribute_2(j.getTitle());
                                price.setAttribute_3(k.getTitle());
                                price.setAttribute_3(l.getTitle());
                                price.setProductId(pid);
                                price.setProductName(pName);
                                priceRepo.save(price);
                            }
                        }
                    }
                }
                return true;
            default:
               return false;
        }


    }

    public ProductDto convertDto(Product pro ,Boolean isDetail){
        ProductDto dto = new ProductDto();
        dto.setId(pro.getId());
        dto.setTitle(pro.getTitle());
        if(pro.getCategory() != null){
        dto.setCategoryId(pro.getCategory().getId());
        dto.setCategoryName(pro.getCategory().getCategoryName());
        }
        dto.setDescription(pro.getDescription());
        if(pro.getImageList().size() != 0){

            if(!isDetail){
            List<ImageModel> list = new ArrayList<>();
                ImageModel image = new ImageModel();
                image = pro.getImageList().get(0);
                image.setPicByte(service.decompressByteArray(image.getPicByte()));
                list.add(image);
                dto.setImageList(list);
            }else{

                pro.getImageList().forEach(i -> {
                    i.setPicByte(service.decompressByteArray( i.getPicByte()));
                });
                dto.setImageList(pro.getImageList());

                if(pro.getCategory() != null){
                dto.setCategoryDto(service.convertChildCategoryDto(pro.getCategory()));

                }
            }
        }
        return dto;
    }

}
