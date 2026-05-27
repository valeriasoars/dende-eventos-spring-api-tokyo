package tokyo_spring_api.dende_eventos.model.dto.request;

import tokyo_spring_api.dende_eventos.model.enums.Sexo;

import java.time.LocalDate;

public record CadastrarUsuarioComumRequestDto(
        String nome,
        LocalDate dataNascimento,
        Sexo sexo,
        String email,
        String senha
) {
}
