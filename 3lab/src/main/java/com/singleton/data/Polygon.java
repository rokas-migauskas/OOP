package com.singleton.data;

public class Polygon {
    protected int sideNumber;

    protected double sideLength;

    protected Polygon(int sideNumber, double sideLength){
        this.sideLength = sideLength;
        this.sideNumber = sideNumber;
    }
    Polygon(){

    }


    public double getPerimeter () {
        return sideNumber*sideLength;
    }
    public String getInfo() {
        return "Side number: " + sideNumber + " Side length: " + sideLength ;
    }
    public double getSideNumber() {
        return sideNumber;
    }

    public double getSideLength() {
        return sideLength;
    }

    public void setSideNumber(int sideNumber) {
        this.sideNumber = sideNumber;
    }

    public void setSideLength(double sideLength) {
        this.sideLength = sideLength;
    }
}
