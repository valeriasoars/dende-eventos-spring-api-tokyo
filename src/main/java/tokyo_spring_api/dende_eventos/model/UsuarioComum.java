package tokyo_spring_api.dende_eventos.model;

import lombok.NoArgsConstructor;
import tokyo_spring_api.dende_eventos.model.dto.AlterarPerfilComumDTO;
import tokyo_spring_api.dende_eventos.model.enums.Sexo;
import tokyo_spring_api.dende_eventos.model.enums.StatusIngresso;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
@Entity
@DiscriminatorValue("COMUM")
public class UsuarioComum extends Usuario {

    public UsuarioComum(String nome, LocalDate dataNascimento, Sexo sexo, String email, String senha) {
        super(nome, dataNascimento, sexo, email, senha);
    }

    public void alterarPerfil(AlterarPerfilComumDTO dto) {
        if (dto.nome() != null) setNome(dto.nome());
        if (dto.dataNascimento() != null) setDataNascimento(dto.dataNascimento());
        if (dto.sexo() != null) setSexo(dto.sexo());
        if (dto.senha() != null) setSenha(dto.senha());
    }

    /*public List<Ingresso> solicitarIngresso(Evento evento) {
        return evento.processarCompraIngresso(this);
    }*/

    /*public List<Ingresso> listarIngressos(List<Ingresso> todos) {
        return todos.stream()
                .sorted(Comparator
                        .comparingInt((Ingresso ingresso) -> {
                            boolean ingressoAtivo = ingresso.getStatus() == StatusIngresso.ATIVO;
                            boolean eventoAtivo = ingresso.getEvento().estaAtivo();
                            return (ingressoAtivo && eventoAtivo) ? 0 : 1;
                        })
                        .thenComparing(ingresso -> ingresso.getEvento().getDataInicio())
                        .thenComparing(ingresso -> ingresso.getEvento().getNome())
                )
                .collect(Collectors.toList());
    }*/

    @Override
    public String toString() {
        return "UsuarioComum{" + super.toString() + "}";
    }
}