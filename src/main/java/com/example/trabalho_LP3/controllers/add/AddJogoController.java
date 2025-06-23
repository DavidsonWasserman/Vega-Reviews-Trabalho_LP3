package com.example.trabalho_LP3.controllers.add;

import com.example.trabalho_LP3.ConexaoBanco;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

import java.io.File;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AddJogoController {

    @FXML TextField nomeField;
    @FXML TextField desenvolvedoraField;
    @FXML TextField generoField;
    @FXML TextArea sinopseArea;
    @FXML Button botaoImagem;
    @FXML Button botaoAddJogo;
    @FXML Button botaoCancelar;
    @FXML ImageView imagemPreview;
    @FXML Label mensagemError;

    @FXML
    public void initialize(){
        botaoAddJogo.setOnAction(event -> adicionarJogo());
        botaoImagem.setOnAction(event -> selecionarImagem());
        botaoCancelar.setOnAction(event -> limparCampos());
        mensagemError.setVisible(false);
        connection = ConexaoBanco.getConnection();
    }

    private Connection connection;
    private File imagemSelecionada= null;

    private void selecionarImagem() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Selecionar Capa do Jogo");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Imagens", "*.jpg", "*.jpeg", "*.png")
        );

        File arquivo = fileChooser.showOpenDialog(null);
        if (arquivo != null) {
            imagemSelecionada = arquivo;
            System.out.println("Imagem selecionada: " + imagemSelecionada.getName());

            Image image = new Image(imagemSelecionada.toURI().toString());
            imagemPreview.setImage(image);
        }
    }

    private void adicionarJogo() {
        String nome = nomeField.getText();
        String desenvolvedora = desenvolvedoraField.getText();
        String genero = generoField.getText();
        String sinopse = sinopseArea.getText();
        String nomeImagem = (imagemSelecionada != null) ? imagemSelecionada.getName() : null;

        if (nome.isEmpty() || desenvolvedora.isEmpty() || genero.isEmpty() || sinopse.isEmpty()) {
            mensagemError.setText("Preencha todos os campos.");
            mensagemError.setVisible(true);
            return;
        }

        if (imagemSelecionada != null) {
            try {
                File destino = new File("src/main/resources/com/example/App_LP3/imagens/covers/" + nomeImagem);
                if (!destino.exists()) {
                    Files.copy(imagemSelecionada.toPath(), destino.toPath());
                    System.out.println("Imagem copiada para: " + destino.getPath());
                }
            } catch (Exception ex) {
                System.out.println("Erro ao copiar imagem.");
                ex.printStackTrace();
            }
        }

        String sql = "INSERT INTO jogos (nome, desenvolvedora, genero, sinopse, imagem) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, nome);
            stmt.setString(2, desenvolvedora);
            stmt.setString(3, genero);
            stmt.setString(4, sinopse);
            stmt.setString(5, nomeImagem);

            int linhasAfetadas = stmt.executeUpdate();
            if (linhasAfetadas > 0) {
                mensagemError.setText("Jogo adicionado com sucesso!");
                limparCampos();
            }
        } catch (SQLException e) {
            System.out.println("Erro ao adicionar jogo.");
            e.printStackTrace();
        }
    }

    private void limparCampos() {
        nomeField.clear();
        desenvolvedoraField.clear();
        generoField.clear();
        sinopseArea.clear();
        imagemSelecionada = null;
        imagemPreview.setImage(null);
    }
}
