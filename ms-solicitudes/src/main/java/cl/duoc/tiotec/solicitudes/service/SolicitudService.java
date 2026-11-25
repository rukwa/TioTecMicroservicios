package cl.duoc.tiotec.solicitudes.service;

import cl.duoc.tiotec.solicitudes.exception.RecursoNoEncontradoException;
import cl.duoc.tiotec.solicitudes.model.Solicitud;
import cl.duoc.tiotec.solicitudes.repository.SolicitudRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Lógica de negocio de las solicitudes. El controlador solo recibe y responde;
 * las reglas (estado inicial, fecha, qué se puede actualizar) viven aquí.
 */
@Service
public class SolicitudService {

    public static final String ESTADO_INICIAL = "ENVIADA";

    private final SolicitudRepository repositorio;

    // Inyección de dependencias por constructor: Spring entrega el repositorio
    public SolicitudService(SolicitudRepository repositorio) {
        this.repositorio = repositorio;
    }

    /** Todas, de la más nueva a la más antigua. Si llega un estado, filtra por él. */
    public List<Solicitud> listar(String estado) {
        if (estado == null || estado.isBlank()) {
            return repositorio.findAllByOrderByFechaDesc();
        }
        return repositorio.findByEstadoOrderByFechaDesc(estado);
    }

    public Solicitud buscar(Long id) {
        return repositorio.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("No existe la solicitud " + id));
    }

    /** Una solicitud nueva siempre parte ENVIADA y con la fecha del servidor. */
    public Solicitud crear(Solicitud solicitud) {
        solicitud.setId(null);
        solicitud.setEstado(ESTADO_INICIAL);
        solicitud.setFecha(LocalDateTime.now());
        return repositorio.save(solicitud);
    }

    /** Actualiza los datos y el estado. La fecha de envío no cambia. */
    public Solicitud actualizar(Long id, Solicitud datos) {
        Solicitud actual = buscar(id);
        actual.setCategoria(datos.getCategoria());
        actual.setDispositivo(datos.getDispositivo());
        actual.setDescripcion(datos.getDescripcion());
        if (datos.getEstado() != null) {
            actual.setEstado(datos.getEstado());
        }
        return repositorio.save(actual);
    }

    public void eliminar(Long id) {
        repositorio.delete(buscar(id));
    }
}
