package tokyo_spring_api.dende_eventos.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tokyo_spring_api.dende_eventos.model.dto.response.CompraIngressoResponseDTO;
import tokyo_spring_api.dende_eventos.model.dto.response.IngressoResponseDTO;
import tokyo_spring_api.dende_eventos.services.IngressoService;

import java.util.List;

@RestController
@RequestMapping("/ingressos")
public class IngressoController {

    private final IngressoService ingressoService;

    public IngressoController(IngressoService ingressoService) {
        this.ingressoService = ingressoService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<IngressoResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(ingressoService.buscarPorId(id));
    }

    @PostMapping("/usuario/{email}/evento/{eventoId}")
    public ResponseEntity<CompraIngressoResponseDTO> comprar(
            @PathVariable String email,
            @PathVariable Long eventoId) {
        return ResponseEntity.ok(ingressoService.comprar(email, eventoId));
    }

    @GetMapping("/usuario/{email}")
    public ResponseEntity<List<IngressoResponseDTO>> listarPorUsuario(@PathVariable String email) {
        return ResponseEntity.ok(ingressoService.listarPorUsuario(email));
    }

    @PatchMapping("/{id}/cancelar")
    public ResponseEntity<String> cancelar(
            @PathVariable Long id,
            @RequestParam String email) {
        return ResponseEntity.ok(ingressoService.cancelar(email, id));
    }
}