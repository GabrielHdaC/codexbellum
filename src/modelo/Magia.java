package modelo;

import java.io.Serializable;
import java.util.Objects;

/**
 * Magia conhecida por um personagem (no fichas.txt vem como "Nome:dano";
 * guerreiros e ladinos não têm — campo vazio).
 * Serializable para ser gravada junto na exportação binária.
 */
public class Magia implements Serializable {
    private String nome;
    private int dano;

    /** Construtor vazio: usado quando os dados são preenchidos depois, via setters. */
    public Magia() {
    }

    /**
     * Cria a magia já completa.
     *
     * @param nome nome da magia
     * @param dano dano que ela causa
     */
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

    /**
     * Duas magias são iguais quando nome e dano coincidem.
     */
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

    /** Anda em par com o equals: objetos iguais devem ter o mesmo hash. */
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
