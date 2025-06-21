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
    requires java.desktop;
    requires java.sql;
    requires annotations;
    requires com.google.protobuf;

    opens com.example.trabalho_LP3 to javafx.fxml;
    exports com.example.trabalho_LP3;
    exports com.example.trabalho_LP3.controllers;
    opens com.example.trabalho_LP3.controllers to javafx.fxml;
    exports com.example.trabalho_LP3.controllers.searchBiblioteca;
    opens com.example.trabalho_LP3.controllers.searchBiblioteca to javafx.fxml;
    exports com.example.trabalho_LP3.controllers.exibicaoDetalhes;
    opens com.example.trabalho_LP3.controllers.exibicaoDetalhes to javafx.fxml;
    exports com.example.trabalho_LP3.controllers.add;
    opens com.example.trabalho_LP3.controllers.add to javafx.fxml;
    opens com.example.trabalho_LP3.controllers.perfil to javafx.fxml;
}