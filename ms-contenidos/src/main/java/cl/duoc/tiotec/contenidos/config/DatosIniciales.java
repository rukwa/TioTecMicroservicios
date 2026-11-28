package cl.duoc.tiotec.contenidos.config;

import cl.duoc.tiotec.contenidos.model.Contenido;
import cl.duoc.tiotec.contenidos.repository.ContenidoRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/** La primera vez carga los 12 consejos que antes estaban fijos en la app (3 por área). */
@Configuration
public class DatosIniciales {

    @Bean
    CommandLineRunner cargarContenidos(ContenidoRepository repositorio) {
        return args -> {
            if (repositorio.count() > 0) return;
            repositorio.saveAll(List.of(
                    new Contenido("RESPALDOS", "Copia automática",
                            "Activa la copia automática en la nube para tus fotos y contactos."),
                    new Contenido("RESPALDOS", "Revisa tu respaldo",
                            "Revisa una vez al mes que la última copia sea reciente."),
                    new Contenido("RESPALDOS", "Dos lugares",
                            "Guarda los documentos importantes en dos lugares distintos."),
                    new Contenido("ACTUALIZACIONES", "Sistema al día",
                            "Instala las actualizaciones del sistema apenas aparezcan: corrigen fallas de seguridad."),
                    new Contenido("ACTUALIZACIONES", "Apps automáticas",
                            "Activa la actualización automática de aplicaciones."),
                    new Contenido("ACTUALIZACIONES", "Tienda oficial",
                            "Descarga aplicaciones solo desde la tienda oficial."),
                    new Contenido("AUTENTICACION", "Una clave por cuenta",
                            "Usa una contraseña distinta para cada cuenta importante."),
                    new Contenido("AUTENTICACION", "Verificación en dos pasos",
                            "Activa la verificación en dos pasos en tu correo principal."),
                    new Contenido("AUTENTICACION", "Bloqueo de pantalla",
                            "Bloquea tu teléfono con PIN, patrón o huella."),
                    new Contenido("RECUPERACION", "Teléfono de recuperación",
                            "Mantén actualizado el teléfono de recuperación de tu correo."),
                    new Contenido("RECUPERACION", "Correo alternativo",
                            "Registra un correo alternativo en tus cuentas principales."),
                    new Contenido("RECUPERACION", "Códigos de respaldo",
                            "Guarda los códigos de respaldo en un lugar seguro fuera del teléfono.")
            ));
        };
    }
}
