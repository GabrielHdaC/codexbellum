package combate;

import modelo.Personagem;

public interface Combatente {

    void atacar(Personagem personagem);

    void defender(int dano);
}
