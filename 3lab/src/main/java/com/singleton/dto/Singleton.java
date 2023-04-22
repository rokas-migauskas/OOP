package com.singleton.dto;

import com.singleton.data.Triangle;

public class Singleton {
    private static Singleton instance;
    private Triangle triangle;

    private Singleton(){

    }
    public static Singleton getInstance() {
        if (instance == null) {
            instance = new Singleton();
        }
        return instance;
    }
    public Triangle getTriangle() {
        return triangle;
    }

    public void setTriangle(Triangle triangle) {
        this.triangle = triangle;
    }
}
