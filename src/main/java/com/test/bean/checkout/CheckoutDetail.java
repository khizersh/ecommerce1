package com.test.bean.checkout;

import com.test.bean.product.AttributePrice;

import javax.persistence.*;

@Entity
public class CheckoutDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer productId;
    private String productTitle;
    private String productImage;
    private Integer quantity;
    private Double price;
    @ManyToOne
    private AttributePrice attributePrice;

    @Transient
    private Integer priceId;
//    getter setter


    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public AttributePrice getAttributePrice() {
        return attributePrice;
    }

    public void setAttributePrice(AttributePrice attributePrice) {
        this.attributePrice = attributePrice;
    }

    public Integer getPriceId() {
        return priceId;
    }

    public void setPriceId(Integer priceId) {
        this.priceId = priceId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getProductTitle() {
        return productTitle;
    }

    public void setProductTitle(String productTitle) {
        this.productTitle = productTitle;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }


}
