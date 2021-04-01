package com.test.bean.category;

import com.test.bean.attribute.ParentAttributes;

import javax.persistence.*;

@Entity
public class ChildCategoryAttribute {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    private ParentAttributes parentAttributes;

    @Transient
    private Integer parentAttributeId;

//    gette setter

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public ParentAttributes getParentAttributes() {
        return parentAttributes;
    }

    public void setParentAttributes(ParentAttributes parentAttributes) {
        this.parentAttributes = parentAttributes;
    }

    public Integer getParentAttributeId() {
        return parentAttributeId;
    }

    public void setParentAttributeId(Integer parentAttributeId) {
        this.parentAttributeId = parentAttributeId;
    }
}
