package com.example.graphspenaflor;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

import java.io.Serializable;

public class Node implements Serializable {
    private String text;
    private double circleRadius;
    private double xPos;
    private double yPos;

    public Node(String text, double circleRadius, double xPos, double yPos) {
        this.text = text;
        this.circleRadius = circleRadius;
        this.xPos = xPos;
        this.yPos = yPos;
    }
    public String getText() {
        return text;
    }
    public double getCircleRadius() {
        return circleRadius;
    }
    public double getxPos() {
        return xPos;
    }
    public double getyPos() {
        return yPos;
    }
}
