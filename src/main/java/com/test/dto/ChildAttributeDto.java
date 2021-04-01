package com.test.dto;


import com.test.bean.product.ImageModel;

import java.util.*;

public class ChildAttributeDto {

    private Integer id;
    private String title;
    private List<ImageModel> attributeImage = new ArrayList<>();

//    getter setter

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

    public List<ImageModel> getAttributeImage() {
        return attributeImage;
    }

    public void setAttributeImage(List<ImageModel> attributeImage) {
        this.attributeImage = attributeImage;
    }
}
