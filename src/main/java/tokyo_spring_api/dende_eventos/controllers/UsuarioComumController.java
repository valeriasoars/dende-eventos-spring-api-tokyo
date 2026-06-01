package tokyo_spring_api.dende_eventos.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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
    public ResponseEntity<String> cadastrar(@RequestBody CadastrarUsuarioComumRequestDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioComumService.cadastrar(dto));
    }

    @GetMapping("/{email}")
    public ResponseEntity<PerfilComumResponseDTO> buscarPerfil(@PathVariable String email) {
        return ResponseEntity.ok(usuarioComumService.buscarPerfil(email));
    }

    @PutMapping("/{email}")
    public ResponseEntity<String> alterar(
            @PathVariable String email,
            @RequestBody AlterarPerfilComumDTO dto) {
        return ResponseEntity.ok(usuarioComumService.alterar(email, dto));
    }

    @PatchMapping("/{email}/desativar")
    public ResponseEntity<String> desativar(@PathVariable String email) {
        return ResponseEntity.ok(usuarioComumService.desativar(email));
    }

    @PatchMapping("/{email}/reativar")
    public ResponseEntity<String> reativar(
            @PathVariable String email,
            @RequestBody(required = false) ReativarUsuarioDTO dto) {
        return ResponseEntity.ok(usuarioComumService.reativar(email, dto));
    }
}