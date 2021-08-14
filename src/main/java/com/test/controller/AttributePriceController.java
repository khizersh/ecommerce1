package com.test.controller;

import com.test.bean.product.AttributePrice;
import com.test.bean.product.Product;
import com.test.repo.AttributePriceRepo;
import com.test.repo.ProductRepo;
import com.test.utility.GlobalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/price-attribute")
public class AttributePriceController {

    @Autowired
    private AttributePriceRepo priceRepo;
    @Autowired
    private GlobalService service;

    @Autowired
    private ProductRepo productRepo;

    @GetMapping("/{id}")
    public ResponseEntity getById(@PathVariable Integer id){
        if(id == null){
            return service.getErrorResponse("Invalid request!");
        }
        if(!priceRepo.existsById(id)){
            return service.getErrorResponse("Product not found!");
        }
        AttributePrice price = priceRepo.getOne(id);

        return service.getSuccessResponse(price);
    }


    @PostMapping("/update")
    public ResponseEntity updatePrice(@RequestBody List<AttributePrice> priceList){

        List<Double> priceArray = new ArrayList<>();
        List<Double> priceArrayCad = new ArrayList<>();
        List<Double> priceArrayEuro = new ArrayList<>();
        boolean discount = false;
        if(priceList.size() > 0) {

            for (AttributePrice price: priceList) {
                if (price.getId() == null) {
                    return service.getErrorResponse("Invalid request!");
                }
                if (priceRepo.existsById(price.getId())) {
                AttributePrice attributePrice = priceRepo.getOne(price.getId());
                if(price.getDiscount() != null && price.getDiscount() != false){
                    discount = true;
                    if(price.getPrice() != null && price.getDiscountPrice() != null){
                            attributePrice.setDiscountPrice(price.getDiscountPrice());
                            attributePrice.setPrice(price.getPrice());
                            attributePrice.setCadPrice(price.getCadPrice());
                            attributePrice.setCadDiscountPrice(price.getCadDiscountPrice());
                            attributePrice.setEuroPrice(price.getEuroPrice());
                            attributePrice.setEuroDiscountPrice(price.getEuroDiscountPrice());
                            attributePrice.setDiscount(true);
                            priceRepo.save(attributePrice);
                            priceArray.add(price.getDiscountPrice());
                            priceArrayCad.add(price.getCadDiscountPrice());
                            priceArrayEuro.add(price.getEuroDiscountPrice());
                    }
                }else{
                    if(price.getPrice() != null){
                        attributePrice.setPrice(price.getPrice());
                        attributePrice.setCadPrice(price.getCadPrice());
                        attributePrice.setEuroPrice(price.getEuroPrice());
                        priceRepo.save(attributePrice);
                        priceArray.add(price.getPrice());
                        priceArrayCad.add(price.getCadPrice());
                        priceArrayEuro.add(price.getEuroPrice());
                    }
                }


                }
            }
//            setting product active

            Product pro = productRepo.getOne(priceList.get(0).getProductId());
            pro.setPriceRange("" + Collections.min(priceArray) + " - " + Collections.max(priceArray));
            pro.setPriceRangeCad("" + Collections.min(priceArrayCad) + " - " + Collections.max(priceArrayCad));
            pro.setPriceRangeEuro("" + Collections.min(priceArrayEuro) + " - " + Collections.max(priceArrayEuro));
            pro.setPriceSet(true);
            pro.setDiscount(discount);
            productRepo.save(pro);

        }
        return service.getSuccessResponse(priceList);
    }

    @GetMapping("/product/{id}")
    public ResponseEntity getListOfAttributeByProductId(@PathVariable Integer id){
        if(id == null){
            return service.getErrorResponse("Invalid request!");
        }
        List<AttributePrice> list = priceRepo.findByProductId(id);
        if(list.size() == 0){
            return service.getErrorResponse("Attribute not found!");
        }


        return service.getSuccessResponse(list);
    }

    @PostMapping("/price")
    public ResponseEntity getAttributePrice(@RequestBody AttributePriced obj){

        if(obj.getProductId() == null){
            return service.getErrorResponse("Invalid request!");
        }
        AttributeResponse price = new AttributeResponse();
        if(obj.getList().size() > 0){
            int size = obj.getList().size();
            List< AttributePrice> list = priceRepo.findByProductId(obj.getProductId());
            price =  returnPrice(size , obj.getList() ,list );

        }
        if( price == null ){
        return service.getErrorResponse("Invalid Selection!");
        }
        return service.getSuccessResponse(price);
    }

