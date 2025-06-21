package com.example.trabalho_LP3.controllers.add;

import com.example.trabalho_LP3.ConexaoBanco;
import com.example.trabalho_LP3.UsuarioLogado;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AddReviewController {

    @FXML private TextField jogoField;
    @FXML private TextArea reviewArea;
    @FXML private MenuButton menuNota;
    @FXML private Button botaoAddReview;
    @FXML private Button botaoVoltar;
    @FXML private MenuItem itemNota0, itemNota1, itemNota2, itemNota3, itemNota4, itemNota5, itemNota6, itemNota7, itemNota8, itemNota9, itemNota10;
    @FXML private Label mensagemError;

    @FXML
    private void voltar() {
        if (mainContent != null && paginaAnterior != null) {
            mainContent.getChildren().setAll(paginaAnterior);
        } else {
            System.out.println("mainContent ou paginaAnterior está null!");
        }
    }

    @FXML
    public void initialize() {
        connection = ConexaoBanco.getConnection();
        mensagemError.setVisible(false);

        configurarMenuNota();

        botaoAddReview.setOnAction(e -> adicionarReview());
    }

    private int notaSelecionada = -1;

    private Connection connection;
    private StackPane mainContent;
    private Parent paginaAnterior;

    private String nomeJogo;

    private void configurarMenuNota() {
        MenuItem[] itens = {itemNota0, itemNota1, itemNota2, itemNota3, itemNota4, itemNota5, itemNota6, itemNota7, itemNota8, itemNota9, itemNota10};
        for (int i = 0; i < itens.length; i++) {
            int nota = i;
            itens[i].setOnAction(e -> {
                notaSelecionada = nota;
                menuNota.setText("Nota: " + nota);
            });
        }
    }

    public void setMainContent(StackPane mainContent) {
        this.mainContent = mainContent;
    }

    public void setPaginaAnterior(Parent paginaAnterior) {
        this.paginaAnterior = paginaAnterior;
    }

    public void carregarDados(String nomeJogo) {
        this.nomeJogo = nomeJogo;
        jogoField.setText(nomeJogo);
        jogoField.setDisable(true);
    }

    private void adicionarReview() {
        String comentario = reviewArea.getText().trim();

        if (notaSelecionada == -1 || comentario.isEmpty()) {
            mensagemError.setText("Por favor, selecione uma nota e preencha a review.");
            mensagemError.setVisible(true);
            return;
        }

        String sql = """
            INSERT INTO reviews_jogos (id_jogo, id_usuario, nota, comentario)
            VALUES (
                (SELECT id FROM jogos WHERE nome = ?),
                ?,
                ?,
                ?
            )
        """;

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, nomeJogo);
            stmt.setInt(2, UsuarioLogado.getId());
            stmt.setInt(3, notaSelecionada);
            stmt.setString(4, comentario);

            stmt.executeUpdate();
            voltar();

        } catch (SQLException e) {
            e.printStackTrace();
            mostrarAlerta("Erro", "Erro ao salvar review no banco de dados.");
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
