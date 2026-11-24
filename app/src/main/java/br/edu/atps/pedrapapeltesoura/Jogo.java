package br.edu.atps.pedrapapeltesoura;

import java.util.Random;

/**
 * Regras do jogo Pedra, Papel e Tesoura e controle do placar.
 * Esta classe não depende do Android, o que facilita testar a lógica.
 */
public class Jogo {

    private final Random random;
    private int pontosJogador = 0;
    private int pontosComputador = 0;

    public Jogo() {
        this(new Random());
    }

    // Construtor usado nos testes, para controlar o sorteio
    public Jogo(Random random) {
        this.random = random;
    }

    /** Sorteia aleatoriamente a jogada do computador. */
    public Jogada sortearJogadaComputador() {
        Jogada[] opcoes = Jogada.values();
        return opcoes[random.nextInt(opcoes.length)];
    }

    /**
     * Compara as jogadas usando estrutura condicional e devolve o
     * resultado do ponto de vista do jogador.
     *
     * Regras: Pedra quebra Tesoura, Tesoura corta Papel, Papel embrulha Pedra.
     */
    public static Resultado determinarVencedor(Jogada jogador, Jogada computador) {
        if (jogador == computador) {
            return Resultado.EMPATE;
        } else if ((jogador == Jogada.PEDRA && computador == Jogada.TESOURA)
                || (jogador == Jogada.TESOURA && computador == Jogada.PAPEL)
                || (jogador == Jogada.PAPEL && computador == Jogada.PEDRA)) {
            return Resultado.VITORIA;
        } else {
            return Resultado.DERROTA;
        }
    }

    /** Registra o resultado de uma rodada, atualizando o placar. */
    public Resultado jogarRodada(Jogada jogador, Jogada computador) {
        Resultado resultado = determinarVencedor(jogador, computador);

        if (resultado == Resultado.VITORIA) {
            pontosJogador++;
        } else if (resultado == Resultado.DERROTA) {
            pontosComputador++;
        }
        return resultado;
    }

    /** Zera o placar para começar um novo jogo. */
    public void reiniciar() {
        pontosJogador = 0;
        pontosComputador = 0;
    }

    public int getPontosJogador() {
        return pontosJogador;
    }

    public int getPontosComputador() {
        return pontosComputador;
    }

    // Usado para restaurar o placar quando a tela é girada
    public void restaurarPlacar(int pontosJogador, int pontosComputador) {
        this.pontosJogador = pontosJogador;
        this.pontosComputador = pontosComputador;
    }
}
