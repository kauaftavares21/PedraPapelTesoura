package br.edu.atps.pedrapapeltesoura;

/**
 * As três jogadas possíveis do jogo.
 */
public enum Jogada {
    PEDRA("Pedra"),
    PAPEL("Papel"),
    TESOURA("Tesoura");

    private final String nome;

    Jogada(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }
}
