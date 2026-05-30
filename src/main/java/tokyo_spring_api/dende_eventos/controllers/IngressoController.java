package tokyo_spring_api.dende_eventos.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tokyo_spring_api.dende_eventos.exceptions.*;
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
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(ingressoService.buscarPorId(id));
        } catch (IngressoNaoEncontradoException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    @PostMapping("/usuario/{email}/evento/{eventoId}")
    public ResponseEntity<?> comprar(
            @PathVariable String email,
            @PathVariable Long eventoId) {
        try {
            CompraIngressoResponseDTO response = ingressoService.comprar(email, eventoId);
            return ResponseEntity.ok(response);
        } catch (UsuarioNaoEncontradoException | EventoNaoEncontradoException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        } catch (OperacaoNaoPermitidaException | CapacidadeExcedidaException |
                 IllegalArgumentException | IllegalStateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/usuario/{email}")
    public ResponseEntity<?> listarPorUsuario(@PathVariable String email) {
        try {
            List<IngressoResponseDTO> lista = ingressoService.listarPorUsuario(email);
            return ResponseEntity.ok(lista);
        } catch (UsuarioNaoEncontradoException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        } catch (OperacaoNaoPermitidaException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PatchMapping("/{id}/cancelar")
    public ResponseEntity<?> cancelar(
            @PathVariable Long id,
            @RequestParam String email) {
        try {
            return ResponseEntity.ok(ingressoService.cancelar(email, id));
        } catch (IngressoNaoEncontradoException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        } catch (OperacaoNaoPermitidaException | IllegalStateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
