package tokyo_spring_api.dende_eventos.model.dto;

import tokyo_spring_api.dende_eventos.model.enums.Sexo;

import java.time.LocalDate;

public record AlterarPerfilComumDTO(
        String nome,
        LocalDate dataNascimento,
        Sexo sexo,
        String senha
) {}
