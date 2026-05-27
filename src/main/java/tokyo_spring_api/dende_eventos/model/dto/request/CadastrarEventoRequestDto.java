package tokyo_spring_api.dende_eventos.model.dto.request;

import tokyo_spring_api.dende_eventos.model.enums.ModalidadeEvento;
import tokyo_spring_api.dende_eventos.model.enums.TipoEvento;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record CadastrarEventoRequestDto(
        String nome,
        String descricao,
        String paginaEvento,
        LocalDateTime dataInicio,
        LocalDateTime dataFinal,
        TipoEvento tipo,
        ModalidadeEvento modalidade,
        Integer capacidadeMaxima,
        String localAcesso,
        BigDecimal precoIngresso,
        Boolean permiteEstorno,
        BigDecimal taxaEstorno,
        Long eventoPrincipalId
) {
}
