package br.edu.atps.pedrapapeltesoura;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * Testes da lógica do jogo (rodam no computador, sem emulador).
 */
public class JogoTest {

    @Test
    public void jogadasIguaisEmpatam() {
        for (Jogada j : Jogada.values()) {
            assertEquals(Resultado.EMPATE, Jogo.determinarVencedor(j, j));
        }
    }

    @Test
    public void regrasDeVitoria() {
        assertEquals(Resultado.VITORIA, Jogo.determinarVencedor(Jogada.PEDRA, Jogada.TESOURA));
        assertEquals(Resultado.VITORIA, Jogo.determinarVencedor(Jogada.TESOURA, Jogada.PAPEL));
        assertEquals(Resultado.VITORIA, Jogo.determinarVencedor(Jogada.PAPEL, Jogada.PEDRA));
    }

    @Test
    public void regrasDeDerrota() {
        assertEquals(Resultado.DERROTA, Jogo.determinarVencedor(Jogada.TESOURA, Jogada.PEDRA));
        assertEquals(Resultado.DERROTA, Jogo.determinarVencedor(Jogada.PAPEL, Jogada.TESOURA));
        assertEquals(Resultado.DERROTA, Jogo.determinarVencedor(Jogada.PEDRA, Jogada.PAPEL));
    }

    @Test
    public void placarEAtualizadoACadaRodada() {
        Jogo jogo = new Jogo();
        jogo.jogarRodada(Jogada.PEDRA, Jogada.TESOURA); // vitória
        jogo.jogarRodada(Jogada.PEDRA, Jogada.PAPEL);   // derrota
        jogo.jogarRodada(Jogada.PAPEL, Jogada.PAPEL);   // empate
        jogo.jogarRodada(Jogada.TESOURA, Jogada.PAPEL); // vitória

        assertEquals(2, jogo.getPontosJogador());
        assertEquals(1, jogo.getPontosComputador());

        jogo.reiniciar();
        assertEquals(0, jogo.getPontosJogador());
        assertEquals(0, jogo.getPontosComputador());
    }
}
