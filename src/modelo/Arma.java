package modelo;

/**
 * Arma equipada por um personagem (no fichas.txt vem como "Nome:dano").
 * Herda de Equipamento nome, dano, igualdade e serialização.
 */
public class Arma extends Equipamento {

    /** Construtor vazio: usado quando os dados são preenchidos depois, via setters. */
    public Arma() {
    }

    /** Cria a arma já completa. */
    public Arma(String nome, int dano) {
        super(nome, dano);
    }
}
