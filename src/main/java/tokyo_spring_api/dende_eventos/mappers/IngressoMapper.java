package tokyo_spring_api.dende_eventos.mappers;

import tokyo_spring_api.dende_eventos.model.Ingresso;
import tokyo_spring_api.dende_eventos.model.dto.response.CompraIngressoResponseDTO;
import tokyo_spring_api.dende_eventos.model.dto.response.IngressoResponseDTO;

import java.util.List;

public class IngressoMapper {
    public static IngressoResponseDTO toResponse(Ingresso ingresso) {
        return new IngressoResponseDTO(
                ingresso.getId(),
                ingresso.getEvento().getNome(),
                ingresso.getEvento().getDataInicio(),
                ingresso.getValorPago(),
                ingresso.getStatus().name(),
                ingresso.getDataCompra()
        );
    }

    public static CompraIngressoResponseDTO toCompraResponse(List<Ingresso> ingressos) {
        java.math.BigDecimal valorTotal = Ingresso.calcularValorTotal(ingressos);
        List<IngressoResponseDTO> lista = ingressos.stream()
                .map(IngressoMapper::toResponse)
                .toList();
        return new CompraIngressoResponseDTO(lista, valorTotal);
    }
}