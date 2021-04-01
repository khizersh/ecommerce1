package com.test.bean.product_attribute;
import javax.persistence.*;
import java.util.*;

@Entity
public class ProductAttribute {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer parentAttributeId;
    private String parentAttributeName;
    private Boolean multiImage;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn
    private List<ProductSubAttribute> subAttributeList = new ArrayList<>();

//    getter setter


    public Boolean getMultiImage() {
        return multiImage;
    }

    public void setMultiImage(Boolean multiImage) {
        this.multiImage = multiImage;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getParentAttributeId() {
        return parentAttributeId;
    }

    public void setParentAttributeId(Integer parentAttributeId) {
        this.parentAttributeId = parentAttributeId;
    }

    public String getParentAttributeName() {
        return parentAttributeName;
    }

    public void setParentAttributeName(String parentAttributeName) {
        this.parentAttributeName = parentAttributeName;
    }

    public List<ProductSubAttribute> getSubAttributeList() {
        return subAttributeList;
    }

    public void setSubAttributeList(List<ProductSubAttribute> subAttributeList) {
        this.subAttributeList = subAttributeList;
    }
}
