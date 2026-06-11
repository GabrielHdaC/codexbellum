package modelo;

import java.io.Serializable;
import java.util.Objects;

public class Arma implements Serializable {
    private String nome;
    private int dano;

    public Arma() {
    }

    public Arma(String nome, int dano) {
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
        if (!(obj instanceof Arma outraArma)) {
            return false;
        }
        return dano == outraArma.dano && Objects.equals(nome, outraArma.nome);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nome, dano);
    }

    @Override
    public String toString() {
        return "Arma{" +
                "nome='" + nome + '\'' +
                ", dano=" + dano +
                '}';
    }
}