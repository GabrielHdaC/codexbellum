package repositorio;

/**
 * Contrato de persistência binária: quem implementa sabe exportar e importar a
 * base de fichas em arquivo .dat (serialização de objetos).
 */
public interface Exportavel {

    /** Grava a base no arquivo indicado usando serialização binária. */
    void exportarDat(String caminho);

    /** Restaura a base a partir do arquivo indicado. */
    void importarDat(String caminho);
}
