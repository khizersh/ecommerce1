package com.test.dto;


import com.test.bean.product.ImageModel;

public class childCategoryDto {

    private Integer id;

    private String title;
    private Boolean active;
    private Integer parentCategoryId;
    private String parentCategoryTitle;
    private String image;
    private String banner;




//    getter setter


    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getBanner() {
        return banner;
    }

    public void setBanner(String banner) {
        this.banner = banner;
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


}
