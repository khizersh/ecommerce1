package com.test.bean.product;


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



    //    @Lob
    @Column(name = "picByte", length = 1000)
    private byte[] picByte;




    public ImageModel() {
        super();
    }
    public ImageModel( String name, String type, byte[] picByte ) {

        this.name = name;
        this.type = type;
        this.picByte = picByte;
    }

//   getter setter\


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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public byte[] getPicByte() {
        return picByte;
    }

    public void setPicByte(byte[] picByte) {
        this.picByte = picByte;
    }


}
