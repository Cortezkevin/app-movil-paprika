package com.example.paprika.Model;

public class Supplier {

    private String id_supplier;
    private String name;
    private String address;
    private Integer phone;
    private String state;

    public Supplier() {
    }

    public Supplier(String id_supplier, String name, String address, Integer phone, String state) {
        this.id_supplier = id_supplier;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.state = state;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getPhone() {
        return phone;
    }

    public void setPhone(Integer phone) {
        this.phone = phone;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return name;
    }
}
