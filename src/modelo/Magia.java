package modelo;

import java.io.Serializable;
import java.util.Objects;

public class Magia implements Serializable {

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
        Magia magia = (Magia) o;
        return dano == magia.dano && Objects.equals(nome, magia.nome);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nome, dano);
    }
}
