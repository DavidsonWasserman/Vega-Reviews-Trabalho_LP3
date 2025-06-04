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

public class JogosController implements Initializable {

    @FXML
    private TextField campoBusca;

    @FXML
    private ListView<String> campoExibe;

    private Connection connection;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        connectToDatabase();
        carregarJogos("");

        campoBusca.setOnKeyReleased(event -> {
            String textoBusca = campoBusca.getText();
            carregarJogos(textoBusca);
            });

        campoExibe.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                String jogoSelecionado = campoExibe.getSelectionModel().getSelectedItem();
                if (jogoSelecionado != null) {
                    abrirDetalhesJogo(jogoSelecionado);
                }
            }
        });
    }

    // Abre outra tela com mais info(jogo.fxml)
    private void abrirDetalhesJogo(String nomeJogo) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/App_LP3/Telas_View/Jogo.fxml"));
            Parent root = loader.load();


            JogoController controller = loader.getController();
            controller.carregarDetalhes(nomeJogo);

            Stage stage = new Stage();
            stage.setTitle("Detalhes do Jogo");
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

    private void carregarJogos(String filtro) {
        campoExibe.getItems().clear();
        if (filtro == null || filtro.isEmpty()){
            return;
        }
        String query = "SELECT nome FROM jogos WHERE LOWER(nome) LIKE ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, filtro.toLowerCase() + "%");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                campoExibe.getItems().add(rs.getString("nome"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
