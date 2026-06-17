package combate;

/**
 * O que é: o coordenador de um turno de combate entre dois combatentes.
 * O que faz: pega o dano que o atacante calcula e o entrega ao defensor, que
 * decide quanto desse dano absorve.
 * Por que assim: separar "calcular o golpe" (atacante) de "sofrer o golpe"
 * (defensor) faz a defesa por classe finalmente valer, e mantém a Batalha sem
 * saber se quem luta é Herói ou Monstro — polimorfismo via interface Combatente.
 */
public class Batalha {

    /**
     * O que é: a execução de um turno — um ataca, o outro defende.
     * O que faz: calcula o dano do atacante e manda o defensor defender dele.
     * Por que assim: é o defensor quem aplica a própria redução em defender(),
     * então o golpe nunca ignora a defesa de quem leva.
     *
     * @param atacante quem desfere o golpe
     * @param defensor quem recebe o golpe
     */
    public void executarTurno(Combatente atacante, Combatente defensor) {
        defensor.defender(atacante.calcularDano());
    }
}
