package repositorio;

import modelo.Personagem;

import java.util.ArrayList;
import java.util.List;

public class FichaRepositorio implements Exportavel {

    private List<Personagem> fichas = new ArrayList<>();

    public List<Personagem> getFichas() {
        return fichas;
    }

    public void lerTxt(String caminho) {

    }

    public void ordenarPorVida() {

    }

    @Override
    public void exportarDat(String caminho) {

    }

    @Override
    public void importarDat(String caminho) {

    }
}
