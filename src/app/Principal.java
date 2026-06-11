package app;

import combate.Batalha;
import combate.Combatente;
import modelo.Personagem;
import repositorio.FichaRepositorio;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Principal {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        FichaRepositorio fichaRepositorio = new FichaRepositorio();
        Batalha batalha = new Batalha();
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
                        if (fichaRepositorio.getFichas().isEmpty()) {
                            System.out.println("Carregue as fichas primeiro (opção 1)!");
                            break;
                        }
                        System.out.println("Número do atacante (0 a " + (fichaRepositorio.getFichas().size() - 1) + "):");
                        int indiceAtacante = sc.nextInt();
                        System.out.println("Número do defensor:");
                        int indiceDefensor = sc.nextInt();

                        Personagem atacante = fichaRepositorio.getFichas().get(indiceAtacante);
                        Personagem defensor = fichaRepositorio.getFichas().get(indiceDefensor);

                        batalha.executarTurno((Combatente) atacante, (Combatente) defensor);
                        System.out.println(atacante.getNome() + " atacou " + defensor.getNome()
                                + "! Vida do defensor agora: " + defensor.getVida());
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
            } catch (IndexOutOfBoundsException e) {
                System.out.println("Não existe ficha com esse número!");
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
