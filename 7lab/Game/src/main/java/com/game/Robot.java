package com.game;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Robot {
    int row;
    int col;
    private Circle circle;

    public Robot(int row, int col) {
        this.row = row;
        this.col = col;

        circle = new Circle(25, Color.BLUE);
    }

    public void moveUp() {
        if (row > 0) {
            row--;
        }
    }

    public void moveDown() {
        if (row < 9) {
            row++;
        }
    }

    public void moveLeft() {
        if (col > 0) {
            col--;
        }
    }

    public void moveRight() {
        if (col < 9) {
            col++;
        }
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public Circle getCircle() {
        return circle;
    }
}
