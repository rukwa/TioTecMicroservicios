package cl.duoc.tiotec.solicitudes.exception;

/** Se lanza cuando se pide un id que no existe. El manejador global la convierte en un 404. */
public class RecursoNoEncontradoException extends RuntimeException {

    public RecursoNoEncontradoException(String mensaje) {
        super(mensaje);
    }
}
