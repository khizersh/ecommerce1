package com.test.bean.product;

import com.test.bean.category.ChildCategory;
import com.test.bean.product_attribute.ProductAttribute;
import com.test.utility.ImageURl;

import javax.persistence.*;
import java.util.*;

@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String title;
    private String description;
    private Boolean priceSet = false;
    private String priceRange;
    private String priceRangeCad;
    private String priceRangeEuro;
    private String keywords;
    private Double review;
    private Integer reviewCount;
    @ManyToOne
    private ChildCategory category;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "product_id")
    private List<ProductAttribute> attributeList = new ArrayList<>();
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "product_id")
    private List<ImageURl> imageList = new ArrayList<>();

    @Transient
    private Integer categoryId;
    @Transient
    private List<String> points;

//    getter setter


    public String getPriceRangeCad() {
        return priceRangeCad;
    }

    public void setPriceRangeCad(String priceRangeCad) {
        this.priceRangeCad = priceRangeCad;
    }

    public String getPriceRangeEuro() {
        return priceRangeEuro;
    }

    public void setPriceRangeEuro(String priceRangeEuro) {
        this.priceRangeEuro = priceRangeEuro;
    }

    public Double getReview() {
        return review;
    }

    public void setReview(Double review) {
        this.review = review;
    }

    public Integer getReviewCount() {
        return reviewCount;
    }

    public void setReviewCount(Integer reviewCount) {
        this.reviewCount = reviewCount;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public List<String> getPoints() {
        return points;
    }

    public void setPoints(List<String> points) {
        this.points = points;
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

    public Boolean getPriceSet() {
        return priceSet;
    }

    public void setPriceSet(Boolean priceSet) {
        this.priceSet = priceSet;
    }

    public String getPriceRange() {
        return priceRange;
    }

    public void setPriceRange(String priceRange) {
        this.priceRange = priceRange;
    }

    public ChildCategory getCategory() {
        return category;
    }

    public void setCategory(ChildCategory category) {
        this.category = category;
    }

    public List<ProductAttribute> getAttributeList() {
        return attributeList;
    }

    public void setAttributeList(List<ProductAttribute> attributeList) {
        this.attributeList = attributeList;
    }

    public List<ImageURl> getImageList() {
        return imageList;
    }

    public void setImageList(List<ImageURl> imageList) {
        this.imageList = imageList;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", category=" + category +
                ", imageList=" + imageList +
                ", categoryId=" + categoryId +
                '}';
    }
}
