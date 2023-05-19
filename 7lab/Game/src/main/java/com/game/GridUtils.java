package com.game;

import javafx.scene.Node;
import javafx.scene.layout.Background;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Shape;

public class GridUtils {

    public static GridPane createGridPane(int gridSize, int cellSize) {
        GridPane gridPane = new GridPane();

        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                StackPane cell = new StackPane();
                cell.setStyle("-fx-border-color: black;");
                cell.setMinSize(cellSize, cellSize);
                cell.setMaxSize(cellSize, cellSize);
                gridPane.add(cell, j, i);
            }
        }

        return gridPane;
    }

    public static void placeInGrid(GridPane gridPane, Object object, int row, int col) {
        StackPane cell = (StackPane) getNodeFromGridPane(gridPane, col, row);
        if (cell != null) {
            cell.getChildren().clear();

            if (object instanceof Robot) {
                Robot robot = (Robot) object;
                cell.getChildren().add(robot.getCircle());
                robot.row = row;
                robot.col = col;
            } else if (object instanceof Target) {
                Target target = (Target) object;
                cell.getChildren().add(target.getRectangle());
                target.row = row;
                target.col = col;
            } else if (object instanceof Obstacle) {
                Obstacle obstacle = (Obstacle) object;
                cell.getChildren().add(obstacle.getRectangle());
                obstacle.row = row;
                obstacle.col = col;
            }
        }
    }

    public static void removeFromGrid(GridPane gridPane, Node node) {
        for (Node gridNode : gridPane.getChildren()) {
            if (gridNode instanceof StackPane && ((StackPane) gridNode).getChildren().contains(node)) {
                ((StackPane) gridNode).getChildren().remove(node);
                break;
            }
        }
    }

    public static void removeFromGrid(GridPane gridPane, Robot robot) {
        gridPane.getChildren().remove(robot.getCircle());
    }

    public static void removeFromGrid(GridPane gridPane, Obstacle obstacle) {
        if (obstacle != null) {
            int row = obstacle.getRow();
            int col = obstacle.getCol();
            gridPane.getChildren().remove(obstacle.getRectangle());

            // Clear background color
            Node node = getNodeFromGridPane(gridPane, col, row);
            if (node instanceof StackPane) {
                StackPane cell = (StackPane) node;
                cell.setBackground(Background.EMPTY);
                cell.getChildren().remove(obstacle.getRectangle());
            }
        }
    }
    public static void removeFromGrid(GridPane gridPane, int row, int col) {
        StackPane cell = (StackPane) getNodeFromGridPane(gridPane, col, row);
        if (cell != null) {
            cell.getChildren().clear();
        }
    }



    public static void removeFromGrid(GridPane gridPane, Target target) {
        gridPane.getChildren().remove(target.getRectangle());
    }





    static javafx.scene.Node getNodeFromGridPane(GridPane gridPane, int col, int row) {
        for (javafx.scene.Node node : gridPane.getChildren()) {
            if (GridPane.getColumnIndex(node) == col && GridPane.getRowIndex(node) == row) {
                return node;
            }
        }
        return null;
    }
}
