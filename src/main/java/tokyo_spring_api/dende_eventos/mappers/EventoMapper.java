package tokyo_spring_api.dende_eventos.mappers;

public class EventoMapper {

    public static Evento toModel(CadastrarEventoRequestDto dto, Evento eventoPrincipal) {
        return new EventoBuilder()
                .nome(dto.nome())
                .descricao(dto.descricao())
                .paginaEvento(dto.paginaEvento())
                .dataInicio(dto.dataInicio())
                .dataFinal(dto.dataFinal())
                .tipo(dto.tipo())
                .modalidade(dto.modalidade())
                .capacidadeMaxima(dto.capacidadeMaxima())
                .localAcesso(dto.localAcesso())
                .precoIngresso(dto.precoIngresso())
                .permiteEstorno(dto.permiteEstorno())
                .taxaEstorno(dto.taxaEstorno())
                .eventoPrincipal(eventoPrincipal)
                .build();
    }

    public static EventoResponseDTO toResponse(Evento evento){
        String nomeOrganizador = (evento.getOrganizador() != null) ? evento.getOrganizador().getNome() : null;
        return new EventoResponseDTO(
                evento.getNome(),
                evento.getDescricao(),
                evento.getDataInicio(),
                evento.getDataFinal(),
                nomeOrganizador,
                evento.getTipo(),
                evento.getModalidade(),
                evento.getCapacidadeMaxima(),
                evento.getLocalAcesso(),
                evento.getPrecoIngresso()
        );
    }
}