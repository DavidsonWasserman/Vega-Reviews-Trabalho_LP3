package com.example.trabalho_LP3.controllers.perfil;

import com.example.trabalho_LP3.ConexaoBanco;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AddPerfilController {

    @FXML TextField nicknameField;
    @FXML TextField senhaField;
    @FXML TextArea sobremimArea;
    @FXML Button botaoImagem;
    @FXML ImageView imagemPreview;
    @FXML Button botaoVoltar;
    @FXML Button botaoCriarConta;
    @FXML StackPane pagContent;
    @FXML Label sucessLabel;
    @FXML Label mensagemErro;

    @FXML
    public void initialize() {
        connection = ConexaoBanco.getConnection();
        botaoCriarConta.setOnAction(event -> criarPerfil());
        botaoImagem.setOnAction(event -> selecionarImagem());
        mensagemErro.setVisible(false);
    }

    @FXML
    private void goToEntrarConta(ActionEvent event) {
        loadScreen("/com/example/App_LP3/Telas_Perfil/Perfil_Entrar.fxml");
    }

    @FXML
    private void criarPerfil(){
        adicionarUsuario();
    }

    private Connection connection;
    private File imagemSelecionada = null;

    public void setMainContent(StackPane mainContent){
        this.pagContent = mainContent;
    }

    private void selecionarImagem() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Selecionar Foto de Perfil");
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

    private void adicionarUsuario() {
        String nome = nicknameField.getText();
        String senha = senhaField.getText();
        String sobremim = sobremimArea.getText();
        String nomeImagem = (imagemSelecionada != null) ? imagemSelecionada.getName() : null;

        if (nome.isEmpty() || senha.isEmpty() || sobremim.isEmpty()) {
            System.out.println("Preencha todos os campos.");
            mensagemErro.setText("Preencha todos os campos.");
            mensagemErro.setVisible(true);
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

        String sql = "INSERT INTO usuarios (nickname, senha, sobre_mim, foto_perfil) VALUES (?, ?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, nome);
            stmt.setString(2, senha);
            stmt.setString(3, sobremim);
            stmt.setString(4, nomeImagem);

            int linhasAfetadas = stmt.executeUpdate();
            if (linhasAfetadas > 0) {
                System.out.println("Usuario adicionado com sucesso!");
                sucessLabel.setVisible(true);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao adicionar usuario.");
            mensagemErro.setText("Erro ao adicionar usuario.");
            mensagemErro.setVisible(true);
            e.printStackTrace();
        }
    }

    private void loadScreen(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent screen = loader.load();

            Object controller = loader.getController();
            if (controller instanceof LoginPerfilController){
                ((LoginPerfilController) controller).setMainContent(pagContent);
            }
            pagContent.getChildren().setAll(screen);
        } catch (IOException e) {
            System.out.println("Erro ao carregar a tela: " + fxmlPath);
            e.printStackTrace();
        }
    }
}
