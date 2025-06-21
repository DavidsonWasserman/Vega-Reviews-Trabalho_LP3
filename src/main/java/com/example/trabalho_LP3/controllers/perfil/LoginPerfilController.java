package com.example.trabalho_LP3.controllers.perfil;

import com.example.trabalho_LP3.ConexaoBanco;
import com.example.trabalho_LP3.UsuarioLogado;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginPerfilController {

    @FXML TextField nicknameField;
    @FXML PasswordField senhaField;
    @FXML Button botaoEntrar;
    @FXML Button botaoCriarConta;
    @FXML StackPane pagContent;
    @FXML Label mensagemErro;

    @FXML
    private void login() throws SQLException {
        String usuario = nicknameField.getText();
        String senha = senhaField.getText();

        if (usuario.isEmpty() || senha.isEmpty()) {
            mensagemErro.setText("Preencha todos os campos.");
            mensagemErro.setVisible(true);
            return;
        }

        String sql = "SELECT id, nickname FROM usuarios WHERE nickname = ? AND senha = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, usuario);
            stmt.setString(2, senha);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                int idUsuario = rs.getInt("id");
                String nicknameUsuario = rs.getString("nickname");
                UsuarioLogado.iniciarSessao(idUsuario, nicknameUsuario);

                System.out.println("Login concluído!");
                loadScreen("/com/example/App_LP3/Telas_Principais/Menu.fxml");
            } else {
                mensagemErro.setText("Nickname ou senha incorretos.");
                mensagemErro.setVisible(true);
            }

        } catch (SQLException e){
            System.out.println("Erro ao fazer login.");
            e.printStackTrace();
            mensagemErro.setText("Erro ao acessar o banco.");
            mensagemErro.setVisible(true);
        }
    }

    @FXML
    public void initialize() {
        connection = ConexaoBanco.getConnection();
        mensagemErro.setVisible(false);
    }

    @FXML
    private void goToCriarConta(ActionEvent event) {
        loadScreen("/com/example/App_LP3/Telas_Perfil/Perfil_Criar.fxml");
    }

    private Connection connection;

    public void setMainContent(StackPane mainContent){
        this.pagContent = mainContent;
    }

    private void loadScreen(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent screen = loader.load();

            Object controller = loader.getController();
            if (controller instanceof AddPerfilController){
                ((AddPerfilController) controller).setMainContent(pagContent);
            }
            pagContent.getChildren().setAll(screen);

        } catch (IOException e) {
            System.out.println("Erro ao carregar a tela: " + fxmlPath);
            e.printStackTrace();
        }
    }
}
