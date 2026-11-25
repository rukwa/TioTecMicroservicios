package cl.duoc.tiotec.solicitudes.config;

import cl.duoc.tiotec.solicitudes.model.Solicitud;
import cl.duoc.tiotec.solicitudes.repository.SolicitudRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/** Carga dos solicitudes ficticias la primera vez, para que la app no parta vacía. */
@Configuration
public class DatosIniciales {

    @Bean
    CommandLineRunner cargarSolicitudes(SolicitudRepository repositorio) {
        return args -> {
            if (repositorio.count() > 0) return;
            repositorio.saveAll(List.of(
                    new Solicitud("Respaldos", "Teléfono de prueba 1",
                            "No sé si mis fotos se están respaldando en la nube.", "EN_REVISION"),
                    new Solicitud("Contraseñas y acceso", "Otro dispositivo",
                            "Quiero activar la verificación en dos pasos en mi correo.", "RESUELTA")
            ));
        };
    }
}
