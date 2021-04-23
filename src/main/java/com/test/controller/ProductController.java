package com.test.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.bean.attribute.ChildAttribute;
import com.test.bean.attribute.ParentAttributes;
import com.test.bean.category.ChildCategory;
import com.test.bean.product.AttributeImages;
import com.test.bean.product.AttributePrice;
import com.test.bean.product.ImageModel;
import com.test.bean.product.Product;
import com.test.bean.product_attribute.ProductAttribute;
import com.test.bean.product_attribute.ProductSubAttribute;
import com.test.dto.ChildAttributeDto;
import com.test.dto.ParentAttributeDto;
import com.test.dto.ProductDto;
import com.test.repo.*;
import com.test.service.ProductService;
import com.test.utility.GlobalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

import static com.test.utility.GlobalService.compressBytes;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    @Autowired
    private ProductRepo productRepo;

    @Autowired
    private GlobalService service;
    @Autowired
    private ProductService productService;
    @Autowired
    private ChildCategoryRepo childCatRepo;

//    attribute
    @Autowired
    private AttributePriceRepo priceRepo;
    @Autowired
    private ProductAttributeRepo productAttributeRepo;
    @Autowired
    private ParentAttributeRepo parentAttributeRepo;
    @Autowired
    private ChildAttributeRepo childAttributeRepo;
    @Autowired
    private AttributeImageRepo attributeImageRepo;

    
