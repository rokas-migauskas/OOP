package com.game;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Target {
    int row;
    int col;
    private Rectangle rectangle;

    public Target(int row, int col) {
        this.row = row;
        this.col = col;

        rectangle = new Rectangle(50, 50, Color.GREEN);
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