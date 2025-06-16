package com.example.trabalho_LP3.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class JogadoresController implements Initializable {

    @FXML
    private TextField campoBusca;

    @FXML
    private ListView<String> campoExibe;

    private Connection connection;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        connectToDatabase();
        carregarJogador("");

        campoBusca.setOnKeyReleased(event -> {
            String textoBusca = campoBusca.getText();
            carregarJogador(textoBusca);
        });

        campoExibe.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                String jogadorSelecionado = campoExibe.getSelectionModel().getSelectedItem();
                if (jogadorSelecionado != null) {
                    abrirDetalhesJogador(jogadorSelecionado);
                }
            }
        });
    }

    // Abre outra tela com mais info(Jogador.fxml)
    private void abrirDetalhesJogador(String nickName) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/App_LP3/Telas_View/Jogador.fxml"));
            Parent root = loader.load();


            JogadorController controller = loader.getController();
            controller.carregarDetalhes(nickName);

            Stage stage = new Stage();
            stage.setTitle("Detalhes do Jogador");
            Scene scene = new Scene(root, 675, 600);
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void connectToDatabase() {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/banco_projeto_lp3", "adminlp3", "adminlp3");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void carregarJogador(String filtro) {
        campoExibe.getItems().clear();

        String query;
        if (filtro == null || filtro.trim().isEmpty()) {
            query = "SELECT nickname FROM usuarios";
        } else {
            query = "SELECT nickname FROM usuarios WHERE LOWER(nickname) LIKE ?";
        }

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            if (filtro != null && !filtro.trim().isEmpty()) {
                stmt.setString(1, filtro.toLowerCase() + "%");
            }

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String nome = rs.getString("nickname");
                campoExibe.getItems().add(nome);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
