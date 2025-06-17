package com.example.trabalho_LP3.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.event.ActionEvent;
import javafx.scene.layout.StackPane;
import java.io.IOException;
import java.util.ResourceBundle;
import java.net.URL;

public class MenuController implements Initializable {

    @FXML
    private StackPane mainContent; // <- Conecta a mudança de painel no Menu.fxml

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadScreen("/com/example/App_LP3/Telas_Principais/Home.fxml");
    }
    @FXML
    private void goToHome(ActionEvent event) {
        loadScreen("/com/example/App_LP3/Telas_Principais/Home.fxml");
    }

    @FXML
    private void goToJogos(ActionEvent event)throws IOException {
        loadScreen("/com/example/App_LP3/Telas_Principais/Jogos.fxml");
    }

    @FXML
    private void goToJogadores(ActionEvent event) {
        loadScreen("/com/example/App_LP3/Telas_Principais/Jogadores.fxml");
    }

    @FXML
    private void goToAddJogo(ActionEvent event) {
        loadScreen("/com/example/App_LP3/Telas_Add/Add_Jogo.fxml");
    }

    private void loadScreen(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent screen = loader.load();

            Object controller = loader.getController();
            if (controller instanceof JogosController){
                ((JogosController) controller).setMainContent(mainContent);
            } else if (controller instanceof JogadoresController) {
                ((JogadoresController) controller).setMainContent(mainContent);
            }
            mainContent.getChildren().setAll(screen);
        } catch (IOException e) {
            System.out.println("Erro ao carregar a tela: " + fxmlPath);
            e.printStackTrace();
        }
    }
}
