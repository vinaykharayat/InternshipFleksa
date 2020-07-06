package com.androboot.internshipwork.Objects;

public class Order {
    private String orderAddress, orderStatus, orderId, customerName, customerAddress, phoneNumber, orderTime;

    public Order() {
    }

    public Order(String orderAddress, String orderStatus, String orderId, String customerName, String customerAddress, String phoneNumber) {
        this.orderAddress = orderAddress;
        this.orderStatus = orderStatus;
        this.orderId = orderId;
        this.customerName = customerName;
        this.customerAddress = customerAddress;
        this.phoneNumber = phoneNumber;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerAddress() {
        return customerAddress;
    }

    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    public void setOrderAddress(String orderAddress) {
        this.orderAddress = orderAddress;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }


    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderAddress() {
        return orderAddress;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public String getOrderId() {
        return orderId;
    }

}
