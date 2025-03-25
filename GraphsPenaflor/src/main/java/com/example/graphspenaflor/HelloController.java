package com.example.graphspenaflor;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.function.Consumer;

public class HelloController {
    public AnchorPane apPane;

    public void initialize(){
        try (ObjectOutputStream ooS = new ObjectOutputStream(new FileOutputStream("person.txt"))){
            ooS.writeObject(apPane);
        } catch (IOException e) {
            System.out.println(e.getClass());
        }
        for(Node node: apPane.getChildren()){
            if(node instanceof Text txt){
                txt.set();
            }
        }
    }

    public void onVertexClicked(MouseEvent mouseEvent) {
        TextInputDialog dialog = new TextInputDialog("what name do you want?");
        dialog.showAndWait().ifPresent(new Consumer<String>() {
            @Override
            public void accept(String s) {
                StackPane sp = (StackPane) mouseEvent.getSource();
                if(sp.getChildren() instanceof Text txt){
                    txt.setText(s);
                }

                for(Node node: sp.getChildren()){
                    if(node instanceof Text txt){
                        txt.setText(s);
                    }
                }
            }
        });
    }


    public void onSaveClicked(ActionEvent actionEvent) {
        try(ObjectInputStream oiS = new ObjectInputStream(new FileInputStream("stackpanes.txt"))) {


        }catch (FileNotFoundException e){
            System.out.println("SERIALIZED  persons");
            //break;
        }
        catch (IOException e) {
            System.err.println(e.getClass());
        }
    }
}