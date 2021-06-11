package com.test.bean.checkout;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class Checkout {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Long userId;
    private Boolean coupon;
    private Integer couponId;
    private Double couponAmount;
    private String couponTitle;
    private Date orderDate;
    private Date shipmentDate;
    private Double totalAmount;
    private Double netAmount;
    private Double expidetAmount;
    private Boolean expidet;
    @Enumerated(EnumType.ORDINAL)
    private Status status;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "checkout_id")
    private List<CheckoutDetail> productList = new ArrayList<>();

//getter setter


    public Double getExpidetAmount() {
        return expidetAmount;
    }

    public void setExpidetAmount(Double expidetAmount) {
        this.expidetAmount = expidetAmount;
    }

    public Boolean getExpidet() {
        return expidet;
    }

    public void setExpidet(Boolean expidet) {
        this.expidet = expidet;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getCouponTitle() {
        return couponTitle;
    }

    public void setCouponTitle(String couponTitle) {
        this.couponTitle = couponTitle;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public Date getShipmentDate() {
        return shipmentDate;
    }

    public void setShipmentDate(Date shipmentDate) {
        this.shipmentDate = shipmentDate;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

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
