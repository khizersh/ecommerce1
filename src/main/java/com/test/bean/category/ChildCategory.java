package com.test.bean.category;

import com.test.bean.product.ImageModel;

import javax.persistence.*;


@Entity
public class ChildCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String categoryName;


    private Boolean isActive;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "parent_id")
    private ParentCategory parentCategory;


    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "image")
    private ImageModel image;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "banner")
    private ImageModel banner;

    @Transient
    private Integer parentId;


   //getter setter


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

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public ParentCategory getParentCategory() {
        return parentCategory;
    }

    public void setParentCategory(ParentCategory parentCategory) {
        this.parentCategory = parentCategory;
    }
}
