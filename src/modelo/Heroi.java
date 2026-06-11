package modelo;

import combate.Combatente;

import java.util.Objects;

public class Heroi extends Personagem implements Combatente {

    private Classe classe;

    public Classe getClasse() {
        return classe;
    }

    public void setClasse(Classe classe) {
        this.classe = classe;
    }

    @Override
    public void usarHabilidade() {

    }

    @Override
    public void atacar(Personagem personagem) {

    }

    public void atacar(Personagem personagem, Arma arma) {

    }

    @Override
    public void defender(int dano) {

    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Heroi heroi = (Heroi) o;
        return classe == heroi.classe;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(classe);
    }
}
