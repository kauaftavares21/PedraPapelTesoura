package br.edu.atps.pedrapapeltesoura;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

/**
 * Tela principal do jogo. Cuida apenas da interface:
 * as regras ficam na classe {@link Jogo}.
 */
public class MainActivity extends AppCompatActivity {

    // Chaves para salvar o estado quando a tela é girada
    private static final String CHAVE_PONTOS_JOGADOR = "pontos_jogador";
    private static final String CHAVE_PONTOS_COMPUTADOR = "pontos_computador";
    private static final String CHAVE_JOGADA_JOGADOR = "jogada_jogador";
    private static final String CHAVE_JOGADA_COMPUTADOR = "jogada_computador";

    private final Jogo jogo = new Jogo();

    // Última rodada (null quando nenhuma rodada está sendo exibida)
    private Jogada ultimaJogadaJogador;
    private Jogada ultimaJogadaComputador;

    private TextView tvPontosJogador;
    private TextView tvPontosComputador;
    private ImageView ivJogadaJogador;
    private ImageView ivJogadaComputador;
    private TextView tvJogadaComputador;
    private TextView tvResultado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inicializarComponentes();
        configurarBotoes();

        if (savedInstanceState != null) {
            restaurarEstado(savedInstanceState);
        } else {
            atualizarPlacar();
            limparRodada();
        }
    }

    private void inicializarComponentes() {
        tvPontosJogador = findViewById(R.id.tvPontosJogador);
        tvPontosComputador = findViewById(R.id.tvPontosComputador);
        ivJogadaJogador = findViewById(R.id.ivJogadaJogador);
        ivJogadaComputador = findViewById(R.id.ivJogadaComputador);
        tvJogadaComputador = findViewById(R.id.tvJogadaComputador);
        tvResultado = findViewById(R.id.tvResultado);
    }

    private void configurarBotoes() {
        ImageButton btnPedra = findViewById(R.id.btnPedra);
        ImageButton btnPapel = findViewById(R.id.btnPapel);
        ImageButton btnTesoura = findViewById(R.id.btnTesoura);
        Button btnNovaJogada = findViewById(R.id.btnNovaJogada);
        Button btnFinalizar = findViewById(R.id.btnFinalizar);

        btnPedra.setOnClickListener(v -> jogar(Jogada.PEDRA));
        btnPapel.setOnClickListener(v -> jogar(Jogada.PAPEL));
        btnTesoura.setOnClickListener(v -> jogar(Jogada.TESOURA));

        btnNovaJogada.setOnClickListener(v -> limparRodada());
        btnFinalizar.setOnClickListener(v -> finalizarJogo());
    }

    /** Executa uma rodada com a escolha do jogador. */
    private void jogar(Jogada jogadaJogador) {
        Jogada jogadaComputador = jogo.sortearJogadaComputador();
        Resultado resultado = jogo.jogarRodada(jogadaJogador, jogadaComputador);

        ultimaJogadaJogador = jogadaJogador;
        ultimaJogadaComputador = jogadaComputador;

        mostrarRodada(jogadaJogador, jogadaComputador, resultado, true);
        atualizarPlacar();
    }

    /** Exibe as jogadas e a mensagem de resultado na tela. */
    private void mostrarRodada(Jogada jogador, Jogada computador,
                               Resultado resultado, boolean animar) {
        ivJogadaJogador.setImageResource(iconeDa(jogador));
        ivJogadaComputador.setImageResource(iconeDa(computador));
        ivJogadaJogador.setContentDescription(jogador.getNome());
        ivJogadaComputador.setContentDescription(computador.getNome());

        tvJogadaComputador.setText(getString(R.string.jogada_computador, computador.getNome()));

        int texto;
        int cor;
        if (resultado == Resultado.VITORIA) {
            texto = R.string.resultado_vitoria;
            cor = R.color.vitoria;
        } else if (resultado == Resultado.DERROTA) {
            texto = R.string.resultado_derrota;
            cor = R.color.derrota;
        } else {
            texto = R.string.resultado_empate;
            cor = R.color.empate;
        }
        tvResultado.setText(texto);
        tvResultado.setTextColor(ContextCompat.getColor(this, cor));
        tvResultado.setVisibility(View.VISIBLE);

        if (animar) {
            animarEntrada(ivJogadaComputador);
            animarEntrada(tvResultado);
        }
    }

    /** Pequena animação de "pulo" para dar retorno visual ao jogador. */
    private void animarEntrada(View view) {
        view.setScaleX(0.6f);
        view.setScaleY(0.6f);
        view.setAlpha(0f);
        view.animate().scaleX(1f).scaleY(1f).alpha(1f).setDuration(250).start();
    }

    private int iconeDa(Jogada jogada) {
        switch (jogada) {
            case PEDRA:
                return R.drawable.ic_pedra;
            case PAPEL:
                return R.drawable.ic_papel;
            default:
                return R.drawable.ic_tesoura;
        }
    }

    private void atualizarPlacar() {
        tvPontosJogador.setText(String.valueOf(jogo.getPontosJogador()));
        tvPontosComputador.setText(String.valueOf(jogo.getPontosComputador()));
    }

    /** "Nova jogada": limpa a rodada atual, mantendo o placar. */
    private void limparRodada() {
        ultimaJogadaJogador = null;
        ultimaJogadaComputador = null;

        ivJogadaJogador.setImageResource(R.drawable.ic_interrogacao);
        ivJogadaComputador.setImageResource(R.drawable.ic_interrogacao);
        ivJogadaJogador.setContentDescription(getString(R.string.aguardando));
        ivJogadaComputador.setContentDescription(getString(R.string.aguardando));
        tvJogadaComputador.setText(R.string.escolha_sua_jogada);
        tvResultado.setText("");
        tvResultado.setVisibility(View.INVISIBLE);
    }

    /** "Finalizar o jogo": mostra o placar final e pergunta o que fazer. */
    private void finalizarJogo() {
        int jogador = jogo.getPontosJogador();
        int computador = jogo.getPontosComputador();

        String vencedor;
        if (jogador > computador) {
            vencedor = getString(R.string.final_vitoria);
        } else if (computador > jogador) {
            vencedor = getString(R.string.final_derrota);
        } else {
            vencedor = getString(R.string.final_empate);
        }

        new AlertDialog.Builder(this)
                .setTitle(R.string.fim_de_jogo)
                .setMessage(getString(R.string.placar_final, jogador, computador, vencedor))
                .setCancelable(false)
                .setPositiveButton(R.string.jogar_novamente, (dialog, which) -> {
                    jogo.reiniciar();
                    atualizarPlacar();
                    limparRodada();
                })
                .setNegativeButton(R.string.sair, (dialog, which) -> finish())
                .show();
    }

    // ---------- Manter o estado ao girar a tela ----------

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(CHAVE_PONTOS_JOGADOR, jogo.getPontosJogador());
        outState.putInt(CHAVE_PONTOS_COMPUTADOR, jogo.getPontosComputador());
        if (ultimaJogadaJogador != null) {
            outState.putString(CHAVE_JOGADA_JOGADOR, ultimaJogadaJogador.name());
            outState.putString(CHAVE_JOGADA_COMPUTADOR, ultimaJogadaComputador.name());
        }
    }

    private void restaurarEstado(Bundle estado) {
        jogo.restaurarPlacar(
                estado.getInt(CHAVE_PONTOS_JOGADOR),
                estado.getInt(CHAVE_PONTOS_COMPUTADOR));
        atualizarPlacar();

        String jogador = estado.getString(CHAVE_JOGADA_JOGADOR);
        String computador = estado.getString(CHAVE_JOGADA_COMPUTADOR);
        if (jogador != null && computador != null) {
            ultimaJogadaJogador = Jogada.valueOf(jogador);
            ultimaJogadaComputador = Jogada.valueOf(computador);
            Resultado resultado = Jogo.determinarVencedor(ultimaJogadaJogador, ultimaJogadaComputador);
            mostrarRodada(ultimaJogadaJogador, ultimaJogadaComputador, resultado, false);
        } else {
            limparRodada();
        }
    }
}