//    end

    @Autowired
    private ImageRepository imageRepository;

    @GetMapping
    public ResponseEntity getAll(){
        List<ProductDto> list = new ArrayList<>();
        for (Product i:productRepo.findAll() ) {
            list.add(productService.convertDto(i , false));
        }
        return service.getSuccessResponse(list);
    }


    @GetMapping("/category/{id}")
    public ResponseEntity getProductListByCategoryId(@PathVariable Integer id){
        List<ProductDto> list = new ArrayList<>();
        ChildCategory cat = childCatRepo.getOne(id);
        if(cat != null){

        for (Product i:productRepo.findProductByCategory(cat)) {
            list.add(productService.convertDto(i , false));
        }
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

        ProductDto dto = productService.convertDto(pro , true);




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
        if(imageList == null){
            return service.getErrorResponse("Please select images!");
        }
        if(imageList.size() == 0){
            return service.getErrorResponse("Please select images!");
        }
        for (MultipartFile i: imageList){
        ImageModel img = new ImageModel(i.getOriginalFilename(), i.getContentType() , compressBytes(i.getBytes()) );
        imageModels.add(img);
        }
        product.setImageList(imageModels);
        List<ProductAttribute> listParent = new ArrayList<>();


        for (ProductAttribute i: product.getAttributeList()){

            ProductAttribute attribute = new ProductAttribute();
            List<ProductSubAttribute> listChild = new ArrayList<>();
            ParentAttributes parent = parentAttributeRepo.getOne(i.getParentAttributeId());
            attribute.setParentAttributeId(parent.getId());
            attribute.setParentAttributeName(parent.getTitle());
            attribute.setMultiImage(i.getMultiImage());

           for(ProductSubAttribute j: i.getSubAttributeList()){

               ProductSubAttribute subAttribute = new ProductSubAttribute();
               ChildAttribute child = childAttributeRepo.getOne(j.getChildAttributeId());
               subAttribute.setChildAttributeId(child.getId());
               subAttribute.setChildAttributeName(child.getTitle());
               listChild.add(subAttribute);
           }
            attribute.setSubAttributeList(listChild);
            listParent.add(attribute);
        }
        product.setAttributeList(listParent);



        Product savedProduct =  productRepo.save(product);

        boolean flag =  setAttributePrice(savedProduct);

        if(flag){
            return service.getSuccessResponse(savedProduct.getId());
        }else{
            return service.getErrorResponse("Attribute not set!");
        }


    }


    @DeleteMapping("/{id}")
    public  ResponseEntity deleteProduc(@PathVariable Integer id )  {

        if(!productRepo.existsById(id)){
            return service.getErrorResponse("Image not found!");
        }

        productRepo.deleteById(id);

        return service.getSuccessResponse("Product deleted");

    }

    @PostMapping("/attribute-image")
    public ResponseEntity addAttributeImage(@RequestParam String attribute ,@RequestParam(value = "imageList") List<MultipartFile> imageList ) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        AttributeImages att = mapper.readValue(attribute, AttributeImages.class);
        if(imageList.size() == 0){
            return service.getErrorResponse("Please select images!");
        }
        for (MultipartFile i: imageList){
            AttributeImages attributeImages = new AttributeImages();
            ImageModel img = new ImageModel(i.getOriginalFilename(), i.getContentType() , compressBytes(i.getBytes()) );
            attributeImages.setProductId(att.getProductId());
            attributeImages.setAttributeId(att.getAttributeId());
            attributeImages.setImage(img);
            attributeImageRepo.save(attributeImages);

        }
        return service.getSuccessResponse("Success");
    }



    @PutMapping("/{id}")
    public ResponseEntity editProductDetail(@RequestBody Product product , @PathVariable Integer id ) throws Exception {

        if(!productRepo.existsById(id)){
            return service.getErrorResponse("Invalid request!");
        }

        Product productDb = productRepo.getOne(id);

        if(!product.getTitle().isEmpty()){
            productDb.setTitle(product.getTitle());
        }
        if(!product.getDescription().isEmpty()){
            productDb.setDescription(product.getDescription());
        }

        Product savedProduct =  productRepo.save(productDb);
        return service.getSuccessResponse(savedProduct);
    }



    @PutMapping("/image/{id}")
    public  ResponseEntity updateImage(@PathVariable Integer id ,  @RequestParam(value = "imageFile") MultipartFile imageFile) throws IOException {

        if(!imageRepository.existsById(id)){
            return service.getErrorResponse("Image not found!");
        }
          ImageModel imageDb = imageRepository.getOne(id);
        if(imageFile != null){
            imageDb.setPicByte(compressBytes(imageFile.getBytes()));
            imageDb.setName(imageFile.getOriginalFilename());
            imageDb.setType(imageFile.getContentType());
        }

        ImageModel savedImage =  imageRepository.save(imageDb);

        return service.getSuccessResponse(savedImage);

    }


    @DeleteMapping("/image/{id}")
    public  ResponseEntity deleteProductImage(@PathVariable Integer id )  {

        if(!imageRepository.existsById(id)){
            return service.getErrorResponse("Image not found!");
        }

        imageRepository.deleteById(id);

        return service.getSuccessResponse("Image deleted");

    }


    public Boolean setAttributePrice(Product pro){

        int loopNo = pro.getAttributeList().size();
       boolean flag = attributeLoops(loopNo , pro.getAttributeList()  , pro.getId() , pro.getTitle());
       if(flag){
           return true;
       }
        return false;
    }


    public Boolean attributeLoops(int count , List<ProductAttribute>  list, Integer pid , String pName){

         if(count == 1) {
             for (ProductSubAttribute i : list.get(0).getSubAttributeList()) {
                 AttributePrice price = new AttributePrice();
                 price.setAttribute_1(i.getChildAttributeName());
                 price.setProductId(pid);
                 price.setProductName(pName);
                 priceRepo.save(price);
             }
             return true;
         }

           if(count == 2) {
               System.out.println("count 2");
               for (ProductSubAttribute i : list.get(0).getSubAttributeList()) {
                   for (ProductSubAttribute j : list.get(1).getSubAttributeList()) {
                       AttributePrice price = new AttributePrice();
                       price.setAttribute_1(i.getChildAttributeName());
                       price.setAttribute_2(j.getChildAttributeName());
                       price.setProductId(pid);
                       price.setProductName(pName);
                       priceRepo.save(price);
                   }
               }
               return true;
           }

         if(count == 3) {
             System.out.println("count 3");
             for (ProductSubAttribute i : list.get(0).getSubAttributeList()) {
                 for (ProductSubAttribute j : list.get(1).getSubAttributeList()) {
                     for (ProductSubAttribute k : list.get(2).getSubAttributeList()) {
                         AttributePrice price = new AttributePrice();
                         price.setAttribute_1(i.getChildAttributeName());
                         price.setAttribute_2(j.getChildAttributeName());
                         price.setAttribute_3(k.getChildAttributeName());
                         price.setProductId(pid);
                         price.setProductName(pName);
                         priceRepo.save(price);
                     }
                 }
             }
             return true;
         }
           if(count == 3) {
               System.out.println("count 4");
               for (ProductSubAttribute i : list.get(0).getSubAttributeList()) {
                   for (ProductSubAttribute j : list.get(1).getSubAttributeList()) {
                       for (ProductSubAttribute k : list.get(2).getSubAttributeList()) {
                           for (ProductSubAttribute l : list.get(3).getSubAttributeList()) {
                               AttributePrice price = new AttributePrice();
                               price.setAttribute_1(i.getChildAttributeName());
                               price.setAttribute_2(j.getChildAttributeName());
                               price.setAttribute_3(k.getChildAttributeName());
                               price.setAttribute_3(l.getChildAttributeName());
                               price.setProductId(pid);
                               price.setProductName(pName);
                               priceRepo.save(price);
                           }
                       }
                   }
               }
               return true;
           }


        return false;
    }
//


}
