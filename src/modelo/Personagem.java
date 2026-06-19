package modelo;

import java.io.Serializable;
import java.util.Objects;

/**
 * Base de todo personagem do jogo: guarda os dados comuns a heróis e monstros e
 * obriga cada filho a implementar usarHabilidade(). Implementa Serializable para
 * poder ser gravada na exportação binária (.dat).
 */
public abstract class Personagem implements Serializable {
    private String nome;
    private Sexo sexo;
    private Arma arma;
    private Magia magia;
    private int vida;

    /** Construtor vazio: usado quando os dados são preenchidos depois, via setters. */
    protected Personagem() {
    }

    /** Cria o personagem já completo. */
    protected Personagem(String nome, Sexo sexo, Arma arma, Magia magia, int vida) {
        this.nome = nome;
        this.sexo = sexo;
        this.arma = arma;
        this.magia = magia;
        this.vida = vida;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Sexo getSexo() {
        return sexo;
    }

    public void setSexo(Sexo sexo) {
        this.sexo = sexo;
    }

    public Arma getArma() {
        return arma;
    }

    public void setArma(Arma arma) {
        this.arma = arma;
    }

    public Magia getMagia() {
        return magia;
    }

    public void setMagia(Magia magia) {
        this.magia = magia;
    }

    public int getVida() {
        return vida;
    }

    public void setVida(int vida) {
        // Math.max protege a invariante: a vida nunca fica negativa
        this.vida = Math.max(0, vida);
    }

    /** Habilidade especial: cada filho (Heroi/Monstro) implementa a sua. */
    public abstract void usarHabilidade();

    /** Aplica dano na vida, ignorando valores não positivos. */
    public void receberDano(int dano) {
        if (dano <= 0) {
            return;
        }
        vida = Math.max(0, vida - dano);
    }

    /**
     * Dano base do golpe: o da arma equipada, ou 1 se estiver desarmado.
     * Heroi e Monstro partem deste valor em calcularDano() e cada um decide se
     * soma a magia — Template Method, sem duplicar a regra do "desarmado bate 1".
     */
    protected int danoDaArma() {
        return arma != null ? arma.getDano() : 1;
    }

    /** Indica se ainda está vivo (vida maior que zero). */
    public boolean estaVivo() {
        return vida > 0;
    }

    /**
     * Dois personagens são iguais quando são do mesmo tipo exato e todos os
     * campos coincidem. getClass (em vez de instanceof) garante que um Heroi e um
     * Monstro com os mesmos campos base nunca sejam considerados iguais.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Personagem outroPersonagem = (Personagem) obj;
        return vida == outroPersonagem.vida
                && Objects.equals(nome, outroPersonagem.nome)
                && sexo == outroPersonagem.sexo
                && Objects.equals(arma, outroPersonagem.arma)
                && Objects.equals(magia, outroPersonagem.magia);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nome, sexo, arma, magia, vida);
    }
}
