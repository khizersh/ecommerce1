package com.test.dto;


import com.test.bean.product.AttributeImages;
import com.test.bean.product.ImageModel;
import com.test.utility.ImageURl;

import java.util.*;

public class ChildAttributeDto {

    private Integer id;
    private String title;
    private Integer parentId;
    private List<String> attributeImage = new ArrayList<>();
    private List<AttributeImages> attributeImageFull = new ArrayList<>();

//    getter setter


    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public List<AttributeImages> getAttributeImageFull() {
        return attributeImageFull;
    }

    public void setAttributeImageFull(List<AttributeImages> attributeImageFull) {
        this.attributeImageFull = attributeImageFull;
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

    public List<String> getAttributeImage() {
        return attributeImage;
    }

    public void setAttributeImage(List<String> attributeImage) {
        this.attributeImage = attributeImage;
    }
}
