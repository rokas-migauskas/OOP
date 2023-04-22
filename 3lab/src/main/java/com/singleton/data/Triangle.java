package com.singleton.data;

public class Triangle extends Polygon {
    private double height;

    private double area;

    public Triangle() {
        super.sideNumber = 3;
    }
    public void calculateHeight() {
        height = Math.sqrt(Math.pow(sideLength, 2) - Math.pow(sideLength / 2, 2));
        this.height = height;
    }
    public void calculateArea() {
        area = height*sideLength/2;
        this.area = area;
    }

    public double getHeight() {
        calculateHeight();
        return height;
    }


    public double getArea() {
        calculateHeight();
        calculateArea();
        return area;
    }


}
