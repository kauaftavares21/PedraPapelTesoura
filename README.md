# Pedra, Papel e Tesoura — ATPS Desenvolvimento Mobile (M2 2026)

**Aluno(a):** [SEU NOME COMPLETO]
**Matrícula:** [SUA MATRÍCULA]
**Professora:** Joseneuza Julita Pimenta de Aguiar

Aplicativo Android em **Java** (Android Studio) em que o usuário joga Pedra, Papel e Tesoura contra o computador.

## Funcionalidades

- Três botões grandes com ícone (ImageButton) e texto: **Pedra**, **Papel** e **Tesoura**
- Jogada do computador sorteada aleatoriamente (`java.util.Random`)
- Comparação das jogadas com estrutura condicional (`if / else if / else`)
- Placar de **Jogador** e **Computador** atualizado automaticamente a cada rodada
- Texto com a jogada do computador ("Computador: Papel") e as imagens das duas jogadas (ImageView)
- Resultado em destaque e com cor: **Você venceu!** (verde), **Você perdeu!** (vermelho), **Empate!**
- Botão **Nova jogada**: limpa a rodada e mantém o placar
- Botão **Finalizar o jogo**: mostra o placar final e quem ganhou, com opção de jogar novamente ou sair
- Layout responsivo: versão retrato e versão paisagem (duas colunas), com rolagem em telas pequenas
- Placar preservado ao girar a tela

## Estrutura do projeto

```
app/src/main/
├── AndroidManifest.xml
├── java/br/edu/atps/pedrapapeltesoura/
│   ├── MainActivity.java   → tela: liga os botões, mostra placar e resultado
│   ├── Jogo.java           → regras: sorteio, quem vence, placar
│   ├── Jogada.java         → enum PEDRA, PAPEL, TESOURA
│   └── Resultado.java      → enum VITORIA, DERROTA, EMPATE
└── res/
    ├── layout/activity_main.xml        → tela em retrato
    ├── layout-land/activity_main.xml   → tela em paisagem
    ├── drawable/                       → ícones vetoriais e fundos
    └── values/                         → cores, textos, estilos e dimensões
app/src/test/.../JogoTest.java          → testes automáticos da lógica
```

A lógica do jogo (`Jogo.java`) fica separada da interface (`MainActivity.java`), o que deixa o código mais organizado e permite testar as regras sem abrir o emulador.

## Como abrir e executar

1. Android Studio → **File › Open** → selecione a pasta `PedraPapelTesoura`
2. Aguarde o Gradle sincronizar (na primeira vez ele baixa as dependências)
3. Crie/escolha um emulador (Device Manager) ou conecte um celular com depuração USB
4. Clique em **Run ▶**

Requisitos: Android Studio recente (Ladybug ou mais novo), JDK 17 (já vem com o Android Studio), Android 8.0 (API 26) ou superior no aparelho.

Para rodar os testes: clique com o botão direito em `app/src/test/.../JogoTest.java` → **Run 'JogoTest'**.
