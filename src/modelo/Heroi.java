package modelo;

import combate.Combatente;

import java.util.Objects;

public class Heroi extends Personagem implements Combatente {
    private Classe classe;

    public Heroi() {
    }

    public Heroi(String nome, Sexo sexo, Arma arma, Magia magia, int vida, Classe classe) {
        super(nome, sexo, arma, magia, vida);
        this.classe = classe;
    }

    public Classe getClasse() {
        return classe;
    }

    public void setClasse(Classe classe) {
        this.classe = classe;
    }

    @Override
    public void usarHabilidade() {
        if (classe == null) {
            return;
        }

        switch (classe) {
            case GUERREIRO -> setVida(getVida() + 10);
            case MAGO -> setVida(getVida() + 5);
            case LADINO -> setVida(getVida() + 3);
            case CLERIGO -> setVida(getVida() + 15);
        }
    }

    @Override
    public void atacar(Personagem alvo) {
        if (alvo == null) {
            return;
        }
        int danoBase = getArma() != null ? getArma().getDano() : 1;
        if (classe == Classe.MAGO && getMagia() != null) {
            danoBase += getMagia().getDano();
        }

        alvo.receberDano(danoBase);
    }

    public void atacar(Personagem alvo, Arma arma) {
        if (alvo == null) {
            return;
        }
        int danoBase = arma != null ? arma.getDano() : 1;
        alvo.receberDano(danoBase);
    }

    @Override
    public void defender(int dano) {
        if (dano <= 0) {
            return;
        }

        int danoFinal = dano;
        if (classe != null) {
            danoFinal = switch (classe) {
                case GUERREIRO -> dano / 2;
                case MAGO -> (int) Math.ceil(dano * 0.9);
                case LADINO -> (int) Math.ceil(dano * 0.7);
                case CLERIGO -> (int) Math.ceil(dano * 0.8);
            };
        }

        receberDano(danoFinal);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Heroi outroHeroi)) {
            return false;
        }
        return super.equals(obj) && classe == outroHeroi.classe;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), classe);
    }
}