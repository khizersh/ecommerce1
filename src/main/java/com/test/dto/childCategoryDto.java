package com.test.dto;

import com.test.bean.ChildAttribute;

import java.util.ArrayList;
import java.util.List;


public class childCategoryDto {

    private Integer id;

    private String title;
    private Boolean active;
    private Integer parentCategoryId;
    private String parentCategoryTitle;

    private List<ParentAttributeDto> attributeList = new ArrayList<>();


//    getter setter


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getParentCategoryId() {
        return parentCategoryId;
    }

    public void setParentCategoryId(Integer parentCategoryId) {
        this.parentCategoryId = parentCategoryId;
    }

    public String getParentCategoryTitle() {
        return parentCategoryTitle;
    }

    public void setParentCategoryTitle(String parentCategoryTitle) {
        this.parentCategoryTitle = parentCategoryTitle;
    }

    public List<ParentAttributeDto> getAttributeList() {
        return attributeList;
    }

    public void setAttributeList(List<ParentAttributeDto> attributeList) {
        this.attributeList = attributeList;
    }
}
