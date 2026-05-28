package tokyo_spring_api.dende_eventos.mappers;

import tokyo_spring_api.dende_eventos.model.Evento;
import tokyo_spring_api.dende_eventos.model.UsuarioOrganizador;
import tokyo_spring_api.dende_eventos.model.dto.request.CadastrarUsuarioOrganizadorRequestDto;
import tokyo_spring_api.dende_eventos.model.dto.response.EventoOrganizadorResponseDTO;
import tokyo_spring_api.dende_eventos.model.dto.response.PerfilOrganizadorResponseDTO;

public class UsuarioOrganizadorMapper {
    public static UsuarioOrganizador toModel(CadastrarUsuarioOrganizadorRequestDto dto) {
        return new UsuarioOrganizador(
                dto.nome(),
                dto.dataNascimento(),
                dto.sexo(),
                dto.email(),
                dto.senha(),
                dto.empresa()
        );
    }

    public static PerfilOrganizadorResponseDTO toResponse(UsuarioOrganizador organizador) {
        String nomeFantasia = null;
        String cnpj = null;
        String razaoSocial = null;

        if (organizador.getEmpresa() != null) {
            nomeFantasia = organizador.getEmpresa().getNomeFantasia();
            cnpj = organizador.getEmpresa().getCnpj();
            razaoSocial = organizador.getEmpresa().getRazaoSocial();
        }

        return new PerfilOrganizadorResponseDTO(
                organizador.getEmail(),
                organizador.getNome(),
                organizador.getDataNascimento(),
                organizador.calcularIdade(),
                organizador.getSexo(),
                nomeFantasia,
                cnpj,
                razaoSocial
        );
    }

    public static EventoOrganizadorResponseDTO toListarEventoOrganizadorDTO(Evento evento) {
        return new EventoOrganizadorResponseDTO(
                evento.getNome(),
                evento.getDataInicio(),
                evento.getDataFinal(),
                evento.getPrecoIngresso(),
                evento.getCapacidadeMaxima(),
                evento.getLocalAcesso()
        );
    }
}