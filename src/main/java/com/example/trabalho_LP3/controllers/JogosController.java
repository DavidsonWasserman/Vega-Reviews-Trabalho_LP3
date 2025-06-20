package com.example.trabalho_LP3.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import com.example.trabalho_LP3.ConexaoBanco;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class JogosController implements Initializable {

    @FXML private TextField campoBusca;
    @FXML private ListView<String> campoExibe;

    @FXML
    private void ordenarAZ() {
        ordemAtual = "AZ";
        carregarJogos(campoBusca.getText());
    }

    @FXML
    private void ordenarZA() {
        ordemAtual = "ZA";
        carregarJogos(campoBusca.getText());
    }

    @FXML
    private void ordenarMaiorNota() {
        ordemAtual = "MAIOR";
        carregarJogos(campoBusca.getText());
    }

    @FXML
    private void ordenarMenorNota() {
        ordemAtual = "MENOR";
        carregarJogos(campoBusca.getText());
    }

    private StackPane mainContent;
    private Connection connection;
    private String ordemAtual = "AZ"; //ordem alfabetica como classificacao padrao

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        connection = ConexaoBanco.getConnection();
        carregarJogos("");

        campoBusca.setOnKeyReleased(event -> {
            String textoBusca = campoBusca.getText();
            carregarJogos(textoBusca);
            });

        campoExibe.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1) {
                String jogoSelecionado = campoExibe.getSelectionModel().getSelectedItem();
                if (jogoSelecionado != null) {
                    abrirDetalhesJogo(jogoSelecionado);
                }
            }
        });
    }

    public void setMainContent(StackPane mainContent){
        this.mainContent = mainContent;
    }

    private void abrirDetalhesJogo(String nomeJogo) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/App_LP3/Telas_View/Jogo.fxml"));
            Parent root = loader.load();
            JogoController controller = loader.getController();
            controller.setMainContent(mainContent);
            controller.setPaginaAnterior((Parent) mainContent.getChildren().get(0));
            controller.carregarDetalhes(nomeJogo);

            if (mainContent != null) {
                mainContent.getChildren().setAll(root);
            } else {
                System.out.println("mainContent está null!");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void carregarJogos(String filtro) {
        campoExibe.getItems().clear();

        StringBuilder query = new StringBuilder("SELECT nome FROM jogos");
        boolean temFiltro = filtro != null && !filtro.trim().isEmpty();

        if (temFiltro) {
            query.append(" WHERE LOWER(nome) LIKE ?");
        }

        switch (ordemAtual) {
            case "AZ" -> query.append(" ORDER BY nome ASC");
            case "ZA" -> query.append(" ORDER BY nome DESC");
            case "MAIOR" -> query.append(" ORDER BY nota_media DESC");
            case "MENOR" -> query.append(" ORDER BY nota_media ASC");
        }

        try (PreparedStatement stmt = connection.prepareStatement(query.toString())) {
            if (temFiltro) {
                stmt.setString(1, filtro.toLowerCase() + "%");
            }

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String nome = rs.getString("nome");
                campoExibe.getItems().add(nome);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
