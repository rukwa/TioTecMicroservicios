package cl.duoc.tiotec.contenidos.repository;

import cl.duoc.tiotec.contenidos.model.Contenido;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/** Acceso a la tabla "contenidos". Las consultas se arman a partir del nombre del método. */
public interface ContenidoRepository extends JpaRepository<Contenido, Long> {

    List<Contenido> findAllByOrderByIdAsc();

    List<Contenido> findByAreaOrderByIdAsc(String area);
}
