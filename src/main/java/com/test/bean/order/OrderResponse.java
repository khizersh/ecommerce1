package com.test.bean.order;

import com.test.bean.checkout.Status;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.Date;

public class OrderResponse {

    private Integer orderId;
    private Integer checkoutId;
    private Long userId;
    private String email;
    private String fullName;
    private String country;
    private String state;
    private String city;
    private String addressLine1;
    private String addressLine2;
    private String postalCode;
    private String phoneNo;
    private String suggestion;
    @Enumerated(EnumType.STRING)
    private Status orderStatus;
    @Enumerated(EnumType.STRING)
    private Status shipStatus;
    private Double couponAmount;
    private Double totalAmount;
    private Double netAmount;
    private Double expidetAmount;
    private Date orderDate;

    public Status getShipStatus() {
        return shipStatus;
    }

    public void setShipStatus(Status shipStatus) {
        this.shipStatus = shipStatus;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getCheckoutId() {
        return checkoutId;
    }

    public void setCheckoutId(Integer checkoutId) {
        this.checkoutId = checkoutId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddressLine1() {
        return addressLine1;
    }

    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getSuggestion() {
        return suggestion;
    }

    public void setSuggestion(String suggestion) {
        this.suggestion = suggestion;
    }

    public Status getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Status orderStatus) {
        this.orderStatus = orderStatus;
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

    public Double getExpidetAmount() {
        return expidetAmount;
    }

    public void setExpidetAmount(Double expidetAmount) {
        this.expidetAmount = expidetAmount;
    }
}
