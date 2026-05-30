package tokyo_spring_api.dende_eventos.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tokyo_spring_api.dende_eventos.exceptions.EmailJaCadastradoException;
import tokyo_spring_api.dende_eventos.exceptions.EventoNaoEncontradoException;
import tokyo_spring_api.dende_eventos.exceptions.OperacaoNaoPermitidaException;
import tokyo_spring_api.dende_eventos.exceptions.UsuarioNaoEncontradoException;
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
    public ResponseEntity<?> cadastrar(@RequestBody CadastrarUsuarioOrganizadorRequestDto dto) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(organizadorService.cadastrar(dto));
        } catch (EmailJaCadastradoException e) {
            return ResponseEntity.status(409).body(e.getMessage());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{email}")
    public ResponseEntity<?> buscarPerfil(@PathVariable String email) {
        try {
            PerfilOrganizadorResponseDTO perfil = organizadorService.buscarPerfil(email);
            return ResponseEntity.ok(perfil);
        } catch (UsuarioNaoEncontradoException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    @PutMapping("/{email}")
    public ResponseEntity<?> alterar(
            @PathVariable String email,
            @RequestBody AlterarPerfilOrganizadorDTO dto) {
        try {
            return ResponseEntity.ok(organizadorService.alterar(email, dto));
        } catch (UsuarioNaoEncontradoException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PatchMapping("/{email}/desativar")
    public ResponseEntity<?> desativar(@PathVariable String email) {
        try {
            return ResponseEntity.ok(organizadorService.desativar(email));
        } catch (UsuarioNaoEncontradoException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        } catch (OperacaoNaoPermitidaException | IllegalStateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PatchMapping("/{email}/reativar")
    public ResponseEntity<?> reativar(
            @PathVariable String email,
            @RequestBody(required = false) ReativarUsuarioDTO dto) {
        try {
            return ResponseEntity.ok(organizadorService.reativar(email, dto));
        } catch (UsuarioNaoEncontradoException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        } catch (OperacaoNaoPermitidaException | IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/{email}/eventos")
    public ResponseEntity<?> cadastrarEvento(
            @PathVariable String email,
            @RequestBody CadastrarEventoRequestDto dto) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(organizadorService.cadastrarEvento(email, dto));
        } catch (UsuarioNaoEncontradoException | EventoNaoEncontradoException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        } catch (OperacaoNaoPermitidaException | IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{email}/eventos/{eventoId}")
    public ResponseEntity<?> alterarEvento(
            @PathVariable String email,
            @PathVariable Long eventoId,
            @RequestBody AlterarEventoRequestDto dto) {
        try {
            return ResponseEntity.ok(organizadorService.alterarEvento(email, eventoId, dto));
        } catch (UsuarioNaoEncontradoException | EventoNaoEncontradoException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        } catch (OperacaoNaoPermitidaException | IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{email}/eventos")
    public ResponseEntity<?> listarEventos(@PathVariable String email) {
        try {
            List<EventoOrganizadorResponseDTO> lista = organizadorService.listarEventos(email);
            return ResponseEntity.ok(lista);
        } catch (UsuarioNaoEncontradoException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    @PatchMapping("/{email}/eventos/{eventoId}/status")
    public ResponseEntity<?> alterarStatus(
            @PathVariable String email,
            @PathVariable Long eventoId,
            @RequestParam String acao) {
        try {
            return ResponseEntity.ok(organizadorService.alterarStatusEvento(email, eventoId, acao));
        } catch (UsuarioNaoEncontradoException | EventoNaoEncontradoException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        } catch (OperacaoNaoPermitidaException | IllegalStateException | IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
