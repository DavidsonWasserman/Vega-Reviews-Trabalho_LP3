package com.example.trabalho_LP3.controllers;

import com.example.trabalho_LP3.Navegacao;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.sql.*;

public class JogoController {

    @FXML private TextField nomeField;
    @FXML private TextField desenvolvedoraField;
    @FXML private TextField generoField;
    @FXML private TextField notaField;
    @FXML private TextField qtdReview;
    @FXML private TextArea sinopseArea;
    @FXML private ImageView imageView;
    @FXML private StackPane pagContent;

    private StackPane mainContent;
    private Parent paginaAnterior;

    @FXML private void voltar(ActionEvent event)throws IOException {
        if (mainContent != null) {
            mainContent.getChildren().setAll(paginaAnterior);
        } else {
            System.out.println("mainContent está null!");
        }
    }

    private Connection connection;

    @FXML
    public void initialize() {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/banco_projeto_lp3", "adminlp3", "adminlp3");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setMainContent(StackPane mainContent){
        this.mainContent = mainContent;
    }

    public void setPaginaAnterior(Parent paginaAnterior){
        this.paginaAnterior = paginaAnterior;
    }

    private void loadScreen(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent screen = loader.load();

            Object controller = loader.getController();
            if (controller instanceof JogosController){
                ((JogosController) controller).setMainContent(pagContent);
            }
            pagContent.getChildren().setAll(screen);
        } catch (IOException e) {
            System.out.println("Erro ao carregar a tela: " + fxmlPath);
            e.printStackTrace();
        }
    }

    public void carregarDetalhes(String nomeJogo) {
        String query = "SELECT * FROM jogos WHERE nome = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, nomeJogo);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                nomeField.setText(rs.getString("nome"));
                desenvolvedoraField.setText(rs.getString("desenvolvedora"));
                generoField.setText(rs.getString("genero"));
                notaField.setText(rs.getString("nota_media"));
                sinopseArea.setText(rs.getString("sinopse"));

                String caminhoImagem = rs.getString("imagem");
                nomeField.setDisable(true);
                desenvolvedoraField.setDisable(true);
                generoField.setDisable(true);
                notaField.setDisable(true);
                sinopseArea.setDisable(true);
                qtdReview.setDisable(true);
                if (caminhoImagem != null && !caminhoImagem.isEmpty()) {
                    String caminhoCompleto = "/com/example/App_LP3/imagens/covers/" + caminhoImagem;

                    try {
                        System.out.println("Caminho da imagem: " + getClass().getResource(caminhoCompleto));
                        Image imagem = new Image(getClass().getResourceAsStream(caminhoCompleto));
                        imageView.setImage(imagem);
                        if (getClass().getResource(caminhoCompleto) == null) {
                            System.out.println("Imagem não encontrada em: " + caminhoCompleto);
                        } else {
                            System.out.println("Imagem carregada com sucesso!");
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                        System.out.println("Erro ao carregar imagem: " + caminhoCompleto);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
