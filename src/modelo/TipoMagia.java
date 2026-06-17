package modelo;

/**
 * Natureza de uma magia. Define para que o clérigo a usa: uma magia de CURA
 * reforça a habilidade de recuperar vida; uma de DANO some ao golpe no ataque.
 * O mago só conhece magias de DANO. Como o fichas.txt traz a magia apenas como
 * "Nome:dano" (sem coluna de tipo), a Magia deduz o tipo a partir do nome.
 */
public enum TipoMagia {
    CURA,
    DANO
}
