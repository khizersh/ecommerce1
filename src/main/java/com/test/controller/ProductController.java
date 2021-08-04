package com.test.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.org.apache.xpath.internal.operations.Bool;
import com.test.bean.attribute.ChildAttribute;
import com.test.bean.attribute.ParentAttributes;
import com.test.bean.category.ChildCategory;
import com.test.bean.product.AttributeImages;
import com.test.bean.product.AttributePrice;
import com.test.bean.product.ImageModel;
import com.test.bean.product.Product;
import com.test.bean.product_attribute.ProductAttribute;
import com.test.bean.product_attribute.ProductSubAttribute;
import com.test.dto.*;
import com.test.repo.*;
import com.test.service.AmazonClient;
import com.test.service.FileStorageService;
import com.test.service.ProductService;
import com.test.utility.GlobalService;
import com.test.utility.ImageURl;
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

    @Autowired
    private ProductSubAtrributeRepo produSubAttributeRepo;

    
//    end

    @Autowired
    private ImageUrlRepo imageRepository;
    @Autowired
    private FileStorageService fileService;
    @Autowired
    private AmazonClient amazonClient;




    @GetMapping
    public ResponseEntity getAll(){
        List<ProductDto> list = new ArrayList<>();
        for (Product i:productRepo.findByOrderByIdAsc() ) {
            list.add(productService.convertDto(i , true));
        }
        return service.getSuccessResponse(list);
    }


    @GetMapping("/category/{id}")
    public ResponseEntity getProductListByCategoryId(@PathVariable Integer id){
        List<ProductDto> list = new ArrayList<>();
        ChildCategory cat = childCatRepo.getOne(id);
        if(cat != null){

        for (Product i:productRepo.findProductByCategory(cat)) {
            list.add(productService.convertDto(i , true));
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

    @GetMapping("/full/{id}")
    public ResponseEntity getProductFullDetail(@PathVariable Integer id){
        if(id == null){
            return service.getErrorResponse("Invalid request!");
        }
        if(!productRepo.existsById(id)){
            return service.getErrorResponse("Product not found!");
        }
        Product pro = productRepo.getOne(id);

        ProductDto dto = productService.productDetailFull(pro);

        return service.getSuccessResponse(dto);
    }
    @PostMapping("/sub")
    public ResponseEntity removeSubAttributeOfProduct(@RequestBody  ProductSubAttribute att){
        if(att.getParentID() == null){
            return service.getErrorResponse("Invalid request!");
        }
        if(att.getChildAttributeId() == null){
            return service.getErrorResponse("Invalid product!");
        }
        try{
            produSubAttributeRepo.deleteSub(att.getChildAttributeId() , att.getParentID());
            priceRepo.deleteByProductId(att.getProductId());
            setAttributePrice(productRepo.getOne(att.getProductId()));
            return service.getSuccessResponse("Success");
        }catch (Exception e){

        }

        return service.getErrorResponse( "Failed");

    }
    @DeleteMapping("/remove-parent/{id}")
    public ResponseEntity removeAttributeOfProduct(@PathVariable  Integer id){
        if(id == null){
            return service.getErrorResponse("Invalid request!");
        }

         ProductAttribute productAttribute = productAttributeRepo.getOne(id);
        int productId = productAttribute.getpId();


        if(productAttribute == null){
            return service.getErrorResponse("Invalid product!");
        }

        try{
            productAttributeRepo.deleteById(id);
            produSubAttributeRepo.deleteSubAttributeByParentId(id);
            priceRepo.deleteByProductId(productId);
            setAttributePrice(productRepo.getOne(productId));
            return service.getSuccessResponse("Success");
        }catch (Exception e){
            return service.getErrorResponse( "Failed");
        }

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

    @PostMapping("/parent")
    public ResponseEntity addParentAttribute(@RequestBody ProductAttribute attribute){

        ProductAttribute att = new ProductAttribute();
        if(attribute.getpId() == null){
            return service.getErrorResponse("Invalid Request!");
        }
        if(attribute.getParentAttributeId() == null){
            return service.getErrorResponse("Invalid Request!");
        }

        if(!productRepo.existsById(attribute.getpId())){
            return service.getErrorResponse("Invalid Request!");
        }
        ParentAttributes db = parentAttributeRepo.getOne(attribute.getParentAttributeId());

        att.setpId(attribute.getpId());
        att.setParentAttributeId(attribute.getParentAttributeId());
        att.setParentAttributeName(db.getTitle());
        att.setMultiImage(attribute.getMultiImage());

        ProductAttribute p =  productAttributeRepo.save(att);
//        produSubAttributeRepo.deleteSub(att.getChildAttributeId() , att.getParentID());
//        priceRepo.deleteByProductId(p.getpId());
//        setAttributePrice(productRepo.getOne(p.getpId()));
        return service.getSuccessResponse("success");
    }

    @PostMapping("/child")
    public ResponseEntity addChildAttribute(@RequestBody ProductSubAttribute attribute){

        ProductSubAttribute att = new ProductSubAttribute();
        if(attribute.getParentID() == null){
            return service.getErrorResponse("Invalid Request!");
        }
        if(attribute.getChildAttributeId() == null){
            return service.getErrorResponse("Invalid Request!");
        }

        if(!productAttributeRepo.existsById(attribute.getParentID())){
            return service.getErrorResponse("Invalid Request!");
        }
        ChildAttribute db = childAttributeRepo.getOne(attribute.getChildAttributeId());

        att.setChildAttributeId(db.getId());
        att.setChildAttributeName(db.getTitle());
        att.setParentID(attribute.getParentID());
//        deleteByProductId
        ProductSubAttribute savedAtt = produSubAttributeRepo.save(att);
        priceRepo.deleteByProductId(attribute.getProductId());

        setAttributePrice(productRepo.getOne(attribute.getProductId()));
        return service.getSuccessResponse("Success");
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
        List<ImageURl> imageUrls = new ArrayList<>();
        if(imageList == null){
            return service.getErrorResponse("Please select images!");
        }
        if(imageList.size() == 0){
            return service.getErrorResponse("Please select images!");
        }
        for (MultipartFile i: imageList){
            ImageURl imageURl = new ImageURl();
//            imageURl.setImage(fileService.storeAndReturnFile(i));
            imageURl.setImage(amazonClient.uploadFile(i));
            imageUrls.add(imageURl);

        }
        product.setImageList(imageUrls);
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

        product.setReview(0.0);
        product.setReviewCount(0);


        Product savedProduct =  productRepo.save(product);

        for (ProductAttribute i: savedProduct.getAttributeList()) {
            i.setpId(savedProduct.getId());
            productAttributeRepo.save(i);
            for (ProductSubAttribute j:i.getSubAttributeList()) {
                j.setParentID(i.getId());
                produSubAttributeRepo.save(j);
            }
        }


        boolean flag =  setAttributePrice(savedProduct);

        if(flag){
            return service.getSuccessResponse(savedProduct.getId());
        }else{
            return service.getErrorResponse("Attribute not set!");
        }


    }


    @DeleteMapping("/{id}")
    public  ResponseEntity deleteProduct(@PathVariable Integer id )  {


       Boolean flag =  productService.deleteProduct(id);
       if(flag){
        return service.getSuccessResponse("Deleted successfully");
       }
        return service.getErrorResponse("Operation failed! try again later");

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
//            ImageModel img = new ImageModel(i.getOriginalFilename(), i.getContentType() , compressBytes(i.getBytes()) );
//            String img = fileService.storeAndReturnFile(i);
            String img = amazonClient.uploadFile(i);
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
        }  if(!product.getKeywords().isEmpty()){
            productDb.setKeywords(product.getKeywords());
        }

        Product savedProduct =  productRepo.save(productDb);
        return service.getSuccessResponse(savedProduct);
    }


    @PutMapping("/image/{id}")
    public  ResponseEntity updateImage(@PathVariable Integer id ,  @RequestParam(value = "imageFile") MultipartFile imageFile) throws IOException {

        if(!imageRepository.existsById(id)){
            return service.getErrorResponse("Image not found!");
        }
          ImageURl imageDb = imageRepository.getOne(id);
        if(imageFile != null){
            imageDb.setImage(amazonClient.uploadFile(imageFile));
        }

        ImageURl savedImage =  imageRepository.save(imageDb);

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

        List<ProductAttribute> list = productAttributeRepo.findByPid(pro.getId());
        list.get(0).getSubAttributeList();

        for (ProductAttribute i: list) {

            List<ProductSubAttribute> childList = produSubAttributeRepo.findByParentId(i.getId());

            if(childList.size() > 0){
                i.setSubAttributeList(childList);
            }

        }

        int loopNo = list.size();
        for (ProductAttribute i:list ) {
            System.out.println("Loop: "+i.toString());
        }
       boolean flag = attributeLoops(loopNo ,  list , pro.getId() , pro.getTitle());
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
