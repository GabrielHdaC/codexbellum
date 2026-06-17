package modelo;

import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

/**
 * Magia conhecida por um personagem (no fichas.txt vem como "Nome:dano";
 * guerreiros e ladinos não têm — campo vazio).
 * Serializable para ser gravada junto na exportação binária.
 */
public class Magia implements Serializable {

    /**
     * Magias de cura/suporte do jogo. Como o arquivo não traz o tipo da magia,
     * reconhecemos as curativas pelo nome; qualquer outra é considerada de dano.
     * Atualize este conjunto se novas magias de cura forem adicionadas ao txt.
     */
    private static final Set<String> NOMES_DE_CURA = Set.of("Luz Curativa", "Escudo da Fe");

    private String nome;
    private int dano;

    /** Construtor vazio: usado quando os dados são preenchidos depois, via setters. */
    public Magia() {
    }

    /**
     * Cria a magia já completa.
     *
     * @param nome nome da magia
     * @param dano dano que ela causa
     */
    public Magia(String nome, int dano) {
        this.nome = nome;
        this.dano = dano;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getDano() {
        return dano;
    }

    public void setDano(int dano) {
        this.dano = dano;
    }

    /**
     * Deduz a natureza da magia pelo nome: as listadas em NOMES_DE_CURA são de
     * cura, todas as outras são de dano. Usado pelo clérigo para decidir entre
     * reforçar a cura (usarHabilidade) ou o golpe (atacar).
     *
     * @return CURA se for uma magia de cura/suporte, senão DANO
     */
    public TipoMagia getTipo() {
        return NOMES_DE_CURA.contains(nome) ? TipoMagia.CURA : TipoMagia.DANO;
    }

    /**
     * Duas magias são iguais quando nome e dano coincidem.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Magia outraMagia)) {
            return false;
        }
        return dano == outraMagia.dano && Objects.equals(nome, outraMagia.nome);
    }

    /** Anda em par com o equals: objetos iguais devem ter o mesmo hash. */
    @Override
    public int hashCode() {
        return Objects.hash(nome, dano);
    }

    @Override
    public String toString() {
        return "Magia{" +
                "nome='" + nome + '\'' +
                ", dano=" + dano +
                '}';
    }
}
