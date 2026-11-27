package cl.duoc.tiotec.contenidos.service;

import cl.duoc.tiotec.contenidos.exception.RecursoNoEncontradoException;
import cl.duoc.tiotec.contenidos.model.Contenido;
import cl.duoc.tiotec.contenidos.repository.ContenidoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/** Lógica de negocio de los contenidos educativos. */
@Service
public class ContenidoService {

    private final ContenidoRepository repositorio;

    public ContenidoService(ContenidoRepository repositorio) {
        this.repositorio = repositorio;
    }

    /** Todos los contenidos, o solo los de un área si llega ?area=RESPALDOS. */
    public List<Contenido> listar(String area) {
        if (area == null || area.isBlank()) {
            return repositorio.findAllByOrderByIdAsc();
        }
        return repositorio.findByAreaOrderByIdAsc(area.toUpperCase());
    }

    public Contenido buscar(Long id) {
        return repositorio.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("No existe el contenido " + id));
    }

    public Contenido crear(Contenido contenido) {
        contenido.setId(null); // el id lo genera MySQL
        return repositorio.save(contenido);
    }

    public Contenido actualizar(Long id, Contenido datos) {
        Contenido actual = buscar(id);
        actual.setArea(datos.getArea());
        actual.setTitulo(datos.getTitulo());
        actual.setDetalle(datos.getDetalle());
        return repositorio.save(actual);
    }

    public void eliminar(Long id) {
        repositorio.delete(buscar(id));
    }
}
