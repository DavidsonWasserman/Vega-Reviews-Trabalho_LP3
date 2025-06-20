package com.example.trabalho_LP3;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class ConexaoBanco {
    private static final String URL = "jdbc:mysql://localhost:3306/banco_projeto_lp3";
    private static final String USER = "adminlp3";
    private static final String PASSWORD ="adminlp3";

    private static Connection connection;

    public static Connection getConnection() {if (connection == null) {
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Conexão com o banco estabelecida!");
        } catch (SQLException e) {
            System.out.println("Erro ao conectar com o banco.");
            e.printStackTrace();
        }
    }
        return connection;
    }
}