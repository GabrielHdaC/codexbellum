package modelo;

import java.util.Set;

/**
 * Magia conhecida por um personagem (no fichas.txt vem como "Nome:dano";
 * guerreiros e ladinos não têm — campo vazio). Herda de Equipamento nome, dano,
 * igualdade e serialização; acrescenta a noção de tipo (cura ou dano).
 */
public class Magia extends Equipamento {

    /**
     * Magias de cura/suporte do jogo. Como o arquivo não traz o tipo da magia,
     * reconhecemos as curativas pelo nome; qualquer outra é considerada de dano.
     * Atualize este conjunto se novas magias de cura forem adicionadas ao txt.
     */
    private static final Set<String> NOMES_DE_CURA = Set.of("Luz Curativa", "Escudo da Fe");

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
        super(nome, dano);
    }

    /**
     * Deduz a natureza da magia pelo nome: as listadas em NOMES_DE_CURA são de
     * cura, todas as outras são de dano. Usado pela classe do herói para decidir
     * entre reforçar a cura (usarHabilidade) ou o golpe (atacar).
     *
     * @return CURA se for uma magia de cura/suporte, senão DANO
     */
    public TipoMagia getTipo() {
        return NOMES_DE_CURA.contains(getNome()) ? TipoMagia.CURA : TipoMagia.DANO;
    }
}
