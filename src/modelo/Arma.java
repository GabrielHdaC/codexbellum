package modelo;

import java.io.Serializable;
import java.util.Objects;

/**
 * Arma equipada por um personagem (no fichas.txt vem como "Nome:dano").
 * Serializable para ser gravada junto na exportação binária.
 */
public class Arma implements Serializable {
    private String nome;
    private int dano;

    /** Construtor vazio: usado quando os dados são preenchidos depois, via setters. */
    public Arma() {
    }

    /**
     * Cria a arma já completa.
     *
     * @param nome nome da arma
     * @param dano dano que ela causa
     */
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

    /**
     * Duas armas são iguais quando nome e dano coincidem.
     */
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

    /** Anda em par com o equals: objetos iguais devem ter o mesmo hash. */
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
