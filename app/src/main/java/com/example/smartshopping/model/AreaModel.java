package com.example.smartshopping.model;

public class AreaModel {
    private int id;
    private char area;

    public AreaModel(int id, char area) {
        this.id = id;
        this.area = area;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public char getArea() {
        return area;
    }

    public void setArea(char area) {
        this.area = area;
    }
}
