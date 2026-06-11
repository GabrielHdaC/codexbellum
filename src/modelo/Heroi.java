package modelo;

import combate.Combatente;

import java.util.Objects;

/**
 * Personagem jogável: além dos dados comuns, tem uma classe (guerreiro, mago,
 * ladino ou clérigo) que muda o efeito da habilidade, do ataque e da defesa.
 */
public class Heroi extends Personagem implements Combatente {
    private Classe classe;

    /** Construtor vazio: usado quando os dados são preenchidos depois, via setters. */
    public Heroi() {
    }

    /**
     * Cria o herói já completo.
     *
     * @param nome   nome do herói
     * @param sexo   sexo (M/F)
     * @param arma   arma equipada
     * @param magia  magia conhecida (pode ser null)
     * @param vida   pontos de vida iniciais
     * @param classe classe do herói
     */
    public Heroi(String nome, Sexo sexo, Arma arma, Magia magia, int vida, Classe classe) {
        super(nome, sexo, arma, magia, vida);
        this.classe = classe;
    }

    public Classe getClasse() {
        return classe;
    }

    public void setClasse(Classe classe) {
        this.classe = classe;
    }

    /**
     * Habilidade do herói: recupera uma quantidade de vida que depende da classe.
     */
    @Override
    public void usarHabilidade() {
        if (classe == null) {
            return;
        }

        switch (classe) {
            case GUERREIRO -> setVida(getVida() + 10);
            case MAGO -> setVida(getVida() + 5);
            case LADINO -> setVida(getVida() + 3);
            case CLERIGO -> setVida(getVida() + 15);
        }
    }

    /**
     * Ataca com a arma equipada; mago soma o dano da magia ao golpe.
     *
     * @param alvo personagem que recebe o dano
     */
    @Override
    public void atacar(Personagem alvo) {
        if (alvo == null) {
            return;
        }
        int danoBase = getArma() != null ? getArma().getDano() : 1;
        if (classe == Classe.MAGO && getMagia() != null) {
            danoBase += getMagia().getDano();
        }

        alvo.receberDano(danoBase);
    }

    /**
     * Sobrecarga do atacar: mesmo nome, parâmetros diferentes.
     * Ataca usando a arma recebida em vez da equipada.
     *
     * @param alvo personagem que recebe o dano
     * @param arma arma usada neste golpe
     */
    public void atacar(Personagem alvo, Arma arma) {
        if (alvo == null) {
            return;
        }
        int danoBase = arma != null ? arma.getDano() : 1;
        alvo.receberDano(danoBase);
    }

    /**
     * Defende reduzindo o dano conforme a classe (guerreiro absorve metade,
     * ladino esquiva 30%...) antes de repassar ao receberDano.
     *
     * @param dano dano bruto do golpe recebido
     */
    @Override
    public void defender(int dano) {
        if (dano <= 0) {
            return;
        }

        int danoFinal = dano;
        if (classe != null) {
            danoFinal = switch (classe) {
                case GUERREIRO -> dano / 2;
                case MAGO -> (int) Math.ceil(dano * 0.9);
                case LADINO -> (int) Math.ceil(dano * 0.7);
                case CLERIGO -> (int) Math.ceil(dano * 0.8);
            };
        }

        receberDano(danoFinal);
    }

    /**
     * Dois heróis são iguais quando os dados de personagem e a classe coincidem.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Heroi outroHeroi)) {
            return false;
        }
        return super.equals(obj) && classe == outroHeroi.classe;
    }

    /** Anda em par com o equals: objetos iguais devem ter o mesmo hash. */
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), classe);
    }
}
