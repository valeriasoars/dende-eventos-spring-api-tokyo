package tokyo_spring_api.dende_eventos.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tokyo_spring_api.dende_eventos.model.Empresa;

@Repository
public interface EmpresaRepository extends JpaRepository<Empresa, String> {
}
