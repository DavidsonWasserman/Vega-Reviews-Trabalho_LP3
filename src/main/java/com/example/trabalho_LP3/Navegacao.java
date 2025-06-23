package com.example.trabalho_LP3;

import com.example.trabalho_LP3.controllers.HomeController;
import com.example.trabalho_LP3.controllers.add.AddReviewController;
import com.example.trabalho_LP3.controllers.perfil.MeuPerfilController;
import com.example.trabalho_LP3.controllers.searchBiblioteca.UsuariosController;
import com.example.trabalho_LP3.controllers.exibicaoDetalhes.JogoController;
import com.example.trabalho_LP3.controllers.searchBiblioteca.JogosController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.util.Stack;

public class Navegacao {
    private static StackPane mainContent;
    private static final Stack<String> history = new Stack<>();

    public static void setMainContent(StackPane content) {
        mainContent = content;
    }

    public static void navigateTo(String fxmlPath) {
        if (mainContent == null) {
            System.out.println("mainContent ainda não foi definido!");
            return;
        }

        if (mainContent.getChildren().size() > 0) {
            Node currentNode = mainContent.getChildren().get(0);
            if (!history.isEmpty() && history.peek().equals(fxmlPath)) return;
            history.push(getCurrentFxml(currentNode));
        }

        try {
            FXMLLoader loader = new FXMLLoader(Navegacao.class.getResource(fxmlPath));
            Parent screen = loader.load();

            Object controller = loader.getController();
            if (controller instanceof JogosController) {
                ((JogosController) controller).setMainContent(mainContent);
            } else if (controller instanceof UsuariosController) {
                ((UsuariosController) controller).setMainContent(mainContent);
            } else if (controller instanceof JogoController) {
                ((JogoController) controller).setMainContent(mainContent);
            }else if (controller instanceof HomeController) {
                ((HomeController) controller).setMainContent(mainContent);
            } else if (controller instanceof MeuPerfilController) {
                ((MeuPerfilController) controller).setMainContent(mainContent);
            } else if (controller instanceof AddReviewController) {
                ((AddReviewController) controller).setMainContent(mainContent);
            }

            mainContent.getChildren().setAll(screen);
        } catch (IOException e) {
            System.out.println("Erro ao carregar a tela: " + fxmlPath);
            e.printStackTrace();
        }
    }
    private static String getCurrentFxml(Node node) {
        return "";
    }
}
