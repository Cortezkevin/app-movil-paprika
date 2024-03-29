package com.example.paprika.Model;

import java.util.Date;

public class ProductCar {

    private String id_product;
    private String id_category;
    private String id_supplier;
    private String name;
    private String mark;
    private String description;
    private String url_image;
    private String expiration_date;
    private Double price;
    private Integer stock;
    private String state;
    private Integer amount;


    public ProductCar() {
    }

    public ProductCar(Product p) {
        this(p.getId_product(), p.getId_category(), p.getId_supplier(), p.getName(), p.getMark(), p.getDescription(), p.getUrl_image()
                , p.getExpiration_date(), p.getPrice(), p.getStock(), p.getState(), 1);
    }


    public ProductCar(String id_product, String id_category, String id_supplier, String name, String mark, String description, String url_image, String expiration_date, Double price, Integer stock, String state, Integer amount) {
        this.id_product = id_product;
        this.id_category = id_category;
        this.id_supplier = id_supplier;
        this.name = name;
        this.mark = mark;
        this.description = description;
        this.url_image = url_image;
        this.expiration_date = expiration_date;
        this.price = price;
        this.stock = stock;
        this.state = state;
        this.amount = amount;
    }

    public String getId_product() {
        return id_product;
    }

    public void setId_product(String id_product) {
        this.id_product = id_product;
    }

    public String getId_category() {
        return id_category;
    }

    public void setId_category(String id_category) {
        this.id_category = id_category;
    }

    public String getId_supplier() {
        return id_supplier;
    }

    public void setId_supplier(String id_supplier) {
        this.id_supplier = id_supplier;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl_image() {
        return url_image;
    }

    public void setUrl_image(String url_image) {
        this.url_image = url_image;
    }

    public String getExpiration_date() {
        return expiration_date;
    }

    public void setExpiration_date(String expiration_date) {
        this.expiration_date = expiration_date;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }
}
