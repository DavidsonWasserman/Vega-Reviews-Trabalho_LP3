package com.example.teste_trabalho;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage homePag) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/com/example/App_LP3/HOME.fxml"));
        Scene scene = new Scene(root);

        homePag.setTitle("HOME");
        homePag.setScene(scene);

        homePag.setMinWidth(600);
        homePag.setMinHeight(400);

        homePag.show();
    }

    public static void main(String[] args) {
        launch();
    }
}