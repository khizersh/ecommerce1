package com.test.dto;

import com.test.bean.ChildAttribute;
import java.util.*;

public class ParentAttributeDto {

    private Integer id;

    private String parentTitle;

    private boolean active;

    private List<ChildAttributeDto> childAttributeList = new ArrayList<>();

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

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public List<ChildAttributeDto> getChildAttributeList() {
        return childAttributeList;
    }

    public void setChildAttributeList(List<ChildAttributeDto> childAttributeList) {
        this.childAttributeList = childAttributeList;
    }
}
