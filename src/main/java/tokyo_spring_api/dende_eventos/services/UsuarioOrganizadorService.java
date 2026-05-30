package tokyo_spring_api.dende_eventos.services;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import tokyo_spring_api.dende_eventos.exceptions.EmailJaCadastradoException;
import tokyo_spring_api.dende_eventos.exceptions.EventoNaoEncontradoException;
import tokyo_spring_api.dende_eventos.exceptions.OperacaoNaoPermitidaException;
import tokyo_spring_api.dende_eventos.exceptions.UsuarioNaoEncontradoException;
import tokyo_spring_api.dende_eventos.mappers.EventoMapper;
import tokyo_spring_api.dende_eventos.mappers.UsuarioOrganizadorMapper;
import tokyo_spring_api.dende_eventos.model.Evento;
import tokyo_spring_api.dende_eventos.model.Usuario;
import tokyo_spring_api.dende_eventos.model.UsuarioOrganizador;
import tokyo_spring_api.dende_eventos.model.dto.AlterarPerfilOrganizadorDTO;
import tokyo_spring_api.dende_eventos.model.dto.ReativarUsuarioDTO;
import tokyo_spring_api.dende_eventos.model.dto.request.AlterarEventoRequestDto;
import tokyo_spring_api.dende_eventos.model.dto.request.CadastrarEventoRequestDto;
import tokyo_spring_api.dende_eventos.model.dto.request.CadastrarUsuarioOrganizadorRequestDto;
import tokyo_spring_api.dende_eventos.model.dto.response.EventoOrganizadorResponseDTO;
import tokyo_spring_api.dende_eventos.model.dto.response.PerfilOrganizadorResponseDTO;
import tokyo_spring_api.dende_eventos.repositories.EventoRepository;
import tokyo_spring_api.dende_eventos.repositories.UsuarioRepository;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsuarioOrganizadorService {

    private final UsuarioRepository usuarioRepository;
    private final EventoRepository eventoRepository;

    public UsuarioOrganizadorService(UsuarioRepository usuarioRepository, EventoRepository eventoRepository) {
        this.usuarioRepository = usuarioRepository;
        this.eventoRepository = eventoRepository;
    }

    public String cadastrar(CadastrarUsuarioOrganizadorRequestDto dto) {
        if (usuarioRepository.existsByEmail(dto.email())) {
            throw new EmailJaCadastradoException(dto.email());
        }
        UsuarioOrganizador organizador = UsuarioOrganizadorMapper.toModel(dto);
        usuarioRepository.save(organizador);
        return "Organizador " + organizador.getEmail() + " cadastrado com sucesso!";
    }

    public PerfilOrganizadorResponseDTO buscarPerfil(String email) {
        UsuarioOrganizador organizador = buscarOrganizador(email);
        return UsuarioOrganizadorMapper.toResponse(organizador);
    }

    @Transactional
    public String alterar(String email, AlterarPerfilOrganizadorDTO dto) {
        UsuarioOrganizador organizador = buscarOrganizador(email);
        organizador.alterarPerfil(dto);
        usuarioRepository.save(organizador);
        return "Perfil de " + email + " atualizado com sucesso.";
    }

    @Transactional
    public String desativar(String email) {
        UsuarioOrganizador organizador = buscarOrganizador(email);
        if (!organizador.isAtivo()) {
            throw new OperacaoNaoPermitidaException("Organizador ja esta inativo.");
        }
        organizador.desativarUsuario();
        usuarioRepository.save(organizador);
        return "Organizador desativado com sucesso.";
    }

    @Transactional
    public String reativar(String email, ReativarUsuarioDTO dto) {
        UsuarioOrganizador organizador = buscarOrganizador(email);
        if (organizador.isAtivo()) {
            throw new OperacaoNaoPermitidaException("Organizador ja esta ativo.");
        }
        String senha = (dto != null) ? dto.senha() : null;
        organizador.reativarUsuario(email, senha);
        usuarioRepository.save(organizador);
        return "Organizador reativado com sucesso.";
    }


    @Transactional
    public String cadastrarEvento(String email, CadastrarEventoRequestDto dto) {
        UsuarioOrganizador organizador = buscarOrganizador(email);
        if (!organizador.isAtivo()) {
            throw new OperacaoNaoPermitidaException("Organizador inativo nao pode cadastrar eventos.");
        }

        Evento eventoPrincipal = null;
        if (dto.eventoPrincipalId() != null) {
            eventoPrincipal = eventoRepository.findById(dto.eventoPrincipalId())
                    .orElseThrow(() -> new EventoNaoEncontradoException(dto.eventoPrincipalId()));
        }

        Evento evento = EventoMapper.toModel(dto, eventoPrincipal);
        organizador.cadastrarEvento(evento);
        eventoRepository.save(evento);
        return "Evento '" + evento.getNome() + "' cadastrado com sucesso.";
    }

    @Transactional
    public String alterarEvento(String email, Long eventoId, AlterarEventoRequestDto dto) {
        buscarOrganizador(email);
        Evento evento = eventoRepository.findById(eventoId)
                .orElseThrow(() -> new EventoNaoEncontradoException(eventoId));

        if (!evento.getUsuarioOrganizador().getEmail().equals(email)) {
            throw new OperacaoNaoPermitidaException("Voce nao tem permissao para alterar este evento.");
        }

        Evento eventoPrincipal = null;
        if (dto.eventoPrincipalId() != null) {
            eventoPrincipal = eventoRepository.findById(dto.eventoPrincipalId())
                    .orElseThrow(() -> new EventoNaoEncontradoException(dto.eventoPrincipalId()));
        }

        Evento novosDados = Evento.parcialParaAlterar(
                dto.nome(), dto.descricao(), dto.paginaEvento(),
                dto.dataInicio(), dto.dataFinal(),
                dto.tipo(), dto.modalidade(),
                dto.capacidadeMaxima(), dto.localAcesso(),
                dto.precoIngresso(), dto.permiteEstorno(), dto.taxaEstorno(),
                eventoPrincipal
        );

        evento.alterarDados(novosDados);
        eventoRepository.save(evento);
        return "Evento alterado com sucesso.";
    }

    // Ordenação feita pelo banco no repository — sem .sorted() aqui
    /*public List<EventoOrganizadorResponseDTO> listarEventos(String email) {
        buscarOrganizador(email);
        return eventoRepository.findByUsuarioOrganizadorEmail(email).stream()
                .sorted(Comparator.comparing(Evento::getDataInicio)
                        .thenComparing(Evento::getNome, String.CASE_INSENSITIVE_ORDER))
                .map(UsuarioOrganizadorMapper::toListarEventoOrganizadorDTO)
                .collect(Collectors.toList());
    }*/

    public List<EventoOrganizadorResponseDTO> listarEventos(String email) {
        buscarOrganizador(email);
        return eventoRepository.findByUsuarioOrganizadorEmail(email)
                .stream()
                .map(UsuarioOrganizadorMapper::toListarEventoOrganizadorDTO)
                .toList();
    }


    @Transactional
    public String alterarStatusEvento(String email, Long eventoId, String status) {
        buscarOrganizador(email);
        Evento evento = eventoRepository.findById(eventoId)
                .orElseThrow(() -> new EventoNaoEncontradoException(eventoId));

        if (!evento.getUsuarioOrganizador().getEmail().equals(email)) {
            throw new OperacaoNaoPermitidaException("Voce nao tem permissao para alterar este evento.");
        }

        return switch (status.toLowerCase()) {
            case "ativar" -> {
                evento.ativarEvento();
                eventoRepository.save(evento);
                yield "Evento ativado com sucesso!";
            }
            case "desativar" -> {
                evento.desativarEvento();
                eventoRepository.save(evento);
                yield "Evento desativado com sucesso! Ingressos ativos foram cancelados.";
            }
            case "cancelar" -> {
                evento.cancelarEvento();
                eventoRepository.save(evento);
                yield "Evento cancelado com sucesso! Ingressos ativos foram cancelados.";
            }
            case "encerrar" -> {
                evento.encerrarEvento();
                eventoRepository.save(evento);
                yield "Evento encerrado com sucesso!";
            }
            default -> throw new OperacaoNaoPermitidaException(
                    "Status invalido. Use 'ativar', 'desativar', 'cancelar' ou 'encerrar'.");
        };
    }

    private UsuarioOrganizador buscarOrganizador(String email) {
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new UsuarioNaoEncontradoException(email));
        if (!(usuario instanceof UsuarioOrganizador organizador)) {
            throw new UsuarioNaoEncontradoException(email);
        }
        return organizador;
    }
}
