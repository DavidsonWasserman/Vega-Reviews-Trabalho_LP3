package com.example.trabalho_LP3;

public class Review {
    private final String nomeJogo;
    private final String jogador;
    private final int nota;
    private final String comentario;

    public Review(String nomeJogo, String jogador, int nota, String comentario) {
        this.nomeJogo = nomeJogo;
        this.jogador = jogador;
        this.nota = nota;
        this.comentario = comentario;
    }

    public String getNomeJogo() {
        return nomeJogo;
    }

    public String getJogador() {
        return jogador;
    }

    public int getNota() {
        return nota;
    }

    public String getComentario() {
        return comentario;
    }

    @Override
    public String toString() {
        int limite = 20;
        String resumo = comentario.length() > limite ? comentario.substring(0, limite) + "..." : comentario;
        return String.format("Review de %s\nNota: %d\nComentario: \"%s\"", nomeJogo, nota, resumo);
    }
}
