package modelo;

import combate.Combatente;

/**
 * Personagem inimigo: não tem classe; a habilidade e o ataque dependem
 * de ter ou não uma magia.
 */
public class Monstro extends Personagem implements Combatente {

    /** Construtor vazio: usado quando os dados são preenchidos depois, via setters. */
    public Monstro() {
    }

    /**
     * Cria o monstro já completo.
     *
     * @param nome  nome do monstro
     * @param sexo  sexo (M/F)
     * @param arma  arma equipada
     * @param magia magia conhecida (pode ser null)
     * @param vida  pontos de vida iniciais
     */
    public Monstro(String nome, Sexo sexo, Arma arma, Magia magia, int vida) {
        super(nome, sexo, arma, magia, vida);
    }

    /**
     * Habilidade do monstro: regenera vida — metade do dano da magia,
     * ou 2 pontos se não tiver magia.
     */
    @Override
    public void usarHabilidade() {
        if (getMagia() != null) {
            setVida(getVida() + Math.max(1, getMagia().getDano() / 2));
            return;
        }
        setVida(getVida() + 2);
    }

    /**
     * Ataca com a arma; se tiver magia, soma o dano dela ao golpe.
     *
     * @param alvo personagem que recebe o dano
     */
    @Override
    public void atacar(Personagem alvo) {
        if (alvo == null) {
            return;
        }
        int danoBase = getArma() != null ? getArma().getDano() : 1;
        if (getMagia() != null) {
            danoBase += getMagia().getDano();
        }

        alvo.receberDano(danoBase);
    }

    /**
     * Defende absorvendo 20% do dano antes de repassar ao receberDano.
     *
     * @param dano dano bruto do golpe recebido
     */
    @Override
    public void defender(int dano) {
        if (dano <= 0) {
            return;
        }
        receberDano((int) Math.ceil(dano * 0.8));
    }

    /**
     * Monstros são iguais quando os dados de personagem coincidem.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Monstro)) {
            return false;
        }
        return super.equals(obj);
    }

    /** Anda em par com o equals: objetos iguais devem ter o mesmo hash. */
    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
