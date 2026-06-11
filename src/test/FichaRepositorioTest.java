package test;

import modelo.Personagem;
import org.junit.jupiter.api.Test;
import repositorio.FichaInvalidaException;
import repositorio.FichaRepositorio;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FichaRepositorioTest {

    private static final String ARQUIVO_FICHAS = "src/fichas.txt";

    @Test
    public void lerTxtDeveCarregarNoMinimo500Fichas() {
        FichaRepositorio repositorio = new FichaRepositorio();

        repositorio.lerTxt(ARQUIVO_FICHAS);

        assertTrue(repositorio.getFichas().size() >= 500,
                "o fichas.txt tem 520 registros, todos devem virar objetos");
    }

    @Test
    public void lerTxtDeveConverterOsCamposDaFicha() {
        FichaRepositorio repositorio = new FichaRepositorio();

        repositorio.lerTxt(ARQUIVO_FICHAS);

        Personagem primeiro = repositorio.getFichas().get(0);
        assertEquals("Aldric Dentebravo", primeiro.getNome(),
                "o arquivo esta em ordem alfabetica, a primeira ficha e Aldric Dentebravo");
        assertEquals(59, primeiro.getVida());
        assertEquals("Alabarda", primeiro.getArma().getNome());
        assertEquals(8, primeiro.getArma().getDano());
    }

    @Test
    public void lerTxtDeveLancarExcecaoParaFichaInvalida() throws IOException {
        Path arquivo = Files.createTempFile("fichas-invalidas", ".txt");
        Files.write(arquivo, List.of(
                "nome;sexo;classe;arma;magia;vida",
                "linha sem os campos esperados"));
        FichaRepositorio repositorio = new FichaRepositorio();

        assertThrows(FichaInvalidaException.class,
                () -> repositorio.lerTxt(arquivo.toString()));
    }

    @Test
    public void ordenarPorVidaDeveOrdenarDaMenorParaAMaior() {
        FichaRepositorio repositorio = new FichaRepositorio();
        repositorio.lerTxt(ARQUIVO_FICHAS);

        repositorio.ordenarPorVida();

        List<Personagem> fichas = repositorio.getFichas();
        for (int i = 1; i < fichas.size(); i++) {
            assertTrue(fichas.get(i - 1).getVida() <= fichas.get(i).getVida(),
                    "ficha na posicao " + i + " esta fora de ordem");
        }
    }

    @Test
    public void exportarEImportarDatDevePreservarABase() throws IOException {
        Path arquivoDat = Files.createTempFile("fichas", ".dat");
        FichaRepositorio original = new FichaRepositorio();
        original.lerTxt(ARQUIVO_FICHAS);

        original.exportarDat(arquivoDat.toString());

        FichaRepositorio importado = new FichaRepositorio();
        importado.importarDat(arquivoDat.toString());

        assertEquals(original.getFichas().size(), importado.getFichas().size(),
                "a base importada deve ter a mesma quantidade de fichas da exportada");
    }
}
