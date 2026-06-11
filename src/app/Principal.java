package app;

import modelo.Personagem;
import repositorio.FichaRepositorio;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Principal {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        FichaRepositorio fichaRepositorio = new FichaRepositorio();
        Principal principal = new Principal();

        int opcao = -1;
        do {
            principal.exibirMenu();
            try {
                opcao = sc.nextInt();

                switch (opcao) {
                    case 1:
                        fichaRepositorio.lerTxt("src/fichas.txt");
                        System.out.println(fichaRepositorio.getFichas().size() + " fichas carregadas!");
                        break;
                    case 2:
                        for (Personagem personagem : fichaRepositorio.getFichas()) {
                            System.out.println(personagem.getNome() + " | vida: " + personagem.getVida());
                        }
                        break;
                    case 3:
                        fichaRepositorio.ordenarPorVida();
                        System.out.println("Fichas ordenadas por vida!");
                        break;
                    case 4:
                        fichaRepositorio.exportarDat("fichas.dat");
                        break;
                    case 5:
                        fichaRepositorio.importarDat("fichas.dat");
                        break;
                    case 6:
                        // Falta implementar
                        break;
                    case 0:
                        System.out.println("Até a próxima!");
                        break;
                    default:
                        System.out.println("Opção não encontrada");
                        break;
                }
            } catch (InputMismatchException e) {
                System.out.println("Digite um número válido!");
                sc.nextLine();
            }
        } while (opcao != 0);
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
