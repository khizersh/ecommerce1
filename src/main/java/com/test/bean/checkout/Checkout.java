package com.test.bean.checkout;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Checkout {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Boolean coupon;
    private Integer couponId;
    private Double couponAmount;
    private Double totalAmount;
    private Double netAmount;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "checkout_id")
    private List<CheckoutDetail> productList = new ArrayList<>();

//getter setter
    public List<CheckoutDetail> getProductList() {
        return productList;
    }

    public void setProductList(List<CheckoutDetail> productList) {
        this.productList = productList;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Boolean getCoupon() {
        return coupon;
    }

    public void setCoupon(Boolean coupon) {
        this.coupon = coupon;
    }

    public Integer getCouponId() {
        return couponId;
    }

    public void setCouponId(Integer couponId) {
        this.couponId = couponId;
    }

    public Double getCouponAmount() {
        return couponAmount;
    }

    public void setCouponAmount(Double couponAmount) {
        this.couponAmount = couponAmount;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Double getNetAmount() {
        return netAmount;
    }

    public void setNetAmount(Double netAmount) {
        this.netAmount = netAmount;
    }
}
