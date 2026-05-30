package tokyo_spring_api.dende_eventos.services;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import tokyo_spring_api.dende_eventos.exceptions.CapacidadeExcedidaException;
import tokyo_spring_api.dende_eventos.exceptions.EventoNaoEncontradoException;
import tokyo_spring_api.dende_eventos.exceptions.IngressoNaoEncontradoException;
import tokyo_spring_api.dende_eventos.exceptions.OperacaoNaoPermitidaException;
import tokyo_spring_api.dende_eventos.exceptions.UsuarioNaoEncontradoException;
import tokyo_spring_api.dende_eventos.mappers.IngressoMapper;
import tokyo_spring_api.dende_eventos.model.Evento;
import tokyo_spring_api.dende_eventos.model.Ingresso;
import tokyo_spring_api.dende_eventos.model.Usuario;
import tokyo_spring_api.dende_eventos.model.UsuarioComum;
import tokyo_spring_api.dende_eventos.model.dto.response.CompraIngressoResponseDTO;
import tokyo_spring_api.dende_eventos.model.dto.response.IngressoResponseDTO;
import tokyo_spring_api.dende_eventos.model.enums.StatusEvento;
import tokyo_spring_api.dende_eventos.model.enums.StatusIngresso;
import tokyo_spring_api.dende_eventos.repositories.EventoRepository;
import tokyo_spring_api.dende_eventos.repositories.IngressoRepository;
import tokyo_spring_api.dende_eventos.repositories.UsuarioRepository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class IngressoService {

    private final IngressoRepository ingressoRepository;
    private final EventoRepository eventoRepository;
    private final UsuarioRepository usuarioRepository;

    public IngressoService(IngressoRepository ingressoRepository,
                           EventoRepository eventoRepository,
                           UsuarioRepository usuarioRepository) {
        this.ingressoRepository = ingressoRepository;
        this.eventoRepository = eventoRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public IngressoResponseDTO buscarPorId(Long id) {
        Ingresso ingresso = ingressoRepository.findById(id)
                .orElseThrow(() -> new IngressoNaoEncontradoException(id));
        return IngressoMapper.toResponse(ingresso);
    }


    @Transactional
    public CompraIngressoResponseDTO comprar(String email, Long eventoId) {
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new UsuarioNaoEncontradoException(email));
        if (!(usuario instanceof UsuarioComum usuarioComum)) {
            throw new OperacaoNaoPermitidaException("O usuário informado não é um usuário comum.");
        }

        Evento evento = eventoRepository.findById(eventoId)
                .orElseThrow(() -> new EventoNaoEncontradoException(eventoId));

        int vagasOcupadas = ingressoRepository.countByEventoIdAndStatus(eventoId, StatusIngresso.ATIVO);
        evento.validarDisponibilidadeParaCompra(vagasOcupadas);
        List<Ingresso> ingressos = new ArrayList<>();

        if (evento.getEventoPrincipal() != null) {
            Evento eventoPrincipal = evento.getEventoPrincipal();
            int vagasPrincipal = ingressoRepository.countByEventoIdAndStatus(eventoPrincipal.getId(), StatusIngresso.ATIVO);
            eventoPrincipal.validarDisponibilidadeParaCompra(vagasPrincipal);

            ingressos.add(Ingresso.criar(evento, usuarioComum, evento.getPrecoIngresso()));
            ingressos.add(Ingresso.criar(eventoPrincipal, usuarioComum, eventoPrincipal.getPrecoIngresso()));
        } else {
            ingressos.add(Ingresso.criar(evento, usuarioComum, evento.getPrecoIngresso()));
        }

        ingressoRepository.saveAll(ingressos);
        return IngressoMapper.toCompraResponse(ingressos);
    }

    public List<IngressoResponseDTO> listarPorUsuario(String email) {
        usuarioRepository.findByEmail(email)
                .filter(u -> u instanceof UsuarioComum)
                .orElseThrow(() -> new UsuarioNaoEncontradoException(email));

        return ingressoRepository.findByUsuarioEmail(email)
                .stream()
                .map(IngressoMapper::toResponse)
                .toList();
    }

    public String cancelar(String email, Long ingressoId) {
        Ingresso ingresso = ingressoRepository.findById(ingressoId)
                .orElseThrow(() -> new IngressoNaoEncontradoException(ingressoId));

        if (!ingresso.getUsuario().getEmail().equals(email)) {
            throw new OperacaoNaoPermitidaException("Ingresso não pertence a este usuário.");
        }

        BigDecimal valorEstorno = ingresso.cancelarIngresso();
        ingressoRepository.save(ingresso);

        String mensagem = "Ingresso cancelado com sucesso!";
        if (valorEstorno.compareTo(BigDecimal.ZERO) > 0) {
            mensagem += " Valor de estorno: R$ " + String.format("%.2f", valorEstorno);
        } else {
            mensagem += " Sem estorno disponível.";
        }
        return mensagem;
    }
}
