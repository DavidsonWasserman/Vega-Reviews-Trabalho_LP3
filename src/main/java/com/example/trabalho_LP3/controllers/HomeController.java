package com.example.trabalho_LP3.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class HomeController implements Initializable {

    @FXML
    private AnchorPane mainContent;

    @FXML
    private HBox barra;

    private MenuController barraController;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            Object property = barra.getProperties().get("fxmlloader");
            if (property instanceof FXMLLoader) {
                FXMLLoader loader = (FXMLLoader) property;
                barraController = loader.getController();
                if (barraController != null) {
                    barraController.setHomeController(this);
                }
            }
        } catch (Exception e) {
            System.err.println("Erro ao obter controller da barra de menu");
            e.printStackTrace();
        }
    }

    public void loadPage(String path) {
        try {
            Node page = FXMLLoader.load(getClass().getResource(path));
            mainContent.getChildren().setAll(page);
        } catch (IOException e) {
            System.err.println("Erro ao carregar página: " + path);
            e.printStackTrace();
        }
    }
}