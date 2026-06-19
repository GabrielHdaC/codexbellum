package combate;

/**
 * Coordena um turno de combate: pega o dano que o atacante calcula e o entrega
 * ao defensor, que decide quanto absorve. Trabalha só com a interface
 * Combatente, então não sabe se quem luta é Herói ou Monstro (polimorfismo).
 */
public class Batalha {

    /**
     * Executa um turno: o atacante calcula o dano e o defensor o defende. Como é
     * o defensor quem aplica a própria redução, o golpe nunca ignora a defesa.
     */
    public void executarTurno(Combatente atacante, Combatente defensor) {
        defensor.defender(atacante.calcularDano());
    }
}
