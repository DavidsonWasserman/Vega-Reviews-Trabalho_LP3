package com.example.trabalho_LP3.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.sql.*;

public class JogadorController {

    @FXML private TextField nicknameField;
    @FXML private TextArea sobremimArea;
    @FXML private ImageView imageView;

    private Connection connection;

    @FXML
    public void initialize() {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/banco_projeto_lp3", "adminlp3", "adminlp3");
        } catch (SQLException e) {
            e.printStackTrace();
        }
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
