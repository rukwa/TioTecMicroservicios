package cl.duoc.tiotec.solicitudes.repository;

import cl.duoc.tiotec.solicitudes.model.Solicitud;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Acceso a la tabla "solicitudes".
 * JpaRepository ya trae save, findById, findAll, delete, count...
 * Los métodos de abajo los arma Spring Data a partir de su nombre (consultas derivadas).
 */
public interface SolicitudRepository extends JpaRepository<Solicitud, Long> {

    List<Solicitud> findAllByOrderByFechaDesc();

    List<Solicitud> findByEstadoOrderByFechaDesc(String estado);
}
