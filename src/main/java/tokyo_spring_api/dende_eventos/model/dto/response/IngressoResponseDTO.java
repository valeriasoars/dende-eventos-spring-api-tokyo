package tokyo_spring_api.dende_eventos.model.dto.response;


import java.math.BigDecimal;
import java.time.LocalDateTime;

public record IngressoResponseDTO(
        Long id,
        String nomeEvento,
        LocalDateTime dataEvento,
        BigDecimal valorPago,
        String status,
        LocalDateTime dataCompra
) {
}

