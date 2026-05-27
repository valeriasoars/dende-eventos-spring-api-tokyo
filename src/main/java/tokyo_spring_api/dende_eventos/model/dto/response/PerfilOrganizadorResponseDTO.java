package tokyo_spring_api.dende_eventos.model.dto.response;


import tokyo_spring_api.dende_eventos.model.enums.Sexo;
import java.time.LocalDate;

public record PerfilOrganizadorResponseDTO(
        String email,
        String nome,
        LocalDate dataNascimento,
        String idade,
        Sexo sexo,
        String nomeEmpresa,
        String cnpj,
        String razaoSocial
) {}


