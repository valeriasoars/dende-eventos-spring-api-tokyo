package tokyo_spring_api.dende_eventos.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tokyo_spring_api.dende_eventos.exceptions.EmailJaCadastradoException;
import tokyo_spring_api.dende_eventos.exceptions.UsuarioNaoEncontradoException;
import tokyo_spring_api.dende_eventos.model.dto.AlterarPerfilComumDTO;
import tokyo_spring_api.dende_eventos.model.dto.ReativarUsuarioDTO;
import tokyo_spring_api.dende_eventos.model.dto.request.CadastrarUsuarioComumRequestDto;
import tokyo_spring_api.dende_eventos.model.dto.response.PerfilComumResponseDTO;
import tokyo_spring_api.dende_eventos.services.UsuarioComumService;

@RestController
@RequestMapping("/usuarios")
public class UsuarioComumController {
    private final UsuarioComumService usuarioComumService;

    public UsuarioComumController(UsuarioComumService usuarioComumService) {
        this.usuarioComumService = usuarioComumService;
    }

    @PostMapping
    public ResponseEntity<?> cadastrar(@RequestBody CadastrarUsuarioComumRequestDto dto) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(usuarioComumService.cadastrar(dto));
        } catch (EmailJaCadastradoException e) {
            return ResponseEntity.status(409).body(e.getMessage());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{email}")
    public ResponseEntity<?> buscarPerfil(@PathVariable String email) {
        try {
            PerfilComumResponseDTO perfil = usuarioComumService.buscarPerfil(email);
            return ResponseEntity.ok(perfil);
        } catch (UsuarioNaoEncontradoException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    @PutMapping("/{email}")
    public ResponseEntity<?> alterar(
            @PathVariable String email,
            @RequestBody AlterarPerfilComumDTO dto) {
        try {
            return ResponseEntity.ok(usuarioComumService.alterar(email, dto));
        } catch (UsuarioNaoEncontradoException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PatchMapping("/{email}/desativar")
    public ResponseEntity<?> desativar(@PathVariable String email) {
        try {
            return ResponseEntity.ok(usuarioComumService.desativar(email));
        } catch (UsuarioNaoEncontradoException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PatchMapping("/{email}/reativar")
    public ResponseEntity<?> reativar(
            @PathVariable String email,
            @RequestBody(required = false) ReativarUsuarioDTO dto) {
        try {
            return ResponseEntity.ok(usuarioComumService.reativar(email, dto));
        } catch (UsuarioNaoEncontradoException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        } catch (IllegalArgumentException | IllegalStateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
