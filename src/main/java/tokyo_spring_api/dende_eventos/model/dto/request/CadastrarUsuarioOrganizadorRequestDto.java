package tokyo_spring_api.dende_eventos.model.dto.request;

import tokyo_spring_api.dende_eventos.model.Empresa; // <-- IMPORT QUE FALTAVA!
import tokyo_spring_api.dende_eventos.model.enums.Sexo;
import java.time.LocalDate;

public record CadastrarUsuarioOrganizadorRequestDto(
        String nome,
        LocalDate dataNascimento,
        Sexo sexo,
        String email,
        String senha,
        Empresa empresa
) {
}
