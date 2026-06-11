package modelo;

import combate.Combatente;

import java.util.Objects;

public class Monstro extends Personagem implements Combatente {
    public Monstro() {
    }

    public Monstro(String nome, Sexo sexo, Arma arma, Magia magia, int vida) {
        super(nome, sexo, arma, magia, vida);
    }

    @Override
    public void usarHabilidade() {
        if (getMagia() != null) {
            setVida(getVida() + Math.max(1, getMagia().getDano() / 2));
            return;
        }
        setVida(getVida() + 2);
    }

    @Override
    public void atacar(Personagem alvo) {
        if (alvo == null) {
            return;
        }
        int danoBase = getArma() != null ? getArma().getDano() : 1;
        if (getMagia() != null) {
            danoBase += getMagia().getDano();
        }

        alvo.receberDano(danoBase);
    }

    @Override
    public void defender(int dano) {
        if (dano <= 0) {
            return;
        }
        receberDano((int) Math.ceil(dano * 0.8));
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Monstro)) {
            return false;
        }
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode());
    }
}