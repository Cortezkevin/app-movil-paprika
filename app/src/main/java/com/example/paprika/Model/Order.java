package com.example.paprika.Model;

import java.util.Date;

public class Order {

    private String id_order;
    private String id_user;
    private String id_payment;
    private String user;
    private Date order_date;
    private Double total;
    private String state;

    public Order() {
    }

    public Order(String id_order, String id_user, String id_payment, String user, Date order_date, Double total, String state) {
        this.id_order = id_order;
        this.id_user = id_user;
        this.id_payment = id_payment;
        this.user = user;
        this.order_date = order_date;
        this.total = total;
        this.state = state;
    }

    public String getId_order() {
        return id_order;
    }

    public void setId_order(String id_order) {
        this.id_order = id_order;
    }

    public String getId_user() {
        return id_user;
    }

    public void setId_user(String id_user) {
        this.id_user = id_user;
    }

    public String getId_payment() {
        return id_payment;
    }

    public void setId_payment(String id_payment) {
        this.id_payment = id_payment;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public Date getOrder_date() {
        return order_date;
    }

    public void setOrder_date(Date order_date) {
        this.order_date = order_date;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
