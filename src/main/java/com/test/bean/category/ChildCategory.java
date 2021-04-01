package com.test.bean.category;

import javax.persistence.*;


@Entity
public class ChildCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String categoryName;


    private Boolean isActive;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private ParentCategory parentCategory;



//    @OneToMany(cascade = CascadeType.ALL)
//    @JoinColumn(name = "parent_attribute")
//    private List<ChildCategoryAttribute> attributeList = new ArrayList<>();


    @Transient
    private Integer parentId;


   //getter setter

//
//    public List<ChildCategoryAttribute> getAttributeList() {
//        return attributeList;
//    }
//
//    public void setAttributeList(List<ChildCategoryAttribute> attributeList) {
//        this.attributeList = attributeList;
//    }

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
