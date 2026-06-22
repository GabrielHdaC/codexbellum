package combate;

import modelo.Personagem;

/**
 * Contrato de quem pode lutar: sabe atacar um personagem, se defender de um dano
 * e calcular o próprio golpe. Implementada por Heroi e Monstro.
 */
public interface Combatente {

    /**
     * Calcula o próprio dano (calcularDano) e o entrega ao defender() do alvo,
     * que aplica a defesa dele e desconta da vida. Só ataca se o alvo for um
     * Combatente: o instanceof testa e, no mesmo passo, expõe a referência como
     * 'defensor'; se o alvo não souber se defender, o golpe não tem efeito.
     * É default para que Heroi e Monstro herdem esta mesma regra sem reescrevê-la.
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
