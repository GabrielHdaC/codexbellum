package repositorio;

/**
 * Contrato de persistência binária: quem implementa sabe exportar e
 * importar a base de fichas em arquivo .dat (serialização de objetos).
 */
public interface Exportavel {

    /**
     * Grava a base no arquivo indicado usando serialização binária.
     *
     * @param caminho caminho do arquivo .dat a criar
     */
    void exportarDat(String caminho);

    /**
     * Restaura a base a partir do arquivo indicado.
     *
     * @param caminho caminho do arquivo .dat a ler
     */
    void importarDat(String caminho);
}
