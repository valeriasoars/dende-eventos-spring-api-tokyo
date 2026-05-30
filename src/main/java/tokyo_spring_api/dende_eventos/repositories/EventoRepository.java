package tokyo_spring_api.dende_eventos.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tokyo_spring_api.dende_eventos.model.Evento;
import tokyo_spring_api.dende_eventos.model.enums.ModalidadeEvento;
import tokyo_spring_api.dende_eventos.model.enums.StatusEvento;
import tokyo_spring_api.dende_eventos.model.enums.TipoEvento;

import java.util.List;

@Repository
public interface EventoRepository extends JpaRepository<Evento, Long> {

    @Query("""
            SELECT e FROM Evento e
            WHERE e.usuarioOrganizador.email = :email
            ORDER BY e.dataInicio ASC, LOWER(e.nome) ASC
            """)
    List<Evento> findByUsuarioOrganizadorEmail(String email);

    List<Evento> findByStatus(StatusEvento status);

    List<Evento> findByEventoPrincipalId(Long eventoPrincipalId);

    @Query("""
            SELECT e FROM Evento e
            WHERE e.status = 'ATIVO'
            AND e.dataFinal > CURRENT_TIMESTAMP
            AND (e.capacidadeMaxima IS NULL OR
                 (SELECT COUNT(i) FROM Ingresso i WHERE i.evento = e AND i.status = 'ATIVO') < e.capacidadeMaxima)
            AND (:tipo IS NULL OR e.tipo = :tipo)
            AND (:modalidade IS NULL OR e.modalidade = :modalidade)
            AND (:nome IS NULL OR LOWER(e.nome) LIKE LOWER(CONCAT('%', :nome, '%')))
            ORDER BY e.dataInicio ASC
            """)
    List<Evento> findFeedPublico( @Param("tipo") TipoEvento tipo,
                                  @Param("modalidade") ModalidadeEvento modalidade,
                                  @Param("nome") String nome);
}
