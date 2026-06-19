package modelo;

import combate.Combatente;

/**
 * Personagem inimigo: não tem classe; a habilidade e o ataque dependem de ter
 * ou não uma magia.
 */
public class Monstro extends Personagem implements Combatente {

    /** Construtor vazio: usado quando os dados são preenchidos depois, via setters. */
    public Monstro() {
    }

    /** Cria o monstro já completo. */
    public Monstro(String nome, Sexo sexo, Arma arma, Magia magia, int vida) {
        super(nome, sexo, arma, magia, vida);
    }

    /** Regenera vida: metade do dano da magia, ou 2 pontos se não tiver magia. */
    @Override
    public void usarHabilidade() {
        if (getMagia() != null) {
            setVida(getVida() + Math.max(1, getMagia().getDano() / 2));
            return;
        }
        setVida(getVida() + 2);
    }

    /**
     * "IA" simples do monstro: prefere se curar quando a vida está baixa (até 20)
     * e atacar acima disso. O limiar fixo é fácil de entender e de balancear.
     *
     * @return true se deve se curar neste turno; false se deve atacar
     */
    public boolean deveUsarHabilidade() {
        return getVida() <= 20;
    }

    /** Dano bruto: arma mais a magia, se houver (monstro não tem classe, toda magia é ofensiva). */
    @Override
    public int calcularDano() {
        int dano = danoDaArma();
        if (getMagia() != null) {
            dano += getMagia().getDano();
        }
        return dano;
    }

    /** Absorve 20% do dano antes de repassar ao receberDano. */
    @Override
    public void defender(int dano) {
        if (dano <= 0) {
            return;
        }
        receberDano((int) Math.ceil(dano * 0.8));
    }

    @Override
    public boolean equals(Object obj) {
        // Monstro não acrescenta campos: a igualdade exata de tipo + campos base
        // que o super garante (getClass) já basta
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
