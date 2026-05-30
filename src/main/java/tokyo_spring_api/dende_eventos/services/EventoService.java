package tokyo_spring_api.dende_eventos.services;

import org.springframework.stereotype.Service;
import tokyo_spring_api.dende_eventos.mappers.EventoMapper;
import tokyo_spring_api.dende_eventos.model.dto.response.EventoResponseDTO;
import tokyo_spring_api.dende_eventos.model.enums.ModalidadeEvento;
import tokyo_spring_api.dende_eventos.model.enums.TipoEvento;
import tokyo_spring_api.dende_eventos.repositories.EventoRepository;

import java.util.List;

@Service
public class EventoService {

    private final EventoRepository eventoRepository;

    public EventoService(EventoRepository eventoRepository) {
        this.eventoRepository = eventoRepository;
    }

    public List<EventoResponseDTO> feedPublico(TipoEvento tipo, ModalidadeEvento modalidade, String nome) {
        return eventoRepository.findFeedPublico(null, null, null)
                .stream()
                .map(EventoMapper::toResponse)
                .toList();
    }
}
