package modelo;

import java.io.Serializable;
import java.util.Objects;

public class Magia implements Serializable {
    private String nome;
    private int dano;

    public Magia() {
    }

    public Magia(String nome, int dano) {
        this.nome = nome;
        this.dano = dano;
    }

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
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Magia outraMagia)) {
            return false;
        }
        return dano == outraMagia.dano && Objects.equals(nome, outraMagia.nome);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nome, dano);
    }

    @Override
    public String toString() {
        return "Magia{" +
                "nome='" + nome + '\'' +
                ", dano=" + dano +
                '}';
    }
}