package modelo;

import java.io.Serializable;
import java.util.Objects;

/**
 * Item equipável por um personagem, no formato "Nome:dano" do fichas.txt. Base
 * comum de Arma e Magia, que compartilham nome, dano e as regras de igualdade.
 * Serializable para ser gravado na exportação binária.
 */
public abstract class Equipamento implements Serializable {
    private String nome;
    private int dano;

    /** Construtor vazio: usado quando os dados são preenchidos depois, via setters. */
    protected Equipamento() {
    }

    /** Cria o equipamento já completo. */
    protected Equipamento(String nome, int dano) {
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
     * Dois equipamentos são iguais quando são do mesmo tipo exato (Arma só com
     * Arma, Magia só com Magia) e têm o mesmo nome e dano.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Equipamento outro = (Equipamento) obj;
        return dano == outro.dano && Objects.equals(nome, outro.nome);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nome, dano);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "{" +
                "nome='" + nome + '\'' +
                ", dano=" + dano +
                '}';
    }
}
