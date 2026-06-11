package modelo;

import java.io.Serializable;
import java.util.Objects;

public abstract class Personagem implements Serializable {
    private String nome;
    private Sexo sexo;
    private Arma arma;
    private Magia magia;
    private int vida;

    protected Personagem() {
    }

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
        this.vida = Math.max(0, vida);
    }

    public abstract void usarHabilidade();

    public void receberDano(int dano) {
        if (dano <= 0) {
            return;
        }
        vida = Math.max(0, vida - dano);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Personagem outroPersonagem)) {
            return false;
        }
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