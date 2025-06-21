package com.example.trabalho_LP3.controllers.perfil;

import com.example.trabalho_LP3.ConexaoBanco;
import com.example.trabalho_LP3.Review;
import com.example.trabalho_LP3.UsuarioLogado;
import com.example.trabalho_LP3.controllers.exibicaoDetalhes.ReviewController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MeuPerfilController {


    @FXML
    private TextField nicknameField;
    @FXML private TextArea sobremimArea;
    @FXML private ImageView imageView;
    @FXML private ListView<Review> reviewsList;

    @FXML
    public void initialize() {
        connection = ConexaoBanco.getConnection();
        carregarDadosJogador();

        reviewsList.setCellFactory(list -> new ListCell<>() {
            @Override
            protected void updateItem(Review review, boolean empty) {
                super.updateItem(review, empty);
                if (empty || review == null) {
                    setText(null);
                    setStyle("");
                } else {
                    int limite = 20;
                    String resumo = review.getComentario().length() > limite
                            ? review.getComentario().substring(0, limite) + "..."
                            : review.getComentario();
                    setText(String.format("Review de %s\nNota: %d\nComentário: \"%s\"",
                            review.getNomeJogo(), review.getNota(), resumo));
                    setStyle("-fx-control-inner-background: #7c7c7c; -fx-background-color: transparent; -fx-border-color: #1aa7ec; -fx-border-width: 0 0 1 0;");
                }
            }
        });

        reviewsList.setStyle("-fx-background-color: #7c7c7c; -fx-control-inner-background: #7c7c7c;");

        reviewsList.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1 && !reviewsList.getSelectionModel().isEmpty()) {
                Review selecionada = reviewsList.getSelectionModel().getSelectedItem();
                if (selecionada != null) {
                    abrirDetalhesReview(selecionada);
                }
            }
        });

    }

    private Connection connection;
    private StackPane mainContent;


    public void setMainContent(StackPane mainContent) {
        this.mainContent = mainContent;
    }

    public void carregarDadosJogador() {
        String query = "SELECT * FROM usuarios WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, UsuarioLogado.getId());
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

        String reviewQuery = """
                    SELECT j.nome AS nome_jogo, r.nota, r.comentario
                    FROM reviews_jogos r
                    JOIN jogos j ON r.id_jogo = j.id
                    JOIN usuarios u ON r.id_usuario = u.id
                    WHERE u.nickname = ?
                """;

        try (PreparedStatement reviewStmt = connection.prepareStatement(reviewQuery)) {
            reviewStmt.setString(1, UsuarioLogado.getNickname());
            ResultSet reviewRs = reviewStmt.executeQuery();

            reviewsList.getItems().clear();

            while (reviewRs.next()) {
                String nomeJogo = reviewRs.getString("nome_jogo");
                int nota = reviewRs.getInt("nota");
                String comentario = reviewRs.getString("comentario");

                Review review = new Review(nomeJogo, UsuarioLogado.getNickname(), nota, comentario);
                reviewsList.getItems().add(review);
            }

            if (reviewsList.getItems().isEmpty()){
                reviewsList.setPlaceholder(new Label("Este jogador ainda não fez uma review"));
            } else {
                reviewsList.setCursor(javafx.scene.Cursor.HAND);
                reviewsList.setOnMouseClicked(event -> {
                    if (event.getClickCount() == 1 && !reviewsList.getSelectionModel().isEmpty()) {
                        Review selecionada = reviewsList.getSelectionModel().getSelectedItem();
                        if (selecionada != null) {
                            abrirDetalhesReview(selecionada);
                        }
                    }
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private void abrirDetalhesReview(Review review) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/App_LP3/Telas_View/Review.fxml"));
            Parent root = loader.load();
            ReviewController controller = loader.getController();
            controller.setMainContent(mainContent);
            controller.setPaginaAnterior((Parent) mainContent.getChildren().get(0));
            controller.carregarDetalhes(review);

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