package com.example.trabalho_LP3;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class ConexaoBanco {
    private static final String URL = "jdbc:mysql://localhost:3306/banco_projeto_lp3?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String USER = "adminlp3";
    private static final String PASSWORD ="adminlp3";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
