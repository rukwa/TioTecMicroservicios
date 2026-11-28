package cl.duoc.tiotec.contenidos.service;

import cl.duoc.tiotec.contenidos.exception.RecursoNoEncontradoException;
import cl.duoc.tiotec.contenidos.model.Contenido;
import cl.duoc.tiotec.contenidos.repository.ContenidoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/** Prueba la lógica del servicio con el repositorio simulado (Mockito): no necesita MySQL. */
@ExtendWith(MockitoExtension.class)
class ContenidoServiceTest {

    @Mock
    ContenidoRepository repositorio;

    @InjectMocks
    ContenidoService servicio;

    @Test
    void listar_conArea_filtraPorEsaArea() {
        List<Contenido> respaldos = List.of(new Contenido("RESPALDOS", "Copia", "Activa la copia automática."));
        when(repositorio.findByAreaOrderByIdAsc("RESPALDOS")).thenReturn(respaldos);

        assertEquals(respaldos, servicio.listar("respaldos"));
    }

    @Test
    void crear_ignoraElIdQueManda() {
        when(repositorio.save(any(Contenido.class))).thenAnswer(invocacion -> invocacion.getArgument(0));
        Contenido nuevo = new Contenido("RESPALDOS", "Copia", "Activa la copia automática.");
        nuevo.setId(50L);

        assertNull(servicio.crear(nuevo).getId());
    }

    @Test
    void buscar_idInexistente_lanzaExcepcion() {
        when(repositorio.findById(99L)).thenReturn(Optional.empty());

        assertThrows(RecursoNoEncontradoException.class, () -> servicio.buscar(99L));
    }

    @Test
    void eliminar_borraElContenidoEncontrado() {
        Contenido existente = new Contenido("RESPALDOS", "Copia", "Activa la copia automática.");
        when(repositorio.findById(1L)).thenReturn(Optional.of(existente));

        servicio.eliminar(1L);

        verify(repositorio).delete(existente);
    }
}
