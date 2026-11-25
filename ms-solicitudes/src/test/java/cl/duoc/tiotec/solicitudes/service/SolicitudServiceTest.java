package cl.duoc.tiotec.solicitudes.service;

import cl.duoc.tiotec.solicitudes.exception.RecursoNoEncontradoException;
import cl.duoc.tiotec.solicitudes.model.Solicitud;
import cl.duoc.tiotec.solicitudes.repository.SolicitudRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/** Prueba la lógica del servicio con el repositorio simulado (Mockito): no necesita MySQL. */
@ExtendWith(MockitoExtension.class)
class SolicitudServiceTest {

    @Mock
    SolicitudRepository repositorio;

    @InjectMocks
    SolicitudService servicio;

    @Test
    void crear_siempreParteEnviadaYConFecha() {
        when(repositorio.save(any(Solicitud.class))).thenAnswer(invocacion -> invocacion.getArgument(0));
        Solicitud nueva = new Solicitud("Respaldos", "Tablet", "Mi tablet no respalda las fotos hace un mes.", "RESUELTA");

        Solicitud guardada = servicio.crear(nueva);

        assertEquals("ENVIADA", guardada.getEstado());
        assertNotNull(guardada.getFecha());
    }

    @Test
    void buscar_idInexistente_lanzaExcepcion() {
        when(repositorio.findById(99L)).thenReturn(Optional.empty());

        assertThrows(RecursoNoEncontradoException.class, () -> servicio.buscar(99L));
    }

    @Test
    void actualizar_cambiaElEstado() {
        Solicitud existente = new Solicitud("Respaldos", "Tablet", "Mi tablet no respalda las fotos hace un mes.", "ENVIADA");
        when(repositorio.findById(1L)).thenReturn(Optional.of(existente));
        when(repositorio.save(any(Solicitud.class))).thenAnswer(invocacion -> invocacion.getArgument(0));
        Solicitud cambios = new Solicitud("Respaldos", "Tablet", "Mi tablet no respalda las fotos hace un mes.", "RESUELTA");

        Solicitud resultado = servicio.actualizar(1L, cambios);

        assertEquals("RESUELTA", resultado.getEstado());
    }

    @Test
    void eliminar_borraLaSolicitudEncontrada() {
        Solicitud existente = new Solicitud("Respaldos", "Tablet", "Mi tablet no respalda las fotos hace un mes.", "ENVIADA");
        when(repositorio.findById(1L)).thenReturn(Optional.of(existente));

        servicio.eliminar(1L);

        verify(repositorio).delete(existente);
    }
}
