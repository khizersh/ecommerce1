package com.test.dto;

import com.test.bean.product.ImageModel;

public class ChildCat {

    private Integer id;
    private String childTitle;
    private ImageModel image;
    private ImageModel banner;
    private Boolean active;

    public ImageModel getImage() {
        return image;
    }

    public void setImage(ImageModel image) {
        this.image = image;
    }

    public ImageModel getBanner() {
        return banner;
    }

    public void setBanner(ImageModel banner) {
        this.banner = banner;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getChildTitle() {
        return childTitle;
    }

    public void setChildTitle(String childTitle) {
        this.childTitle = childTitle;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}
