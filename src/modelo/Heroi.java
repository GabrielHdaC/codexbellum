package modelo;

import combate.Combatente;

import java.util.Objects;

/**
 * Personagem jogável: além dos dados comuns, tem uma classe (guerreiro, mago,
 * ladino ou clérigo) que muda o efeito da habilidade, do ataque e da defesa.
 */
public class Heroi extends Personagem implements Combatente {
    private Classe classe;

    /** Construtor vazio: usado quando os dados são preenchidos depois, via setters. */
    public Heroi() {
    }

    /** Cria o herói já completo. */
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

    /** Recupera vida conforme a classe; a regra de cura mora na própria Classe (Strategy). */
    @Override
    public void usarHabilidade() {
        if (classe == null) {
            return;
        }
        setVida(getVida() + classe.curar(getMagia()));
    }

    /**
     * Dano bruto do golpe: a arma mais, conforme a classe, a magia (o mago
     * canaliza qualquer magia; o clérigo só a ofensiva). Quem aplica a defesa é o
     * alvo, então este valor vai cru para o defender() dele.
     */
    @Override
    public int calcularDano() {
        int dano = danoDaArma();
        if (classe != null && classe.somaMagiaNoAtaque(getMagia())) {
            dano += getMagia().getDano();
        }
        return dano;
    }

    /**
     * Sobrecarga do atacar: mesmo nome, parâmetros diferentes. Ataca com a arma
     * recebida em vez da equipada; o alvo ainda aplica a própria defesa.
     */
    public void atacar(Personagem alvo, Arma arma) {
        if (alvo instanceof Combatente defensor) {
            int danoBase = arma != null ? arma.getDano() : 1;
            defensor.defender(danoBase);
        }
    }

    /** Reduz o dano conforme a classe (Strategy) antes de repassar ao receberDano. */
    @Override
    public void defender(int dano) {
        if (dano <= 0) {
            return;
        }
        int danoFinal = classe != null ? classe.reduzirDano(dano) : dano;
        receberDano(danoFinal);
    }

    @Override
    public boolean equals(Object obj) {
        // super.equals já exige o mesmo tipo exato (getClass) e compara os campos
        // base; aqui só falta a classe, exclusiva do herói
        if (!super.equals(obj)) {
            return false;
        }
        Heroi outroHeroi = (Heroi) obj;
        return classe == outroHeroi.classe;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), classe);
    }
}
