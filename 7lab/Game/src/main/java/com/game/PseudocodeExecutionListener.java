package com.game;

import com.almasb.fxgl.ui.Position;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;
import javafx.util.Duration;
import org.antlr.v4.runtime.misc.NotNull;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

public class PseudocodeExecutionListener extends PseudocodeBaseListener {
    private Robot robot;
    private Target target;
    private List<Obstacle> obstacles;
    private GridPane gridPane;

    private Map<String, Integer> variables;

    private RobotNavigationGame game;
    private Timeline timeline;
    boolean gameReset;

    private int frame = 0;
    private List<Position> positions;
    private TextArea outputArea;

    private boolean executeStatements = true;


    public PseudocodeExecutionListener(Robot robot, Target target, List<Obstacle> obstacles, GridPane gridPane, RobotNavigationGame game, TextArea outputArea) {
        this.robot = robot;
        this.target = target;
        this.obstacles = obstacles;
        this.gridPane = gridPane;
        this.game = game;
        this.variables = new HashMap<>();
        this.timeline = new Timeline();
        this.timeline.setCycleCount(Timeline.INDEFINITE);
        this.positions = new ArrayList<>();
        this.outputArea = outputArea;
    }


    @Override
    public void enterMove(@NotNull PseudocodeParser.MoveContext ctx) {
        if (!executeStatements) {
            return;
        }
        int oldRow = robot.getRow();
        int oldCol = robot.getCol();

        String direction = ctx.getChild(1).getText();

        switch (direction) {
            case "up":
                robot.moveUp();
                break;
            case "down":
                robot.moveDown();
                break;
            case "left":
                robot.moveLeft();
                break;
            case "right":
                robot.moveRight();
                break;
        }



        // Check for lose condition (robot on top of obstacle)
        if (isObstacle(robot.getRow(), robot.getCol())) {
            outputArea.appendText("You lost! The robot collided with an obstacle.\n");
            game.resetLevel();
            return;
        }

        // Check for win condition (robot on top of target)
        if (isTarget(robot.getRow(), robot.getCol())) {
            outputArea.appendText("Congratulations! You won! The robot reached the target.\n");
            game.resetLevel();
            return;
        }

        if (gameReset) {
            return;
        }

        GridUtils.removeFromGrid(gridPane, oldRow, oldCol);
        GridUtils.placeInGrid(gridPane, robot, robot.getRow(), robot.getCol());
        outputArea.appendText("Robot position: (" + robot.getRow() + ", " + robot.getCol() + ")\n");

    }


    private boolean isTarget(int row, int col) {
        return target.getRow() == row && target.getCol() == col;
    }



    @Override
    public void enterCheckObstacle(@NotNull PseudocodeParser.CheckObstacleContext ctx) {
        if (!executeStatements) {
            return;
        }
        String direction = ctx.getChild(2).getText();
        int row = robot.getRow();
        int col = robot.getCol();

        switch (direction) {
            case "up":
                if (row > 0 && isObstacle(row - 1, col)) {
                    destroyObstacle(row - 1, col);
                }
                break;
            case "down":
                if (row < 9 && isObstacle(row + 1, col)) {
                    destroyObstacle(row + 1, col);
                }
                break;
            case "left":
                if (col > 0 && isObstacle(row, col - 1)) {
                    destroyObstacle(row, col - 1);
                }
                break;
            case "right":
                if (col < 9 && isObstacle(row, col + 1)) {
                    destroyObstacle(row, col + 1);
                }
                break;
        }
    }

    private boolean isObstacle(int row, int col) {
        for (Obstacle obstacle : obstacles) {
            if (obstacle.getRow() == row && obstacle.getCol() == col) {
                return true;
            }
        }
        return false;
    }


