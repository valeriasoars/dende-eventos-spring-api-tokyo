package tokyo_spring_api.dende_eventos.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tokyo_spring_api.dende_eventos.model.Ingresso;
import tokyo_spring_api.dende_eventos.model.enums.StatusIngresso;

import java.util.List;

@Repository
public interface IngressoRepository extends JpaRepository<Ingresso, Long> {

    @Query("""
            SELECT i FROM Ingresso i
            JOIN i.evento e
            WHERE i.usuario.email = :email
            ORDER BY
                CASE WHEN i.status = 'ATIVO' AND e.status = 'ATIVO' THEN 0 ELSE 1 END ASC,
                e.dataInicio ASC,
                e.nome ASC
            """)
    List<Ingresso> findByUsuarioEmail(@Param("email") String email);

    List<Ingresso> findByEventoId(Long eventoId);

    int countByEventoIdAndStatus(Long eventoId, StatusIngresso status);

    @Query("SELECT COUNT(i) FROM Ingresso i WHERE i.evento.id = :eventoId AND i.status = 'ATIVO'")
    int countAtivosByEvento(Long eventoId);
}
