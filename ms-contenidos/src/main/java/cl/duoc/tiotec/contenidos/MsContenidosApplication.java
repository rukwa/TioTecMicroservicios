package cl.duoc.tiotec.contenidos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/** Punto de entrada del microservicio de contenidos educativos (puerto 8081). */
@SpringBootApplication
public class MsContenidosApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsContenidosApplication.class, args);
    }
}
