package tokyo_spring_api.dende_eventos.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import tokyo_spring_api.dende_eventos.model.dto.response.CompraIngressoResponseDTO;
import tokyo_spring_api.dende_eventos.model.dto.response.IngressoResponseDTO;
import tokyo_spring_api.dende_eventos.services.IngressoService;

import java.util.List;

@RestController
@RequestMapping("/ingressos")
@Tag(name = "Ingressos", description = "Endpoints para compra, consulta e cancelamento de bilhetes")
public class IngressoController {


    private final IngressoService ingressoService;
    public IngressoController(IngressoService ingressoService) {
        this.ingressoService = ingressoService;
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar ingresso por ID", description = "Retorna os detalhes completos de um bilhete específico cadastrado no sistema.")
    @ApiResponse(responseCode = "200", description = "Ingresso localizado com sucesso")
    @ApiResponse(responseCode = "404", description = "Nenhum ingresso encontrado para o ID fornecido")
    public ResponseEntity<IngressoResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(ingressoService.buscarPorId(id));
    }

    @PostMapping("/usuario/{email}/evento/{eventoId}")
    @Operation(summary = "Comprar ingresso", description = "Gera e emite um bilhete ativo para o usuário se o evento possuir capacidade disponível.")
    @ApiResponse(responseCode = "200", description = "Compra processada e ingresso emitido com sucesso")
    @ApiResponse(responseCode = "400", description = "Erro na compra: Capacidade máxima esgotada ou usuário inválido")
    @ApiResponse(responseCode = "404", description = "Usuário comum ou Evento não localizado")
    public ResponseEntity<CompraIngressoResponseDTO> comprar(
            @PathVariable String email,
            @PathVariable Long eventoId) {
        return ResponseEntity.ok(ingressoService.comprar(email, eventoId));
    }

    @GetMapping("/usuario/{email}")
    @Operation(summary = "Listar ingressos do usuário", description = "Retorna o histórico de todos os ingressos comprados por um cliente através do e-mail.")
    @ApiResponse(responseCode = "200", description = "Lista de ingressos recuperada com sucesso")
    @ApiResponse(responseCode = "404", description = "Usuário comum não encontrado")
    public ResponseEntity<List<IngressoResponseDTO>> listarPorUsuario(@PathVariable String email) {
        return ResponseEntity.ok(ingressoService.listarPorUsuario(email));
    }

    @PatchMapping("/{id}/cancelar")
    @Operation(summary = "Cancelar ingresso", description = "Inativa o bilhete informado e calcula o estorno financeiro com base nos prazos vigentes.")
    @ApiResponse(responseCode = "200", description = "Cancelamento efetuado com sucesso (Retorna o valor do estorno calculado)")
    @ApiResponse(responseCode = "400", description = "Operação não permitida: O ingresso não pertence a este usuário ou já está cancelado")
    @ApiResponse(responseCode = "404", description = "ID de ingresso não localizado")
    public ResponseEntity<String> cancelar(
            @PathVariable Long id,
            @RequestParam String email) {
        return ResponseEntity.ok(ingressoService.cancelar(email, id));
    }
}