package tokyo_spring_api.dende_eventos.exceptions;

public class CapacidadeExcedidaException extends RuntimeException {

    public CapacidadeExcedidaException(Long eventoId) {
        super("Evento " + eventoId + " não possui vagas disponíveis.");
    }
}