    private void destroyObstacle(int row, int col) {
        Obstacle obstacleToRemove = null;
        for (Obstacle obstacle : obstacles) {
            if (obstacle.getRow() == row && obstacle.getCol() == col) {
                obstacleToRemove = obstacle;
                break;
            }
        }
        if (obstacleToRemove != null) {
            obstacles.remove(obstacleToRemove);
            outputArea.appendText("Obstacle removed in:  (" + row + ", " + col +  ")\n" );
            GridUtils.removeFromGrid(gridPane, obstacleToRemove);
        }
    }




    @Override
    public void enterIfStatement(@NotNull PseudocodeParser.IfStatementContext ctx) {
        boolean conditionResult = evaluateCondition(ctx.condition());
        executeStatements = conditionResult;

//        if (executeStatements) {
//            for (PseudocodeParser.StatementContext statement : ctx.statement()) {
//                ParseTreeWalker.DEFAULT.walk(this, statement);
//            }
//        }
    }
    @Override
    public void exitIfStatement(@NotNull PseudocodeParser.IfStatementContext ctx) {
        executeStatements = true;
    }



    @Override
    public void enterForLoop(@NotNull PseudocodeParser.ForLoopContext ctx) {
        String variableName = ctx.VARIABLE().getText();
        int start = Integer.parseInt(ctx.INT(0).getText());
        int end = Integer.parseInt(ctx.INT(1).getText());


        for (int i = start; i < end; i++) {
            variables.put(variableName, i);
            for (PseudocodeParser.StatementContext statement : ctx.statement()) {
                ParseTreeWalker.DEFAULT.walk(this, statement);
            }
        }
    }

    @Override
    public void enterWhileLoop(@NotNull PseudocodeParser.WhileLoopContext ctx) {
        boolean conditionResult = evaluateCondition(ctx.condition());
        while (conditionResult) {
            for (PseudocodeParser.StatementContext statement : ctx.statement()) {
                ParseTreeWalker.DEFAULT.walk(this, statement);
            }
            conditionResult = evaluateCondition(ctx.condition());
        }
    }

    private boolean evaluateCondition(PseudocodeParser.ConditionContext ctx) {
        int leftValue = getExprValue(ctx.expr(0));
        int rightValue = getExprValue(ctx.expr(1));
        String operator = ctx.comparison_operator().getText();

        switch (operator) {
            case "<":
                return leftValue < rightValue;
            case "<=":
                return leftValue <= rightValue;
            case ">":
                return leftValue > rightValue;
            case ">=":
                return leftValue >= rightValue;
            case "==":
                return leftValue == rightValue;
            case "!=":
                return leftValue != rightValue;
            default:
                throw new IllegalArgumentException("Invalid comparison operator: " + operator);
        }
    }

    private int getExprValue(PseudocodeParser.ExprContext ctx) {
        if (ctx.INT() != null) {
            return Integer.parseInt(ctx.INT().getText());
        } else if (ctx.VARIABLE() != null) {
            String varName = ctx.VARIABLE().getText();
            return variables.get(varName);
        } else {
            int leftValue = getExprValue(ctx.expr(0));
            int rightValue = getExprValue(ctx.expr(1));
            String operator = ctx.getChild(1).getText();

            return operator.equals("+") ? leftValue + rightValue : leftValue - rightValue;
        }
    }

    @Override
    public void enterSetVariable(@NotNull PseudocodeParser.SetVariableContext ctx) {
        String variableName = ctx.VARIABLE().getText();
        int value = getExprValue(ctx.expr());
        variables.put(variableName, value);
    }



    public void enterPrint(@NotNull PseudocodeParser.PrintContext ctx) {
        String variableName = ctx.VARIABLE().getText();
        String text = variableName + ": " + variables.get(variableName);
        outputArea.appendText(text + "\n");
    }

    @Override
    public void enterDelay(@NotNull PseudocodeParser.DelayContext ctx) {
        int delayMilliseconds = Integer.parseInt(ctx.INT().getText());
        try {
            TimeUnit.MILLISECONDS.sleep(delayMilliseconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void stopAndClearTimeline() {
        timeline.stop();
        timeline.getKeyFrames().clear();
        gameReset = true;
    }

    public Timeline getTimeline() {
        return timeline;
    }

}

