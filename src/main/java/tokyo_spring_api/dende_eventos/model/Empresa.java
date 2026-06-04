package tokyo_spring_api.dende_eventos.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "empresas")
public class Empresa {

    @Id
    @Column(nullable = false, unique = true, length = 18)
    private String cnpj;

    @Column(nullable = false)
    private String razaoSocial;

    @Column(nullable = false)
    private String nomeFantasia;

    public Empresa(String cnpj, String razaoSocial, String nomeFantasia) {
        if (cnpj == null || cnpj.isBlank())
            throw new IllegalArgumentException("CNPJ não pode ser nulo ou vazio.");
        if (razaoSocial == null || razaoSocial.isBlank())
            throw new IllegalArgumentException("Razão social não pode ser nula ou vazia.");
        if (nomeFantasia == null || nomeFantasia.isBlank())
            throw new IllegalArgumentException("Nome fantasia não pode ser nulo ou vazio.");
        this.cnpj = cnpj;
        this.razaoSocial = razaoSocial;
        this.nomeFantasia = nomeFantasia;
    }
}