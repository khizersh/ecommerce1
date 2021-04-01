package com.test.dto;

import java.util.*;

public class ParentAttributeDto {

    private Integer id;
    private String parentTitle;
    private  Boolean multi;
    private List<ChildAttributeDto> childAttributeList = new ArrayList<>();


//    getter setter


    public Boolean getMulti() {
        return multi;
    }

    public void setMulti(Boolean multi) {
        this.multi = multi;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getParentTitle() {
        return parentTitle;
    }

    public void setParentTitle(String parentTitle) {
        this.parentTitle = parentTitle;
    }


    public List<ChildAttributeDto> getChildAttributeList() {
        return childAttributeList;
    }

    public void setChildAttributeList(List<ChildAttributeDto> childAttributeList) {
        this.childAttributeList = childAttributeList;
    }
}
