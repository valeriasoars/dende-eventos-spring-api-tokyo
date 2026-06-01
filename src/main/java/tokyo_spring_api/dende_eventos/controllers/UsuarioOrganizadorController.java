package tokyo_spring_api.dende_eventos.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tokyo_spring_api.dende_eventos.model.dto.AlterarPerfilOrganizadorDTO;
import tokyo_spring_api.dende_eventos.model.dto.ReativarUsuarioDTO;
import tokyo_spring_api.dende_eventos.model.dto.request.AlterarEventoRequestDto;
import tokyo_spring_api.dende_eventos.model.dto.request.CadastrarEventoRequestDto;
import tokyo_spring_api.dende_eventos.model.dto.request.CadastrarUsuarioOrganizadorRequestDto;
import tokyo_spring_api.dende_eventos.model.dto.response.EventoOrganizadorResponseDTO;
import tokyo_spring_api.dende_eventos.model.dto.response.PerfilOrganizadorResponseDTO;
import tokyo_spring_api.dende_eventos.services.UsuarioOrganizadorService;

import java.util.List;

@RestController
@RequestMapping("/organizadores")
public class UsuarioOrganizadorController {

    private final UsuarioOrganizadorService organizadorService;

    public UsuarioOrganizadorController(UsuarioOrganizadorService organizadorService) {
        this.organizadorService = organizadorService;
    }

    @PostMapping
    public ResponseEntity<String> cadastrar(@RequestBody CadastrarUsuarioOrganizadorRequestDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(organizadorService.cadastrar(dto));
    }

    @GetMapping("/{email}")
    public ResponseEntity<PerfilOrganizadorResponseDTO> buscarPerfil(@PathVariable String email) {
        return ResponseEntity.ok(organizadorService.buscarPerfil(email));
    }

    @PutMapping("/{email}")
    public ResponseEntity<String> alterar(
            @PathVariable String email,
            @RequestBody AlterarPerfilOrganizadorDTO dto) {
        return ResponseEntity.ok(organizadorService.alterar(email, dto));
    }

    @PatchMapping("/{email}/desativar")
    public ResponseEntity<String> desativar(@PathVariable String email) {
        return ResponseEntity.ok(organizadorService.desativar(email));
    }

    @PatchMapping("/{email}/reativar")
    public ResponseEntity<String> reativar(
            @PathVariable String email,
            @RequestBody(required = false) ReativarUsuarioDTO dto) {
        return ResponseEntity.ok(organizadorService.reativar(email, dto));
    }

    @PostMapping("/{email}/eventos")
    public ResponseEntity<String> cadastrarEvento(
            @PathVariable String email,
            @RequestBody CadastrarEventoRequestDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(organizadorService.cadastrarEvento(email, dto));
    }

    @PutMapping("/{email}/eventos/{eventoId}")
    public ResponseEntity<String> alterarEvento(
            @PathVariable String email,
            @PathVariable Long eventoId,
            @RequestBody AlterarEventoRequestDto dto) {
        return ResponseEntity.ok(organizadorService.alterarEvento(email, eventoId, dto));
    }

    @GetMapping("/{email}/eventos")
    public ResponseEntity<List<EventoOrganizadorResponseDTO>> listarEventos(@PathVariable String email) {
        return ResponseEntity.ok(organizadorService.listarEventos(email));
    }

    @PatchMapping("/{email}/eventos/{eventoId}/status")
    public ResponseEntity<String> alterarStatus(
            @PathVariable String email,
            @PathVariable Long eventoId,
            @RequestParam String acao) {
        return ResponseEntity.ok(organizadorService.alterarStatusEvento(email, eventoId, acao));
    }
}