package modelo;

import java.io.Serializable;
import java.util.Objects;

/**
 * Base de todo personagem do jogo: guarda os dados comuns a heróis e monstros
 * e define o que cada filho é obrigado a saber fazer (usarHabilidade).
 * Implementa Serializable — interface vazia que apenas marca a classe como
 * conversível em bytes, exigência da exportação binária (.dat).
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

    /**
     * Cria o personagem já completo.
     *
     * @param nome  nome do personagem
     * @param sexo  sexo (M/F)
     * @param arma  arma equipada
     * @param magia magia conhecida (pode ser null)
     * @param vida  pontos de vida iniciais
     */
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
        // Math.max impede estado inválido: a vida nunca fica negativa
        this.vida = Math.max(0, vida);
    }

    /**
     * Habilidade especial — método abstrato: cada filho (Heroi/Monstro)
     * é obrigado a implementar a própria versão.
     */
    public abstract void usarHabilidade();

    /**
     * Aplica dano diretamente na vida (ignora valores não positivos).
     *
     * @param dano quantidade a subtrair da vida
     */
    public void receberDano(int dano) {
        if (dano <= 0) {
            return;
        }
        // Math.max impede estado inválido: a vida nunca fica negativa
        vida = Math.max(0, vida - dano);
    }

    /**
     * Dois personagens são iguais quando todos os campos coincidem.
     */
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

    /** Anda em par com o equals: objetos iguais devem ter o mesmo hash. */
    @Override
    public int hashCode() {
        return Objects.hash(nome, sexo, arma, magia, vida);
    }
}
