package combate;

import modelo.Personagem;

/**
 * Contrato de quem pode lutar: todo combatente sabe atacar um personagem
 * e se defender de uma quantidade de dano.
 * Implementada por Heroi e Monstro, cada um com as próprias regras.
 */
public interface Combatente {

    /**
     * Ataca o alvo, causando dano conforme as regras de quem implementa.
     *
     * @param personagem alvo do ataque
     */
    void atacar(Personagem personagem);

    /**
     * Recebe um golpe, podendo reduzir o dano conforme as regras de quem implementa.
     *
     * @param dano quantidade de dano bruto recebido
     */
    void defender(int dano);
}
