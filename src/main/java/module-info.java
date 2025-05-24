module com.example.trabalho_LP3 {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;

    opens com.example.trabalho_LP3 to javafx.fxml;
    exports com.example.trabalho_LP3;
    exports com.example.trabalho_LP3.controllers;
    opens com.example.trabalho_LP3.controllers to javafx.fxml;
}