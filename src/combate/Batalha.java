package combate;

import modelo.Personagem;

public class Batalha {

    public void executarTurno(Combatente atacante, Combatente defensor) {
        atacante.atacar((Personagem) defensor);
    }
}
