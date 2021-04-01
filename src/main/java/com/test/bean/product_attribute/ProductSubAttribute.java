package com.test.bean.product_attribute;

import com.test.bean.product.AttributeImages;
import com.test.bean.product.ImageModel;

import javax.persistence.*;
import java.util.*;

@Entity
public class ProductSubAttribute {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer childAttributeId;
    private String childAttributeName;



//    getter setter




    public Integer getId() {
        return id;
    }




    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getChildAttributeId() {
        return childAttributeId;
    }

    public void setChildAttributeId(Integer childAttributeId) {
        this.childAttributeId = childAttributeId;
    }

    public String getChildAttributeName() {
        return childAttributeName;
    }

    public void setChildAttributeName(String childAttributeName) {
        this.childAttributeName = childAttributeName;
    }
}
