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
     * O que é: a "IA" simples que decide a jogada do monstro no turno dele.
     * O que faz: manda usar a habilidade (curar) quando a vida está baixa
     * (até 20); acima disso, prefere atacar.
     * Por que assim: dá ao monstro um instinto mínimo de autopreservação sem
     * complicar — o limiar fixo é fácil de entender e de ajustar no balanceamento.
     *
     * @return true se o monstro deve se curar neste turno; false se deve atacar
     */
    public boolean deveUsarHabilidade() {
        return getVida() <= 20;
    }

    /**
     * O que é: o dano bruto do monstro num golpe.
     * O que faz: soma o dano da arma e, se houver magia, o dano dela (monstro
     * não tem classe, então toda magia que conhece é ofensiva).
     * Por que assim: como no herói, separar cálculo de aplicação permite que a
     * Batalha mande esse dano ao defender() do alvo.
     *
     * @return dano bruto do golpe, antes de qualquer defesa
     */
    @Override
    public int calcularDano() {
        int dano = getArma() != null ? getArma().getDano() : 1;
        if (getMagia() != null) {
            dano += getMagia().getDano();
        }
        return dano;
    }

    /**
     * O que é: um golpe direto no alvo.
     * O que faz: aplica na vida do alvo o dano apurado em calcularDano().
     * Por que assim: ataque "cru" sem defesa; a orquestração de ataque + defesa
     * do turno fica na Batalha (calcularDano() + defender()).
     *
     * @param alvo personagem que recebe o dano
     */
    @Override
    public void atacar(Personagem alvo) {
        if (alvo == null) {
            return;
        }
        alvo.receberDano(calcularDano());
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
