package com.example.trabalho_LP3.controllers;

import com.example.trabalho_LP3.ConexaoBanco;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.input.MouseEvent;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class HomeController implements Initializable {


    @FXML private Label nomeJogoTop1;
    @FXML private Label nomeJogoTop2;
    @FXML private Label nomeJogoTop3;
    @FXML private Label nomeJogoTop4;
    @FXML private Label nomeJogoTop5;
    @FXML private ImageView imagemJogoTop1;
    @FXML private ImageView imagemJogoTop2;
    @FXML private ImageView imagemJogoTop3;
    @FXML private ImageView imagemJogoTop4;
    @FXML private ImageView imagemJogoTop5;
    @FXML private Label nickJogadorTop1;
    @FXML private Label nickJogadorTop2;
    @FXML private Label nickJogadorTop3;
    @FXML private Label nickJogadorTop4;
    @FXML private Label nickJogadorTop5;
    @FXML private ImageView imagemJogadorTop1;
    @FXML private ImageView imagemJogadorTop2;
    @FXML private ImageView imagemJogadorTop3;
    @FXML private ImageView imagemJogadorTop4;
    @FXML private ImageView imagemJogadorTop5;

    @FXML
    private void abrirDetalhesJogo(MouseEvent event) {
        for (Node node : ((VBox) event.getSource()).getChildren()) {
            if (node instanceof Label label) {
                abrirDetalhesJogo(label.getText());
                break;
            }
        }
    }

    @FXML
    private void abrirDetalhesJogador(MouseEvent event) {
        for (Node node : ((VBox) event.getSource()).getChildren()) {
            if (node instanceof Label label) {
                abrirDetalhesJogador(label.getText());
                break;
            }
        }
    }

    private StackPane mainContent;
    private Connection connection;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        connection = ConexaoBanco.getConnection();
        carregarTopJogos();
        carregarTopJogadores();
    }

    public void setMainContent(StackPane mainContent) {
        this.mainContent = mainContent;
    }

    private void carregarTopJogos() {
        String query = "SELECT nome, imagem FROM jogos ORDER BY nota_media DESC LIMIT 5";
        try (PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            int i = 1;
            while (rs.next()) {
                String nome = rs.getString("nome");
                String imagem = rs.getString("imagem");

                switch (i) {
                    case 1 -> {
                        nomeJogoTop1.setText(nome);
                        if (imagem != null) {
                            imagemJogoTop1.setImage(new Image(getClass().getResourceAsStream("/com/example/App_LP3/imagens/covers/" + imagem)));
                        }
                    }
                    case 2 -> {
                        nomeJogoTop2.setText(nome);
                        if (imagem != null) {
                            imagemJogoTop2.setImage(new Image(getClass().getResourceAsStream("/com/example/App_LP3/imagens/covers/" + imagem)));
                        }
                    }
                    case 3 -> {
                        nomeJogoTop3.setText(nome);
                        if (imagem != null) {
                            imagemJogoTop3.setImage(new Image(getClass().getResourceAsStream("/com/example/App_LP3/imagens/covers/" + imagem)));
                        }
                    }
                    case 4 -> {
                        nomeJogoTop4.setText(nome);
                        if (imagem != null) {
                            imagemJogoTop4.setImage(new Image(getClass().getResourceAsStream("/com/example/App_LP3/imagens/covers/" + imagem)));
                        }
                    }
                    case 5 -> {
                        nomeJogoTop5.setText(nome);
                        if (imagem != null) {
                            imagemJogoTop5.setImage(new Image(getClass().getResourceAsStream("/com/example/App_LP3/imagens/covers/" + imagem)));
                        }
                    }
                }
                i++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void carregarTopJogadores() {
        String query = "SELECT nickname, foto_perfil FROM usuarios ORDER BY quantidade_de_reviews DESC LIMIT 5";
        try (PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            int i = 1;
            while (rs.next()) {
                String nome = rs.getString("nickname");
                String foto = rs.getString("foto_perfil");

                switch (i) {
                    case 1 -> {
                        nickJogadorTop1.setText(nome);
                        if (foto != null) {
                            imagemJogadorTop1.setImage(new Image(getClass().getResourceAsStream("/com/example/App_LP3/imagens/pfp/" + foto)));
                        }
                    }
                    case 2 -> {
                        nickJogadorTop2.setText(nome);
                        if (foto != null) {
                            imagemJogadorTop2.setImage(new Image(getClass().getResourceAsStream("/com/example/App_LP3/imagens/pfp/" + foto)));
                        }
                    }
                    case 3 -> {
                        nickJogadorTop3.setText(nome);
                        if (foto != null) {
                            imagemJogadorTop3.setImage(new Image(getClass().getResourceAsStream("/com/example/App_LP3/imagens/pfp/" + foto)));
                        }
                    }
                    case 4 -> {
                        nickJogadorTop4.setText(nome);
                        if (foto != null) {
                            imagemJogadorTop4.setImage(new Image(getClass().getResourceAsStream("/com/example/App_LP3/imagens/pfp/" + foto)));
                        }
                    }
                    case 5 -> {
                        nickJogadorTop5.setText(nome);
                        if (foto != null) {
                            imagemJogadorTop5.setImage(new Image(getClass().getResourceAsStream("/com/example/App_LP3/imagens/pfp/" + foto)));
                        }
                    }
                }
                i++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
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
}
