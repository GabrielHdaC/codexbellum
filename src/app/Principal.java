package app;

import app.menu.GerenciadorMenu;
import combate.Batalha;
import repositorio.FichaRepositorio;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Ponto de entrada do CodexBellum: monta as dependências, exibe o menu e, a cada
 * opção lida, pede ao GerenciadorMenu para executá-la, até o usuário digitar 0.
 * O que cada opção faz mora no GerenciadorMenu, mantendo o main curto.
 */
public class Principal {

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
            System.out.print("Escolha uma opção: ");
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
