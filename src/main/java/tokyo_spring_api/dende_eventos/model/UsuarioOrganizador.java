package tokyo_spring_api.dende_eventos.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import tokyo_spring_api.dende_eventos.model.dto.AlterarPerfilOrganizadorDTO;
import tokyo_spring_api.dende_eventos.model.enums.Sexo;
import tokyo_spring_api.dende_eventos.model.enums.StatusEvento;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
@Entity
@DiscriminatorValue("ORGANIZADOR")
public class UsuarioOrganizador extends Usuario {

    @Getter
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "empresa_cnpj")
    private Empresa empresa;

    @OneToMany(mappedBy = "usuarioOrganizador")
    private final List<Evento> eventos = new ArrayList<>();


    public UsuarioOrganizador(String email) {
        super();
        this.setEmail(email);
    }

    public UsuarioOrganizador(String nome, LocalDate dataNascimento, Sexo sexo, String email, String senha, Empresa empresa) {
        super(nome, dataNascimento, sexo, email, senha);
        this.empresa = empresa;
    }


    public void alterarPerfil(AlterarPerfilOrganizadorDTO dto) {
        if (dto.nome() != null) setNome(dto.nome());
        if (dto.dataNascimento() != null) setDataNascimento(dto.dataNascimento());
        if (dto.sexo() != null) setSexo(dto.sexo());
        if (dto.senha() != null) setSenha(dto.senha());
        if (dto.cnpj() != null || dto.razaoSocial() != null || dto.nomeFantasia() != null)
            this.empresa = new Empresa(dto.cnpj(), dto.razaoSocial(), dto.nomeFantasia());
    }

    @Override
    public void desativarUsuario() {
        if (temEventosAtivos())
            throw new IllegalStateException("Não é possível desativar a conta com eventos ativos.");
        super.desativarUsuario();
    }

    boolean temEventosAtivos() {
        return eventos.stream().anyMatch(e -> e.getStatus() == StatusEvento.ATIVO);
    }

    public void cadastrarEvento(Evento evento) {
        evento.atribuirOrganizador(this);
        this.eventos.add(evento);
    }


    public List<Evento> getEventos() {
        return Collections.unmodifiableList(eventos);
    }

    @Override
    public String visualizarPerfil() {
        String perfilBase = super.visualizarPerfil();
        if (empresa != null) {
            return perfilBase +
                    "\nEmpresa: " + empresa.getNomeFantasia() +
                    " | CNPJ: " + empresa.getCnpj() +
                    " | Razao Social: " + empresa.getRazaoSocial();
        }
        return perfilBase;
    }

    /*public void alterarEvento(long eventoId, Evento novosDados) {
        Evento evento = eventos.stream()
                .filter(e -> e.getId() == eventoId)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Evento nao encontrado para este organizador."));
        evento.alterarDados(novosDados);
    }*/

    /*public List<Evento> listarEventosOrganizador() {
        return this.eventos.stream()
                .sorted(Comparator
                        .comparing(Evento::getDataInicio)
                        .thenComparing(Evento::getNome, String.CASE_INSENSITIVE_ORDER))
                .collect(Collectors.toList());
    }*/

    @Override
    public String toString() {
        return "UsuarioOrganizador{" + visualizarPerfil() + "}";
    }
}