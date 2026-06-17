package app;

import app.menu.GerenciadorMenu;
import combate.Batalha;
import repositorio.FichaRepositorio;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * O que é: ponto de entrada do CodexBellum — a tela do menu de console.
 * O que faz: monta as dependências, exibe o menu e, a cada opção lida, pede ao
 * GerenciadorMenu para executá-la, repetindo até o usuário digitar 0.
 * Por que assim: o Principal cuida só do laço e da exibição do menu; o que cada
 * opção faz mora no GerenciadorMenu, mantendo o ponto de entrada minúsculo.
 */
public class Principal {

    /**
     * O que é: o laço principal do programa.
     * O que faz: repete "mostrar menu → ler opção → executar" até o usuário
     * digitar 0, tratando entrada inválida e índice inexistente.
     * Por que assim: a leitura e o tratamento de erro ficam num só lugar; a
     * execução da opção é delegada, então o main permanece curto.
     *
     * @param args não utilizado
     */
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        FichaRepositorio fichaRepositorio = new FichaRepositorio();
        Batalha batalha = new Batalha();
        // main é static; exibirMenu é de instância, daí criamos um Principal
        Principal principal = new Principal();
        GerenciadorMenu menu = new GerenciadorMenu(fichaRepositorio, batalha, sc);

        // começa em -1 para que um erro de leitura não encerre o loop por engano
        int opcao = -1;
        do {
            principal.exibirMenu();
            try {
                opcao = sc.nextInt();
                menu.executar(opcao);
            } catch (InputMismatchException e) {
                System.out.println("Digite um número válido!");
                // o texto inválido fica preso no Scanner; sem descartá-lo, o
                // próximo nextInt falharia de novo para sempre (loop infinito)
                sc.nextLine();
            } catch (IndexOutOfBoundsException e) {
                System.out.println("Não existe ficha com esse número!");
            }
        } while (opcao != 0);

        sc.close();
    }

    /**
     * O que é: a tela do menu.
     * O que faz: apenas imprime as opções disponíveis.
     * Por que assim: separa a exibição da decisão — quem lê e roteia é o main,
     * e quem executa cada opção é o GerenciadorMenu.
     */
    public void exibirMenu() {
        System.out.println("=== CodexBellum ===");
        System.out.println("1 - Carregar fichas do txt");
        System.out.println("2 - Listar fichas");
        System.out.println("3 - Ordenar por vida");
        System.out.println("4 - Exportar base (.dat)");
        System.out.println("5 - Importar base (.dat)");
        System.out.println("6 - Batalhar");
        System.out.println("0 - Sair");
    }
}
