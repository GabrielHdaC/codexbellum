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

    /**
     * Cria o herói já completo.
     *
     * @param nome   nome do herói
     * @param sexo   sexo (M/F)
     * @param arma   arma equipada
     * @param magia  magia conhecida (pode ser null)
     * @param vida   pontos de vida iniciais
     * @param classe classe do herói
     */
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

    /**
     * Habilidade do herói: recupera uma quantidade de vida que depende da classe.
     * O clérigo cura uma base divina que ainda é reforçada pela magia curativa
     * equipada (Luz Curativa, Escudo da Fé).
     */
    @Override
    public void usarHabilidade() {
        if (classe == null) {
            return;
        }
        // a regra de cura mora na própria classe (Strategy): o clérigo, por
        // exemplo, ainda soma a magia de cura equipada
        setVida(getVida() + classe.curar(getMagia()));
    }

    /**
     * O que é: o dano bruto que o herói produz num golpe.
     * O que faz: soma o dano da arma e, conforme a classe, o da magia — o mago
     * canaliza qualquer magia; o clérigo só soma magia ofensiva (ex: Punição
     * Divina), pois magia de cura fica reservada para a habilidade.
     * Por que assim: separar o cálculo da aplicação deixa a Batalha mandar esse
     * dano ao defender() do alvo — é o defensor quem decide quanto absorve.
     *
     * @return dano bruto do golpe, antes de qualquer defesa
     */
    @Override
    public int calcularDano() {
        int dano = danoDaArma();
        // cada classe decide se a magia entra no golpe (mago canaliza qualquer
        // magia; clérigo só a ofensiva; guerreiro e ladino, nunca)
        if (classe != null && classe.somaMagiaNoAtaque(getMagia())) {
            dano += getMagia().getDano();
        }
        return dano;
    }

    /**
     * Sobrecarga do atacar: mesmo nome, parâmetros diferentes.
     * Ataca usando a arma recebida em vez da equipada; o alvo ainda aplica a
     * própria defesa, como em qualquer golpe.
     *
     * @param alvo personagem que recebe o golpe
     * @param arma arma usada neste golpe
     */
    public void atacar(Personagem alvo, Arma arma) {
        if (alvo instanceof Combatente defensor) {
            int danoBase = arma != null ? arma.getDano() : 1;
            defensor.defender(danoBase);
        }
    }

    /**
     * Defende reduzindo o dano conforme a classe (guerreiro absorve metade,
     * ladino esquiva 30%...) antes de repassar ao receberDano.
     *
     * @param dano dano bruto do golpe recebido
     */
    @Override
    public void defender(int dano) {
        if (dano <= 0) {
            return;
        }
        // cada classe aplica a própria redução de defesa (Strategy)
        int danoFinal = classe != null ? classe.reduzirDano(dano) : dano;
        receberDano(danoFinal);
    }

    /**
     * Dois heróis são iguais quando os dados de personagem e a classe coincidem.
     */
    @Override
    public boolean equals(Object obj) {
        // super.equals já exige o mesmo tipo exato (getClass) e compara os campos
        // base; aqui só falta a classe, exclusiva do herói.
        if (!super.equals(obj)) {
            return false;
        }
        Heroi outroHeroi = (Heroi) obj;
        return classe == outroHeroi.classe;
    }

    /** Anda em par com o equals: objetos iguais devem ter o mesmo hash. */
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), classe);
    }
}
