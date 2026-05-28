package tokyo_spring_api.dende_eventos.exceptions;

public class IngressoNaoEncontradoException extends RuntimeException {

    public IngressoNaoEncontradoException(Long id) {
        super("Ingresso não encontrado com o id: " + id);
    }
}
