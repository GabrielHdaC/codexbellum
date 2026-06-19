package app.menu;

import app.batalha.GerenciadorBatalha;
import combate.Batalha;
import modelo.Personagem;
import repositorio.FichaInvalidaException;
import repositorio.FichaRepositorio;
import repositorio.PersistenciaException;

import java.util.Scanner;

/**
 * Executa as opções do menu — a "cola" entre a escolha do usuário e as ações
 * sobre o repositório (e a batalha). Tira do Principal a lógica de cada opção,
 * deixando o ponto de entrada só com o laço e a exibição do menu.
 */
public class GerenciadorMenu {

    private final FichaRepositorio repo;
    private final Batalha batalha;
    private final Scanner sc;

    public GerenciadorMenu(FichaRepositorio repo, Batalha batalha, Scanner sc) {
        this.repo = repo;
        this.batalha = batalha;
        this.sc = sc;
    }

    /** Despacha a opção escolhida para a ação correspondente. */
    public void executar(int opcao) {
        switch (opcao) {
            case 1 -> carregarFichas();
            case 2 -> listarFichas();
            case 3 -> ordenarPorVida();
            case 4 -> exportar();
            case 5 -> importar();
            case 6 -> new GerenciadorBatalha(repo, batalha, sc).iniciar();
            case 0 -> System.out.println("Até a próxima!");
            default -> System.out.println("Opção não encontrada");
        }
    }

    /** Carrega o src/fichas.txt; se o arquivo tiver problema, avisa em vez de quebrar o menu. */
    private void carregarFichas() {
        try {
            repo.lerTxt("src/fichas.txt");
            System.out.println(repo.getFichas().size() + " fichas carregadas!");
        } catch (FichaInvalidaException | PersistenciaException e) {
            System.out.println("Arquivo com problema: " + e.getMessage());
        }
    }

    /** Exporta a base; a mensagem (sucesso ou erro) é decidida aqui, não na persistência. */
    private void exportar() {
        try {
            repo.exportarDat("fichas.dat");
            System.out.println("Base exportada com sucesso!");
        } catch (PersistenciaException e) {
            System.out.println(e.getMessage());
        }
    }

    /** Importa a base; como no exportar, o repositório sinaliza o erro e o menu decide o que mostrar. */
    private void importar() {
        try {
            repo.importarDat("fichas.dat");
            System.out.println("Base importada com sucesso!");
        } catch (PersistenciaException e) {
            System.out.println(e.getMessage());
        }
    }

    private void listarFichas() {
        for (Personagem personagem : repo.getFichas()) {
            System.out.println(personagem.getNome() + " | vida: " + personagem.getVida());
        }
    }

    private void ordenarPorVida() {
        repo.ordenarPorVida();
        System.out.println("Fichas ordenadas por vida!");
    }
}
