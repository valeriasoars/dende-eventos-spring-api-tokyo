package tokyo_spring_api.dende_eventos.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Usuários Organizadores", description = "Endpoints para gerenciamento de contas de produtores e controle de seus eventos corporativos")
public class UsuarioOrganizadorController {

    private final UsuarioOrganizadorService organizadorService;

    public UsuarioOrganizadorController(UsuarioOrganizadorService organizadorService) {
        this.organizadorService = organizadorService;
    }

    @PostMapping
    @Operation(summary = "Cadastrar usuário organizador", description = "Registra uma nova empresa ou produtor de eventos validando a unicidade do CNPJ e e-mail.")
    @ApiResponse(responseCode = "201", description = "Organizador cadastrado com sucesso")
    @ApiResponse(responseCode = "400", description = "Dados cadastrais inválidos")
    @ApiResponse(responseCode = "409", description = "E-mail ou CNPJ já existente no sistema")
    public ResponseEntity<String> cadastrar(@Valid @RequestBody CadastrarUsuarioOrganizadorRequestDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(organizadorService.cadastrar(dto));
    }

    @GetMapping("/{email}")
    @Operation(summary = "Buscar perfil do organizador", description = "Recupera os detalhes corporativos do produtor através de seu e-mail cadastrado.")
    @ApiResponse(responseCode = "200", description = "Perfil localizado com sucesso")
    @ApiResponse(responseCode = "404", description = "Organizador não encontrado")
    public ResponseEntity<PerfilOrganizadorResponseDTO> buscarPerfil(@PathVariable String email) {
        return ResponseEntity.ok(organizadorService.buscarPerfil(email));
    }

    @PutMapping("/{email}")
    @Operation(summary = "Alterar dados do perfil", description = "Atualiza as informações de contato, endereço corporativo ou razão social do organizador.")
    @ApiResponse(responseCode = "200", description = "Perfil atualizado com sucesso")
    @ApiResponse(responseCode = "400", description = "Dados informados inválidos")
    @ApiResponse(responseCode = "404", description = "Organizador não localizado")
    public ResponseEntity<String> alterar(
            @PathVariable String email,
            @Valid @RequestBody AlterarPerfilOrganizadorDTO dto) {
        return ResponseEntity.ok(organizadorService.alterar(email, dto));
    }

    @PatchMapping("/{email}/desativar")
    @Operation(summary = "Desativar conta do organizador", description = "Aplica uma trava de inativação no perfil do produtor. Contas inativas ficam impedidas de publicar novos eventos.")
    @ApiResponse(responseCode = "200", description = "Organizador desativado com sucesso")
    @ApiResponse(responseCode = "400", description = "Operação inválida: O usuário já se encontra inativo")
    @ApiResponse(responseCode = "404", description = "Organizador não localizado")
    public ResponseEntity<String> desativar(@PathVariable String email) {
        return ResponseEntity.ok(organizadorService.desativar(email));
    }

    @PatchMapping("/{email}/reativar")
    @Operation(summary = "Reativar conta do organizador", description = "Remove o bloqueio de inatividade exigindo a checagem da senha corporativa cadastrada.")
    @ApiResponse(responseCode = "200", description = "Organizador reativado com sucesso")
    @ApiResponse(responseCode = "400", description = "Senha incorreta ou usuário já ativo")
    @ApiResponse(responseCode = "404", description = "Organizador não localizado")
    public ResponseEntity<String> reativar(
            @PathVariable String email,
            @RequestBody(required = false) ReativarUsuarioDTO dto) {
        return ResponseEntity.ok(organizadorService.reativar(email, dto));
    }

    @PostMapping("/{email}/eventos")
    @Operation(summary = "Cadastrar novo evento", description = "Cria um show ou festa vinculado ao perfil deste produtor, especificando capacidade e barreira de estorno.")
    @ApiResponse(responseCode = "201", description = "Evento registrado e associado com sucesso")
    @ApiResponse(responseCode = "400", description = "Erro de negócio: Organizador inativo ou parâmetros de data/valores incorretos")
    @ApiResponse(responseCode = "404", description = "Organizador ou ID de evento principal associado não localizado")
    public ResponseEntity<String> cadastrarEvento(
            @PathVariable String email,
            @Valid @RequestBody CadastrarEventoRequestDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(organizadorService.cadastrarEvento(email, dto));
    }

    @PutMapping("/{email}/eventos/{eventoId}")
    @Operation(summary = "Alterar dados de um evento", description = "Permite ao produtor modificar os dados de um evento existente se ele possuir a propriedade dele.")
    @ApiResponse(responseCode = "200", description = "Dados do evento atualizados com sucesso")
    @ApiResponse(responseCode = "400", description = "Dados de modificação inválidos")
    @ApiResponse(responseCode = "403", description = "Acesso negado: Este evento pertence a outro organizador")
    @ApiResponse(responseCode = "404", description = "Evento ou ID do evento principal não encontrado")
    public ResponseEntity<String> alterarEvento(
            @PathVariable String email,
            @PathVariable Long eventoId,
            @Valid @RequestBody AlterarEventoRequestDto dto) {
        return ResponseEntity.ok(organizadorService.alterarEvento(email, eventoId, dto));
    }

    @GetMapping("/{email}/eventos")
    @Operation(summary = "Listar eventos do organizador", description = "Retorna a listagem completa de eventos criados por este organizador específico.")
    @ApiResponse(responseCode = "200", description = "Lista de eventos recuperada com sucesso")
    @ApiResponse(responseCode = "404", description = "Organizador não localizado")
    public ResponseEntity<List<EventoOrganizadorResponseDTO>> listarEventos(@PathVariable String email) {
        return ResponseEntity.ok(organizadorService.listarEventos(email));
    }

    @PatchMapping("/{email}/eventos/{eventoId}/status")
    @Operation(summary = "Alterar status do evento", description = "Modifica o estado atual do evento enviando comandos de alteração de ciclo ('ativar', 'desativar', 'cancelar' ou 'encerrar').")
    @ApiResponse(responseCode = "200", description = "Status do evento atualizado com sucesso e ações em cascata processadas")
    @ApiResponse(responseCode = "400", description = "Comando de ação inválido ou transição de estado proibida por regras de negócio")
    @ApiResponse(responseCode = "403", description = "Acesso negado: O evento informado pertence a outro produtor")
    @ApiResponse(responseCode = "404", description = "Evento não encontrado")
    public ResponseEntity<String> alterarStatus(
            @PathVariable String email,
            @PathVariable Long eventoId,
            @RequestParam String acao) {
        return ResponseEntity.ok(organizadorService.alterarStatusEvento(email, eventoId, acao));
    }
}