package cl.duoc.tiotec.contenidos.controller;

import cl.duoc.tiotec.contenidos.model.Contenido;
import cl.duoc.tiotec.contenidos.service.ContenidoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Endpoints REST de los contenidos de "Aprende".
 *
 * GET    /api/contenidos             lista (opcional ?area=RESPALDOS)
 * GET    /api/contenidos/{id}        un contenido
 * POST   /api/contenidos             crea   → 201 Created
 * PUT    /api/contenidos/{id}        actualiza
 * DELETE /api/contenidos/{id}        elimina → 204 No Content
 */
@RestController
@RequestMapping("/api/contenidos")
public class ContenidoController {

    private final ContenidoService servicio;

    public ContenidoController(ContenidoService servicio) {
        this.servicio = servicio;
    }

    @GetMapping
    public List<Contenido> listar(@RequestParam(required = false) String area) {
        return servicio.listar(area);
    }

    @GetMapping("/{id}")
    public Contenido buscar(@PathVariable Long id) {
        return servicio.buscar(id);
    }

    @PostMapping
    public ResponseEntity<Contenido> crear(@Valid @RequestBody Contenido contenido) {
        return ResponseEntity.status(HttpStatus.CREATED).body(servicio.crear(contenido));
    }

    @PutMapping("/{id}")
    public Contenido actualizar(@PathVariable Long id, @Valid @RequestBody Contenido contenido) {
        return servicio.actualizar(id, contenido);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        servicio.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
