package com.test.dto;

import com.test.bean.product.Points;
import com.test.utility.ImageURl;
import com.test.utility.Gender;

import java.util.ArrayList;
import java.util.*;

public class ProductDto {

    private Integer id;
    private String title;
    private Boolean priceSet;
    private String description;
    private Integer categoryId;
    private String categoryName;
    private Integer sequence;
    private String range;
    private String rangeCad;
    private String rangeEuro;
    private Double review;
    private Integer reviewCount;
    private String keywords;
    private Boolean discount;
    private Gender gender;
    private  List<ImageURl> imageList = new ArrayList<>();
    private  List<ParentAttributeDto> attributeList = new ArrayList<>();
    private  List<Points> bulletList = new ArrayList<>();

//    getter setter


    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Boolean getDiscount() {
        return discount;
    }

    public void setDiscount(Boolean discount) {
        this.discount = discount;
    }

    public String getRangeCad() {
        return rangeCad;
    }

    public void setRangeCad(String rangeCad) {
        this.rangeCad = rangeCad;
    }

    public String getRangeEuro() {
        return rangeEuro;
    }

    public void setRangeEuro(String rangeEuro) {
        this.rangeEuro = rangeEuro;
    }

    public Double getReview() {
        return review;
    }

    public void setReview(Double review) {
        this.review = review;
    }

    public Integer getReviewCount() {
        return reviewCount;
    }

    public void setReviewCount(Integer reviewCount) {
        this.reviewCount = reviewCount;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public List<Points> getBulletList() {
        return bulletList;
    }

    public void setBulletList(List<Points> bulletList) {
        this.bulletList = bulletList;
    }

    public ProductDto() {
    }

    public String getRange() {
        return range;
    }

    public void setRange(String range) {
        this.range = range;
    }

    public Integer getSequence() {
        return sequence;
    }

    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }

    public List<ParentAttributeDto> getAttributeList() {
        return attributeList;
    }

    public void setAttributeList(List<ParentAttributeDto> attributeList) {
        this.attributeList = attributeList;
    }

    public Boolean getPriceSet() {
        return priceSet;
    }

    public void setPriceSet(Boolean priceSet) {
        this.priceSet = priceSet;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }


    public List<ImageURl> getImageList() {
        return imageList;
    }

    public void setImageList(List<ImageURl> imageList) {
        this.imageList = imageList;
    }
}
