package tokyo_spring_api.dende_eventos.model.dto.response;

import tokyo_spring_api.dende_eventos.model.enums.ModalidadeEvento;
import tokyo_spring_api.dende_eventos.model.enums.TipoEvento;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record EventoResponseDTO(
        String nome,
        String descricao,
        LocalDateTime dataHoraInicio,
        LocalDateTime dataHoraFim,
        String nomeOrganizador,
        TipoEvento tipo,
        ModalidadeEvento modalidade,
        Integer vagasDisponiveis,
        String localAcesso,
        BigDecimal precoIngresso
){}
