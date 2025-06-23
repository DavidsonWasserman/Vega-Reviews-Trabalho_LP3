package com.example.trabalho_LP3.controllers;

import com.example.trabalho_LP3.Main;
import com.example.trabalho_LP3.Navegacao;
import com.example.trabalho_LP3.UsuarioLogado;
import com.example.trabalho_LP3.controllers.perfil.MeuPerfilController;
import com.example.trabalho_LP3.controllers.searchBiblioteca.UsuariosController;
import com.example.trabalho_LP3.controllers.searchBiblioteca.JogosController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.layout.StackPane;
import java.io.IOException;
import java.util.ResourceBundle;
import java.net.URL;

public class MenuController implements Initializable {

    @FXML
    private StackPane mainContent;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Navegacao.setMainContent(mainContent);
        Navegacao.navigateTo("/com/example/App_LP3/Telas_Principais/Home.fxml");
    }

    @FXML
    private void goToHome(ActionEvent event) {
        loadScreen("/com/example/App_LP3/Telas_Principais/Home.fxml");
    }

    @FXML
    private void goToJogos(ActionEvent event){
        loadScreen("/com/example/App_LP3/TelasSearchBiblioteca/Jogos.fxml");
    }

    @FXML
    private void goToJogadores(ActionEvent event) {
        loadScreen("/com/example/App_LP3/TelasSearchBiblioteca/Usuarios.fxml");
    }

    @FXML
    private void goToAddJogo(ActionEvent event) {
        loadScreen("/com/example/App_LP3/Telas_Add/Add_Jogo.fxml");
    }

    @FXML
    private void goToMeuPerfil(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/App_LP3/Telas_Perfil/Perfil_Ver.fxml"));
            Parent screen = loader.load();

            Object controller = loader.getController();
            if (controller instanceof MeuPerfilController){
                ((MeuPerfilController) controller).setMainContent(mainContent);
            }

            mainContent.getChildren().setAll(screen);
        } catch (IOException e) {
            System.out.println("Erro ao carregar a tela de perfil:");
            e.printStackTrace();
        }
    }

    @FXML
    private void sair(ActionEvent event){
        UsuarioLogado.encerrarSessao();
        mostrarAlerta("Desconectada", "Sua conta foi desconectada.");
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/App_LP3/Telas_Perfil/Perfil_Entrar.fxml"));
            Parent root = loader.load();
            mainContent.getScene().setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadScreen(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent screen = loader.load();

            Object controller = loader.getController();
            if (controller instanceof JogosController){
                ((JogosController) controller).setMainContent(mainContent);
            } else if (controller instanceof UsuariosController) {
                ((UsuariosController) controller).setMainContent(mainContent);
            }
            Navegacao.navigateTo(fxmlPath);
        } catch (IOException e) {
            System.out.println("Erro ao carregar a tela: " + fxmlPath);
            e.printStackTrace();
        }
    }

    private void mostrarAlerta(String titulo, String mensagem) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }
}
