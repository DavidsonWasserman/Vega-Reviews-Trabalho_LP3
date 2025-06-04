package com.example.trabalho_LP3.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import javafx.event.ActionEvent;

import java.io.IOException;

public class HomeController {

    @FXML
    private AnchorPane mainContent; // <- Certifique-se de que esse ID está no Home.fxml

    @FXML
    public void goToHome(ActionEvent event) {
        loadScreen("/com/example/App_LP3/Telas_Principais/Home.fxml");
    }

    @FXML
    public void goToJogos(ActionEvent event) {
        loadScreen("/com/example/App_LP3/Telas_Principais/Jogos.fxml");
    }

    @FXML
    public void goToJogadores(ActionEvent event) {
        loadScreen("/com/example/App_LP3/Telas_Principais/Jogadores.fxml");
    }

    @FXML
    public void goToReviews(ActionEvent event) {
        loadScreen("/com/example/App_LP3/Telas_Principais/Reviews.fxml");
    }

    private void loadScreen(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent screen = loader.load();
            mainContent.getChildren().setAll(screen);
        } catch (IOException e) {
            System.out.println("Erro ao carregar a tela: " + fxmlPath);
            e.printStackTrace();
        }
    }
}
