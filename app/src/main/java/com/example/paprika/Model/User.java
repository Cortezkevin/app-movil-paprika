package com.example.paprika.Model;

public class User {

    private String id_user;
    private String id_rol;
    private String name;
    private String email;
    private String password;
    private String image;
    private String state;

    public User() {
    }

    public User(String id_user, String id_rol, String name, String email, String password, String image, String state) {
        this.id_user = id_user;
        this.id_rol = id_rol;
        this.name = name;
        this.email = email;
        this.password = password;
        this.image = image;
        this.state = state;
    }

    public String getId_user() {
        return id_user;
    }

    public void setId_user(String id_user) {
        this.id_user = id_user;
    }

    public String getId_rol() {
        return id_rol;
    }

    public void setId_rol(String id_rol) {
        this.id_rol = id_rol;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}


