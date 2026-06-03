package tokyo_spring_api.dende_eventos.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import tokyo_spring_api.dende_eventos.model.dto.AlterarPerfilComumDTO;
import tokyo_spring_api.dende_eventos.model.dto.ReativarUsuarioDTO;
import tokyo_spring_api.dende_eventos.model.dto.request.CadastrarUsuarioComumRequestDto;
import tokyo_spring_api.dende_eventos.model.dto.response.PerfilComumResponseDTO;
import tokyo_spring_api.dende_eventos.services.UsuarioComumService;

@RestController
@RequestMapping("/usuarios")
@Tag(name = "Usuários Comuns", description = "Endpoints para cadastro, consulta e gerenciamento de perfis de clientes compradores")
public class UsuarioComumController {

    private final UsuarioComumService usuarioComumService;

    public UsuarioComumController(UsuarioComumService usuarioComumService) {
        this.usuarioComumService = usuarioComumService;
    }

    @PostMapping
    @Operation(summary = "Cadastrar usuário comum", description = "Realiza o registro de um novo cliente comprador no sistema, validando se o e-mail informado é único.")
    @ApiResponse(responseCode = "201", description = "Usuário cadastrado com sucesso")
    @ApiResponse(responseCode = "400", description = "Dados de entrada inválidos")
    @ApiResponse(responseCode = "409", description = "Conflito: E-mail já cadastrado no sistema")
    public ResponseEntity<String> cadastrar(@Valid @RequestBody CadastrarUsuarioComumRequestDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioComumService.cadastrar(dto));
    }

    @GetMapping("/{email}")
    @Operation(summary = "Buscar perfil do usuário", description = "Recupera os detalhes do perfil cadastrado de um cliente através do e-mail.")
    @ApiResponse(responseCode = "200", description = "Perfil localizado com sucesso")
    @ApiResponse(responseCode = "404", description = "Usuário comum não encontrado")
    public ResponseEntity<PerfilComumResponseDTO> buscarPerfil(@PathVariable String email) {
        return ResponseEntity.ok(usuarioComumService.buscarPerfil(email));
    }

    @PutMapping("/{email}")
    @Operation(summary = "Alterar dados do perfil", description = "Atualiza as informações cadastrais do perfil do comprador informado.")
    @ApiResponse(responseCode = "200", description = "Perfil atualizado com sucesso")
    @ApiResponse(responseCode = "400", description = "Dados de atualização inválidos")
    @ApiResponse(responseCode = "404", description = "Usuário comum não localizado")
    public ResponseEntity<String> alterar(
            @PathVariable String email,
            @Valid @RequestBody AlterarPerfilComumDTO dto) {
        return ResponseEntity.ok(usuarioComumService.alterar(email, dto));
    }

    @PatchMapping("/{email}/desativar")
    @Operation(summary = "Desativar conta do usuário", description = "Aplica o bloqueio de inatividade no perfil do cliente comum.")
    @ApiResponse(responseCode = "200", description = "Usuário desativado com sucesso")
    @ApiResponse(responseCode = "404", description = "Usuário comum não localizado")
    public ResponseEntity<String> desativar(@PathVariable String email) {
        return ResponseEntity.ok(usuarioComumService.desativar(email));
    }

    @PatchMapping("/{email}/reativar")
    @Operation(summary = "Reativar conta do usuário", description = "Remove a trava de inatividade da conta validando o e-mail e os dados de confirmação de senha.")
    @ApiResponse(responseCode = "200", description = "Usuário reativado com sucesso")
    @ApiResponse(responseCode = "400", description = "Senha incorreta para reativação")
    @ApiResponse(responseCode = "404", description = "Usuário comum não localizado")
    public ResponseEntity<String> reativar(
            @PathVariable String email,
            @RequestBody(required = false) ReativarUsuarioDTO dto) {
        return ResponseEntity.ok(usuarioComumService.reativar(email, dto));
    }
}