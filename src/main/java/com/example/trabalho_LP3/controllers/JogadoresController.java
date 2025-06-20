package com.example.trabalho_LP3.controllers;

import com.example.trabalho_LP3.ConexaoBanco;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class JogadoresController implements Initializable {


    @FXML private TextField campoBusca;
    @FXML private ListView<String> campoExibe;

    @FXML
    private void ordenarAZ() {
        ordemAtual = "AZ";
        carregarJogador(campoBusca.getText());
    }

    @FXML
    private void ordenarZA() {
        ordemAtual = "ZA";
        carregarJogador(campoBusca.getText());
    }

    private Connection connection;
    private StackPane mainContent;
    private String ordemAtual = "AZ"; //ordem alfabetica como classificacao padrao

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        connection = ConexaoBanco.getConnection();
        carregarJogador("");

        campoBusca.setOnKeyReleased(event -> {
            String textoBusca = campoBusca.getText();
            carregarJogador(textoBusca);
        });

        campoExibe.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1) {
                String jogadorSelecionado = campoExibe.getSelectionModel().getSelectedItem();
                if (jogadorSelecionado != null) {
                    abrirDetalhesJogador(jogadorSelecionado);
                }
            }
        });
    }

    public void setMainContent(StackPane mainContent){
        this.mainContent = mainContent;
    }

    private void abrirDetalhesJogador(String nickName) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/App_LP3/Telas_View/Jogador.fxml"));
            Parent root = loader.load();
            JogadorController controller = loader.getController();
            controller.setMainContent(mainContent);
            controller.setPaginaAnterior((Parent) mainContent.getChildren().get(0));
            controller.carregarDetalhes(nickName);

            if (mainContent != null) {
                mainContent.getChildren().setAll(root);
            } else {
                System.out.println("mainContent está null!");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void carregarJogador(String filtro) {
        campoExibe.getItems().clear();

        StringBuilder query = new StringBuilder("SELECT nickname FROM usuarios");
        boolean temFiltro = filtro != null && !filtro.trim().isEmpty();

        if (temFiltro) {
            query.append(" WHERE LOWER(nickname) LIKE ?");
        }

        switch (ordemAtual) {
            case "AZ" -> query.append(" ORDER BY nickname ASC");
            case "ZA" -> query.append(" ORDER BY nickname DESC");
        }

        try (PreparedStatement stmt = connection.prepareStatement(query.toString())) {
            if (temFiltro) {
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
