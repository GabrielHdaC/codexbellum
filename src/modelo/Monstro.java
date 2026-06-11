package modelo;

import combate.Combatente;

public class Monstro extends Personagem implements Combatente {

    @Override
    public void usarHabilidade() {

    }

    @Override
    public void atacar(Personagem personagem) {

    }

    @Override
    public void defender(int dano) {

    }

    @Override
    public boolean equals(Object o) {
        return o != null && getClass() == o.getClass();
    }
}
