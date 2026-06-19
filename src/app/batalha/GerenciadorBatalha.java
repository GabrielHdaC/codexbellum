package app.batalha;

import combate.Batalha;
import modelo.Heroi;
import modelo.Monstro;
import modelo.Personagem;
import repositorio.FichaRepositorio;

import java.util.List;
import java.util.Scanner;

/**
 * Conduz a batalha interativa do console — um Herói (você) contra um Monstro
 * (a "IA"): escolhe e valida os combatentes, roda os turnos e anuncia o vencedor.
 * Tirar isso do Principal deixa o ponto de entrada só com o menu.
 */
public class GerenciadorBatalha {

    /** Quantos candidatos no máximo a tela lista antes de resumir o restante. */
    private static final int MAX_LISTAGEM = 10;

    private final FichaRepositorio repo;
    private final Batalha batalha;
    private final Scanner sc;

    public GerenciadorBatalha(FichaRepositorio repo, Batalha batalha, Scanner sc) {
        this.repo = repo;
        this.batalha = batalha;
        this.sc = sc;
    }

    /**
     * Entrada do combate: valida que há fichas, deixa escolher herói e monstro e
     * roda os turnos até alguém cair. O jogador só controla Heróis e mortos não
     * entram na arena — daí as validações antes do laço.
     */
    public void iniciar() {
        if (repo.getFichas().isEmpty()) {
            System.out.println("Carregue as fichas primeiro (opção 1)!");
            return;
        }

        Heroi heroi = selecionar(Heroi.class, "Herói", "Você só pode controlar Heróis!");
        if (heroi == null) {
            return; // a mensagem do motivo já saiu na seleção
        }
        Monstro monstro = selecionar(Monstro.class, "Monstro", "O inimigo precisa ser um Monstro!");
        if (monstro == null) {
            return;
        }

        // turnos alternados: o herói age e, se o monstro sobreviver, ele revida
        while (heroi.estaVivo() && monstro.estaVivo()) {
            turnoDoHeroi(heroi, monstro);
            if (!monstro.estaVivo()) {
                break; // morreu no golpe do herói: não chega a revidar
            }
            turnoDoMonstro(heroi, monstro);
        }

        System.out.println(heroi.estaVivo()
                ? "Você venceu a batalha!"
                : "Você foi derrotado...");
    }

    /**
     * Escolhe um combatente da base: lista os candidatos, lê o índice e devolve o
     * personagem só se for do tipo pedido e estiver vivo; senão avisa e devolve
     * null. Um único método parametrizado pelo tipo evita duplicar essa validação
     * para herói e monstro.
     *
     * @return o combatente escolhido, ou null se a escolha for inválida
     */
    private <T extends Personagem> T selecionar(Class<T> tipo, String papel, String erroTipo) {
        List<Personagem> fichas = repo.getFichas();

        Integer sugestao = listarCandidatos(fichas, tipo, papel);
        if (sugestao == null) {
            System.out.println("Não há " + papel + " vivo disponível para a batalha!");
            return null;
        }

        System.out.print("Digite o número do " + papel + " (sugestão: " + sugestao
                + ") [0 a " + (fichas.size() - 1) + "]: ");
        Personagem escolhido = fichas.get(sc.nextInt());
        if (!tipo.isInstance(escolhido)) {
            System.out.println(erroTipo);
            return null;
        }
        T combatente = tipo.cast(escolhido);
        if (!combatente.estaVivo()) {
            System.out.println(combatente.getNome() + " está morto e não pode batalhar!");
            return null;
        }
        return combatente;
    }

    /**
     * Lista os combatentes vivos do tipo pedido (índice, nome e vida), até
     * MAX_LISTAGEM, e devolve o índice do primeiro como sugestão. Sem essa lista
     * o jogador escolheria um índice de 0 a N no escuro.
     *
     * @return o índice do primeiro candidato vivo, ou null se não houver
     */
    private <T extends Personagem> Integer listarCandidatos(List<Personagem> fichas, Class<T> tipo, String papel) {
        Integer sugestao = null;
        int total = 0;

        System.out.println("=== " + papel + "s disponíveis (vivos) ===");
        for (int i = 0; i < fichas.size(); i++) {
            Personagem ficha = fichas.get(i);
            if (!tipo.isInstance(ficha) || !ficha.estaVivo()) {
                continue;
            }

            total++;
            if (sugestao == null) {
                sugestao = i; // o primeiro vivo do tipo vira a sugestão padrão
            }
            if (total <= MAX_LISTAGEM) {
                System.out.println("  [" + i + "] " + ficha.getNome() + " - vida " + ficha.getVida());
            }
        }

        if (total > MAX_LISTAGEM) {
            System.out.println("  ... e mais " + (total - MAX_LISTAGEM)
                    + " (pode digitar o número de qualquer um da base)");
        }
        return sugestao;
    }

    /** Turno do jogador: ataca (passa pela Batalha, o monstro defende) ou usa a habilidade. */
    private void turnoDoHeroi(Heroi heroi, Monstro monstro) {
        System.out.print("Sua ação (1 - Atacar | 2 - Usar Habilidade): ");
        switch (sc.nextInt()) {
            case 1 -> {
                batalha.executarTurno(heroi, monstro);
                System.out.println("Você atacou! Vida do monstro: " + monstro.getVida());
            }
            case 2 -> {
                heroi.usarHabilidade();
                System.out.println("Habilidade usada! Sua vida: " + heroi.getVida());
            }
            default -> System.out.println("Ação inválida, turno perdido!");
        }
    }

    /** Turno do monstro: a decisão (curar ou atacar) mora no próprio Monstro; aqui só executamos. */
    private void turnoDoMonstro(Heroi heroi, Monstro monstro) {
        if (monstro.deveUsarHabilidade()) {
            monstro.usarHabilidade();
            System.out.println(monstro.getNome() + " se curou! Vida: " + monstro.getVida());
        } else {
            batalha.executarTurno(monstro, heroi);
            System.out.println(monstro.getNome() + " atacou! Sua vida: " + heroi.getVida());
        }
    }
}
