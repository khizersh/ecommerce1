package com.test.bean.attribute;

import javax.persistence.*;

@Entity
public class ChildAttribute {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String title;

    @ManyToOne
    private ParentAttributes parentAttributes;


    @Transient
    private Integer parentId;



//    getter setter

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ParentAttributes getParentAttributes() {
        return parentAttributes;
    }

    public void setParentAttributes(ParentAttributes parentAttributes) {
        this.parentAttributes = parentAttributes;
    }
}
