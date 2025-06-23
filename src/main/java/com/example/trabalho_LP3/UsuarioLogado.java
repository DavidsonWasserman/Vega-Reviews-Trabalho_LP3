package com.example.trabalho_LP3;

public class UsuarioLogado {
    private static int id;
    private static String nickname;

    public static void iniciarSessao(int usuarioId, String usuarioNickname) {
        id = usuarioId;
        nickname = usuarioNickname;
    }

    public static int getId() {
        return id;
    }

    public static String getNickname() {
        return nickname;
    }

    public static void encerrarSessao() {
        id = 0;
        nickname = null;
    }
}
