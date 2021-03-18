package com.test.dto;

import com.test.bean.ImageModel;

import java.util.ArrayList;
import java.util.*;

public class ProductDto {

    private Integer id;
    private String title;
    private Boolean priceSet;
    private String description;
    private Integer categoryId;
    private String categoryName;
    private childCategoryDto categoryDto;
    private  List<ImageModel> imageList = new ArrayList<>();

//    getter setter


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

    public childCategoryDto getCategoryDto() {
        return categoryDto;
    }

    public void setCategoryDto(childCategoryDto categoryDto) {
        this.categoryDto = categoryDto;
    }

    public List<ImageModel> getImageList() {
        return imageList;
    }

    public void setImageList(List<ImageModel> imageList) {
        this.imageList = imageList;
    }
}
