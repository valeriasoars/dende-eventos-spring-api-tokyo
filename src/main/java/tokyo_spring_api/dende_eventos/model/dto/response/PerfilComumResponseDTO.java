package tokyo_spring_api.dende_eventos.model.dto.response;


import tokyo_spring_api.dende_eventos.model.enums.Sexo;
import java.time.LocalDate;

public record PerfilComumResponseDTO(
        String email,
        String nome,
        LocalDate dataNascimento,
        String idade,
        Sexo sexo
) {}


