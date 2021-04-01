package com.test.bean.product;

import javax.persistence.*;

@Entity
public class AttributePrice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String attribute_1;

    private String attribute_2;

    private String attribute_3;

    private String attribute_4;

    private Double price;

    private Double discountPrice;

    private Boolean discount = false;

    private Integer productId;

    private String productName;


//    getter setter


    public Double getDiscountPrice() {
        return discountPrice;
    }

    public void setDiscountPrice(Double discountPrice) {
        this.discountPrice = discountPrice;
    }

    public Boolean getDiscount() {
        return discount;
    }

    public void setDiscount(Boolean discount) {
        this.discount = discount;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAttribute_1() {
        return attribute_1;
    }

    public void setAttribute_1(String attribute_1) {
        this.attribute_1 = attribute_1;
    }

    public String getAttribute_2() {
        return attribute_2;
    }

    public void setAttribute_2(String attribute_2) {
        this.attribute_2 = attribute_2;
    }

    public String getAttribute_3() {
        return attribute_3;
    }

    public void setAttribute_3(String attribute_3) {
        this.attribute_3 = attribute_3;
    }

    public String getAttribute_4() {
        return attribute_4;
    }

    public void setAttribute_4(String attribute_4) {
        this.attribute_4 = attribute_4;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

}
