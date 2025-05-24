package com.example.trabalho_LP3.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.fxml.Initializable;
import javafx.event.ActionEvent;
import java.net.URL;
import java.util.ResourceBundle;

public class MenuController implements Initializable {

    private HomeController homeController;

    @FXML
    private ContextMenu menuContext;

    @FXML
    private Button menuButton;
    @FXML
    private TextField searchField;

    public void setHomeController(HomeController homeController) {
        this.homeController = homeController;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    @FXML
    private void abrirMenu(ActionEvent event) {
        if (menuContext != null && menuButton != null) {
            menuContext.show(menuButton, javafx.geometry.Side.BOTTOM, 5, 0);
        }
    }

    @FXML
    private void navigateToHome() {
        if (homeController != null) {
            homeController.loadPage("/com/example/App_LP3/views/Home.fxml");
        }
    }

    @FXML
    private void navigateToGames() {
        if (homeController != null) {
            homeController.loadPage("/com/example/App_LP3/views/Jogos.fxml");
        }
    }

    @FXML
    private void navigateToPlayers() {
        if (homeController != null) {
            homeController.loadPage("/com/example/App_LP3/views/Jogadores.fxml");
        }
    }

    @FXML
    private void navigateToProfile() {
        if (homeController != null) {
            homeController.loadPage("/com/example/App_LP3/views/MeuPerfil.fxml");
        }
    }
}