    public AttributeResponse returnPrice(int count , List<String> list, List<AttributePrice> dbList){
        AttributeResponse price = new AttributeResponse();
        if(count == 1){
            for (AttributePrice i: dbList){
                for (String j: list) {
                    if(i.getAttribute_1().equals(j)){
                        if(i.getDiscount()){
                            price.setPriceId(i.getId());
                            price.setPrice(i.getDiscountPrice());
                            price.setCadPrice(i.getCadDiscountPrice());
                            price.setEuroPrice(i.getEuroDiscountPrice());
                        }else{
                            price.setCadPrice(i.getCadPrice());
                            price.setEuroPrice(i.getEuroPrice());
                            price.setPriceId(i.getId());
                            price.setPrice(i.getPrice());
                        }
                    }
                }
            }
        }else if(count == 2){
            for (AttributePrice i: dbList){
                if(i.getAttribute_1().equals(list.get(0)) && i.getAttribute_2().equals(list.get(1)) ||
                        i.getAttribute_1().equals(list.get(1)) && i.getAttribute_2().equals(list.get(0))  ){
                    if(i.getDiscount()){
                        price.setPriceId(i.getId());
                        price.setPrice(i.getDiscountPrice());
                        price.setCadPrice(i.getCadDiscountPrice());
                        price.setEuroPrice(i.getEuroDiscountPrice());
                    }else{
                        price.setCadPrice(i.getCadPrice());
                        price.setEuroPrice(i.getEuroPrice());
                        price.setPriceId(i.getId());
                        price.setPrice(i.getPrice());
                    }
                }
            }
        }
        else if(count == 3){
            for (AttributePrice i: dbList){
                if(i.getAttribute_1().equals(list.get(0)) && i.getAttribute_2().equals(list.get(1)) && i.getAttribute_3().equals(list.get(2))  ){
                    if(i.getDiscount()){
                        price.setPriceId(i.getId());
                        price.setPrice(i.getDiscountPrice());
                        price.setCadPrice(i.getCadDiscountPrice());
                        price.setEuroPrice(i.getEuroDiscountPrice());
                    }else{
                        price.setCadPrice(i.getCadPrice());
                        price.setEuroPrice(i.getEuroPrice());
                        price.setPriceId(i.getId());
                        price.setPrice(i.getPrice());
                    }
                }
            }
        }
        else if(count == 4){
            for (AttributePrice i: dbList){
                if(i.getAttribute_1().equals(list.get(0)) && i.getAttribute_2().equals(list.get(1)) && i.getAttribute_3().equals(list.get(2))  && i.getAttribute_4().equals(list.get(23)) ){
                    if(i.getDiscount()){
                        price.setPriceId(i.getId());
                        price.setPrice(i.getDiscountPrice());
                        price.setCadPrice(i.getCadDiscountPrice());
                        price.setEuroPrice(i.getEuroDiscountPrice());

                    }else{
                        price.setCadPrice(i.getCadPrice());
                        price.setEuroPrice(i.getEuroPrice());
                        price.setPriceId(i.getId());
                        price.setPrice(i.getPrice());
                    }
                }
            }
        }
        return price;
    }

}

class AttributePriced{
    private Integer productId;
    private List<String> list;


    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public List<String> getList() {
        return list;
    }

    public void setList(List<String> list) {
        this.list = list;
    }
}
class AttributeResponse{

    private Integer priceId;
    private Double price;
    private Double cadPrice;
    private Double euroPrice;


    public Double getCadPrice() {
        return cadPrice;
    }

    public void setCadPrice(Double cadPrice) {
        this.cadPrice = cadPrice;
    }

    public Double getEuroPrice() {
        return euroPrice;
    }

    public void setEuroPrice(Double euroPrice) {
        this.euroPrice = euroPrice;
    }

    public Integer getPriceId() {
        return priceId;
    }

    public void setPriceId(Integer priceId) {
        this.priceId = priceId;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
