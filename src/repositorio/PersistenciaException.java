package repositorio;

/**
 * Lançada quando uma operação de arquivo (ler txt, exportar/importar .dat)
 * falha por um problema de I/O. Existe para que a camada de persistência avise
 * o erro sem imprimir nada no console: quem decide a mensagem ao usuário é a
 * camada de menu. Carrega a causa original (a exceção de I/O) para diagnóstico.
 */
public class PersistenciaException extends RuntimeException {

    /**
     * @param mensagem descrição do que falhou
     * @param causa    exceção original de I/O que provocou a falha
     */
    public PersistenciaException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
}
