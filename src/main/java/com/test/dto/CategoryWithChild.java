package com.test.dto;

import com.test.bean.category.ChildCategory;
import java.util.*;

public class CategoryWithChild {

    private Integer id;
    private String title;
    private Boolean active;
    private List<ChildCat> childList = new ArrayList<>();

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

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public List<ChildCat> getChildList() {
        return childList;
    }

    public void setChildList(List<ChildCat> childList) {
        this.childList = childList;
    }
}
