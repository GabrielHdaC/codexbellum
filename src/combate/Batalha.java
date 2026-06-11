package combate;

import modelo.Personagem;

/**
 * Coordena o combate entre dois combatentes.
 */
public class Batalha {

    /**
     * Executa um turno: o atacante golpeia o defensor.
     * Polimorfismo em ação: a Batalha não sabe se quem ataca é Heroi ou
     * Monstro — ela chama o atacar da interface e cada classe executa
     * a própria versão do golpe.
     *
     * @param atacante quem desfere o golpe
     * @param defensor quem recebe o golpe
     */
    public void executarTurno(Combatente atacante, Combatente defensor) {
        // Cast necessário: atacar(Personagem) exige um Personagem, mas o
        // defensor chega aqui como Combatente. Como todo Combatente do projeto
        // (Heroi/Monstro) também é um Personagem, a conversão é segura.
        atacante.atacar((Personagem) defensor);
    }
}
