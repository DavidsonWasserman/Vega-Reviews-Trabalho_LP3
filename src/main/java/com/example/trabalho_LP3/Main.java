package com.example.trabalho_LP3;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        // Correção do caminho do FXML
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/App_LP3/views/Home.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root);
        primaryStage.setTitle("HOME");
        primaryStage.setScene(scene);
        primaryStage.setMinWidth(800);
        primaryStage.setMinHeight(600);
        primaryStage.show();
    }

    public static void main(String[] args) {
        // Adiciona a opção para resolver os warnings
        System.setProperty("prism.allowhidpi", "false");
        launch(args);
    }
}