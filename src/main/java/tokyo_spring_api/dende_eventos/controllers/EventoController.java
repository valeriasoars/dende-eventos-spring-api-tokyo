package tokyo_spring_api.dende_eventos.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tokyo_spring_api.dende_eventos.model.dto.response.EventoResponseDTO;
import tokyo_spring_api.dende_eventos.model.enums.ModalidadeEvento;
import tokyo_spring_api.dende_eventos.model.enums.TipoEvento;
import tokyo_spring_api.dende_eventos.services.EventoService;

import java.util.List;

@RestController
@RequestMapping("/eventos")
public class EventoController {

    private final EventoService eventoService;

    public EventoController(EventoService eventoService) {
        this.eventoService = eventoService;
    }

    @GetMapping
    public ResponseEntity<List<EventoResponseDTO>> feedEventos(
            @RequestParam(required = false) TipoEvento tipo,
            @RequestParam(required = false) ModalidadeEvento modalidade,
            @RequestParam(required = false) String nome) {
        return ResponseEntity.ok(eventoService.feedPublico(tipo, modalidade, nome));
    }
}