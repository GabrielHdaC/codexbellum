package app.batalha;

import combate.Batalha;
import modelo.Heroi;
import modelo.Monstro;
import modelo.Personagem;
import repositorio.FichaRepositorio;

import java.util.Scanner;

/**
 * O que é: o controlador da batalha interativa do console — um Herói (você)
 * contra um Monstro (a "IA").
 * O que faz: escolhe e valida os combatentes, roda os turnos até alguém zerar
 * a vida e anuncia o vencedor.
 * Por que assim: tirar essa lógica do Principal deixa o ponto de entrada só com
 * o menu; aqui fica a responsabilidade única de conduzir um combate.
 */
public class GerenciadorBatalha {

    private final FichaRepositorio repo;
    private final Batalha batalha;
    private final Scanner sc;

    /**
     * O que é: o construtor do controlador de batalha.
     * O que faz: guarda as dependências que o combate usa (fichas, regras de
     * turno e leitor de entrada).
     * Por que assim: recebendo tudo pronto, os métodos internos ficam sem
     * parâmetros repetidos e o Principal só precisa montar o objeto e chamar iniciar().
     *
     * @param repo    repositório com as fichas
     * @param batalha coordenador de turno (calcula dano e aplica defesa)
     * @param sc      leitor da entrada do console
     */
    public GerenciadorBatalha(FichaRepositorio repo, Batalha batalha, Scanner sc) {
        this.repo = repo;
        this.batalha = batalha;
        this.sc = sc;
    }

    /**
     * O que é: a entrada pública do combate.
     * O que faz: valida que há fichas, deixa escolher herói e monstro e roda os
     * turnos até alguém cair, anunciando o vencedor.
     * Por que assim: o jogador só controla Heróis e mortos não voltam à arena —
     * por isso as validações antes do laço; o Principal chama só este método.
     */
    public void iniciar() {
        if (repo.getFichas().isEmpty()) {
            System.out.println("Carregue as fichas primeiro (opção 1)!");
            return;
        }

        Heroi heroi = selecionar(Heroi.class, "seu Herói", "Você só pode controlar Heróis!");
        if (heroi == null) {
            return; // a mensagem do motivo já saiu na seleção
        }
        Monstro monstro = selecionar(Monstro.class, "Monstro inimigo", "O inimigo precisa ser um Monstro!");
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
     * O que é: a escolha validada de um combatente da base.
     * O que faz: lê um índice e devolve o personagem só se ele for do tipo pedido
     * (Heroi ou Monstro) e estiver vivo; caso contrário, explica o motivo e
     * devolve null.
     * Por que assim: herói e monstro tinham a MESMA validação (tipo + vivo)
     * duplicada; um único método parametrizado pelo tipo elimina a repetição, e
     * devolver null sinaliza ao chamador para abortar a batalha sem exceção.
     *
     * @param tipo     classe esperada do combatente (Heroi.class ou Monstro.class)
     * @param papel    como o combatente aparece na pergunta ao jogador
     * @param erroTipo mensagem exibida quando o escolhido não é do tipo esperado
     * @param <T>      tipo do combatente selecionado
     * @return o combatente escolhido, ou null se a escolha for inválida
     */
    private <T extends Personagem> T selecionar(Class<T> tipo, String papel, String erroTipo) {
        System.out.println("Número do " + papel + " (0 a " + (repo.getFichas().size() - 1) + "):");
        Personagem escolhido = repo.getFichas().get(sc.nextInt());
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
     * O que é: o turno do jogador.
     * O que faz: pergunta a ação (1 atacar / 2 habilidade) e executa — atacar
     * passa pela Batalha (o monstro defende), habilidade cura o próprio herói.
     * Por que assim: o ataque vai pela Batalha para o monstro poder reduzir o
     * dano; uma ação inválida só perde o turno, sem travar o combate.
     *
     * @param heroi   herói do jogador
     * @param monstro inimigo
     */
    private void turnoDoHeroi(Heroi heroi, Monstro monstro) {
        System.out.println("Sua ação: 1 - Atacar | 2 - Usar Habilidade");
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

    /**
     * O que é: o turno do monstro, jogado pela "IA" simples.
     * O que faz: consulta deveUsarHabilidade() — cura-se se estiver ferido,
     * senão ataca o herói pela Batalha.
     * Por que assim: a decisão mora no Monstro (é o "cérebro" dele); aqui só
     * executamos a jogada escolhida e narramos o resultado.
     *
     * @param heroi   herói do jogador
     * @param monstro inimigo
     */
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
