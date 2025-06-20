package com.example.trabalho_LP3.controllers;

import com.example.trabalho_LP3.ConexaoBanco;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.sql.*;

public class JogadorController {

    @FXML private TextField nicknameField;
    @FXML private TextArea sobremimArea;
    @FXML private ImageView imageView;

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

    public void carregarDetalhes(String nickName) {
        String query = "SELECT * FROM usuarios WHERE nickname = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, nickName);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                nicknameField.setText(rs.getString("nickname"));
                sobremimArea.setText(rs.getString("sobre_mim"));

                String caminhoImagem = rs.getString("foto_perfil");
                nicknameField.setDisable(true);
                sobremimArea.setDisable(true);
                if (caminhoImagem != null && !caminhoImagem.isEmpty()) {
                    String caminhoCompleto = "/com/example/App_LP3/imagens/pfp/" + caminhoImagem;

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
