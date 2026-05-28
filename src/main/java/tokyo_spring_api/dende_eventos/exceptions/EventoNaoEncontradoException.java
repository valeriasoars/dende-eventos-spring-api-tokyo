package tokyo_spring_api.dende_eventos.exceptions;

public class EventoNaoEncontradoException extends RuntimeException {

    public EventoNaoEncontradoException(Long id) {
        super("Evento não encontrado com o id: " + id);
    }
}
