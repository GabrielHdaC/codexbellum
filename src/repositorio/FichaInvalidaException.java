package repositorio;

/**
 * Lançada quando uma linha do arquivo de fichas está fora do formato esperado
 * (campos faltando ou valores impossíveis de converter).
 * Estende RuntimeException, então não obriga try/catch em quem chama.
 */
public class FichaInvalidaException extends RuntimeException {

    /**
     * @param mensagem descrição do problema, de preferência incluindo a linha inválida
     */
    public FichaInvalidaException(String mensagem) {
        super(mensagem);
    }
}
