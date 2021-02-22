package com.test.bean;


import javax.persistence.*;

@Entity
@Table(name = "image_table")
public class ImageModel {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String type;
    private String title;

    @Column(name = "description", length = 1000)
    private String description;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private ChildCategory category;

    //    @Lob
    @Column(name = "picByte", length = 1000)
    private byte[] picByte;


    @Transient
    private Integer categoryId;


    public ImageModel() {
        super();
    }
    public ImageModel( String name, String type, byte[] picByte , ChildCategory categoryId , String title , String desc) {
        this.title = title;
        this.description = desc;
        this.category = categoryId;
        this.name = name;
        this.type = type;
        this.picByte = picByte;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
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

    public ChildCategory getCategory() {
        return category;
    }

    public void setCategory(ChildCategory category) {
        this.category = category;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }




    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getPicByte() {
        return picByte;
    }

    public void setPicByte(byte[] picByte) {
        this.picByte = picByte;
    }


}
