package com.example.paprika.Model;

public class Category {

    private String id_category;
    private String name;
    private String description;
    private String url_image;
    private String state;

    public Category() {
    }

    public Category(String id_category, String name, String description, String url_image, String state) {
        this.id_category = id_category;
        this.name = name;
        this.description = description;
        this.url_image = url_image;
        this.state = state;
    }

    public String getId_category() {
        return id_category;
    }

    public void setId_category(String id_category) {
        this.id_category = id_category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
