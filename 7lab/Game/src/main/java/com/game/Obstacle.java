package com.game;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Obstacle {
    int row;
    int col;
    private Rectangle rectangle;

    public Obstacle(int row, int col) {
        this.row = row;
        this.col = col;

        rectangle = new Rectangle(50, 50, Color.RED);
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public Rectangle getRectangle() {
        return rectangle;
    }
}