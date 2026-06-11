package modelo;

import java.util.Objects;

public class Arma {

    private String nome;
    private int dano;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getDano() {
        return dano;
    }

    public void setDano(int dano) {
        this.dano = dano;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Arma arma = (Arma) o;
        return dano == arma.dano && Objects.equals(nome, arma.nome);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nome, dano);
    }
}
