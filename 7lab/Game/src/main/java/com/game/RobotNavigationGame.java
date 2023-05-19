package com.game;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

public class RobotNavigationGame extends Application {
    private static final int GRID_SIZE = 10;
    private static final int CELL_SIZE = 50;

    private PseudocodeExecutionListener listener;

    private GridPane gridPane;
    private TextArea commandInput;
    private Button runButton;
    private Robot robot;
    private Target target;
    private List<Obstacle> obstacles;
    private TextArea outputArea;


    private TextArea createOutputArea() {
        TextArea outputArea = new TextArea();
        outputArea.setPrefWidth(200);
        outputArea.setPrefHeight(200);
        outputArea.setEditable(false); // Prevent user from editing the output
        return outputArea;
    }

    @Override
    public void start(Stage primaryStage) {
        outputArea = createOutputArea();
        gridPane = new GridPane();
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                StackPane cell = new StackPane();
                cell.setStyle("-fx-border-color: black;");
                cell.setMinSize(CELL_SIZE, CELL_SIZE);
                cell.setMaxSize(CELL_SIZE, CELL_SIZE);
                gridPane.add(cell, j, i);
            }
        }

        robot = new Robot(0, 0);
        GridUtils.placeInGrid(gridPane, robot, robot.getRow(), robot.getCol());

        target = new Target(GRID_SIZE - 1, GRID_SIZE - 1);
        GridUtils.placeInGrid(gridPane, target, target.getRow(), target.getCol());

        obstacles = new ArrayList<>(Arrays.asList(new Obstacle(2, 2), new Obstacle(4, 4), new Obstacle(6, 6)));
        for (Obstacle obstacle : obstacles) {
            GridUtils.placeInGrid(gridPane, obstacle, obstacle.getRow(), obstacle.getCol());
        }

        commandInput = createCommandInput();
        runButton = createRunButton();

        VBox commandArea = new VBox(10, commandInput, runButton, outputArea);

        commandArea.setAlignment(Pos.CENTER);

        BorderPane root = new BorderPane();
        root.setCenter(gridPane);
        root.setRight(commandArea);

        Scene scene = new Scene(root, (GRID_SIZE * CELL_SIZE) + 200, GRID_SIZE * CELL_SIZE);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Robot Navigation Game");
        primaryStage.show();
    }

    private TextArea createCommandInput() {
        TextArea commandInput = new TextArea();
        commandInput.setPrefWidth(200);
        commandInput.setPrefHeight(200);
        commandInput.setWrapText(true);
        return commandInput;
    }

    private Button createRunButton() {
        Button runButton = new Button("Run");
        runButton.setOnAction(event -> {
            String pseudocode = commandInput.getText();
            executePseudocode(pseudocode);
        });
        return runButton;
    }

    public static void main(String[] args) {
        launch(args);
    }
    private void clearGrid() {
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                StackPane cell = (StackPane) GridUtils.getNodeFromGridPane(gridPane, j, i);
                if (cell != null) {
                    cell.getChildren().clear();
                }
            }
        }
    }


    void resetLevel() {

        listener.stopAndClearTimeline();

        GridUtils.removeFromGrid(gridPane, robot);
        for (Obstacle obstacle : obstacles) {
            GridUtils.removeFromGrid(gridPane, obstacle);
        }
        GridUtils.removeFromGrid(gridPane, target);

        clearGrid();

        robot = new Robot(0, 0);
        GridUtils.placeInGrid(gridPane, robot, robot.getRow(), robot.getCol());

        target = new Target(GRID_SIZE - 1, GRID_SIZE - 1);
        GridUtils.placeInGrid(gridPane, target, target.getRow(), target.getCol());

        obstacles = new ArrayList<>(Arrays.asList(new Obstacle(2, 2), new Obstacle(4, 4), new Obstacle(6, 6)));
        for (Obstacle obstacle : obstacles) {
            GridUtils.placeInGrid(gridPane, obstacle, obstacle.getRow(), obstacle.getCol());
        }
    }




    private void executePseudocode(String pseudocode) {
        // Create a lexer and parser for the pseudocode input
        PseudocodeLexer lexer = new PseudocodeLexer(CharStreams.fromString(pseudocode));
        PseudocodeParser parser = new PseudocodeParser(new CommonTokenStream(lexer));

        // Create the custom listener that interacts with the game
        listener = new PseudocodeExecutionListener(robot, target, obstacles, gridPane, this, outputArea);
        listener.gameReset = false;


        // Walk the parse tree and execute the commands
        ParseTreeWalker.DEFAULT.walk(listener, parser.program());

        listener.getTimeline().play();
    }

}
