package app.menu;

import app.batalha.GerenciadorBatalha;
import combate.Batalha;
import modelo.Personagem;
import repositorio.FichaInvalidaException;
import repositorio.FichaRepositorio;

import java.util.Scanner;

/**
 * O que é: o executor das opções do menu — a "cola" entre a escolha do usuário
 * e as ações sobre o repositório (e a batalha).
 * O que faz: recebe a opção digitada e dispara a ação correspondente: carregar,
 * listar, ordenar, exportar/importar a base, ou iniciar um combate.
 * Por que assim: tira do Principal a lógica de cada opção, deixando o ponto de
 * entrada só com o laço e a exibição do menu — cada classe com um papel.
 */
public class GerenciadorMenu {

    private final FichaRepositorio repo;
    private final Batalha batalha;
    private final Scanner sc;

    /**
     * O que é: o construtor do executor de opções.
     * O que faz: guarda as dependências que as ações usam (fichas, regras de
     * combate e leitor de entrada).
     * Por que assim: recebendo tudo pronto, os métodos das ações ficam sem
     * parâmetros repetidos e o Principal só monta o objeto e chama executar().
     *
     * @param repo    repositório com as fichas
     * @param batalha coordenador de combate
     * @param sc      leitor da entrada do console
     */
    public GerenciadorMenu(FichaRepositorio repo, Batalha batalha, Scanner sc) {
        this.repo = repo;
        this.batalha = batalha;
        this.sc = sc;
    }

    /**
     * O que é: o despacho de uma opção do menu.
     * O que faz: olha a opção escolhida e chama a ação correspondente; a batalha
     * é delegada por inteiro ao GerenciadorBatalha.
     * Por que assim: switch de seta (sem break e sem blocos redundantes), cada
     * case numa linha apontando para a ação — fica de baixa complexidade.
     *
     * @param opcao opção escolhida no menu
     */
    public void executar(int opcao) {
        switch (opcao) {
            case 1 -> carregarFichas();                     // carrega o fichas.txt
            case 2 -> listarFichas();                       // lista nome e vida de todas
            case 3 -> ordenarPorVida();                     // ordena da menor vida para a maior
            case 4 -> repo.exportarDat("fichas.dat");       // salva a base em binário (.dat)
            case 5 -> repo.importarDat("fichas.dat");       // recarrega a base do binário
            case 6 -> new GerenciadorBatalha(repo, batalha, sc).iniciar(); // combate: herói (você) x monstro
            case 0 -> System.out.println("Até a próxima!"); // encerra o programa
            default -> System.out.println("Opção não encontrada");
        }
    }

    /**
     * O que é: a ação de carregar as fichas do arquivo texto.
     * O que faz: lê o src/fichas.txt para o repositório e informa quantas
     * entraram; se houver uma ficha inválida, avisa em vez de quebrar o menu.
     * Por que assim: o try/catch fica isolado aqui, tratando o erro de domínio
     * localmente sem aninhar com o tratamento de erro do laço do menu.
     */
    private void carregarFichas() {
        try {
            repo.lerTxt("src/fichas.txt");
            System.out.println(repo.getFichas().size() + " fichas carregadas!");
        } catch (FichaInvalidaException e) {
            System.out.println("Arquivo com problema: " + e.getMessage());
        }
    }

    /**
     * O que é: a ação de listar a base atual.
     * O que faz: imprime nome e vida de cada ficha carregada.
     * Por que assim: método curto e único responsável pela listagem.
     */
    private void listarFichas() {
        for (Personagem personagem : repo.getFichas()) {
            System.out.println(personagem.getNome() + " | vida: " + personagem.getVida());
        }
    }

    /**
     * O que é: a ação de ordenar a base por vida.
     * O que faz: ordena as fichas da menor para a maior vida e confirma na tela.
     * Por que assim: a ordenação em si é do repositório; aqui só disparamos e avisamos.
     */
    private void ordenarPorVida() {
        repo.ordenarPorVida();
        System.out.println("Fichas ordenadas por vida!");
    }
}
