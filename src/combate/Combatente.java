package combate;

import modelo.Personagem;

/**
 * Contrato de quem pode lutar: sabe atacar um personagem, se defender de um dano
 * e calcular o próprio golpe. Implementada por Heroi e Monstro.
 */
public interface Combatente {

    /**
     * Ataca o alvo com a regra única do combate: calcula o próprio dano e o
     * entrega ao defender() do alvo. É um default method porque Heroi e Monstro
     * faziam exatamente isto — evita duplicar o golpe nas duas classes.
     */
    default void atacar(Personagem alvo) {
        if (alvo instanceof Combatente defensor) {
            defensor.defender(calcularDano());
        }
    }

    /** Recebe um golpe, podendo reduzir o dano conforme as regras de quem implementa. */
    void defender(int dano);

    /**
     * Dano bruto do golpe (arma + magia, conforme cada um), sem aplicar nada.
     * Separar o cálculo da aplicação deixa a Batalha mandar o dano ao defender()
     * do alvo — é o defensor quem decide quanto absorve.
     */
    int calcularDano();
}
