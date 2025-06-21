package com.example.trabalho_LP3.controllers.exibicaoDetalhes;

import com.example.trabalho_LP3.Review;
import com.example.trabalho_LP3.ConexaoBanco;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;
import javafx.scene.layout.StackPane;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.sql.*;

public class ReviewController {

    @FXML private TextField jogoField;
    @FXML private TextField jogadorField;
    @FXML private TextField notaField;
    @FXML private TextArea reviewArea;

    @FXML
    private void voltar(ActionEvent event)throws IOException {
        if (mainContent != null) {
            mainContent.getChildren().setAll(paginaAnterior);
        } else {
            System.out.println("mainContent está null!");
        }
    }

    @FXML
    public void initialize() {
        connection = ConexaoBanco.getConnection();
    }

    private Connection connection;
    private StackPane mainContent;
    private Parent paginaAnterior;

    public void setMainContent(StackPane mainContent){
        this.mainContent = mainContent;
    }

    public void setPaginaAnterior(Parent paginaAnterior){
        this.paginaAnterior = paginaAnterior;
    }

    public void carregarDetalhes(@NotNull Review review) {
        jogoField.setText(review.getNomeJogo());
        jogadorField.setText(review.getJogador());
        notaField.setText(String.valueOf(review.getNota()));
        reviewArea.setText(review.getComentario());

        jogoField.setDisable(true);
        jogadorField.setDisable(true);
        notaField.setDisable(true);
        reviewArea.setDisable(true);
    }

}
