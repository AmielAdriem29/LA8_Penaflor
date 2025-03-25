package com.example.graphspenaflor;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class HelloController {
    @FXML
    private AnchorPane anchorPane;


    @FXML
    private void initialize() {
        anchorPane.getChildren().clear();
        List<com.example.graphspenaflor.Node> nodes = loadNode();
        for (com.example.graphspenaflor.Node node : nodes) {
            StackPane sp = new StackPane();
            double[] offset = new double[2];
            Circle circle = new Circle(node.getCircleRadius());
            Text text = new Text(node.getText());

            sp.setOnMouseClicked(this::onVertexClicked);
            circle.setFill(Color.LIME);
            circle.setStroke(Color.GREEN);
            sp.getChildren().add(circle);
            sp.getChildren().add(text);
            sp.setLayoutX(node.getxPos());
            sp.setLayoutY(node.getyPos());

            sp.setOnMousePressed((MouseEvent e) -> {
                offset[0] = e.getX() - sp.getLayoutX();
                offset[1] = e.getY() - sp.getLayoutY();
            });
            sp.setOnMouseDragged((MouseEvent e) -> {
                sp.setLayoutX(e.getX() - offset[0]);
                sp.setLayoutY(e.getY() - offset[1]);
            });

            anchorPane.getChildren().add(sp);
        }
    }

    @FXML
    private void onVertexClicked(MouseEvent event) {
        TextInputDialog dialog = new TextInputDialog("Enter \"DELETE\" to remove circle.");
        dialog.showAndWait().ifPresent(new Consumer<String>() {
            @Override
            public void accept(String s) {
                StackPane sp = (StackPane) event.getSource();

                if (s.equalsIgnoreCase("DELETE")) {
                    anchorPane.getChildren().remove(sp);
                }
                for (Node node : sp.getChildren()) {
                    if (node instanceof Text txt) {
                        txt.setText(s);
                    }
                }
            }
        });

//        TextInputDialog dialog = new TextInputDialog("Enter \"DELETE\" to remove circle.");
//        dialog.showAndWait().ifPresent(new Consumer<String>() {
//            @Override
//            public void accept(String s) {
//                // Get the clicked custom pane
//                CustomStackPane customPane = (CustomStackPane) event.getSource();
//                if (s.equalsIgnoreCase("DELETE")) {
//                    anchorPane.getChildren().remove(customPane);
//                }
//                // Update the text inside the custom pane
//                for (Node node : customPane.getChildren()) {
//                    if (node instanceof Text txt) {
//                        txt.setText(s);
//                    }
//                }
//            }
//        });
    }


    @FXML
    private void onSaveClick() {
        List<com.example.graphspenaflor.Node> nodes = new ArrayList<>();
        for (Node node : anchorPane.getChildren()) {
            if (node instanceof StackPane sp) {
                if (sp.getChildren().get(1) instanceof Text txt) {
                    double xPos = sp.getLayoutX();
                    double yPos = sp.getLayoutY();

                    if (sp.getChildren().get(0) instanceof Circle circle) {
                        double rad = circle.getRadius();

                        com.example.graphspenaflor.Node node1 = new com.example.graphspenaflor.Node(txt.getText(), rad, xPos, yPos);
                        nodes.add(node1);
                    }
                }
            }
        }
        saveNode(nodes);
    }

    @FXML
    private void onAddClick() {
        anchorPane.getChildren().add(createNode());
    }

    private StackPane createNode() {
        StackPane sp = new StackPane();
        double[] offset = new double[2];
        Circle circle = new Circle(25);
        circle.setFill(Color.LIME);
        circle.setStroke(Color.GREEN);
        Text text = new Text("");

        sp.setOnMousePressed((MouseEvent e) -> {
            offset[0] = e.getX() - sp.getLayoutX();
            offset[1] = e.getY() - sp.getLayoutY();
        });
        sp.setOnMouseDragged((MouseEvent e) -> {
            sp.setLayoutX(e.getX() - offset[0]);
            sp.setLayoutY(e.getY() - offset[1]);
        });
        sp.setOnMouseClicked(this::onVertexClicked);
        sp.getChildren().add(circle);
        sp.getChildren().add(text);
        sp.setLayoutX(0);
        sp.setLayoutY(0);
        return sp;
    }

    private List<com.example.graphspenaflor.Node> loadNode() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("nodes.dat"))) {
            return (List<com.example.graphspenaflor.Node>) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    private void saveNode(List<com.example.graphspenaflor.Node> nodes) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("nodes.dat"))) {
            out.writeObject(nodes);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Saved.");
            alert.setHeaderText(null);
            alert.setContentText("Progress saved...");
            alert.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
