package tokyo_spring_api.dende_eventos.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import tokyo_spring_api.dende_eventos.model.dto.response.EventoResponseDTO;
import tokyo_spring_api.dende_eventos.model.enums.ModalidadeEvento;
import tokyo_spring_api.dende_eventos.model.enums.TipoEvento;
import tokyo_spring_api.dende_eventos.services.EventoService;

import java.util.List;

@RestController
@RequestMapping("/eventos")
@Tag(name = "Eventos", description = "Endpoints para consulta e listagem pública de shows e festas")
public class EventoController {

    private final EventoService eventoService;

    public EventoController(EventoService eventoService) {
        this.eventoService = eventoService;
    }

    @GetMapping
    @Operation(summary = "Visualizar feed de eventos", description = "Retorna a listagem de eventos ativos com base nos filtros opcionais de tipo, modalidade e nome.")
    @ApiResponse(responseCode = "200", description = "Feed de eventos carregado com sucesso")
    public ResponseEntity<List<EventoResponseDTO>> feedEventos(
            @RequestParam(required = false) TipoEvento tipo,
            @RequestParam(required = false) ModalidadeEvento modalidade,
            @RequestParam(required = false) String nome) {
        return ResponseEntity.ok(eventoService.feedPublico(tipo, modalidade, nome));
    }
}