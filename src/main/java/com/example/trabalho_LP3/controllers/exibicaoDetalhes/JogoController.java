package com.example.trabalho_LP3.controllers.exibicaoDetalhes;

import com.example.trabalho_LP3.ConexaoBanco;
import com.example.trabalho_LP3.Review;
import com.example.trabalho_LP3.controllers.add.AddReviewController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
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
    @FXML private ListView<Review> reviewsList;

    @FXML
    private void voltar(ActionEvent event)throws IOException {
        if (mainContent != null) {
            mainContent.getChildren().setAll(paginaAnterior);
        } else {
            System.out.println("mainContent está null!");
        }
    }

    @FXML
    private void botaoAddReview(){
        abrirAddReview();
    }

    @FXML
    public void initialize() {
        connection = ConexaoBanco.getConnection();
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
                            review.getJogador(), review.getNota(), resumo));
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
    private Parent paginaAnterior;
    private String nomeJogo;

    public void setMainContent(StackPane mainContent){
        this.mainContent = mainContent;
    }

    public void setPaginaAnterior(Parent paginaAnterior){
        this.paginaAnterior = paginaAnterior;
    }

    public void carregarDetalhes(String nomeJogo) {
        this.nomeJogo = nomeJogo;

        String query = """
                       SELECT j.nome, j.desenvolvedora, j.genero, AVG(r.nota) AS nota_media, COUNT(r.id) AS qtd, j.sinopse, j.imagem
                       FROM jogos j
                       LEFT JOIN reviews_jogos r ON j.id = r.id_jogo
                       WHERE j.nome = ?
                       GROUP BY j.nome, j.desenvolvedora, j.genero, j.sinopse, j.imagem;
                       """;

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, nomeJogo);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                nomeField.setText(rs.getString("nome"));
                desenvolvedoraField.setText(rs.getString("desenvolvedora"));
                generoField.setText(rs.getString("genero"));
                notaField.setText(String.valueOf(rs.getDouble("nota_media")));
                sinopseArea.setText(rs.getString("sinopse"));
                qtdReview.setText(String.valueOf(rs.getInt("qtd")));
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

        String reviewQuery = """
                    SELECT u.nickname AS nome_jogador, r.nota, r.comentario
                    FROM reviews_jogos r
                    JOIN jogos j ON r.id_jogo = j.id
                    JOIN usuarios u ON r.id_usuario = u.id
                    WHERE j.nome = ?
                """;

        try (PreparedStatement reviewStmt = connection.prepareStatement(reviewQuery)) {
            reviewStmt.setString(1, nomeJogo);
            ResultSet reviewRs = reviewStmt.executeQuery();

            reviewsList.getItems().clear();

            while (reviewRs.next()) {
                String nickName = reviewRs.getString("nome_jogador");
                int nota = reviewRs.getInt("nota");
                String comentario = reviewRs.getString("comentario");

                Review review = new Review(nomeJogo, nickName, nota, comentario);
                reviewsList.getItems().add(review);
            }

            if (reviewsList.getItems().isEmpty()){
                reviewsList.setPlaceholder(new Label("Este jogo ainda não possui reviews"));
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

    private void abrirAddReview(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/App_LP3/Telas_Add/Add_Reviews.fxml"));
            Parent root = loader.load();

            AddReviewController controller = loader.getController();
            controller.setMainContent(mainContent);
            controller.setPaginaAnterior((Parent) mainContent.getChildren().get(0));
            controller.carregarDados(nomeJogo);

            mainContent.getChildren().setAll(root);


        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
