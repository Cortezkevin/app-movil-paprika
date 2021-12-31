package com.example.paprika.Model;

public class OrderDetails {

    private String id_order_detail;
    private String id_order;
    private String id_product;
    private Double unit_price;
    private Integer amount;
    private Double subtotal;
    private Double discount;
    private Double total;

    public OrderDetails() {
    }

    public OrderDetails(String id_order_detail, String id_order, String id_product, Double unit_price, Integer amount, Double subtotal, Double discount, Double total) {
        this.id_order_detail = id_order_detail;
        this.id_order = id_order;
        this.id_product = id_product;
        this.unit_price = unit_price;
        this.amount = amount;
        this.subtotal = subtotal;
        this.discount = discount;
        this.total = total;
    }

    public String getId_order_detail() {
        return id_order_detail;
    }

    public void setId_order_detail(String id_order_detail) {
        this.id_order_detail = id_order_detail;
    }

    public String getId_order() {
        return id_order;
    }

    public void setId_order(String id_order) {
        this.id_order = id_order;
    }

    public String getId_product() {
        return id_product;
    }

    public void setId_product(String id_product) {
        this.id_product = id_product;
    }

    public Double getUnit_price() {
        return unit_price;
    }

    public void setUnit_price(Double unit_price) {
        this.unit_price = unit_price;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(Double subtotal) {
        this.subtotal = subtotal;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }
}
